function fit_circle_graph(type)
load('points.mat', 'A');
if(isempty(A)) 
    error('points.mat neobsahuje maticu A');
end

% vytvori pozadovanu figuru
img = figure;
hold on;
axis([-1.5 1.5 -1.5 1.5]);
plot(A(:,1), A(:,2),'ro');
axis square;

N=100; x1=linspace(-1.5,1.5,N)'*ones(1,N); x2=x1'; r=ones(N);
f=f(A,x1,x2,r);
if(strcmp(type, 'c')) 
    contour(x1,x2,f,[0.0:0.01:1.5; 0.0:0.01:1.5], 'r');
else
    mesh(x1,x2,f);
    axis vis3d;
    view([-45.5 30]);
end


end

% a je dany bod [x,y], x je kruznica [x,y,r]
function d=dist(a,x)
    d=sqrt((a(1)-x(1))^2+(a(2)-x(2))^2)-x(3);
end

% f je ucelova funkcia, vstup matica A, x je mnozina bodov [x1,x2,r]
function f=f(A,x1,x2,r)
    f=zeros(size(x1));
    for(i=1:size(x1,1))
       for(j=1:size(x1,2))
           for(k=1:size(A,1))
               f(i,j)=f(i,j)+dist(A(k,:),[x1(i,j),x2(i,j),r(i,j)])^2;
           end
       end
    end
end