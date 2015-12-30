x=1;y=1;
for i=1:10
    df = [2*x-2*cos(y^2-2*x) -1+cos(y^2-2*x)*2*y]
    d2f = [2-4*sin(y^2-2*x) 4*y*sin(y^2-2*x); 4*y*sin(y^2-2*x) -sin(y^2-2*x)*4*y^2+2*cos(y^2-2*x)];
    xk=[x;y]-d2f^-1*df';
    x=xk(1); y=xk(2);
end

disp(x);
disp(y);

