function priklad4
clf;
M=40; N=20; x1=linspace(0,2*pi,M)'*ones(1,N); x2=ones(M,1)*linspace(0,2*pi,N);
[y1,y2,y3]=F(x1,x2); mesh(y1,y2,y3,'facecolor','w','edgecolor','k');
axis equal; axis vis3d; axis manual; hold on
xx1=3; xx2=.5;
[yy1,yy2,yy3]=F(xx1,xx2); plot3(yy1,yy2,yy3,'ro');
[y1,y2,y3]=T1(xx1,xx2,x1,x2); mesh(y1,y2,y3,'facecolor','w','edgecolor','g');
[y1,y2,y3]=T2(xx1,xx2,x1,x2); mesh(y1,y2,y3,'facecolor','w','edgecolor','b');
return

function [y1,y2,y3]=F(x1,x2)
R=2; r=1;
y1=(R+r*cos(x2)).*cos(x1);
y2=(R+r*cos(x2)).*sin(x1);
y3=r*sin(x2);

function [y1,y2,y3]=T1(xx1,xx2,x1,x2)
R=2; r=1;
d1dx = (R+r*cos(xx2))*(-sin(xx1));
d1dy = r*cos(xx1)*(-sin(xx2));
d2dx = (R+r*cos(xx2))*cos(xx1);
d2dy = r*sin(xx1)*(-sin(xx2));
d3dx = 0;
d3dy = r*cos(xx2);
d1 = x1-xx1;
d2 = x2-xx2;

[a,b,c]=F(xx1,xx2);
y1=repmat(a,size(d1,1),size(d1,2))+d1dx.*d1+d1dy.*d2;
y2=repmat(b,size(d1,1),size(d1,2))+d2dx.*d1+d2dy.*d2;
y3=repmat(c,size(d1,1),size(d1,2))+d3dx.*d1+d3dy.*d2;

function [y1,y2,y3]=T2(xx1,xx2,x1,x2)
R=2; r=1;
d1dxdx=(R+r*cos(xx2))*(-cos(xx1));
d1dxdy=r*(-sin(xx1))*(-sin(xx2));
d1dydy=r*cos(xx1)*(-cos(xx2));
d2dxdx=(R+r*cos(xx2))*(-sin(xx1));
d2dxdy=r*cos(xx1)*(-sin(xx2));
d2dydy=r*sin(xx1)*(-cos(xx2));
d3dxdx=0;
d3dxdy=0;
d3dydy=r*(-sin(xx2));
d1=x1-xx1;
d2=x2-xx2;

[a,b,c]=T1(xx1,xx2,x1,x2);
y1 = a+(1/2)*((d1.^2).*d1dxdx+2*d1.*d2.*d1dxdy+(d2.^2).*d1dydy);
y2 = b+(1/2)*((d1.^2).*d2dxdx+2*d1.*d2.*d2dxdy+(d2.^2).*d2dydy);
y3 = c+(1/2)*((d1.^2).*d3dxdx+2*d1.*d2.*d3dxdy+(d2.^2).*d3dydy);