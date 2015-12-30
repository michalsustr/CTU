function priklad6
clf;

M=40; N=20; x1=linspace(0,2*pi,M)'*ones(1,N); x2=ones(M,1)*linspace(0,2*pi,N);
[y1,y2,y3]=F(x1,x2); mesh(y1,y2,y3,'facecolor','w','edgecolor','y');
axis equal; axis vis3d; axis manual; hold on

x=linspace(0,2*pi,1000);
u=G(x); [y1,y2,y3]=F(u(1,:),u(2,:)); plot3(y1,y2,y3,'k');

xx=1;
uu=G(xx); [yy1,yy2,yy3]=F(uu(1,:),uu(2,:)); plot3(yy1,yy2,yy3,'ro');

[y1,y2,y3]=T1(xx,x); plot3(y1,y2,y3,'g');
return

% zobrazeni z prikladu 2
function y=G(x)
y=[cos(x);sin(2*x)];

% zobrazeni z prikladu 4
function [y1,y2,y3]=F(x1,x2)
R=2; r=1;
y1=(R+r*cos(x2)).*cos(x1);
y2=(R+r*cos(x2)).*sin(x1);
y3=r*sin(x2);

function [y1,y2,y3]=T1(xx,x)
R=2; r=1;
uu = G(xx);
uu1 = uu(1); uu2 = uu(2);
% Taylor z prikladu 4
% f'(g(x*))
d1dx = (R+r*cos(uu2))*(-sin(uu1));
d1dy = r*cos(uu1)*(-sin(uu2));
d2dx = (R+r*cos(uu2))*cos(uu1);
d2dy = r*sin(uu1)*(-sin(uu2));
d3dx = 0;
d3dy = r*cos(uu2);
% Taylor z prikladu 2
% g'(x*)
g1=-sin(xx);
g2=2*(cos(2*xx));

d=x-xx;

[a,b,c]=F(uu1,uu2);
y1=repmat(a,size(d,1),size(d,2))+(d1dx.*g1+d1dy.*g2)*d;
y2=repmat(b,size(d,1),size(d,2))+(d2dx.*g1+d2dy.*g2)*d;
y3=repmat(c,size(d,1),size(d,2))+(d3dx.*g1+d3dy.*g2)*d;


