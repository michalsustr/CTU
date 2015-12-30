clearvars;

A = load('walk1.txt','-ASCII');
r = 1; % pozadovana hodnost 

%%

for(i=1:size(A,2))
    % vypocitame tazisko
    T(i) = mean(A(:,i));
    % posunieme do pociatku
    B(:,i) = A(:,i)-T(i);
end

[U,S,V] = svd(B,0);

% ulozime si diagonalne prvky
diag = diag(S);
S2 = S;
% spravime priemet do podprostoru podla pozadovaneho r
for(i=(r+1):size(S,1))
    S(i,i) = 0;
end

At = U*S*V'; % A tilda
krit = 0;
for(i=1:size(A,1)) 
    krit = krit + norm(At(i,:)-A(i,:))^2;
end

test = 0;
for(i=1:size(S,1))
    test = test+norm(S2(i,:)-S(i,:))^2;
end

% ortogonalne bazove vektory a komprimovane y
Vt = V(:,1:r);
y = U*S;

% posunieme naspat
for(i=1:size(A,2))
    At(:,i) = At(:,i)+T(i);
end

% chceme poznat hodnotu rovnice (1) v zadani
krit = 0;
for(i=1:size(A,1)) 
    krit = krit + norm(At(i,:)-A(i,:))^2;
end

test = 0;
for(i=1:size(S,1))
    test = test+norm(S2(i,:)-S(i,:))^2;
end

% nakreslenie grafu pohybu
if r==2 
    plot(y(:,1), y(:,2));
elseif r == 3 % 3D graf
    plot3(y(:,1), y(:,2), y(:,3));
end

