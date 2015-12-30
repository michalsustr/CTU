#/bin/sh

for f in *.i; do mv "$f" "$(echo $f)n"; done
