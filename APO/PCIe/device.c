#include <sys/mman.h>
#include <stdlib.h>
#include <stdio.h>
#include <unistd.h>
#include <fcntl.h>
#include <string.h>
#include <byteswap.h>
#include <inttypes.h>
#include <sys/types.h>
#include <dirent.h>

#include "kbd_hw.h"
#include "chmod_lcd.h"

#include "device.h"


#define BUS_CTRL 0x8060
#define BUS_DATA_WRITE 0x8020
#define BUS_DATA_READ 0x8040
#define BUS_LED 0x8080

#define PCI_DIR "/proc/bus/pci/"

// memory pointer
volatile unsigned char* mem;

/*
 * Return memory base address of given device
 * Search in directories and subdirectories in /proc/bus/pci
 */
int get_device(int device) {
    DIR *dirp, *subdirp;
    struct dirent *dir_entryp, *subdir_entryp;
    FILE *filep;
    char subdir_name[100], file_name[100];
    int data;
    int bar0;

    // loop all directories
    dirp = opendir(PCI_DIR);
    if (dirp == NULL) {
        fprintf(stderr, "Couldn't open the directory %s\n", PCI_DIR);
        exit(1);
    }

    while(dir_entryp = readdir (dirp)) {
        // skip "." and ".." and entries that are not directories
        if(  strcmp(dir_entryp->d_name,  ".") == 0
          || strcmp(dir_entryp->d_name, "..") == 0
          || dir_entryp->d_type != DT_DIR) {
            continue;
        }

        // loop all subdirectories
        strcpy(subdir_name, PCI_DIR);
        strcat(subdir_name, dir_entryp->d_name);
        subdirp = opendir(subdir_name);
        if (subdirp == NULL) {
            fprintf(stderr, "Couldn't open the directory %s\n", subdir_name);
            exit(1);
        }

        while(subdir_entryp = readdir (subdirp)) {
            // skip "." and ".."
            if(  strcmp(subdir_entryp->d_name,  ".") == 0
              || strcmp(subdir_entryp->d_name, "..") == 0) {
                continue;
            }

            // read the first 4 bytes of each file
            strcpy(file_name, PCI_DIR);
            strcat(file_name, dir_entryp->d_name);
            strcat(file_name, "/");
            strcat(file_name, subdir_entryp->d_name);
            filep = fopen(file_name, "r");
            if(!filep) {
                fprintf(stderr, "Couldn't open file '%s' for read", file_name);
                exit(1);
            }
            fread(&data, 1, 4, filep);

            if(data == device) {
                // read base address
                fseek(filep, 16, SEEK_SET);
                fread(&bar0, 1, 4, filep);

                enable_device(dir_entryp->d_name, subdir_entryp->d_name);

                fclose(filep);
                closedir(subdirp);
                closedir(dirp);
                return bar0;
            }

            fclose(filep);
        }
        closedir(subdirp);
    }
    closedir(dirp);

    return -1;
}

void enable_device(char *bb, char *ddf) {
    char file_name[100] = "/sys/bus/pci/devices/0000:";
    FILE *filep;
    strcat(file_name, bb);
    strcat(file_name, ":");
    strcat(file_name, ddf);
    strcat(file_name, "/enable");
    filep = fopen(file_name, "w");
    if(!filep) {
        fprintf(stderr, "Could not open file '%s'", file_name);
        exit(1);
    }
    fwrite("1", 1, 1, filep);
    fclose(filep);
}


void wait() {
    int dummy, i;
    for(i=0;i<1000;i++)
        dummy=*(mem+BUS_CTRL);
}

void map_memory(uint bar0) {
    int fd; int pagesize = getpagesize();

    fd = open("/dev/mem", O_RDWR|O_SYNC);
    if (fd < 0) {
        fprintf(stderr,"Cannot open %s\n", "/dev/mem");
        exit(1);
    }

    unsigned char* mm = mmap(NULL, 0xF000, PROT_WRITE|PROT_READ, MAP_SHARED, fd, bar0 & ~(pagesize-1));
    if (mm == MAP_FAILED) {
      fprintf(stderr,"Couldn't map memory\n");
      exit(1);
    }

    mem = mm + (bar0 & (pagesize-1));
}


void turn_on(int device) {
    uint bar0;

    // get the base address of DEVICE
    bar0 = get_device(device);

    if(bar0 == -1) {
        fprintf(stderr, "Device 0x%08x not found\n", device);
        exit(1);
    }

    printf("Device %08x found with base address %08x\n", device, bar0);

    // map memory using base address bar0 using mmap
    map_memory(bar0);

    *(mem+BUS_CTRL) = 0xFF;
}

void turn_off() {
    *(mem+BUS_CTRL) = 0x00;
}

// addr - 2bit integer
void write_data(int addr, int data) {
    // prepare data for writing
    *(mem+BUS_DATA_WRITE) = data;
    // activate WR at addr
    *(mem+BUS_CTRL) = 0xF4 | addr;

    wait();

    // activate also CS0 at addr
    *(mem+BUS_CTRL) = 0xF0 | addr;    

    wait();
    
    // deactivate CS0
    *(mem+BUS_CTRL) = 0xF4 | addr;

    wait();

    // deactivate WR
    *(mem+BUS_DATA_WRITE) = 0xFC;
}


int read_data(int addr) {
    int data;

    // activate RD at addr
    *(mem+BUS_CTRL) = 0xEC | addr;

    wait();
    // activate also CS0 at addr
    *(mem+BUS_CTRL) = 0xE8 | addr;    

    wait();
    
    data = *(mem+BUS_DATA_READ);

    // deactivate CS0
    *(mem+BUS_CTRL) = 0xEC | addr;
    wait();

    *(mem+BUS_DATA_WRITE) = 0xFC;

    return data;
}


void write_led(int data) {
    write_data(BUS_LED_WR_o, data);
}

void show_led_parade() {
    int i;
    for(i = 0; i <= 7; i++) {
        write_led(1 << i);
        sleep(1);
    }
}

void hide_leds() {
    write_led(0x00);
}

void initialize_lcd() {
    write_data(BUS_LCD_INST_o, CHMOD_LCD_MOD);
    wait();
    write_data(BUS_LCD_INST_o, CHMOD_LCD_MOD);
    wait();
    write_data(BUS_LCD_INST_o, CHMOD_LCD_CLR);
    write_data(BUS_LCD_INST_o, CHMOD_LCD_DON);
}

void write_lcd(int line, int position, char c) {
    write_data(BUS_LCD_INST_o, CHMOD_LCD_DON);
    while(read_data(BUS_LCD_STAT_o) & CHMOD_LCD_BF) { // LCD is busy
        wait();
        printf("LCD busy\n");
    }
    write_data(BUS_LCD_INST_o, CHMOD_LCD_POS+position+line*0x40);
    write_data(BUS_LCD_WDATA_o, c);
//    printf("written char %c\n", c);
}

void write_lcd_line(char *string, int line) {
    int i, fill_space = 0;
    char c;
    for(i = 0; i < 16; i++) { // maximum number of chars per line is 16
        if(string[i] == '\0') {
            fill_space = 1;
        }
        c = fill_space ? ' ' : string[i];

        write_lcd(line, i, c);
    }
}

void stop_sound() {
    write_data(BUS_KBD_WR_o, 0x00);
}

void start_sound() {
    write_data(BUS_KBD_WR_o, 0xFF);
}


/*
 Keyboard buttons:
 [ 0 ] [ 1 ] [ 2 ] [ 3 ]
 [ 4 ] [ 5 ] [ 6 ]
 [ 7 ] [ 8 ] [ 9 ]
 [ 10] [ 11] [ 12] [ 13]
 */
int read_keyboard() {
    int i;
    int key;

    for(i = 0; i < 3; i++) {
        // enable each column one by one (lower bits are active in zero)
        write_data(BUS_KBD_WR_o, 0x7F & ~(1 << i));
        //wait();
        key = read_data(BUS_KBD_RD_o);

        // a key has been selected
        if(key != 0xff) {
#if BEEP == 1
            write_data(BUS_KBD_WR_o, 0xFF);
            usleep(10000);
#endif
            switch(key) {
                case 0xfe:
                    return 2-i;
                case 0xfd:
                    return 4+2-i;
                case 0xfb:
                    return 7+2-i;
                case 0xf7:
                    return 10+2-i;
                case 0xef:
                    if(i == 1) return 3;
                    if(i == 2) return 13;
            }
        }
    }
    return -1;
}
