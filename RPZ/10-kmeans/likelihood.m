function L = likelihood(X,M,C,P)   
% LIKELIHOOD evaluates likelihood of gaussian mixture model
%
% Synopsis:
% L = likelihood(X,M,C) 
% 
% Input arguments:
%  
% X ... D x N array of measurments, N is number of measurments, D is dimension
% M ... D x K array of K means
% C ... D x D x K array of covariance matrices
% P ... 1 x K vector with weights of gaussians
%
% Output arguments:
%
% L ... likelihood
%
L = 0;
for j=1:size(M,2)
   C(:,:,j) = C(:,:,j) + 1e-6*eye(size(C,1)); 
end;
for i=1:size(X,2)   
    S = 0;
    for j=1:size(M,2)
        D = X(:,i) - M(:,j);
        S = S + P(j)* min([exp(-0.5*D'*inv(C(:,:,j))*D) / (2*pi*sqrt(det(C(:,:,j)))),0.9999]);
        if S>1
            keyboard;
        end;
    end;
    L = L + log(S);        
end;