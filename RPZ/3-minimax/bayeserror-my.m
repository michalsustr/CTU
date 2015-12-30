function [risk,epsA,epsC,interA] = bayeserror(D1,D2)
    % get intersections of the two gaussians
    mu_A = D1.Mean;
    s_A  = D1.Sigma;
    p_A  = D1.Prior;
    mu_C = D2.Mean;
    s_C  = D2.Sigma;
    p_C  = D2.Prior;

    sol= solve('log(p_A)-(x-mu_A)^2/(2*s_A^2)-log(s_A*sqrt(2*pi))=log(p_C)-(x-mu_C)^2/(2*s_C^2)-log(s_C*sqrt(2*pi))', 'x');
    
    if(length(sol) == 2) 
        x1 = eval(sol(1));
        x2 = eval(sol(2));
        t1=min(x1,x2);
        t2=max(x1,x2);

        % find out which gaussian is between t1 and t2
        g1=gaussian(mean([t1 t2]), mu_A, s_A);
        g2=gaussian(mean([t1 t2]), mu_C, s_C);


        if(g1 > g2) % A is in between
            interA = [t1 t2]; % one interval
            epsA = 1+normcdf(t1,mu_A,s_A)-normcdf(t2,mu_A,s_A);
            epsC =   normcdf(t2,mu_C,s_C)-normcdf(t1,mu_C,s_C);
        else % C is in between
            interA = [-Inf t1 t2 Inf]; % two intervals
            epsC = 1+normcdf(t1,mu_C,s_C)-normcdf(t2,mu_C,s_C);
            epsA =   normcdf(t2,mu_A,s_A)-normcdf(t1,mu_A,s_A);
        end

        risk = p_A*( epsA - epsC ) + epsC;
    else
        warning('not implemented');
    end
end

function y=gaussian(x, mu, s)  % x, mean, dev
    y=exp(-((x-mu)^2)/(2*s^2))/(s*sqrt(2*pi));
end