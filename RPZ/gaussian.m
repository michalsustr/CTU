function y=gaussian(x, mu, s)  % x, mean, dev
    y=exp(-((x-mu).^2)/(2*s^2))/(s*sqrt(2*pi));
end