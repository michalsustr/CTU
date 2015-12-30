#!/bin/bash

# Show results

# USAGE: ./results.sh

# runtimes
find ./results/ -name *.log -exec cat {} \; | grep ".runtime:" | sort
