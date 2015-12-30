#!/bin/bash

# Run planner on all problem instances

# USAGE: ./problems.sh <planner>

# run all problems
planner=$1
tmp=problems.tmp
ls problems | cut -c1-3 > ${tmp}
count=0
total=`cat ${tmp} | wc -l`

while read problem; do 
  count=$(($count+1));
  echo " * ${problem} [${count}/${total}]"
  ./plan.sh ${planner} ${problem}
done < ${tmp}
rm ${tmp}
