function [ model ] = svmperceptron( data, options )
%SVMPERCEPTRON
%
% INPUT:
%   data         : struct [ 1xn ] data for training the classifier
%					.X
%					.y
%   options      : struct [ --- ]
%					.ker  
%				 	.arg  
%  					.C    
%
% OUTPUT:
%   model   ... struct
%     .W
%	  .b    
%	  .fun  ... 'linclass'
%
%

%% Prepare input

	data = c2s(data);

	if ( ~exist('options', 'var') ),
		options = struct('ker', 'linear', 'arg', [], 'C', 1, 'verb', 1, 'tmax', 1000);
	else
		options = c2s(options);
	end;
    
    if ( ~isfield(options, 'ker') ),
        options.ker = 'linear';
    end;

    if ( ~isfield(options, 'arg') ),
    	options.arg = [];
    end;  

	if ( ~isfield(options, 'C') ),
		options.C = 1;
	end;

%% Initialization

	X = data.X;
	y = data.y;
	
	K = kernel(X, options.ker, options.arg);
	C = options.C;

	n = size(X, 2);

	f = -1 * ones(n, 1);
	a = y';
	b = zeros(n, 1);
	H =  y' * y .* K;

	LB = zeros(n, 1);
	UB = ones(n, 1) * C;

	%% Prepare output

	clear model;

	%% Training

    params.D = H;
    params.f = f;
    params.a = a;
    params.b = b;
    params.LB = LB;
    params.UB = UB;
    params.options = options;
    model.params = params;
    
	[x1, ~, stat] = gsmo(H, f, a, b, LB, UB, [], [], options);
	model.stat = stat;

	model.W(1,:) = x1' .* y .* X(1,:);
	model.W(2,:) = x1' .* y .* X(2,:);

	model.W = sum(model.W,2);

	b = 0;

	idx = find(x1 ~= 0);

	for i1=1:length(idx),
		ii = idx(i1);
		tempsum = 0;
		for jj=1:n,
			tempsum = tempsum + x1(jj) * y(jj) * K(jj,ii);
		end;
		b = b + (y(ii) - tempsum);
	end;

	model.b = 1/length(idx) * b;

%% Fill output

	model.options = options;

	idx = find(x1 ~= 0);

	model.Alpha = x1(idx) .* data.y(idx)';
	model.sv.X = data.X(:,idx);
	model.sv.inx = idx;
    model.h = x1;
    model.w = model.W;
	% model.sv.y = data.y(:,idx);

	model.fun = 'svmclass';

end