load data_33rpz_cv04.mat;

%% Estimate apriori probabilities
trn=trn_2000;

pA=length(find(trn.labels == 1))/length(trn.labels);
pC=length(find(trn.labels == 2))/length(trn.labels);

%% Compute parameters of normal distributions

% calculate values of measurement
left_half  = 1:size(trn.images,2)/2; % left half indices
right_half = size(trn.images,2)/2+1:size(trn.images,2);

x=sum(sum(trn.images(:, left_half, :))) - sum(sum(trn.images(:, right_half, :)));
x=permute(x, [3 2 1]); % rearrange to get a column vector

posA = find(trn.labels == 1);
posC = find(trn.labels == 2);

figure, hold on;

[N, u] = hist(x(posA), 20);
b2=bar(u,N/max(N)*pA,'b');
legend(b2, 'letter A');

[N, u] = hist(x(posC), 20);
b2=bar(u,N/max(N)*pC,'g');
legend(b2, 'letter C');

MeanA = mean(x(posA));
SigmaA  = sqrt(1/length(posA)* sum( (x(posA)-MeanA).^2 ));

MeanC = mean(x(posC));
SigmaC  = sqrt(1/length(posC)* sum( (x(posC)-MeanC).^2 ));

range = min(x):(max(x)-min(x))/1000:max(x);
y1 = pdf('normal', range, MeanA, SigmaA);
y2 = pdf('normal', range, MeanC, SigmaC);
plot(range,y1/max(y1)*pA, 'b'); 
plot(range,y2/max(y2)*pC, 'g');
legend('letter A', 'letter B');

% calculate thresholds
DA.Mean = MeanA;   DC.Mean = MeanC;
DA.Sigma = SigmaA; DC.Sigma = SigmaC;
DA.Prior = pA;     DC.Prior = pC;
[t1,t2] = bayesthresholds(DA,DC);

%% Plot log-likelihood function

range=SigmaA*0.5:SigmaA*0.01:SigmaA*1.5;

L=[];
for sigma=range
    L=[L sum( log( exp(-((x(posA)-MeanA).^2)/(2*sigma^2))/(sigma*sqrt(2*pi)) ) )];
end

figure;
plot(range, L);
xlabel('\sigma');
ylabel('L(T | \theta)');

%% Classify test data
x=sum(sum(tst.images(:, left_half, :))) - sum(sum(tst.images(:, right_half, :)));
x=permute(x, [3 2 1]); % rearrange to get a column vector

posA=find(x > t1 | x < t2); % A is outside gaussian
posC=find(x < t1 & x > t2); % C is inside gaussian

error=(length(find(tst.labels(posA) == 2)) + ...    % wrongly classified C as A
       length(find(tst.labels(posC) == 1)) ...      % wrongly classified A as C
      )/length(tst.labels)