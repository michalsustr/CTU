function [T_s]=settling_time(zeta, omega_n)
	T_s = 4/(zeta*omega_n)
end
