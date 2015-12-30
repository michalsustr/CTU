load czechRepublic.mat;

x = [0 1.26 2.51 3.77 5.03 6.28];
y = [0.01 1.16 0.70 -0.34 -0.80 0.2100];

g = @(k,i,j) (((y(j)-y(i)) / (x(j) - x(i))) * (x(k) - x(i)) + y(i));
p = 2;

c = zeros(length(x));
for i=1:length(x)
    for j=1:length(x)
        G = 0;
        for k=(i+1):(j-1)
            G = G+(y(k)-g(k,i,j)^2);
        end
        c(i,j) = 1+p*G;
    end
end


%%

clear all;
close all;

% linearni interpolace, pak vzdalenost od bodu ve smeru y
val = @(x1,y1,x2,y2,x,y) abs(y - (((x - x1)/(x2 - x1))*y1+(1-((x - x1)/(x2 - x1)))*y2));

% matice distance
c = [ inf 2 (2+10*(val(0.8,0.1,2.9,1.3,2.15,0.7)^2)) (2+10*(val(0.8,0.1,4.1,0.65,2.15,0.7)^2 + val(0.8,0.1,4.1,0.65,2.9,1.3)^2)); ...
      inf inf 2 (2+10*(val(2.15,0.7,4.1,0.65,2.9,1.3)^2));...
      inf inf inf 2; ...
      inf inf inf inf]

N = size(c,1);
U = inf(1,N);
U(1) = 0;
previous = zeros(N,1);
next = inf(N,1);
replacement = zeros(1,N);

% dijkstra
for i=1:N         % while pres vsechny nenavstivene vrcholy
    next = inf(N,1);
    next(previous == 0) = U(previous == 0);
    [dis,id] = min(next);
    previous(id) = id;
    P = (c(id,:)+U(id));     
    replacement(P<U) = id;
    U(P<U) = P(P<U); 
end

%cesta
route = N;
i = N;
while (i ~= 0)
    route = [replacement(i) route];
    i = replacement(i);
end
route = route(2:end)



%%





clear all;
close all;
tic;
% matice distance
load ('czechRepublic.mat');
alpha = 300;
beta = 1;

% vytvoreni matice
limit = 100;
c = Inf(size(x,1));

b = repmat(x',size(x,1),1) - repmat(x,1,size(x,1));
a = - repmat(y',size(y,1),1) + repmat(y,1,size(y,1));
U =  - a.*repmat(x',size(y,1),1) - b.*repmat(y',size(x,1),1);
div = sqrt(a.^2 + b.^2);

% for prs radky
for i = 1:size(x,1)

    %osetreni preteceni
    if ((i+limit) > size(x,1))
        offset = (i+limit) - size(x,1);
    else 
        offset = 0;
    end
    
    % prochazeni do bodu urceni
    for j = (i+2):(i+limit - offset)
        c(i,j) = alpha + beta*abs(sum((a(i,j))*x((i+1):(j-1)) + b(i,j)*y((i+1):(j-1)) + U(i,j)))/div(i,j);
    end
end

N = size(c,1);
U = inf(1,N);
U(1) = 0;
previous = zeros(N,1);
next = inf(N,1);
replacement = zeros(1,N);

% dijkstra
for i=1:N         % while pres vsechny nenavstivene vrcholy
    next = inf(N,1);
    next(previous == 0) = U(previous == 0);
    [dis,id] = min(next);
    previous(id) = id;
    P = (c(id,:)+U(id));     
    replacement(P<U) = id;
    U(P<U) = P(P<U); 
end

%cesta
route = N;
i = N;
while (i ~= 0)
    route = [replacement(i) route];
    i = replacement(i);
end
route = route(2:end)
toc;


% vykresleni
step = 20;
plot(x(1:step:size(x,1)),y(1:step:size(y,1)),'x-');
axis equal;
hold on;
plot(x(route),y(route),'r+--');
hold off;

%%
