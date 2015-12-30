%syms y x;

%% A, Forward Euler
figure;
ranges=[5 10 100 1000];

for n=ranges
dx = 1/n;
x=zeros(1,n);
y=zeros(1,n);
x(1) = 0;
y(1) = 1;
for k=1:n-1
x(k+1)=x(k) + dx;
y(k+1)= y(k) -15*y(k)*dx;
end

real_function=exp(-15*x);
error=sum((real_function-y).^2)/n

subplot(2,2, find(ranges==n));
grid on, hold on;
title(['(A) Forward Euler method, h=' num2str(dx) ', error=' num2str(error)]);
plot(x,y,'b');
plot(x,real_function,'r');
legend('Calculated solution', 'Exact solution');
end

%% B, Forward Euler
figure;
ranges=2*pi*[1 5 10 100];

for n=ranges
dx = 2*pi/n;
x=zeros(2,n);
x(:,1) = [1; 0];
for k=1:n
x(:,k+1)=x(:,k) + [x(2,k); -x(1,k)]*dx;
end

t=[0:dx:2*pi];
real_function=cos(t);
y=x(1,:);
error=sum((real_function-y).^2)/n

subplot(2,2, find(ranges==n));
grid on, hold on;
title(['(B) Forward Euler method, h=' num2str(dx) ', error=' num2str(error)]);
plot(t,y,'b');
plot(t,real_function,'r');
legend('Calculated solution', 'Exact solution');
end

