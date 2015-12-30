addpath('../scheduling/');

R = [3,2,3,1];
C = [2,1,3,3];

% rozmery vysledneho obrazku...
n1 = size(R,2);
n2 = size(C,2);
nn = n1+n2;

% vysledny obrazek init...
I = zeros(n1,n2);

% pomocny vektor pro zapamatovane ceny z minule iterace...
cprev = zeros(nn);

% zdroje a spotrebice...
b = [ R' ; -C' ];

% lower-bounds na vsechny trubky je nula...
l = zeros(nn);

% upper-bounds je jedna pro trubky vedouci ze zdrojů R do spotřebičů C...
u = [repmat(0,n1,n1), repmat(1,n1,n2);...
     repmat(0,n2,n1), repmat(0,n2,n2)];

c = zeros(nn);

% create graph G
g = graph;
F = g.mincostflow(c,l,u,b);
I = F(1:n1,n2+1:end);

imagesc(logical(I));
colormap(gray);
axis off;
axis square;