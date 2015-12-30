% 1)
load data
load geneNames
X = data.exprs;
y = data.classes;
% '2' ... AML
% '1' ... ALL

% learning the tree on complete data
tree = ClassificationTree.fit(X,y); 
%%
% 2)

tree.view('mode','graph')
% and its TRAINING accuracy
mean(y==tree.predict(X))

% Gene ZYX (4847) and STXBP2 (88) are crucial for decision, and another unnamed gene at
% index 2
% 100% Accuracy

%%
% 3)  Estimation of real accuracy of the tree by the means of crossvalidation
cv = cvpartition(length(y),'kfold',10);

PA = []; % predictive accuracy
for i = 1:10
    trI = cv.training(i);
    tstI = cv.test(i);
    
    % tree learning
    sub_tree = ClassificationTree.fit(X(trI, :),y(trI)); 
    
    % tree testing
    pa = (y(tstI)==sub_tree.predict(X(tstI, :))); % accuracy in THIS fold
    PA = [PA; pa];
end
mean(PA)

% 80% Accuracy using 10-fold crossvalidation

%%
% % % % % % % % % % % % % % % % % % %
% Analyzing the data in terms of PCA

% 1)  
% learning principal components of the data
V = pca(X);

% 2) - 3)
PAs = []; % predictive accuracies of trees based on subsequent decompositions
for K = [5 10 20 50]

    % transforming the data into top K components
    Z = X*V(1:K,:)';
    
    % Here learn a tree on Z
    tree = ClassificationTree.fit(Z,y); 

    % show it and enumearate its TRAINING accuracy
    tree.view('mode','graph')
    PA = mean(y==tree.predict(Z));  % training accuracy of the tree learned on top K data components
    
    PAs = [PAs; PA];
end

% K = 5  PA = 0.9861
% K = 10 PA = 1
% K = 20 PA = 0.9861
% K = 50 PA = 1

%%
% 4) 
% For the optimal K_opt, heuristically chosen according to the criteria
% above...

K_opt = 20; % optimal number of copmponents

% Make an optimal data transformation
Z_opt = X*V(1:K_opt,:)';

% And estimate the real accuracy of emerging tree-model by the means of 
% crossvalidation

PA = [];
for i = 1:10
    trI = cv.training(i);
    tstI = cv.test(i);
           
    % learning principal components of TRAINING data
    V_tr = pca(X(trI, :));
    
    % transformation of TRAINING data into first K_opt of the principal components
    Z_tr = X(trI,:)*V_tr(1:K_opt,:)';
    y_tr = y(trI);
    
    % learning a tree on the transformed data
    tree = ClassificationTree.fit(Z_tr,y_tr); 
    tree.view('mode','graph');
    
    % transformation of TESTING data
    Z_tst = X(tstI,:)*V_tr(1:K_opt,:)';
    y_tst = y(tstI);
    
    % testing the tree on the transformed data
    pa = mean(y_tst==tree.predict(Z_tst)); % accuracy in THIS fold
    
    PA = [PA; pa];
end
mean(PA)

% 84.8% Accuracy using 10-fold crossvalidation

%%
% % % % % % % % % % % % % % 
% Interpreting the results

% indices of the top components in your tree
componentIndices = [% indices of the top components in your tree
    1 4 19 10 3 20 15 13 6
		      ]
compGenes = {}; % genes overrepresented in the top components in your tree
for c = componentIndices
    compGenes = {compGenes, mineGenes(V(c,:), geneNames)};
end

