#!/bin/bash -e

# usage function
usage()
{
echo "usage: $0 file1.tar.gz file2.tar.gz

Compares the contents of the given .tar.gz archives 
and outputs differences in files" 1>&2

}

# task number
if [[ "$1" == "-v" ]]; then
    echo "2"
    exit 0;
fi

# Argument parsing
if [[ -f "$1" ]]; then
    file1="$1"
else
    usage; exit 1;
fi

if [[ -f "$2" ]]; then
    file2="$2"
else
    usage; exit 1;
fi

# test if the two archives are proper
if ! gunzip -t "$1"
then
	echo "First archive is not proper" 1>&2
	exit 1;
fi
if ! gunzip -t "$2"
then
	echo "Second archive is not proper" 1>&2
	exit 1;
fi

# get file listings
list1=$(tar -tzf "$1" 2> /dev/null | sort )
list2=$(tar -tzf "$2" 2> /dev/null | sort )


IFS=$'\n'


last_dir="//"
for t1 in $list1; do
	found="0"
	if [[ "$t1" == "./" ]]; then
		continue
	fi
	
	for t2 in $list2; do
		if [[ "$t2" == "./" ]]; then
			continue
		fi
		if [[ "$t1" = "$t2" ]]; then
			found="1"
		fi
	done
	
	if [[ $found = "0" ]]; then
		if [[ "$t1" != $last_dir* ]]; then
			if [[ ${t1:${#t1}-1:1} == "/" ]]; then
				echo "$file1:${t1%?}"
			else 
				echo "$file1:$t1"
			fi
			last_dir="$t1"
		fi
	fi
done

last_dir="//"
for t2 in $list2; do
	found="0"
	if [[ "$t2" == "./" ]]; then
		continue
	fi

	for t1 in $list1; do
		if [[ "$t1" == "./" ]]; then
			continue
		fi
		if [[ "$t1" = "$t2" ]]; then
			found="1"
		fi
	done
	
	if [[ $found = "0" ]]; then
		if [[ "$t2" != $last_dir* ]]; then
			if [[ ${t2:${#t2}-1:1} == "/" ]]; then
				echo "$file2:${t2%?}"
			else 
				echo "$file2:$t2"
			fi
			last_dir="$t2"
		fi
	fi
done
