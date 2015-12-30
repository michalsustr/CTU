function [theta, parity] = findThetaPar(x, y)
% Returns threshold theta and parity of for a weak classifier.
% Inputs:
%  x .... 1D data used to estimate the theta and parity parameters 
%  y .... +1/-1 labels corresponding to x
%  d .... weights of x
  
  mu_plus = mean(x(y == 1));
  mu_minus = mean(x(y == -1));

  theta = (mu_plus + mu_minus) / 2;
  parity = (mu_plus > mu_minus) * 2 - 1;

  return;
  