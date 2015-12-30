function [a] = QRminimalizace(M,b)
[Q,R] = qr(M,0);
% mala by platit rovnost Ra = Q'b
% R je horni trojuhelnikova matice, vektor a riesime odspodu

rightside = Q'*b;

for i=size(R,1):-1:1
    a(i) = rightside(i)/R(i,i);
    % vynulujeme riadky v tomto istom stlpci
    for j=i:-1:1
        rightside(j) = rightside(j) - a(i)*R(j,i);
    end
end

a = a'; % obratime na stlpec, nie riadok