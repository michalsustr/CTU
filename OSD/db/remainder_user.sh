#!/bin/sh

# Return the sum of all partial remainders at account of a given user
# 
# How it works - let's take an example of transactions like:
# Change:   Partial remainder:  
#  a    50                   50=a
#  b  +100                  150=a+b
#  c  +200                  350=a+b+c
#  d  -300                   50=a+b+c+d
#  e  +250                  300=a+b+c+d+e
# ---------                --------------
# a+b+c+d+e=300            5a+4b+3c+2d+e=2300 <--- this is the value we are looking for
# 
# The algorithm: order all transactions by date, and create a column that corresponds to coefficients (row_index),
# then multiply each other out, and add initial remainder everywhere needed.

psql -h localhost -U postgres -d osd -A -t -c "SELECT SUM(transakce*row_index+pocatecni_zustatek)+pocatecni_zustatek FROM (
	SELECT transakce, row_number() OVER (ORDER BY datum DESC) AS row_index, pocatecni_zustatek FROM ucty 
	LEFT JOIN transakce using(cislo_uctu) WHERE jmeno='$1' and prijmeni='$2' ORDER BY cislo_uctu, datum DESC
) AS subquery GROUP BY pocatecni_zustatek"

