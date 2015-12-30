function [Dist,rw,pw]=align(r,p,gap,match,mismatch)
% ALIGN align DNA sequences using dynamic programming with linear gap function
% 
% Usage: [Dist,rw,pw]=align(r,p,gap,match,mismatch)
%
% r: first DNA string
% p: second DNA string
% gap, match, mismatch - integer penalties
%
% Dist - distance between strings
% rw - aligned string r
% pw - aligned string p
%
% Example: [Dist,rw,pw]=align('acct', 'accgt', -2, 1, 0)
%
% This script uses function alignseq, you may need to compile it with 
% $ mex alignseq.cpp
x = (r == 'a') + (r == 't')*2 + (r == 'c')*3 + (r == 'g')*4;
y = (p == 'a') + (p == 't')*2 + (p == 'c')*3 + (p == 'g')*4;

[Dist,W]=alignseq(x',y', gap, match, mismatch);

rx=W(1,:);
px=W(2,:);
rx(rx==0) = '-';
rx(rx==1) = 'a';
rx(rx==2) = 't';
rx(rx==3) = 'c';
rx(rx==4) = 'g';

px(px==0) = '-';
px(px==1) = 'a';
px(px==2) = 't';
px(px==3) = 'c';
px(px==4) = 'g';

rw=char(rx);
pw=char(px);
end
