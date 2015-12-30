data = load('teplota.txt', '-ascii');
den = data(:,1);
teplota = data(:,2);

% frekvencia pre sin a cos
freqv = 2*pi/365;

% matica A je ma 4 stplce pre 4 premenne, kazdy stlpec niecomu odpoveda
% nakolko modelujeme funkciu x0+x1*t+x2*sin(w*t)+x3*cos(w*t)
% tak prvy stlpec musia byt same jednicky, druhy hodnoty t, 
% treti sin(w*t) a stvrty cos(w*t)
A = [ones(size(den,1),1), den, sin(freqv*den), cos(freqv*den)];
b = teplota;
x = pinv(A)*b;
x0 = x(1);
x1 = x(2);
x2 = x(3);
x3 = x(4);

% aproximovana funkcia
t = den;
% ak chceme peknu sinusovku, tak musime zadat viac bodov
%t = min(den):1:max(den);
y = x0+x1*t+x2*sin(freqv*t)+x3*cos(freqv*t);

hold on;
xlabel('Den');
ylabel('Teplota');
plot(den, teplota, 'r');
plot(t, y);
hold off;

% hodnota kriterialni funkce
F=sum((y-mzdy).^2)
% Graficka interpretacia hodnoty kriterialni funkce: jedna sa o sucet
% stvorcov rozdielov medzi prekladanou sinusovkou a skutocnymi hodnotami

% Preco sa da Asin(wt+B) zapisat ako Csin(wt)+Dcos(wt) ?
% Zo vzorca sin(x+y) = sin(x)cos(y)+cos(x)sin(y) (ktory sa da odvodit zo
% vztahu CIS) si mozeme upravit pre nase potreby ako
%   A*sin(wt+B) = Asin(wt)cos(B)+Acos(wt)sin(B)
% Nakolko posuv B je konstantne cislo, tak si mozeme oznacit 
%   Acos(B) = C
%   Asin(B) = D
% a po dosadeni dostaneme nas hladany vztah
