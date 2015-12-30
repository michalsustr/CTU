A=[-0.45 0 -0.015; 1 0 0; -1.4 9.8 -0.02]
B=[5.8; 0; 9.9]
C=[0 0 1]

eig(A) % nestabilny system

is_controllable(A,B)
is_observable(A,C)
