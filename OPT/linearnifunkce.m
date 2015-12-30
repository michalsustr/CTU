f=[0;0;1];
A=zeros(2*m,3);
b=zeros(2*m,1);
for(i=1:1:m)
    A(i,1) = x(i); % ax_i
    A(i,2) = -1; % y_i
    A(i,3) = -1; % lambda
    b(i) = -y(i); % b
end
for(i=m+1:1:2*m)
    A(i,1) = -x(i-m); % ax_i
    A(i,2) = 1; % y_i
    A(i,3) = -1; % lambda
    b(i) = y(i-m);  % b
end

[priamka] = linprog(f,A,b);

a=linspace(0,1,1000);
b=-priamka(1)*a+priamka(2);
figure;
hold on;
plot(a,b,'r');
plot(x,y,'kx');
% vykreslene odchylkove primky
b=-priamka(1)*a+priamka(2)+priamka(3);
plot(a,b,'r:');
b=-priamka(1)*a+priamka(2)-priamka(3);
plot(a,b,'r:');