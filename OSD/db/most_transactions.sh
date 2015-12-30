#!/bin/sh

# Return the user with the greatest number of transactions

psql -h localhost -U postgres -d osd -A -t -F " - " -c "SELECT jmeno || ' ' || prijmeni, COUNT(transakce) cnt FROM ucty LEFT JOIN transakce USING(cislo_uctu) group by jmeno, prijmeni ORDER BY cnt DESC LIMIT 1"

