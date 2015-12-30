clearvars;

A = load('walk1.txt','-ASCII');

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

for(r=1:123)
    % spravime priemet do podprostoru podla pozadovaneho r
    S2 = S;
    for(i=(r+1):size(S,1))
        S2(i,i) = 0;
    end

    At = U*S2*V'; % A tilda

    % posunieme naspat
    for(i=1:size(A,2))
        At(:,i) = At(:,i)+T(i);
    end

    % chceme poznat hodnotu rovnice (1) v zadani
    krit = 0;
    for(i=1:size(A,1)) 
        krit = krit + norm(At(i,:)-A(i,:))^2;
    end
    % ulozime do matice
    k(r) = krit;
end

hold on;
plot(diag);
plot(krit);