#!/bin/sh

# Get the sum of all user account remainders

psql -h localhost -U postgres -d osd -A -t -c "select coalesce(sum(transakce), 0) + coalesce((select sum(pocatecni_zustatek) from ucty where jmeno='$1' and prijmeni = '$2'), 0) from ucty left join transakce using(cislo_uctu) where jmeno='$1' and prijmeni = '$2'"



