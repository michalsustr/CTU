function priklad1
clf;
x=linspace(-3,2,1000);
plot(x,F(x),'k');
axis equal; axis manual; hold on
xx=.5; plot(xx,F(xx),'ro')
plot(x,T1(xx,x),'g')
plot(x,T2(xx,x),'b')
return

function y=F(x)
y=x.^3/3+x.^2/2-x;

function y=T1(xx,x)
dy=xx^2+xx-1; % 1st derivative at xx
y=F(xx)+dy*(x-xx);

function y=T2(xx,x)
ddy=2*xx+1; % 2nd derivative at xx
y=T1(xx,x)+ddy*(x-xx).^2/2;