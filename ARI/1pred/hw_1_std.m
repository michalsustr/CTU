function [A, B, C, x0] = hw_1_std(dd, mm, yy)
%	
%   hw_1_std    Data Generator for ARI2012 Homework No. 1 
%
%   The command
%       [A, B, C, x0] = hw_1_std(dd, mm, yy)
%   generates input data for ARI homework No.1 - 2012 from student's date of
%   birth
%
%	Instructions:	
%       Run this function with your date of birth to obtain homework assignement.
%	Inputs: 
%		Date of birth (dd - day, mm - month, yy - year)
%	Outputs:
%		Matrices A, B, C and initial conditions x0
%	
%   Example:
%       [A, B, C, x0] = hw_1_std(25,1,54)
%           A =
%                0    -1
%                6    -3
%           B =
%                4
%                2
%           C =
%               12    12
%           x0 =
%               14
%               18
%
%	Author: 
%		Kamil Dolinsky (last modified 8.2.2011)
%       Michael Sebek   (modified 07-Feb-2012)

	[dd, mm, yy] = parseInput(dd, mm, yy);
	
	[A, B, C, x0] = compute_matrices(dd, mm, yy);
	
end

function [dd, mm, yy] = parseInput(dd, mm, yy)
	p = inputParser;
	p.addRequired('dd',@(x)isa(x,'numeric')&& size(x,1) == 1 && size(x,2) == 1 && x >= 1 && x <= 31 && mod(x,1) == 0);
	p.addRequired('mm',@(x)isa(x,'numeric')&& size(x,1) == 1 && size(x,2) == 1 && x >= 1 && x <= 12 && mod(x,1) == 0);
	p.addRequired('yy',@(x)isa(x,'numeric')&& size(x,1) == 1 && size(x,2) == 1 && x >= 0 && x <= 99 && mod(x,1) == 0);

	p.parse(dd, mm, yy);

	dd = p.Results.dd;
	mm = p.Results.mm;
	yy = p.Results.yy;
end

function [A, B, C, x0] = compute_matrices(dd, mm, yy)

	dob = get_dob(dd,mm);
	
	A = zeros(2,2);
	A(1,2) = -1;
	A(2,1) = mod(dob+mm,7) + mod(dd,5) + 1;
	A(2,2) = -(mod(2*dob+mm+yy+dd,13) + mod(dob+5,7) + 1);

	B = [mod(dob - dd + mm,5) + mod(dd - 2*mm,7) + 1; mod(5*dd+2*dob+mm,8) + mod(8*dob+5*dd+yy,9) + 1];
	
	C = [mod(2*dd+mm-yy,13) + mod(yy+mm+dd+dob,4) + 1, mod(dob+2,6) + mod(dd*6+mm,11) + 1];
	
	x0 = [ 
		mod(dd,9) + mod(dob - 2*mm,17) + 1; 
		mod(dob*3+mm,13) + mod(mm*5+dd*3+yy*2,7) + 1
	];
end
function dob = get_dob(dd,mm)

	month = [31 28 31 30 31 30 31 31 30 31 30 31];

	if mm > 1
		dob = sum(month(1:mm-1)) + dd;
	else
		dob = dd;
	end
end