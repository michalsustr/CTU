%% Constants
syms m_1 m_2 g r l_1 l_2;

% Assumed values of constants throughout the script:
% m = 1;
% g = 9.8;
% l = 1;

%% Compute lagrangian
th   = sym('th', 'real');
Dth  = sym('Dth', 'real');
D2th = sym('D2th', 'real');

fi1   = sym('fi1', 'real');
Dfi1  = sym('Dfi1', 'real');
D2fi1 = sym('D2fi1', 'real');

fi2   = sym('fi2', 'real');
Dfi2  = sym('Dfi2', 'real');
D2fi2 = sym('D2fi2', 'real');

% Coordinates of the parts of the system
x_d=r*cos(th);
y_d=r*sin(th);
z_d=0;

x_1=-l_1*sin(fi1)*sin(th)+x_d;
y_1=-l_1*sin(fi1)*cos(th)+y_d;
z_1=-l_1*cos(fi1)+z_d;

x_2=-l_2*sin(fi2)*sin(th)+x_1;
y_2=-l_2*sin(fi2)*cos(th)+y_1;
z_2=-l_2*cos(fi2)+z_1;

% First derivatives (velocities)
dx_d=diff(x_d,'th')*Dth;
dy_d=diff(y_d,'th')*Dth;
dz_d=0; %diff(z_d,'th')*Dth;

dx_1=diff(x_1,'th')*Dth+diff(x_1,'fi1')*Dfi1;
dy_1=diff(y_1,'th')*Dth+diff(y_1,'fi1')*Dfi1;
dz_1=diff(z_1,'th')*Dth+diff(z_1,'fi1')*Dfi1;

dx_2=diff(x_2,'th')*Dth+diff(x_2,'fi1')*Dfi1+diff(x_2,'fi2')*Dfi2;
dy_2=diff(y_2,'th')*Dth+diff(y_2,'fi1')*Dfi1+diff(y_2,'fi2')*Dfi2;
dz_2=diff(z_2,'th')*Dth+diff(z_2,'fi1')*Dfi1+diff(z_2,'fi2')*Dfi2;

V=m_1*g*z_1+m_2*g*z_2;
T=0.5*m_1*(dx_1^2+dy_1^2+dz_1^2)+0.5*m_2*(dx_2^2+dy_2^2+dz_2^2);

L=T-V;

%% Prepare differential equations

% Notation:

% d L
% ---- = dLDq
% d q

% d L
% ----- = dLD2q
% d dq

%  d  / d L  \
% --- | -----| = ddt_dLD2q
%  dt \ d dq /

dLDth  = diff(L, 'th');
dLD2th = diff(L, 'Dth');
ddt_dLD2th = diff(dLD2th, 'fi1')*Dfi1 + diff(dLD2th, 'Dfi1')*D2fi1 + ...
             diff(dLD2th, 'fi2')*Dfi2 + diff(dLD2th, 'Dfi2')*D2fi2 + ...
             diff(dLD2th, 'th')*Dth + diff(dLD2th, 'Dth')*D2th;


dLDfi1  = diff(L, 'fi1');
dLD2fi1 = diff(L, 'Dfi1');
ddt_dLD2fi1 = diff(dLD2fi1, 'fi1')*Dfi1 + diff(dLD2fi1, 'Dfi1')*D2fi1 + ...
              diff(dLD2fi1, 'fi2')*Dfi2 + diff(dLD2fi1, 'Dfi2')*D2fi2 + ...
              diff(dLD2fi1, 'th')*Dth + diff(dLD2fi1, 'Dth')*D2th;
         
dLDfi2  = diff(L, 'fi2');
dLD2fi2 = diff(L, 'Dfi2');
ddt_dLD2fi2 = diff(dLD2fi2, 'fi1')*Dfi1 + diff(dLD2fi2, 'Dfi1')*D2fi1 + ...
              diff(dLD2fi2, 'fi2')*Dfi2 + diff(dLD2fi2, 'Dfi2')*D2fi2 + ...
              diff(dLD2fi2, 'th')*Dth + diff(dLD2fi2, 'Dth')*D2th;

%% Final differential equations
th_eq = simplify(ddt_dLD2th-dLDth);
fi1_eq = simplify(ddt_dLD2fi1-dLDfi1);
fi2_eq = simplify(ddt_dLD2fi2-dLDfi2);

