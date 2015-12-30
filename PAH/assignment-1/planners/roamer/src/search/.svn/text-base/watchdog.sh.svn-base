#!/usr/bin/env bash
# This script is a proxy to search. There are many 
# unpredictablities in search that might make it to exit 
# abnormally (but not every time). So we try to control 
# the behavior of the actual search here. 
# It is a pure engineering hack, don't spend time here.
ulimit -t 1800 
STATUS=-1
COUNTER=0
while [[ $STATUS -ne 0 && $COUNTER -lt 10 ]] ; do
  echo "Issue " $*
  $*
  let STATUS=$?
  let COUNTER=COUNTER+1
done
