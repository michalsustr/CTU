#!/bin/bash

# Clean up of the results

# USAGE: ./cleanup.sh

# results
rm -rf ./results/*

# tmp files
rm -f *.tmp

# planner solutions
find ./planners/ -name *solution* -exec /bin/rm -f {} \;

