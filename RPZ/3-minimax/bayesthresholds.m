function [t1,t2] = bayesthresholds(D1,D2)
    % calculate thresholds
    mu_A = D1.Mean;
    s_A  = D1.Sigma;
    p_A  = D1.Prior;
    mu_C = D2.Mean;
    s_C  = D2.Sigma;
    p_C  = D2.Prior;

    sol= solve('log(p_A)-(x-mu_A)^2/(2*s_A^2)-log(s_A*sqrt(2*pi))=log(p_C)-(x-mu_C)^2/(2*s_C^2)-log(s_C*sqrt(2*pi))', 'x');
    t1 = eval(sol(1));
    t2 = eval(sol(2));
end