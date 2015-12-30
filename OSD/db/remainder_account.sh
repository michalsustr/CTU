#!/bin/sh

# return the remainder of amount of money for given user
psql -h localhost -U postgres -d osd -A -t -c "SELECT pocatecni_zustatek+COALESCE(SUM(transakce), 0) as money FROM ucty LEFT JOIN transakce USING(cislo_uctu) WHERE cislo_uctu='$1' GROUP BY pocatecni_zustatek"

