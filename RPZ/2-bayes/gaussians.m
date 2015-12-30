%% Author: Michal Sustr
% sustrmic@fel.cvut.cz

%% Load data
load data_33rpz_cv02.mat;

%% Get thresholds
% get intersections of the two gaussians
mu_A = D1.Mean;
s_A  = D1.Sigma;
p_A  = D1.Prior;
mu_C = D2.Mean;
s_C  = D2.Sigma;
p_C  = D2.Prior;

% There are 2 solutions in this case, but could be also only one, 
% if means of the gaussians are equal.
% This equations if derived from the equality p(A)*p(x|A) = p(C)*p(x|C)
% p(x|K) is normal distribution, apply a logarithm to solve these equations
sol= solve('log(p_A)-(x-mu_A)^2/(2*s_A^2)-log(s_A*sqrt(2*pi))=log(p_C)-(x-mu_C)^2/(2*s_C^2)-log(s_C*sqrt(2*pi))', 'x');
t1 = eval(sol(1))
t2 = eval(sol(2))

%% plot gaussians with thresholds
figure; hold on; 
title('theoretical (curves) and empirical (bars) distributions of measurement x according to classification');
pgmm(D1);
pgmm(D2);

% top of the curve is where the mean is located => exponent is 0
top1 = D1.Prior/(D1.Sigma*sqrt(2*pi));
top2 = D2.Prior/(D2.Sigma*sqrt(2*pi));
top = max(top1, top2); % could be also a=axis; top=a(4);
p1=plot([t1 t1], [0 top], 'g');
p2=plot([t2 t2], [0 top], 'r');
legend([p1,p2], 'threshold t1', 'threshold t2');

%% Calculate x for one image

posA=strfind(Alphabet, 'A'); % Get position of A
posC=strfind(Alphabet, 'C'); % Get position of C
indA=find(labels==posA);     % Get labels indices
indC=find(labels==posC);                

left_half  = 1:size(images,1)/2; % left half indices
right_half = size(images,1)/2+1:size(images,1);

x_A=sum(sum(images(:,left_half,indA))) - sum(sum(images(:, right_half, indA)));
x_C=sum(sum(images(:,left_half,indC))) - sum(sum(images(:, right_half, indC)));

x_A=permute(x_A, [3 2 1]); % rearrange to get a row vector
x_C=permute(x_C, [3 2 1]);

[N, u] = hist(x_A);
N = N / ( sum(N) );          % normalizes to sum(N)=1
b1=bar(u,N*top1/max(N),'g'); % normalize to the top of the gaussian curves

[N, u] = hist(x_C);
N = N / ( sum(N) );
b2=bar(u,N*top2/max(N),'b');

legend([b1,b2], 'A', 'C');

%% Classify each image as A or C
x=sum(sum(images(:, left_half, :))) - sum(sum(images(:, right_half, :)));
posA=find(x > t1 | x < t2); % letter A is outside gaussian
posC=find(x < t1 & x > t2); % letter C is inside gaussian
letters_C = images(:,:, posC);
letters_A = images(:,:, posA);

% display letters
letters_A=permute(letters_A, [1 2 4 3]);
figure;
montage(letters_A, [0 255]);
title('letters A');

letters_C=permute(letters_C, [1 2 4 3]);
figure;
montage(letters_C, [0 255]);
title('letters C');

%% Calculate error
error=(length(find(labels(posA) == 2)) + ... % wrongly classified letters C as A
       length(find(labels(posC) == 1))) ...  % wrongly classified letters A as C
       /length(labels)