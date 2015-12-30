function [P, S] = viterbi(O, A, B, PI)
% VITERBI algorithm
% Implementation of viterbi algorithm, calculates probability of
% observation sequence O given the hidden state probabilities A and
% emission probabilities B and prior probabilities PI
% 
% S are indices of the most probable sequence of hidden states, P is the
% logarithmic probability of the sequence S
  A     = log10(A) ;
  B     = log10(B) ;
  PI    = log10(PI) ;
  T     = length(O) ;
  N     = length(PI) ;
  delta = zeros(T, N) ;
  prev  = zeros(T, N) ;
  
  delta(1,:) = (PI + B(:,O(1)))' ;
  prev(1,:)  = 0 ;
  
  for t = 2:T
    Pnxt = A ;
    for i = 1:N
      Pnxt(:,i) = delta(t - 1,:)' + Pnxt(:,i) ;
    end
    [~, prev(t,:)] = max(Pnxt) ;
    for i = 1:N
      delta(t,i) = B(i,O(t))' + Pnxt(prev(t,i),i) ;
    end
  end
  
  S = zeros(T, 1) ;
  
  [P, S(T)] = max(delta(T,:)) ;
  
  for t = (T - 1):-1:1
    S(t) = prev(t + 1,S(t+1)) ;
  end
end