function kostra()
    load oldStorage;
    A=oldStorage;

    G = zeros(size(A,1));
    for i=1:size(A,1)
        for j=(i+1):size(A,1)
            G(i,j) = ldist(A(i,:), A(j,:));
        end
    end

    g = graph(G);
    st = spanningtree(g);
    graphedit(st);
end

function [dist,L]=ldist(a,b)
    L1=find(a==-1)-1;
    if(length(L1) > 1) L1=L1(1); end
    if(isempty(L1)) L1 = length(a); end
    L2=find(b==-1)-1;
    if(length(L2) > 1) L2=L2(1); end
    if(isempty(L2)) L2 = length(b); end
    
    L=zeros(L1,L2);
 
    g=+1;%just constant
    m=+0;%match is cheaper, we seek to minimize
    d=+1;%not-a-match is more costly.
 
    %do BC's
    L(:,1)=([0:L1-1]*g)';
    L(1,:)=[0:L2-1]*g;
 
    m4=0;%loop invariant
    for idx=2:L1;
        for idy=2:L2
            if(a(idx-1)==b(idy-1))
                score=m;
            else
                score=d;
            end
            m1=L(idx-1,idy-1) + score;
            m2=L(idx-1,idy) + g;
            m3=L(idx,idy-1) + g;
            L(idx,idy)=min(m1,min(m2,m3));
        end
    end
 
    dist=L(L1,L2);
    return
end
