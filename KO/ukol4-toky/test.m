n1=3;
n2=5;
nn=n1+n2;
I=[1 0 0
   1 1 0
   1 0 1];

diamond = zeros(n2);
nr = n1;
idx=[];
cDiamond = zeros(n2);
uDiamond = zeros(n2);
for i=1:nr
    
    diamond(i:(nr+i-1), i:(nr+i-1)) = diamond(i:(nr+i-1), i:(nr+i-1)) + flipud(eye(nr));
    newidx = setdiff(find(diamond == 1), idx);
    idx=[idx; newidx];

    uDiamond(newidx) = 1;
    cDiamond(newidx) = 1-I(end:-1:1 ,i);
end

c(1:n1, (n1+1):nn) = cDiamond;
u(1:n1, (n1+1):nn) = uDiamond;