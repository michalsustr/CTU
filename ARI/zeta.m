function [zeta]=zeta(os)
%pocitani tlumeni z overshootu v procentech
a=log(os/100);
zeta=-a/sqrt(pi^2+a^2);
end