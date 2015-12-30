%%Volba parametru pro zkusebni simulaci

clear

g=0;
OFFSET=0;

R1=6;
R2=2;
R3=1.5;
R4=2;
R5=1.5;

MR1=0;
%MR1=99999999999999;

MR2=0;
%MR2=99999999999999;

C1=25;
C2=25;
C3=500;
CT=.2;
I=5;

G=9500;
T=21.5;
G2=0.04;
%%Parametry vodarny - pocitane
K=400;
%g=9.81*998;

C1=6.1228e-6;
C2=C1;
C3=4.0856e-5;
CT=3.0502e-7;

R1=0.1;
R5=R1;
R2=1.3871e+7;
R3=5.2401e+6;
R4=1.0172e7;

I=15.6e-3;


A=[
    -R1/I1 -G1/2 0 0 0 0;
    G1/I1 -RM/IM -1/CM 0 0 0;
    0 1/IM 1/(T1^2*CM) -1/(C1*R2) 0 0;
    
    0 0 0 1/(C1*(R3+MR1)) -1/((R3+MR1)*C2)-1/((R4+MR2)*C2)) 0;
    
];
