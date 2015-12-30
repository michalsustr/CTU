function priklad5
clf;
N=30;
x1=linspace(-1,1,N)'*ones(1,N); x2=x1';
[y1,y2]=F(x1,x2); plot(y1,y2,'k'); hold on; plot(y1',y2','k');
axis equal; axis manual; hold on
xx1=.4; xx2=.7;
[yy1,yy2]=F(xx1,xx2); plot(yy1,yy2,'ro');
[y1,y2]=T1(xx1,xx2,x1,x2); plot(y1,y2,'g'); hold on; plot(y1',y2','g');
[y1,y2]=T2(xx1,xx2,x1,x2); plot(y1,y2,'b'); hold on; plot(y1',y2','b');
return

function [y1,y2]=F(x1,x2)
r=log(1+x1.^2+x2.^2);
y1=x1.*r; y2=x2.*r;

function [y1,y2]=T1(xx1,xx2,x1,x2)
d1dx = log(1+xx1^2+xx2^2)+2*xx1^2/(1+xx1^2+xx2^2);
d1dy = 2*xx1*xx2/(1+xx1^2+xx2^2);
d2dx = 2*xx1*xx2/(1+xx1^2+xx2^2);
d2dy = log(1+xx1^2+xx2^2)+2*xx2^2/(1+xx1^2+xx2^2);
d1 = x1-xx1;
d2 = x2-xx2;

[a,b]=F(xx1,xx2);
y1=repmat(a,size(d1,1),size(d1,2))+d1dx.*d1+d1dy.*d2;
y2=repmat(b,size(d1,1),size(d1,2))+d2dx.*d1+d2dy.*d2;


function [y1,y2]=T2(xx1,xx2,x1,x2)
d1dxdx=2*xx1*(xx1^2+3*xx2^2+3)/(1+xx1^2+xx2^2)^2;
d1dxdy=2*xx2*(-xx1^2+xx2^2+1)/(xx1^2+xx2^2+1)^2;
d1dydy=2*xx1*(xx1^2-xx2^2+1)/(xx1^2+xx2^2+1)^2;
d2dxdx=2*xx2*(-xx1^2+xx2^2+1)/(xx1^2+xx2^2+1)^2;
d2dxdy=2*xx1*(xx1^2-xx2^2+1)/(xx1^2+xx2^2+1)^2;
d2dydy=2*xx2*(xx2^2+3*xx1^2+3)/(1+xx1^2+xx2^2)^2;
d1=x1-xx1;
d2=x2-xx2;

[a,b]=T1(xx1,xx2,x1,x2);
y1 = a+(1/2)*((d1.^2).*d1dxdx+2*d1.*d2.*d1dxdy+(d2.^2).*d1dydy);
y2 = b+(1/2)*((d1.^2).*d2dxdx+2*d1.*d2.*d2dxdy+(d2.^2).*d2dydy);