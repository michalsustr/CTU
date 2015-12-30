function [model] = show_nonlinear_svm(data, options)
%SHOW_SVM show plot of SVM computation
%   Also see svmboundary.m

    model = svmboundary(data, options);
    X = data.X;
    y = data.y;

    figure, hold on; axis square; 
    ppatterns(X,y);
    plot(X(1, model.h ~= 0), X(2, model.h ~= 0), 'ro','MarkerSize',12); % highlight support vectors

    pb_model = model;
    pb_model.fun = 'svm_nonlinear_show_boundary';
    pboundary(pb_model);

end

