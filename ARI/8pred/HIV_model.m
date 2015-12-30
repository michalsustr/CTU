%% State-Space Model
% Definovanie matíc
A = [-0.04167 0 -0.0058;0.0217 -0.24 0.0058;0 100 -2.4];
B = [5.2;-5.2;0];
C = [0 0 1];
D = [0];

sys = ss(A,B,C,D);
G = tf(sys);

% Controllability Matrix
C_o = ctrb(sys);

% Kanonicky tvar matic
A_c = [-2.68167 -0.106009 -0.012419;1 0 0;0 1 0];
B_c = [1;0;0];
C_c = [0 -520 -10.38];
D_c = 0;

sysc = ss(A_c,B_c,C_c,D_c);
G_c = tf(sysc);

%% Proporcionalne
% Tvar matice (A_c - B_c*K) - so stavovou ZV - Kanonicky tvar
syms k1 k2 k3;
K = [k1 k2 k3];
A_new = A_c - B_c*K;
A_new = vpa(A_new);

% Char. polynom systemu so ZV z kanonickych rovnic
syms s;
c = det(s*eye(3) - A_new);
c = collect(c);
c = vpa(c);

% Char. polynom systemu so ZV ktory pozadujeme
c_wnt = (s + 0.01997)*(s^2 + 0.08*s + 0.0046);
c_wnt = expand(c_wnt);
c_wnt = vpa(c_wnt);

% Vypocet
k1 = 0.09997 -  2.68167;
k2 = 0.0061976 - 0.106009;
k3 = 0.000091862 - 0.012419;
K = [k1 k2 k3];

A_new = A_c - B_c*K;
sys2 = ss(A_new,B_c,C_c,D_c);
T = tf(sys2);

%% Integracne riadenie
syms k1 k2 k3 k_i;

K = [k1 k2 k3];
K_big = [K k_i];

A_new = A_c - B_c*K;
A_big = [A_new -B_c*k_i;-C_c 0];
B_big = [0;0;0;1];
C_big = [C_c 0];

% Char. polynom systemu so ZV ktory pozadujeme
c_wnt_int = (s + 100)*(s + 0.01997)*(s^2 + 0.08*s + 0.0046);
c_wnt_int = expand(c_wnt_int);
c_wnt_int = vpa(c_wnt_int);

% Char. polynom systemu so ZV zo stavovych rovnic
c_big = det(s*eye(4) - A_big);
c_big = collect(c_big);
c_big = vpa(c_big);

% Vypocitane hodnoty
k1 = 100.09997 - 2.681669999999999998;
k2 = 10.0031976 - 0.106009000000000006003;
k_i = 0.0091862/10.38;
k3 = 0.619851862 - 0.01241899999999999948 -520*k_i;

K = [k1 k2 k3];
K_big = [K k_i];

A_new = A_c - B_c*K;
A_big = [A_new -B_c*k_i;-C_c 0];

sys_big = ss(A_big,B_big,C_big,0);
T_big = tf(sys_big);