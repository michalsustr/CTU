% nacita maticu A
load data_A.mat;

% vypocitame tazisko
Tx = mean(A(:,1));
Ty = mean(A(:,2));

% posunieme do pociatku
X = A(:,1)-Tx;
Y = A(:,2)-Ty;


[U,S2,V] = svd([X Y],0);

% spravime priemet do podprostoru
S1 = S2;
S1(2,2) = 0;


B = U*S1*V';

% posunieme naspat
B(:,1) = B(:,1)+Tx;
B(:,2) = B(:,2)+Ty;

% nakreslime vsetky body
hold on;
plot(A(:,1), A(:,2), 'o');
plot(B(:,1), B(:,2), 'ro');


% vypocitame sucet stvorcov kolmych vdalenosti
soucet = 0;
for(i=1:size(A,1)) 
   soucet = soucet + sqrt((A(i,1)-B(i,1))^2+(A(i,2)-B(i,2))^2);
end
disp(soucet);

% vezmeme si prvy a posledny bod Z0 a Z1
Z0 = B(1,:);
Z1 = B(end,:);

% posledne zadanie v cviceni:

%% klasicky sposob
m = (Z1(2)-Z0(2))/(Z1(1)-Z0(1));
p = Z0(2)-m*Z0(1);
% vykreslime si body
for(i=1:size(B,1)) 
    P1(i) = p+m*B(i,1);
end
plot(B(:,1), P1, 'yo'); % mali by sa dokonale prekryvat so zdrojovymi datami

% prvy sposob
x = [ Z0(2)-Z1(2), Z1(1)-Z0(1) ];
% spravime z neho jednotkovy
x = x/norm(x);
% (tiez by sme ale mohli zobrat 2. riadok v matici V: x=V(2,:); ) 
% dopocitame alfu
alfa = x(1)*Z0(1)+x(2)*Z0(2);
% vykreslime si body
for(i=1:size(B,1)) 
    P2(i) = (alfa-x(1)*B(i,1))/x(2);
end
plot(B(:,1), P2, 'go'); % zase by sa mali prekryvat

% druhy sposob
% hladame priamku ktora prechadza cez (0,0) a ma smernicu ktora je kolma k
%   tej povodnej
% plati ze y=(-1/m)*x (p=0 v tomto pripade)
% a teda (-1/m)x = mx+p
% a z toho jedna suradnica je x=-pm/(m^2+1) = y0(1)
% druhu suradnicu dopocitame dosadenim
y0(1) = -p*m/(m^2+1);
y0(2) = m*y0(1)+p;
% teraz hladame vektor s
s = V(:,1);
s = s/norm(s);
% zase si kvoli skuske vykreslime body
for(t=0:1:9) 
    P3(t,1) = y0(1)+t*s(1);
    P3(t,2) = y0(2)+t*s(2);
end
plot(P3(:,1), P3(:,2), 'yo'); % mali by byt na tej istej priamke
