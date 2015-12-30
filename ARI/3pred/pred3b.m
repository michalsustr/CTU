%% nastavenia
elements = 10; % kolko poslednych vzorkov zobrat pre vypocit y_inf
u_inf = 1;    % velkost jednotkoveho skoku

%% riesenie
y = hw_3b_std(27, 04, 92);

% ustalena hodnota odozvy
y_inf = mean(y(size(y,1)-elements:end));

% hladame %OS, najdeme miesto kde je globalne maximum
[~, t_OS] = max(y);
OS = (y(t_OS)-y_inf)/y_inf*100;

% hladame hodnotu T_s kde bude odchylka od stavu v nekonecne vacsia ako 2%
% ideme odzadu
dev = 0.02*y_inf;
for i=size(y,1):-1:0
    if(abs(y(i)-y_inf) > dev)zeta
        T_s = i-1;
        break;
    end
end

% vypocitame parametre
zeta = (-log(OS/100))/(sqrt(pi^2+(log(OS/100)^2)));
omega_n = 4/(zeta*T_s);
k = y_inf/u_inf;zeta

% vykreslime identifikovany system do povodneho grafu
sys = tf([k*(omega_n^2)], [1 2*zeta*omega_n omega_n^2]);
[y2, t2]=step(sys);
hold on;
plot(t2,y2, 'r', 'LineWidth',2);

% vystupne hodnoty
disp('y_inf');disp(y_inf);
disp('OS');   disp(OS);
disp('T_s');  disp(T_s);
disp('zeta'); disp(zeta);
disp('omega_n'); disp(omega_n);
disp('k');    disp(k);
