%% nastavenia
elements = 5; % kolko poslednych vzorkov zobrat pre vypocit y_inf
u_inf = 5;    % velkost jednotkoveho skoku

%% riesenie
y = ty(:,2);

y_inf = mean(y(size(y,1)-elements:end));
k = y_inf/u_inf;

% prechadzame cyklom a najdeme hodnotu ktora je najblizsie 0.63*y_inf
y_T = 0.63*y_inf;
min_val = intmax('uint64');
min_index = -1;
% pre zjednodusenie prechadzame vsetky hodnoty, toto je bezpecne
for i=1:size(y,1)
   if abs(y_T-y(i)) < min_val
       min_val = abs(y_T-y(i));
       min_index = i;
   end
end
T = min_index-1;

% vykreslime identifikovany system do povodneho grafu
sys = tf([k], [T 1]);
lsim(sys, u_inf*ones(1, max(size(y))), 0:1:max(size(y))-1 );

% vystupne hodnoty
disp('k');
disp(k);
disp('T');
disp(T);