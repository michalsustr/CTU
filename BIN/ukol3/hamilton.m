% Index correspondence:
% ACGT
% 1234

% AGT, AAA, ACT, AAC, CTT, GTA, TTT, TAA
% 134, 111, 124, 112, 244, 341, 444, 411

S=[
    1 3 4;
    1 1 1; 
    1 2 4; 
    1 1 2; 
    2 4 4; 
    3 4 1; 
    4 4 4; 
    4 1 1;
];



N = length(S);
% Create correspondce matrix
G = zeros(N);
for i=1:N
for j=1:N
    if i == j 
        G(i,j) = inf;
        continue;
    end
    
    s1 = S(i, :);
    s2 = S(j, :);
    similarity = 0;
    for k=0:(length(s2)-1)
        if(s1(end-k) == s2(k+1)) 
            similarity = similarity+1;
        else
            break;
        end
    end
    
    G(i,j) = -similarity;
%     if(G(i,j) == 0)
%         G(i,j) = -3;
%     end
end
end

%%
g=graph(G);
g.hamiltoncircuit;
ad=g.hamiltoncircuit.adj;
str=[S(1, :)];

currentIdx=1;
while(1)
    nextIdx = find(ad(currentIdx,:) == 1)
    str=[str S(nextIdx, 1:end+G(currentIdx, nextIdx))];
    currentIdx = nextIdx;
    if(currentIdx == 1) 
        break;
    end
end
str=num2str(str);
% ACGT
% 1234
str(str=='1') = 'A';
str(str=='2') = 'C';
str(str=='3') = 'G';
str(str=='4') = 'T';
str(str==' ') = '';

% Result: AGTTAAACTTGTAACAGT