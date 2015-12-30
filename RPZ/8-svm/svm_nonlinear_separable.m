load data_nonlinear_separable
%%
data.X = X; data.y = y;
options.ker = 'rbf'; options.arg = 0.1; options.C = Inf;
show_nonlinear_svm(data, options);

