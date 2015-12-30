function I = binrec(R,C,D,A)
 
% the image sizes
nR = length(R);
nC = length(C);
nn = nR+nC;

% the final picture
I = repmat(0,nR,nC);

% different types of projections (vertical-(R)ows, horizontal-(C)olumns, (D)iagonal and (A)ntidiagonal
comb(1).sum1 = C;
comb(1).sum2 = R;
comb(1).type = 'RC';

comb(2).sum1 = C;
comb(2).sum2 = D;
comb(2).type = 'CD';

comb(3).sum1 = C;
comb(3).sum2 = A;
comb(3).type = 'CA';

comb(4).sum1 = R;
comb(4).sum2 = D;
comb(4).type = 'RD';

comb(5).sum1 = R;
comb(5).sum2 = A;
comb(5).type = 'RA';

comb(6).sum1 = D;
comb(6).sum2 = A;
comb(6).type = 'DA';

load projectionData;

maxIterations = 64;
i =1;
for it = 1:maxIterations
for oneComb = comb
    
    [c,l,u,b] = calculateBounds(I, oneComb); % = costs, lower, upper bounds, source/sink flows (b)
    
    g = graph;
    F = g.mincostflow(c,l,u,b);
    
    if(isempty(F)) 
       'No solution'
       return;
    end
    Inew = getImage(F, oneComb,nR,nC);
    
    % no change in the image, display it
    if (I == Inew)
        figure(2);
        imagesc(logical(I));
        colormap(gray);
        axis off;
        axis square;
        return;
    end
    
    % display the evolution process
    I = Inew;
    figure(1);
    plotNum = mod(i,42);
    if(plotNum == 0) plotNum = 42; end;
    subplot(7,6,plotNum);
    imagesc(logical(I));
    colormap(gray);
    axis off;
    axis square;
    pause(0.05);
    i=i+1;
end
end

end

function [c,l,u,b] = calculateBounds(I, comb) 
    sum1 = comb.sum1;
    sum2 = comb.sum2;
    type = comb.type;
    
    % sources and sinks
    n1 = length(sum1);
    n2 = length(sum2);
    nn = n1+n2;
    
    % lower-bounds are zero for all pipes
    l = zeros(nn);
    
    b = [ sum1' ; -sum2' ];
    
    c = zeros(nn);
    u = zeros(nn);
    switch(type)
        case 'RC'
            c(1:n1, (1:n2)+n1) = 1-I';
            u = [repmat(0,n1,n1), repmat(1,n1,n2);...
                 repmat(0,n2,n1), repmat(0,n2,n2)];
        case 'CD'
            for i=1:n1
                u(i, (n1+i):(2*n1+i-1)) = ones(1,n1);
                c(i, (n1+i):(2*n1+i-1)) = 1-I(end:-1:1 ,i);
            end
        case 'CA'
            for i=1:n1
                u(i, (n1+i):(2*n1+i-1)) = ones(1,n1);
                c(i, (n1+i):(2*n1+i-1)) = 1-I(1:end ,i);
            end
        case 'RD'
            for i=1:n1
                u(i, (2*n1-i+1):(3*n1-i)) = ones(1,n1);
                c(i, (2*n1-i+1):(3*n1-i)) = 1-I(i, :);
            end
        case 'RA'
            for i=1:n1
                u(i, (n1+i):(2*n1+i-1)) = ones(1,n1);
                c(i, (n1+i):(2*n1+i-1)) = 1-I(i, :);
            end
        case 'DA'
            diamond = zeros(n2);
            nr = (n1+1)/2;
            idx=[];
            cDiamond = zeros(n2);
            uDiamond = zeros(n2);
            for i=1:nr

                diamond(i:(nr+i-1), i:(nr+i-1)) = diamond(i:(nr+i-1), i:(nr+i-1)) + flipud(eye(nr));
                newidx = setdiff(find(diamond == 1), idx);
                idx=[idx; newidx];

                uDiamond(newidx) = 1;
                cDiamond(newidx) = 1-I(: ,i);
            end

            c(1:n1, (n1+1):nn) = cDiamond;
            u(1:n1, (n1+1):nn) = uDiamond;
    end;
end

function I=getImage(F, comb, nR,nC)
    sum1 = comb.sum1;
    sum2 = comb.sum2;
    type = comb.type;
    
    % sources and sinks
    n1 = length(sum1);
    n2 = length(sum2);
    nn = n1+n2;
    
    I = zeros(nR,nC);
    switch(type)
        case 'RC'
            I = F(1:n1,(n2+1):end)';
        case 'CD'
            for i=1:n1
                I(end:-1:1 ,i) = F(i, (n1+i):(2*n1+i-1));
            end
        case 'CA'
            for i=1:n1
                I(: ,i) = F(i, (n1+i):(2*n1+i-1));
            end
        case 'RD'
            for i=1:n1
                I(i, :) = F(i, (2*n1-i+1):(3*n1-i));
            end
        case 'RA'
            for i=1:n1
                I(i, :) = F(i, (n1+i):(2*n1+i-1));
            end
        case 'DA'            
            diamond = zeros(n2);
            nr = (n1+1)/2;
            idx=[];
            FF=F(1:n1, (n1+1):nn);
            for i=1:nr
                diamond(i:(nr+i-1), i:(nr+i-1)) = diamond(i:(nr+i-1), i:(nr+i-1)) + flipud(eye(nr));
                newidx = setdiff(find(diamond == 1), idx);
                idx=[idx; newidx];

                I(: ,i) = FF(newidx);
            end

    end;
end
