load data_33rpz_cv09.mat;

%% Settings
digit=2;
T=40; % number of steps of AB

%% Prepare train & test data

X = reshape(trn_data.images, 13*13, []);
y = -ones(1,size(X,2));
y(trn_data.labels == digit) = 1;

X_tst = reshape(tst_data.images, 13*13, []);
y_tst = -ones(1,size(X,2));
y_tst(tst_data.labels == digit) = 1;

%% AdaBoost algorithm

% prepare "weak" classifiers
theta=zeros(1,size(X,1));
parity=zeros(1,size(X,1));
for j=1:size(X,1)
    [theta(j), parity(j)] = findThetaPar(X(j,:),y);
end

D = zeros(1, length(y));
D(:) = 1/length(y);

h=zeros(1, size(X,1));
alpha=zeros(1, size(X,1));
e=zeros(1, size(X,1));
error=zeros(1, size(X,1));
error_tst=zeros(1, size(X,1));
error_WC=zeros(1, size(X,1));
selected_classifiers=zeros(1, size(X,1));
Z=zeros(1, size(X,1));

for t=1:length(h) % T
   for j=1:size(X,1)
      h(j)=sum(D.*(y ~= sign(parity(j) * (X(j,:)-theta(j))))); % vyhodit von
   end
   [e(t),ht] = min(h); 
   selected_classifiers(t) = ht;
   
   if(e(t) > 0.5) disp('End, e > 1/2'); break; end;
   
   %alpha(t)=1-e(t);
   alpha(t)=0.5*log((1-e(t))/e(t));
   
   D=D.*exp(-alpha(t) * y.* sign(parity(ht) * (X(ht,:)-theta(ht)))); % vyhodit von
   %D=D.*(-alpha(t) * y.* sign(parity(ht) * (X(ht,:)-theta(ht))));
   Z(t)=sum(D);
   D=D/Z(t); % normalization
   
   
%   error(t) = y ~= sign(alpha(t)* sign(parity(t) * (X(t,:)-theta(t))));

    % calculate error
    ah=zeros(length(h), size(X,2));
    ah_tst=zeros(length(h), size(X,2));
    for et=1:t
        ah(et,:)=alpha(et)* sign(parity( selected_classifiers(et)  ) * (X( selected_classifiers(et) ,:)-theta( selected_classifiers(et) )));
        ah_tst(et,:)=alpha(et)* sign(parity( selected_classifiers(et) ) * (X_tst( selected_classifiers(et) ,:)-theta( selected_classifiers(et))));
    end
    error(t)=sum(sign(sum(ah, 1)) ~= y)/length(y);
    error_tst(t)=sum(sign(sum(ah_tst, 1)) ~= y_tst)/length(y_tst);

end

% % final classifier
% ah=zeros(length(h), size(X,2));
% for t=1:length(h)
%     ah(t,:)=alpha(t)* sign(parity(t) * (X(t,:)-theta(t)));
% end
% class=sign(sum(ah, 1));

%% plot errors
figure, hold on;
title(['Adaboost, digit ' num2str(digit), ' exp']), xlabel('training step'), ylabel('error');
plot(error, 'b'); 
plot(error_tst, 'r');
plot(e, 'k');
plot(cumprod(Z), 'g');
legend('train data error', 'test data error', 'WC error');

%% plot how many times what classifiers have been used
figure, hold on;
[x,y]=num_repetition(selected_classifiers);
stem(x,y);
title(['Adaboost classifier usage, digit ' num2str(digit)]);
xlabel('classifier'), ylabel('number of times it has been used');
