function [ class ] = svm_nonlinear_show_boundary( X, model )
    assert(strcmp(model.options.ker, 'rbf'), 'Only rbf kernel is supported for showing boundary');
        
    % rbf kernel is K(a,b)=exp(-0.5*||a-b||^2/sigma^2)
    K=exp( -sum((repmat(permute(model.sv,[2 3 1]), 1, size(X, 2)) - repmat(permute(X,[3 2 1]), size(model.sv, 2), 1)).^2, 3) / (2*model.options.arg^2) );
    class = double((model.alpha'*K+model.b) > 0);
    class(class == 1) = 1;
    class(class == 0) = -1;
end

