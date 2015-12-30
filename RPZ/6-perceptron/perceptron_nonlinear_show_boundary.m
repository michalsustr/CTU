function [ class ] = perceptron_nonlinear_show_boundary( X, model )
    X = [X(1,:); X(2,:); X(1,:).^2; X(1,:).*X(2,:); X(2,:).^2 ones(1, size(X,2))];
    class = sign(model.w'*X);
end

