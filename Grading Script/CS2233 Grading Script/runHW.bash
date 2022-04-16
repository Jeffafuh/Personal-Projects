#!/bin/bash
# Use this to easily run the script~
# Must provide the input file as arg1, optional output file as arg2

if [[ $# -eq 2 ]]; then
    `awk -f formatHW.awk $1 > $2`
    exit 0
fi

if [[ $# -eq 1 ]]; then
    `awk -f formatHW.awk $1 > out.txt`
    exit 0
fi

echo "Usage: $0 inFile [outFile]"
exit 1
