#!/bin/bash

query="SELECT jmeno, prijmeni, COUNT(transakce) AS cnt FROM ucty LEFT JOIN transakce USING(cislo_uctu) group by jmeno, prijmeni ORDER BY cnt DESC LIMIT 1"

mysql --user=root --password --database=osd << eof
$query
eof
