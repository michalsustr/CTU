clear all;
a0=[1;1;1];
x0=[3;3;3];

a1=[2;0;0];
x1=[5;5;0];

plot3(0,0,0,'o');
hold on;
axis equal;
plot3(a0(1),a0(2),a0(3),'go');
plot3(x0(1),x0(2),x0(3),'gx');

plot3(a1(1),a1(2),a1(3),'ro');
plot3(x1(1),x1(2),x1(3),'rx');

t=-5:0.1:5;
y0=a0*ones(1,size(t,2))+(x0-a0)*t;
y1=a1*ones(1,size(t,2))+(x1-a1)*t;

plot3(y0(1,:),y0(2,:),y0(3,:),'g');
plot3(y1(1,:),y1(2,:),y1(3,:),'r');

A=[x0-a0 -x1+a1];
b=[a0-a1];


A = [
  x0' * x0 , x0' * -x1 ;
  x1' * x0 , x1' * -x1
]

b = [
  x0' * ( a1 - a0 );
  x1' * ( a1 - a0 )
]

z=pinv(A)*b;

a2=a0+(x0-a0)*z(2);
x2=a1+(x1-a1)*z(1);

plot3(a2(1),a2(2),a2(3),'bo');
plot3(x2(1),x2(2),x2(3),'bx');

y2=a2*ones(1,size(t,2))+(x2-a2)*t;
plot3(y2(1,:),y2(2,:),y2(3,:),'b');