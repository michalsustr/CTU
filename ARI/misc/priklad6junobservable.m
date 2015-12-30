X=-5;
sys=tf([2 1+X], [1 1+X X]);
sys2=ss(sys);
A=sys2.A;
B=sys2.B;
C=sys2.C;
is_controllable(A,B)
is_observable(A,C)