function x=fit_circle(method, c0, r0, krok)
% nastavi default hodnotu
if(~exist('method'))
    method='GN';
elseif(~strcmp(method, 'GN') && ~strcmp(method,'LM'))
    error('Zle zadana metoda');
end
if(~exist('krok'))
    krok=10; 
end


% nacitame vsetky body
load('points.mat', 'A');
if(isempty(A)) 
    error('points.mat neobsahuje maticu A');
end

% vytvori pozadovanu figuru
img = figure;
hold on;
axis([-5 5 -5 5]);
axis square;
%vykresli osi x,y
%plot([-5 5], [0 0], 'k-');
%plot([0 0], [-5 5], 'k-');

% vykresli zadane body
% zmenil som farbu oproti zadaniu, lebo zltu nie je skoro vobec vidiet
plot(A(:,1), A(:,2), 'rx'); 

% ziska hodnoty pre pociatocnu kruznicu
if(exist('c0') && ~isempty(c0))
    begin_point = plot(c0(1),c0(2),'ro');
    cx = c0(1);
    cy = c0(2);
else
    [c0x,c0y] = ginput(1);
    begin_point = plot(c0x,c0y,'ro');
    cx = c0x;
    cy = c0y;
end
if(exist('r0') && ~isempty(r0))
    r = sqrt((cx-r0(1))^2+(cy-r0(2))^2); 
else
    [r0x,r0y] = ginput(1);
    r = sqrt((cx-r0x)^2+(cy-r0y)^2); 
end

% pomocne premenne
H = [];
lm_before = -1;
mikro = 1000;
k = 1;
%krok = 10;


while true
    if ~isempty(H)
        delete(H); % vyprazdni predoslu namalovanu kruznicu
        if(~isempty('begin_point')) % vymaze pociatocny cerveny bod
            delete(begin_point);
            begin_point=[];
        end
    end
    
    H = circle([cx,cy], r, 1000, '--');
    
    [~,~,but] = ginput(1);
    if isempty(but) % stlaceny ENTER
        break;
    elseif but == 32 % stlaceny SPACE, spravime dalsiu aproximaciu
        g=g(A,[cx,cy,r]);
        dg=dg(A,[cx,cy,r]);
        
        if(method == 'GN')
            new = [cx;cy;r]-pinv(dg)*g;
            cx=new(1);
            cy=new(2);
            r=new(3);
            
            disp(['Iteracia k=', num2str(k)]);
            disp('Nove hodnoty kruznice');
            disp([cx,cy,r]);
            disp('Hodnota kriterialnej funkcie');
            disp(f(A,[new(1),new(2),new(3)]));
            
            k=k+1;
        elseif(method == 'LM')
            if(lm_before == -1)
                lm_before=f(A,[cx,cy,r]);
            end
            
            new = [cx;cy;r]-inv(dg'*dg+mikro)*dg'*g;

            lm_new=f(A,[new(1),new(2),new(3)]);
            
            % zmensila sa hodnota kriterialnej funkcie
            % danu iteraciu prijmeme a zmensime hodnotu k
            if(lm_new <= lm_before) 
                lm_before = lm_new;
                disp(['Iteracia k=', num2str(k)]);
                disp(['Prijala sa iteracia s koeficientom mikro', num2str(mikro)]);
                mikro=mikro/krok;
                cx=new(1);
                cy=new(2);
                r=new(3);
                
                disp('Nove hodnoty kruznice');
                disp([cx,cy,r]);
                disp('Hodnota kriterialnej funkcie');
                disp(lm_new);
                
                k=k+1;
            else
                % hodnota kriterialnej funkcie sa zvacsila
                % danu iteraciu zahodime a zvacsime k
                disp(['Zahodila sa iteracia s koeficientom mikro', num2str(mikro)]);
                mikro=mikro*krok;
            end
        end
    end
end
x=[cx,cy,r];
end

function H=circle(center,radius,NOP,style)
%---------------------------------------------------------------------------------------------
% H=CIRCLE(CENTER,RADIUS,NOP,STYLE)
% This routine draws a circle with center defined as
% a vector CENTER, radius as a scaler RADIS. NOP is 
% the number of points on the circle. As to STYLE,
% use it the same way as you use the rountine PLOT.
% Since the handle of the object is returned, you
% use routine SET to get the best result.
%
%   Usage Examples,
%
%   circle([1,3],3,1000,':'); 
%   circle([2,4],2,1000,'--');
%
%   Zhenhai Wang <zhenhai@ieee.org>
%   Version 1.00
%   December, 2002
%---------------------------------------------------------------------------------------------

if (nargin <3),
 error('Please see help for INPUT DATA.');
elseif (nargin==3)
    style='r-';
end;
THETA=linspace(0,2*pi,NOP);
RHO=ones(1,NOP)*radius;
[X,Y] = pol2cart(THETA,RHO);
X=X+center(1);
Y=Y+center(2);
H=plot(X,Y,style);
end

% a je dany bod [x,y], x je kruznica [x,y,r]
function d=dist(a,x)
    d=sqrt((a(1)-x(1))^2+(a(2)-x(2))^2)-x(3);
end

% A je vstupna matica A, x je kruznica [x,y,r]
function g=g(A,x)
    g=zeros(size(A,1),1);
    for(i=1:size(A,1))
       g(i) = dist(A(i,:), x);
    end
end

% A je vstupna matica A, x je kruznica [x,y,r]
function dg=dg(A,x)
    dg=zeros(size(A,1),3);
    for(i=1:size(A,1))
       dg(i,1) = (x(1)-A(i,1))/sqrt((A(i,1)-x(1))^2+(A(i,2)-x(2))^2);
       dg(i,2) = (x(2)-A(i,2))/sqrt((A(i,1)-x(1))^2+(A(i,2)-x(2))^2);
       dg(i,3) = -1;
    end
end

% f je ucelova funkcia, vstup matica A, x je kruznica [x,y,r]
function f=f(A,x)
    f=0;
    for(i=1:size(A,1))
       f=f+dist(A(i,:),x)^2;
    end
end