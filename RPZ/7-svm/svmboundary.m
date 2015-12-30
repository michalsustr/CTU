function [model] = svmboundary(data, C)
%svmboundary Find the boundary line between two sets of data
% 
% Input
%  data [struct]
%    .X [2 x n] set of data with two coordinates x1 and x2
%    .y [1 x n] set of pertaining classes, either +-1
%  C [double]
%    Constant for soft margins, default Inf
%
% Output
%  model [struct] 
%    Line equation 0=wx+b
%    .w [2 x 1] 
%    .b [1 x 1] y-intercept of the line
%    Output from quadratic optimization
%    .h [n x 1] weight vector
    
    y=data.y(:)';
    X=data.X;
    
    % validation of input
    assert(size(X, 1) == 2, 'Bad size for data.X');
    assert(size(X, 2) == size(y,2), 'Size of data.X and data.y do not match');
      % cannot write it as assert(unique(y) == [-1 1]), input must be logical
    assert(min(unique(y)) == -1 && max(unique(y)) == 1 && numel(unique(y)) == 2, 'Classes of y must be either +-1');
    if(~exist('C', 'var'))
		C = Inf;
    end

    % Prepare matrices for quadratic programming, see
    % http://cmp.felk.cvut.cz/cmp/courses/recognition/33RPZ_lectures_2009w/lecture_svm-2.pdf
    D = ((X'*X).*(y'*y));
    f = -ones(numel(y), 1);
    a = y';
    b = zeros(numel(y), 1);
    LB= zeros(numel(y), 1);
    UB= ones(numel(y), 1)*C;
    
    options.tmax = 10000;
    
    % gsmo(H,f,a,b,LB,UB,x0,Nabla0,options)
    h=gsmo(D,f,a,b,LB,UB,[],[],options);
    
    model.w = sum(repmat( (h .* y'), 1,2) .* X')';
    
    % find average value of b from all of the support vectors
    b = 0;
	idx = find(h ~= 0);
	for i=1:numel(idx)
		ii = idx(i);
		tempsum = 0;
		for j=1:numel(idx)
            jj = idx(j);
			tempsum = tempsum + h(jj) * y(jj) * X(:,jj)'*X(:,ii);
        end
		b = b + (y(ii) - tempsum);
    end

	model.b = 1/length(idx) * b;
    model.h = h;
end

