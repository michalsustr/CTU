function basis = pca(data)

% data  ... NxM matice, N je pocet vzorku, M je pocet atributu
% basis ... NxM matice radky predstavuji prvnich N vlastnich vektoru, 
%           tj. bazovych vektoru hledane transformace 

[v D]=eig(data*data');
basis=v(end:-1:1,:)*data;

