load data_33rpz_parzen.mat;

trn = trn_2000;

%% Calculate measurements x
left_half  = 1:size(trn.images,2)/2; % left half indices
right_half = size(trn.images,2)/2+1:size(trn.images,2);

x=sum(sum(trn.images(:, left_half, :))) - sum(sum(trn.images(:, right_half, :)));
x=permute(x, [3 2 1]); % rearrange to get a column vector

X1=x(trn.labels == 1);
X2=x(trn.labels == 2);

%%

figure, hold on;
[cdfF,cdfX] = ecdf(X1); [cdfN,cdfC] = ecdfhist(cdfF,cdfX); bar(cdfC,cdfN);

x=min(X1):(max(X1)-min(X1))/50:max(X1);
sigma = 2000;
p_x1 = my_parzen(x,X1,sigma);

plot(x,p_x1,'r', 'LineWidth', 2);

%% Separate data into training and testing set, find optimal sigma

sigma=100:20:1000;
L_sigma = zeros(10, length(sigma));
X=X1; % one of X1, X2

for i = 1:10
    if(i ~= 10)
        itst=(i-1)*floor(length(X)/10)+1:i*floor(length(X)/10);
    else
        itst=(i-1)*floor(length(X)/10)+1:length(X);
    end
    itrn=1:length(X);
    itrn(itst) = [];
 
    for j=1:length(sigma)
        p_X = my_parzen(X(itst), X(itrn), sigma(j));
        L_sigma(i,j) = sum(log(p_X));
    end
end

figure; hold on;
L_sigma_avg = mean(L_sigma);
[L_max,L_sigma_opti] = max(L_sigma_avg);
Sigma_opt = sigma(L_sigma_opti);

plot(sigma, L_sigma_avg);
xlabel('Sigma \Sigma');
ylabel('L(\Sigma');
plot(Sigma_opt, L_max, 'ro');

%% Classify test data

pA=length(find(trn.labels == 1))/length(trn.labels);
pC=length(find(trn.labels == 2))/length(trn.labels);

% Classify test data
x=sum(sum(tst.images(:, left_half, :))) - sum(sum(tst.images(:, right_half, :)));
x=permute(x, [3 2 1]); % rearrange to get a column vector

p_xA = my_parzen(x,X1,Sigma_opt);
p_xC = my_parzen(x,X2,Sigma_opt);

figure, hold on;
[N, u] = hist(x(tst.labels == 1), 50);
b2=bar(u,N/max(N)*max(p_xA),'g');
[N, u] = hist(x(tst.labels == 2), 50);
b2=bar(u,N/max(N)*max(p_xA),'b');
plot(x,p_xA*pA,'ro', 'LineWidth', 2);
plot(x,p_xC*pC,'bo', 'LineWidth', 2);

posA=find(p_xA*pA > p_xC*pC); % indices of letters that we classify as A
posC=find(p_xA*pA <= p_xC*pC); % indices of letters that we classify as A

error=(length(find(tst.labels(posA) == 2)) + ...    % wrongly classified C as A
       length(find(tst.labels(posC) == 1)) ...      % wrongly classified A as C
      )/length(tst.labels)