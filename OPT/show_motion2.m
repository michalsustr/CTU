% vyber soubor
switch 2
    case 1
        A = load('makarena1.txt','-ASCII');
    case 2
        A = load('walk1.txt','-ASCII');
end

%% Zde doplnte Vas kod, ktery najde aproximaci A_approx %%%%%%%%%%%%

r = 8; % pozadovana hodnost 

%

for(i=1:size(A,2))
    % vypocitame tazisko
    T(i) = mean(A(:,i));
    % posunieme do pociatku
    B(:,i) = A(:,i)-T(i);
end

[U,S,V] = svd(B,0);

% spravime priemet do podprostoru podla pozadovaneho r
for(i=(r+1):size(S,1))
    S(i,i) = 0;
end

At = U*S*V'; % A tilda

% posunieme naspat
for(i=1:size(A,2))
    At(:,i) = At(:,i)+T(i);
end


A_approx = At;

%% konec Vaseho kodu %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%

% definice bodu, ktere se maji spojit
conn = load('connected_points.txt');

nMarkers = size(A,2)/3;
nFrames = size(A,1);

% najdi rozsah souradnic pro vsechny body
max_x = max(max(A(:,1:3:end)));
max_y = max(max(A(:,2:3:end)));
max_z = max(max(A(:,3:3:end)));
min_x = min(min(A(:,1:3:end)));
min_y = min(min(A(:,2:3:end)));
min_z = min(min(A(:,3:3:end)));

figure(1); 
clf;
axis([min_x max_x min_y max_y min_z max_z]);
hold on;
grid on;
axis equal;

% zobraz prvni snimek
start=100;
P1=33;
P2=84;
a = reshape(A(100,:), 3, nMarkers);
a(:, end+1) = NaN;
H1=plot3(a(1,conn), a(2,conn), a(3,conn), '-b.','markersize',20);
H2=plot3(A(start,1+P1), A(start,2+P1), A(start,3+P1), '-r.','markersize',20);
H3=plot3(A(start,1+P2), A(start,2+P2), A(start,3+P2), '-r.','markersize',20);
H4=plot3(A(start,1+P1), A(start,2+P1), A(start,3+P1), '-r');
H5=plot3(A(start,1+P2), A(start,2+P2), A(start,3+P2), '-r');

pause(60);
    drawnow;

% zobray postupne dalsi snimky
for i=start:5:(length(A)-100)

    a = reshape(A(i,:), 3, nMarkers);
    a(:, end+1) = NaN;
    set(H1,'XData',a(1,conn),'YData',a(2,conn),'ZData',a(3,conn));
    set(H2,'XData',A(i, 1+P1),'YData',A(i, 2+P1),'ZData',A(i, 3+P1));
    set(H3,'XData',A(i, 1+P2),'YData',A(i, 2+P2),'ZData',A(i, 3+P2));
    set(H4,'XData',A(start:i, 1+P1),'YData',A(start:i, 2+P1),'ZData',A(start:i, 3+P1));
    set(H5,'XData',A(start:i, 1+P2),'YData',A(start:i, 2+P2),'ZData',A(start:i, 3+P2));
% zobray postupne dalsi snimky

% t=100:397;

% plot3(A(100, 1+P1)', A(100, 2+P1)', A(100, 3+P1)', 'r.-', 'markersize', 20);
% plot3(A(100, 1+P2), A(100, 2+P2), A(100, 3+P2)', 'r.-', 'markersize', 20);

    print('-dpng', ['mc', num2str(i), '.png']);
    pause(0.001);
    drawnow;
end