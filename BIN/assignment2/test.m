e = 0.000001;
PI=[1;0;0;0];
A=[
    e  0.5  0.5  e   ;
    e  0.7  0.2  0.1 ;
    e  0.2  0.7  0.1 ;
    e  e    e    1   ;
  ];
B=[
    e    e    e    e    1  e;  
    0.4  0.1  0.4  0.1  e  e;
    0.2  0.3  0.2  0.3  e  e;
    e    e    e    e    e  1;
  ];

% Index correspondence:
% ACGT
% 1234
% Start of sequence S- 5
% End of sequence -F 6

% Sequence S-CGTCAG-F
O=[5 2 3 4 2 1 3 6];
[P, trace]=viterbi(O, A, B, PI)

% Sequence S-CCCCCCCCC-F
% O=[5 2 2 2 2 2 2 2 2 2 6];
% [P, trace]=viterbi(O, A, B, PI)