#!/bin/bash

# usage function
usage()
{
cat << EOF
usage: $0 [options] [directory]

Searches for script files and displays what interpreters are used to evalute them.

OPTIONS:
   -h        Show this message
   -n        Non-recursive search
   -f name   List only files that are interpreted by "name"
   -v        Ouput the number of the homework
EOF
}

### Argument parsing

# In which order arguments can be passed
# order=1 is for script [options] [dir]
# order=2 is for script [dir] [options]
if [[ ${1:0:1} = "-" ]]; then
    ORDER=1
else
    ORDER=2
    if [ -z "$1" ]; then
        DIR="."
    else
        DIR="$1"
    fi
    shift
fi

RECURSIVE=
FILTER=
while getopts "f:hnv" OPTION
do
    case $OPTION in
        h)  usage; exit 1; ;;
        n)  RECURSIVE="-maxdepth 1"; ;;
        f)  FILTER="$OPTARG"; ;;
        v)  echo "6"; exit; ;;
        \?) exit 1; ;;
        :)  exit 1; ;;
    esac
done


if [ $ORDER = 1 ]; then
	shift $((OPTIND-1))
	if [ -z "$1" ]; then
	    DIR="."
	else
	    DIR="$1"
	fi
fi

# if there is no trailing slash, do not use recursive search
#if [[ ! ${DIR##${DIR%%?}} = '/'  && ! "$DIR" = '.'  && ! "$DIR" = '..' ]]; then
#    RECURSIVE="-maxdepth 1"
#fi


# testing for directory
if [ ! -d "$DIR" ]; then
    echo "Directory $DIR does not exist" >&2;
    exit 1;
fi

### Main code

# get list of files
find "$DIR" $RECURSIVE -type f |
while read filename; do
    # first line of each file
    firstline=$(head -n 1 "$filename")

    # does it begin with #!
    if [[ ${firstline:0:2} = "#!" ]]; then
        # get the interpreter
        interpreter=$(basename "$firstline" | cut -d' ' -f 1)
        
        # test if interpreter is the one we look for
        if [[ -z "$FILTER" || "$FILTER" = "$interpreter" ]]; then
            echo -n "$filename"
            echo -n ' '
            echo "$interpreter"
        fi
    fi
done

