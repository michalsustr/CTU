%% Volba parametrov
MR1 = 0;
MR2 = 0;
R_m = 230/4.6;
R_s = 0.0018;
R1 = (7*1.004*1.8)/(pi*0.024^4);
R2 = (8*1.004*0.6)/(pi*0.024^4);
R3 = (8*1.004*0.475)/(pi*0.024^4);

I_m = 0.159;
I_s = 15.3e-6;
I1 = (998*1.8)/0.048;
I2 = (998*0.6)/0.048;
I3 = (998*0.475)/0.048;

C1 = 0.4/(998*9.81);
C2 = 0.06/(998*9.81);
C3 = 0.06/(998*9.81);
C_s = 0.000005;

G1 = 0.0023;
T = 3300;

u = 2600;

%{
A = [ 
-R_m/I_m, -G_1/I_s, 0, 0, 0, 0;
G_1/I_m, -R_s/I_s, -1/C_s, 0, 0, 0 ;
0, 1/I_s, -1/(T_1^2*C_s*R_3), 0, 1/(T_1*R_3*C_2), 0 ;
0, 0, -1/(T_1*R_3*C_s), 0, 1/(R_3*C_2), 1/(C_3*(R_5+MR_2)) ;
0, 0, 1/(T_1*C_s*R_3), 0, -1/(R_3*C_2)-1/(C_2*(R_4+MR_1)), 1/(C_3*(R_4+MR_1)) ;
0, 0, 0, 0, 1/(C_2*(R_4+MR_1)), -1/(C_3*(R_4+MR_1))-1/(C_3*(R_5+MR_2));
];
%}

%% STATE-SPACE MODEL
A = [-R_m/I_m,-G1/I_s,0,0,0,0,0,0,0;
    G1/I_m,-R_s/I_s,0,0,0,-1/C_s,0,0,0;
    0,0,-R1/I1,0,0,1/C_s*T,0,-1/C2,0;
    0,0,0,-(R2 + MR1)/I2,0,0,0,1/C2,-1/C3;
    0,0,0,0,-(R3 + MR2)/I3,0,0,0,1/C3;
    0,1/I_s,-1/(I1*T),0,0,0,0,0,0;
    0,0,-1/I1,0,1/I3,0,0,0,0;
    0,0,1/I1,-1/I2,0,0,0,0,0;
    0,0,0,1/I2,-1/I3,0,0,0,0];
B = [u;0;0;0;0;0;0;0;0];
C = eye(9);
D = [0;0;0;0;0;0;0;0;0];

x0 = [0 0 0 0 0 0 0.18 0 0]; % 10 is water in the lower tank

%% SIMULACIA
% Real model
% time, pump speed, tank2, tank3, tank1, flow, valve2
load('./data/cerpadlo_100_v1_open_v2_100.mat');
real_time = ty(:,1);
q1_real = ty(:,5);
q2_real = ty(:,3);
q3_real = ty(:,4);

% Mathematical model
sim_time = 160;

step1_time = 40;
step1_initial = 0;
step1_final = 100;

step2_time = 75;
step2_initial = 0;
step2_final = 100;

sim('./simulink/power_plant_simulink', sim_time);
time = scope_druha_nadrz.time;
q1 = scope_prva_nadrz.signals.values;
q2 = scope_druha_nadrz.signals.values;
q3 = scope_tretia_nadrz.signals.values;
input = scope_input(:,2);

h1 = (100*q1(:,1))/0.4;
h2 = (100*(q2(:,1))/0.06);
h3 = (100*(q3(:,1))/0.06);

%% PLOT
figure(1);
clf
hold on;
title('Tank 1')
plot(real_time, q1_real, 'LineWidth', 2, 'Color', 'b');
plot(time, h1, 'LineWidth', 3, 'Color', 'r');
plot(time, input, 'LineWidth', 2, 'Color', 'k');
xlabel('time[s]');
ylabel('Height [cm] with input [%]');
legend('bottom tank on real model', 'bottom tank in mathematical model', 'input');

figure(2);
clf
hold on;
title('Tank 2')
plot(real_time, q2_real, 'LineWidth', 2, 'Color', 'b');
plot(time, h2, 'LineWidth', 3, 'Color', 'r');
plot(time, input, 'LineWidth', 2, 'Color', 'k');
xlabel('time[s]');
ylabel('Height [cm] with input [%]');
legend('top-left tank on real model', 'top-left tank in mathematical model', 'input');

figure(3);
clf
hold on;
title('Tank 3')
plot(real_time, q3_real, 'LineWidth', 2, 'Color', 'b');
plot(time, h3, 'LineWidth', 3, 'Color', 'r');
plot(time, input, 'LineWidth', 2, 'Color', 'k');
xlabel('time[s]');
ylabel('Height [cm] with input [%]');
legend('top-right tank on real model', 'top-right tank in mathematical model', 'input');

