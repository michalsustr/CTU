#!/bin/bash

# Validates PDDL

# USAGE: ./val.sh <numberOfProblemInstance>

# init
scenario=$1
#scenario="p01"

# validation
./validate -v ./domain/domain.pddl ./problems/${scenario}.pddl

