function [P trace] = viterbi(O, A, B, PI)
% VITERBI algorithm
% Implementation of viterbi algorithm, calculates probability of
% observation sequence O given the hidden state probabilities A and
% emission probabilities B and prior probabilities PI
% 
% Trace are indices of the most probable sequence of hidden states

  T = length(O);
  Delta(1,:) = (PI .* B(:,O(1))) ;
%   [~,maxIdx] = max(Delta(1,:));
%   Psi(1, :) = 
  for t = 2:T
    [~,maxIdx] = max(  repmat(Delta(t-1,:)', 1, size(A,1)) .* A' .* repmat(B(:, O(t))', size(A,2), 1), [], 2);
    Psi(t, :) = maxIdx;
    Delta(t,:) = Delta(t-1, Psi(t,:)) .* diag(A( Psi(t,:), :))' .* B(:, O(t))';
  end
  
  [P, maxIdx] = max(Delta(end, :));
  
  trace = zeros(T, 1);
  trace(T) = maxIdx;
  for i=(T-1):-1:1
      trace(i) = Psi(i, trace(i+1));
  end
  
  
  Delta
  Psi
  