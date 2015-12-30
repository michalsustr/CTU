function [model] = show_svm(data, C)
%SHOW_SVM show plot of SVM computation
%   Also see svmboundary.m

    model = svmboundary(data, C);
    X = data.X;
    y = data.y;

    figure, hold on; axis square; 
    ppatterns(X,y);
    plot(X(1, model.h ~= 0), X(2, model.h ~= 0), 'ro','MarkerSize',12); % highlight support vectors

    pline(model.w, model.b-1, 'k--'); 
    pline(model.w, model.b, 'k');
    pline(model.w, model.b+1, 'k--'); 


end

