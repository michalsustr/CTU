function translun() 
% matica A
A = [0 0 0 1 0 0;
     0 0 0 0 1 0;
     0 0 0 0 0 1;
     7.3809 0 0 0 2 0;
     0 -2.1904 0 -2 0 0;
     0 0 -3.1904 0 0 0];

 % je system stabilny?
[V,D] = eig(A);
disp(diag(D));

% ktorym vstupom je system riditelny?
B1 = [0; 0; 0; 1; 0; 0];
B2 = [0; 0; 0; 0; 1; 0];
B3 = [0; 0; 0; 0; 0; 1];
disp(is_controllable(A, B1));
disp(is_controllable(A, B2));
disp(is_controllable(A, B3));

% prenos na stav
S=tf('s');
x = (S*eye(rank(A))-A)^-1;
zpk(x*B1)
zpk(x*B2)
zpk(x*B3)

% riditelnost celeho systemu
disp(is_controllable(A, [B1 B2 B3]));

% prenosova matica zo vstupov na stav
y=x*[B1 B2 B3];
disp(zpk(y(:,1)));
disp(zpk(y(:,2)));
disp(zpk(y(:,3)));
end

function ret = is_controllable(A,B)
    C = [B];
    
    for i=1:rank(A)-1
       C = [C A^i*B];
    end
    disp(rank(A));
    disp(rank(C));
    ret = rank(A) == rank(C);
end