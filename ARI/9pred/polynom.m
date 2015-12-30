d=2;
m=7;
r=9;

% v poradi od najmensej mocniny po tu najvacsiu, tj a0+a1*s+a2*s^2
a = [d*r, d+r, 1];
b = [d, 1, 0];
c = [d^2+m*r*d, m*d+m*r+d*r, d+m+r-1, 1];

as = d*r+(d+r)*s+s^2;
bs = d+s;
cs = (d^2+m*r*d)+(m*d+m*r+d*r)*s+(d+m+r-1)*s^2+s^3;

[minx_x,minx_y] = axbyc(as,bs,cs,'minx');
[miny_x,miny_y] = axbyc(as,bs,cs,'miny');
[syl_x,syl_y] = axbyc(as,bs,cs,'syl');

% Sylvestrova matica
S = [
	a(1) a(2) a(3) 0;
	b(1) b(2) b(3) 0;
	0 a(1) a(2) a(3);
	0 b(1) b(2) b(3);
];
