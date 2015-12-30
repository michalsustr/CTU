%% Nastavenia
% prenosova funkcia povodneho systemu
% b/a = (b1*s+b0)/(s^2+a1*s+a0)
% prenosova funkcia pozadovaneho systemu
% (s+alfa*omega)(s^2+2*zeta*omega*s+omega^2)
% prenosova funkcia najdeneho PID regulatoru
% q/p = (kd*s^2+kp*s+ki)/s

% zadane koeficienty
b1=1;
b0=7;
a1=0;
a0=4;
alfa=sqrt(2);
zeta=sqrt(2)/2;
omega=1/sqrt(2);

%% Vypocet

A = [b1 0 (b0-b1*(alfa*omega+2*zeta*omega)); 
    b0 b1 -(1+2*alfa*zeta)*omega^2*b1; 
    0 b0 -alfa*omega^3*b1];
b = [alfa*omega+2*zeta*omega-a1;
    -a0+(1+2*alfa*zeta)*omega^2;
    alfa*omega^3];

x=inv(A)*b;

b=[b1 b0];
a=[1 a1 a0];
q=[x(3), x(1), x(2)];
p=[1 0];

c=conv(a,p)+conv(b,q);
disp('korene sp. polynomu:');
roots(c)
disp('[kp,ki,kd]');
disp(x);