clear all;
% parametry
M1 = 20000;
M2 = 15000;
Fmot = 62000;
k = 5000;
sigma = 800;
mu = 0.007;
g = 9.8;

% matice A, B, C, D
%A=[0 0 1 0; 0 0 0 1; -0.25 0.25 -0.1086 0.04; 1/3 -1/3 8/150 -0.121933]
A = [0 1 0 0; -k/M1 (-sigma/M1 -mu*g) k/M1 sigma/M1; 0 0 0 1; k/M2 sigma/M2 -k/M2 (-sigma/M2 -mu*g)]
B = [0; 1/M1; 0; 0]
C = [1 0 0 0]
D = [0]

% riditelnost, pozorovatelnost
%CON = subs([B A*B A^2*B]);
%rank(CON)
%OBS = subs([C; C*A; C*A^2]);
%rank(OBS)

 % prenos
sys = ss(A,B,C,D);
H = tf(sys)

% nuly a poly
%[N,P,K] = ss2zp(A,B,C,D)