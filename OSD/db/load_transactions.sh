#!/bin/sh

#load all sql commands from a file
psql -h localhost -U postgres -d osd -c "COPY transakce FROM STDIN WITH DELIMITER AS ','"

