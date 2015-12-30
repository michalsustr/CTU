%Script that demonstrates Euler integration for a second order problem using Matlab.

% The problem to be solved is:

%y''(t)+100*y'(t)+1E4*y(t)=1E4*abs(sin(wt))

h=0.00001;		%h is the time step.
t=0:h:0.08;	   %initialize time variable.

clear x;	%wipe out old variable.

x(1,:)=[0 0];	%initial condition are zero.
%Note that x is two dimensional since there are two state variables.

for i=2:length(t),	%Set up "for" loop.
   
   %Calculate the values of k1 (note that it is a vector of length 2).
   k1=[x(i-1,2) 1E4*abs(sin(377*t(i)))-100*x(i-1,2)-1E4*x(i-1,1)];	%Calculate derivatives;
   x(i,:)=x(i-1,:)+k1*h;		%Estimate new value of x;
end

%Plot approximate and exact solution.
plot(t,x(:,1),'r',t,abs(sin(377*t)),'b');
legend('Output','Input');
title('Euler Approximation, Second order system');
xlabel('Time');
ylabel('Input and Output');

