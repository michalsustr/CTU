[y,Fs]=wavread('gong.wav');


% v tomto subore som ratal s tym, ze koeficient a0 tiez patri do radu 
% modelu, tj ziskame celkovo 300 koeficientov popisujucich signal, 
% takze je mozno vhodne rad_modelu % upravit na 301
rad_modelu = 300; 
T = size(y,1);

% inicializujeme maticu M
M = ones(T-rad_modelu, rad_modelu);
% prvy riadok matice M odpoveda poslednym 300 prvkom v opacnom poradi
for t=rad_modelu:T
    for i=1:rad_modelu-1
        M(t-rad_modelu+1, i+1) = y(t-i);
    end
end

% vyberieme maticu b ako podmaticu y
b = y(rad_modelu:end);

% zistime koeficienty a
a = pinv(M)*b;


% pokusime sa o opatovnu rekonstrukciu signalu
% prepocitame skomprimovanu stopu
comp = y(1:rad_modelu-1);
sub_a = a(2:end); % preskocime prvok a0
a0 = a(1);
for i=rad_modelu:T
    comp(i) = a0+sum(sub_a .* flipud(comp(i-rad_modelu+1:i-1)));
end

% povodna zvukova stopa
hold on;
plot(y, 'r');

% komprimovana zvukova stopa
plot(comp, 'b');
hold off;

% zapiseme zvuk z komprimovanej stopy
wavwrite(comp,Fs,'comp-gong.wav');

% mozeme si porovnat aky je rozdiel ak vypocitame koeficienty a pouzitim
% QR rozkladu a pseudoinverziou
z = QRminimalizace(M,b);
rozdiel = sum(z-a);