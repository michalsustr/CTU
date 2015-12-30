


function [t, y]=EULER_backward_ODE
% [t, y]=EULER_backward_ODE(f, t0, y0, tend, Niter)
%%%%%%%%%%%%%%%%% EULER_backward_ODE.m %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
% Euler modified approximation method to solve IVP ODEs
% f defines the function f(t,y)
% t0 defines initial value of t
% y0 defines initial value of y
%%%%%%%%%%%%%%%%% EULER_backward_ODE.m %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
%  Euler forward approximation method is implemented via MATLAB      %
%         Created by Sulaymon L. ESHKABILOV                          %
%         a visiting professor to Ohio University                    %
%                September 2010                                      %
%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%

 

 if nargin <5 && nargin==4; 
 display('Wrong number of input arguments. Read help comments of EULER_forward.m');
 pause 
 disp('Hit any key to continue anyway and wish to program make a decision for you')
 disp('Default value for number of iterations is taken to be 10 !!! ');
 Niter=10; % That is a default value for number of iterations

 elseif nargin==3 && nargin<4;
     disp('Do you know how many iterations to carry out? ')
     Niter=input('Enter the number of iterations, i.e.  you need to perform  Niter = ');
     disp('Do you know end value for t i.e., tend ?  ');
     tend=input('Enter tend =  ');
 elseif nargin==2;
     disp('Do you know how many iterations to carry out? ')
     Niter=input('Enter the number of iterations, i.e.  you need to perform  Niter = ');
     disp('Do you know the end value for t?  ');
     tend=input('Enter tend = :  ');
     disp('Do you know initial value for y i.e., y0 ')
     y0=input('Enter y0 =  ');

 elseif   nargin==1;

     disp('Do you know how many iterations to carry out? ')
     Niter=input('Enter the number of iterations,  Niter = ');
     disp('Do you know the end value for t?  ');
     tend=input('Enter tend = :  ');
     disp('Do you know initial value for y i.e., y0 ')
     y0=input('Enter y0 =  ');
     display('Do you know inital value of t i.e., t0');
     t0=input('Enter t0 = ');
     display('The entry is to be a function called by an anonymous function');

 else

     disp('Do you know how many iterations to carry out? ')
     Niter=input('Enter the number of iterations,  Niter = ');
     disp('Do you know the end value for t?  ');
     tend=input('Enter tend = :  ');
     disp('Do you know initial value for y i.e., y0 ')
     y0=input('Enter y0 =  ');
     display('Do you know inital value of t i.e., t0 ');
     t0=input('Enter t0 = ');
     disp('Create anonymous function f=@(t,y)expression');
     f=input('Enter anonymous function: f =   ');
 end
hstep=(tend-t0)/Niter;
t=[t0, zeros(1,Niter)]; % Memory allocation
y=[y0, zeros(1,Niter)]; % Memory allocation

  for kk=1:Niter
    t(kk+1)=t(kk)+hstep;
    y_NEW=y(kk)+hstep*(f(t(kk), y(kk)));
    y(kk+1)=y(kk)+hstep*f(t(kk+1), y_NEW);

  end

% Visualization of computed data 
plot(t,y, 'kh-');
grid on;
title('Numerical solution of ODE using EULER backward Method');
axis tight;

end

 

