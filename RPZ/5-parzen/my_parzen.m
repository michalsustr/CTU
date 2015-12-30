function y = my_parzen(x,X,sigma)
    y=sum( NORMPDF( repmat(x(:)', length(X), 1) - repmat(X(:), 1, length(x)), 0, sigma))./length(X);

%     y = 0;
%     for i=1:length(X)
%         y= y+NORMPDF(x-X(i), 0, sigma);
%     end
%     y=1/length(X)*y;
end