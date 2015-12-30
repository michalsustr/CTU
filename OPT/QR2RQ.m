A=randn(3,3);
ReverseRows = [0 0 1; 0 1 0 ; 1 0 0];
[Q R] = qr((ReverseRows * A)');
Y = ReverseRows * R' * ReverseRows;
X = ReverseRows * Q';

[Q R] = qr(A);

disp(A);
disp('QR');
disp(Q*R-A);
disp('RQ');
disp(Y*X-A);