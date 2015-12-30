%% Constants
syms m g l;

% Assumed values of constants throughout the script:
% m = 1;
% g = 9.8;
% l = 1;

%% Compute lagrangian
th   = sym('th', 'real');
Dth  = sym('Dth', 'real');
D2th = sym('D2th', 'real');

fi   = sym('fi', 'real');
Dfi  = sym('Dfi', 'real');
D2fi = sym('D2fi', 'real');

x=l*sin(th)*cos(fi);
y=l*sin(th)*sin(fi);
z=l*(1-cos(th));

dx=diff(x,'fi')*Dfi+diff(x,'th')*Dth;
dy=diff(y,'fi')*Dfi+diff(y,'th')*Dth;
dz=diff(z,'fi')*Dfi+diff(z,'th')*Dth;

V=m*g*z;
T=0.5*m*(dx^2+dy^2+dz^2);

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

dLDfi  = diff(L, 'fi');
dLD2fi = diff(L, 'Dfi');
ddt_dLD2fi = diff(dLD2fi, 'fi')*Dfi + diff(dLD2fi, 'Dfi')*D2fi + ...
             diff(dLD2fi, 'th')*Dth + diff(dLD2fi, 'Dth')*D2th;
         
dLDth  = diff(L, 'th');
dLD2th = diff(L, 'Dth');
ddt_dLD2th = diff(dLD2th, 'fi')*Dfi + diff(dLD2th, 'Dfi')*D2fi + ...
             diff(dLD2th, 'th')*Dth + diff(dLD2th, 'Dth')*D2th;

%% Final differential equations
fi_eq = simplify(ddt_dLD2fi-dLDfi);
th_eq = simplify(ddt_dLD2th-dLDth);

