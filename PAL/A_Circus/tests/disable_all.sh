#/bin/sh

for f in *.in; do mv "$f" `echo "$f" | rev | cut -c 2- | rev`; done

