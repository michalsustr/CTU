#!/bin/bash

# USAGE: ./runtime.sh <startTime>

# Returns runtime as "HH:MM:SS.SSS"

START=$1
END=$(date +%s%N | cut -b1-13)

#echo $START
#echo $END

# show time
miliseconds=$(($END-$START))

# hours
hours=$((miliseconds / 3600000))
miliseconds=$((miliseconds % 3600000))

# minutes
minutes=$((miliseconds / 60000))
miliseconds=$((miliseconds % 60000))

# seconds & miliseconds
seconds=$((miliseconds / 1000))
miliseconds=$((miliseconds % 1000))

result=`printf "%02d:%02d:%02d.%03d" ${hours} ${minutes} ${seconds} ${miliseconds}`
echo "${result}"
