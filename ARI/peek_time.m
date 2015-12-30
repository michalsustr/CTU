function [T_p]=peek_time(zeta, omega_n)
	T_p = pi/(omega_n*sqrt(1-zeta^2))
end
