function plot_gaussian(D1,D2, color)
%% Get thresholds
    % get intersections of the two gaussians
    mu_A = D1.Mean;
    s_A  = D1.Sigma;
    p_A  = D1.Prior;
    mu_C = D2.Mean;
    s_C  = D2.Sigma;
    p_C  = D2.Prior;
    
    D1.Cov = D1.Sigma^2;
    D2.Cov = D2.Sigma^2;

    % There are 2 solutions in this case, but could be also only one, 
    % if means of the gaussians are equal.
    % This equations if derived from the equality p(A)*p(x|A) = p(C)*p(x|C)
    % p(x|K) is normal distribution, apply a logarithm to solve these equations
    sol= solve('log(p_A)-(x-mu_A)^2/(2*s_A^2)-log(s_A*sqrt(2*pi))=log(p_C)-(x-mu_C)^2/(2*s_C^2)-log(s_C*sqrt(2*pi))', 'x');
    t1 = eval(sol(1))
    t2 = eval(sol(2))

    %% plot gaussians with thresholds
    hold on; 
    options.color = color;
    pgmm(D1, options);
    pgmm(D2, options);

    % top of the curve is where the mean is located => exponent is 0
    top1 = D1.Prior/(D1.Sigma*sqrt(2*pi));
    top2 = D2.Prior/(D2.Sigma*sqrt(2*pi));
    top = max(top1, top2); % could be also a=axis; top=a(4);
    p1=plot([t1 t1], [0 top], 'g');
    p2=plot([t2 t2], [0 top], 'r');
    legend([p1,p2], 'threshold t1', 'threshold t2');

end