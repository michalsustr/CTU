function [ pol ] = charpol( OS, Ts )
%CHARPOL Summary of this function goes here
%   Detailed explanation goes here
    z = zeta(OS);
    omega = 4/(z*Ts);
    pol = [1 2*z*omega omega^2 ];

end

