#!/bin/bash

# Find a solution of a given problem instance by a planner

# USAGE: ./plan.sh <planner> <problem>

# input
domainfile="../../domain/domain.pddl"
problem=$2
problemfile="../../problems/${problem}.pddl"
planner="./plan"
plannername="$1"
plannerdir="./planners/${plannername}/"

# output
resultsdir="results/${plannername}"
mkdir -p ${resultsdir}
log="../../${resultsdir}/${problem}.log"
solution="${problem}.solution"

# start time
START=$(date +%s%N | cut -b1-13)

# run planner and record runtime
cd ${plannerdir}
${planner} ${domainfile} ${problemfile} ${solution} > ${log} 2>&1

runtime=`../../runtime.sh ${START}`
echo "-------------------------------------------------------" >> ${log}
echo "${plannername}.${problem}.runtime: ${runtime}" >> ${log}

# move solutions to the results directory
mv ${solution}* "../../${resultsdir}"

