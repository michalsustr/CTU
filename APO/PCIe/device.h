/*
 * File:   device.h
 * Author: michal
 *
 * Created on May 1, 2012, 3:13 PM
 */

#ifndef DEVICE_H
#define	DEVICE_H

int get_device(int device);
void enable_device(char *bb, char *ddf);
void wait();
void map_memory(uint bar0);
extern void turn_on();
extern void turn_off();
extern void write_data(int addr, int data);
extern int read_data(int addr);
extern void write_led(int data);extern void show_led_parade();
extern void hide_leds();
extern void initialize_lcd();
extern void write_lcd(int line, int position, char c);
extern void write_lcd_line(char *string, int line);
extern void stop_sound();
extern void start_sound();
extern int read_keyboard();

#endif	/* DEVICE_H */

