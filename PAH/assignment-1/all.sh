#!/bin/bash

# Run all planners on all scenarios

# USAGE: ./all.sh

# start time
START=$(date +%s%N | cut -b1-13)

# run all planners
tmp=planners.tmp
ls planners/ > ${tmp}
count=0
total=`cat ${tmp} | wc -l`

while read planner; do 
  count=$(($count+1));
  echo "Planner ${planner} [${count}/${total}]"
  ./problems.sh ${planner}
  echo ""
done < ${tmp}
rm ${tmp}

# record time
runtime=`./runtime.sh ${START}`
echo "-- ${total} planners finished in ${runtime}"
