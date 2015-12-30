addpath ../scheduling/

c = [ ...
  zeros(1,10);
  0 0 0 0 100 300 0 0 50 0 ;
  zeros(1,6) 300 150 75 0 ;
  0 0 0 0 80 240 0 80 40 0 ;
  zeros(6,10);
];
l = [ ...
  0 100 50 120 zeros(1,6);
  zeros(3,10);
  zeros(1,9), 99;
  zeros(1,9), 79;
  zeros(1,9), 49;
  zeros(1,9), 69;
  zeros(1,10);
  zeros(1,10);
];
u = [ ...
  0 150 100 140 zeros(1,6);
  0 0 0 0 inf inf 0 0 inf 0;
  zeros(1, 6) inf inf inf 0;
  0 0 0 0 inf inf 0 inf inf 0;
  zeros(1,9), 100;
  zeros(1,9), 80;
  zeros(1,9), 50;
  zeros(1,9), 70;
  zeros(1,9), inf;
  zeros(1,10);
];
b = [350 zeros(1,8) -350]';


'Počet vrcholov siete'
length(b)

g = graph;
F = g.mincostflow(c,l,u,b);

% Matica C
% bola vytvorena z náčrtu grafu na papieri, zo zdroja k zamestnancom idú
% ceny hrán rovné nule, z uzlov zamestnancov ku projektom idú s cenou ich
% hodinovej mzdy*koeficient zložitosti, v prípade voľna s polovičnou cenou,
% a z projektov ku spotrebiču s nulovou cenou

% Cena za projekty
'Cena za projekt 1'
p1=(F(:,5).*c(:,5));
sum(p1(~isnan(p1)))
'Cena za projekt 2'
p2=(F(:,6).*c(:,6));
sum(p2(~isnan(p2)))
'Cena za projekt 3'
p3=(F(:,7).*c(:,7));
sum(p3(~isnan(p3)))
'Cena za projekt 4'
p4=(F(:,8).*c(:,8));
sum(p4(~isnan(p4)))
'Cena za volno'
p5=(F(:,9).*c(:,9));
sum(p5(~isnan(p5)))

%%

gr = graph(F);
n = length(F);

gr.N(1).Name='Source';
gr.N(2).Name='Z1';
gr.N(3).Name='Z2';
gr.N(4).Name='Z3';
gr.N(5).Name='P1';
gr.N(6).Name='P2';
gr.N(7).Name='P3';
gr.N(8).Name='P4';
gr.N(9).Name='volno';
gr.N(10).Name='spotrebic';

gr.N(1).graphicParam(1).x = 100;
gr.N(1).graphicParam(1).y = 100;

gr.N(2).graphicParam(1).x = 180;
gr.N(2).graphicParam(1).y = 50;

gr.N(3).graphicParam(1).x = 180;
gr.N(3).graphicParam(1).y = 100;

gr.N(4).graphicParam(1).x = 180;
gr.N(4).graphicParam(1).y = 150;

gr.N(5).graphicParam(1).x = 280;
gr.N(5).graphicParam(1).y = 0;

gr.N(6).graphicParam(1).x = 280;
gr.N(6).graphicParam(1).y = 80;

gr.N(7).graphicParam(1).x = 280;
gr.N(7).graphicParam(1).y = 160;

gr.N(8).graphicParam(1).x = 280;
gr.N(8).graphicParam(1).y = 240;

gr.N(8).graphicParam(1).x = 280;
gr.N(8).graphicParam(1).y = 320;
graphedit(gr);

