load data_33rpz_cv03_minmax.mat;
% I choose letters A and B as the two letters that will be used throught
% the homework - we have a two class problem only

%% 2) I. calculate values of apriori probability p(A)
DA = D(1);
DB = D(4);

pA_array=0:0.01:1;
error=zeros(length(pA_array), 3);
for pA_index=1:length(pA_array)
    pA=pA_array(pA_index);
    pB = 1-pA;
    
    DA.Prior = pA;
    DB.Prior = pB;
    [risk,eps1,eps2]=bayeserror(DA,DB);
    error(pA_index, :) = [risk,eps1,eps2];
end

figure; hold on;
plot(pA_array, error(:, 1), 'b');
xlabel('P(A)'), ylabel('Risk');

%% 2) II. Risk of Bayesian strategy which is optimal for p('A') = 0.25. 
DA.Prior=0.25; DB.Prior = 1-DA.Prior;
[risk, eps1, eps2, interval] = bayeserror(DA,DB);
disp('Risk of Bayesian strategy for p(A)=0.25');
disp(risk); % DA.Prior*(eps1-eps2)+eps2
plot(pA_array, pA_array*(eps1-eps2)+eps2, 'k');

% plot two gaussians, p(A) = 0.25 and p(A) = 0.5
%figure;
%plot_gaussian(DA,DB,'g');
%DA.Prior = 0.5; DB.Prior = 0.5;
%plot_gaussian(DA,DB,'b');

%% 2) III. Optimal Bayesian strategy, worst-case risk
worstCase = max([0;1]*(error(:,2)-error(:,3))'+[error(:,3), error(:,3)]');
plot(pA_array, worstCase, 'r');

%% 3) Find the minimax strategy and its risk
[v,i]=min(worstCase); 
disp('Risk of minimax strategy');
disp(v);
disp('Apriori probability');
disp(pA_array(i));

legend('Bayes risk', 'Fixed bayes risk', 'Minimax');