#!/bin/sh

#load all sql commands from a file
psql -h localhost -U postgres -d osd -c "COPY ucty FROM STDIN WITH DELIMITER AS ','"

