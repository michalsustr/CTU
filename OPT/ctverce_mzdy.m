data = load('mzdy.txt', '-ascii');
rok = data(:,1);
mzdy = data(:,2);

A = [ones(size(rok,1),1), rok];
b = mzdy;
x = pinv(A)*b;
x0 = x(1);
x1 = x(2);

% aproximovana funkcia
y = x0+x1*rok;

hold on;
xlabel('Rok');
ylabel('Mzda');
plot(rok, mzdy, 'r');
plot(rok, y, 'o');
hold off;

% odhad
druhykvartal=x0+x1*2009.5 % vychadza 23,525 co je oproti skutocnej nasej
                          % hodnote  23,664 rozdiel 139,-

% hodnota kriterialni funkce
f = 0;
i = 1;
for i = 1:size(rok,1) % iteruj cez pocet riadkov matice rok
    f = f+(x0+x1*rok(i,:)-mzdy(i,:))^2;
end;

% vypise jej hodnotu 
f

% tiez sa da kratsie zapisat ako
%f=sum((y-mzdy).^2)

% Graficka interpretacia hodnoty kriterialni funkce: jedna sa o sucet
% stvorcov rozdielov medzi prekladanou priamkou a skutocnymi hodnotami