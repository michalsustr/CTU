function priklad2
clf;
x=linspace(0,2*pi,1000);
y=F(x); plot(y(1,:),y(2,:),'k'); 
axis equal; axis manual; hold on
xx=1;
yy=F(xx); plot(yy(1,:),yy(2,:),'ro'); 
y=T1(xx,x); plot(y(1,:),y(2,:),'g'); 
y=T2(xx,x); plot(y(1,:),y(2,:),'b'); 
return

function y=F(x)
y=[cos(x);sin(2*x)];

function y=T1(xx,x)
d = x-xx;
r = [(-sin(xx)*d); (2*(cos(2*xx))*d)];
y=repmat(F(xx), 1, size(r,2))+r;

function y=T2(xx,x)
dx1=-cos(xx);
dx2=-4*sin(2*xx);
d = x-xx;
y=T1(xx,x)+(1/2)*[d.^2*dx1; d.^2*dx2];
