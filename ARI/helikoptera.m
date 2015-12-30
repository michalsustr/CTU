%% 1)

q0 = 5;
t0 = pi/8;
u0 = 10;
d0 = pi/16;

A=[
-0.45 0 -0.015
1 0 0
-1.4 9.8 -0.02
];

B=[
5.8;
0;
9.9
];


C=[
0 0 1   
];

sys=ss(A,B,C,0);
%zpk(sys)
%    9.9 (s^2 - 0.3702s + 5.741)
%-----------------------------------
%(s+0.7485) (s^2 - 0.2785s + 0.1964)

%sys_tf=tf(sys);
%roots(sys_tf.num{:})
%   0.1851 + 2.3890i
%   0.1851 - 2.3890i

%eig(A)
%   -0.7485          
%    0.1392 + 0.4207i
%    0.1392 - 0.4207i



%rank(ctrb(A,B))
%   3 

%rank(obsv(A,C))
%   3 

%% 2)
 

%% 3)

Abig = [A, zeros(3,1); -C, 0]
Bbig = [B; 0]
Bref = [0;0;0;1]
Cbig = [C 0]

char = charpol(10,6)
Kcelk = place(Abig, Bbig, [roots(char);-5;-6])

sys_stav = ss((Abig-Bbig*Kcelk),Bref,Cbig, 0)

step(sys_stav)

%% 4)
G=sys_stav;

Anew = G.a';
Bnew = G.c';

K = place(Anew, Bnew, [roots(char);-5;-6]);
L = K';
Gnew = ss(G.a-L*G.c, G.b, G.c, 0);

Hp = tf(Gnew)
step(Hp)

%% 5)
Bnew=[
    0.1;
    0;
    4
    ]

sys.b = Bnew;
sys_stav.b= Bnew;
%% 6)
discrete = c2d(sys_stav, 1);
step(discrete)
