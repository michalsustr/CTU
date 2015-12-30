#!/bin/sh

bin="java -jar -Xss50m -Xms64m -Xmx1024m ../8_slovnikovy_automat/dist/8_slovnikovy_automat.jar"
diff="diff -iad"   # Diff command, or what ever

files=$(ls *.in)
for f in $files; do
    # Padd file_base with suffixes
    file="${f%.*}"
    file_in="$file.in"             # The in file
    file_out_val="$file.out"       # The out file to check against
    file_out_tst="$file.out.tst"   # The outfile from test application

    printf "Testing against %s\n" "$file_in"

    # Run application, redirect in file to app, and output to out file
    eval "$bin < $file_in > $file_out_tst"

    # Execute diff
    $diff "$file_out_tst" "$file_out_val"


    # Check exit code from previous command (ie diff)
    # We need to add this to a variable else we can't print it
    # as it will be changed by the if [
    # Iff not 0 then the files differ (at least with diff)
    e_code=$?
    if [ $e_code != 0 ]; then
            printf "TEST FAIL : %d\n" "$e_code"
    else
            printf "TEST OK!\n"
    fi
done
