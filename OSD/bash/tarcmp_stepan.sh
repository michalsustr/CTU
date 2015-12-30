diff <(tar ztf "/home/michal/Code/APO2.tar.gz" | sort) <(tar ztf "/home/michal/Code/APO.tar.gz" | sort) | grep '^[<,>]'
