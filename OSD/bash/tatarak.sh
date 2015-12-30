#!/bin/bash

if [ $1 = -v ]
then
	echo "2"
	exit 1
fi

if [ $1 = -h ]
then
	echo "compares two tar archives contents"
	echo "prints out redundant files"
	echo "Usage: `basename $0` \"archive 1\" \"archive 2\""
	exit 1
fi	

if [ $# -ne 2 ]
then
	echo "Usage: `basename $0` \"archive 1\" \"archive 2\"" >&2
	exit 1
fi



if [ ! -f $1 ]
then
	echo "file $1 doesn't exist " >&2
	exit 1
fi

if [ ! -f $2 ]
then
	echo "file $2 doesn't exist " >&2
	exit 1
fi

# test if the two archives are ok
if ! gunzip -t "$1"
then
	echo "First archive is not ok" 1>&2
	exit 1;
fi
if ! gunzip -t "$2"
then
	echo "Second archive is not ok" 1>&2
	exit 1;
fi


p1="$1"
p1="${p1//\//\\/}"
p2="$2"
p2="${p2//\//\\/}"


IFS=$'\n'

list=$((diff <(tar ztf "$1" | sort) <(tar ztf "$2" | sort)) | grep '^[<,>]' | sed -e "s/^< /${p1}:/g" | sed -e "s/^> /${p2}:/g")
lastItem="//"
for line in $list; do
	if [[ "$line" == "$1:./" ]]; then
		continue
	fi
	if [[ "$line" == "$2:./" ]]; then
		continue
	fi

	if [[ "$line" != $lastItem* ]]; then
		if [[ ${line:${#line}-1:1} == "/" ]]; then
			echo "${line%?}"
		else 
			echo "$line"
		fi
		lastItem="$line"
	fi
done
