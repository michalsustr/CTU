function [y,t] = hw_3b_std(dd, mm, yy)
%	
% hw_3_1b_std    Data Generator for ARI2012 Homework No. 3 
% 
%   The command
%        y = hw_3b_std(dd, mm, yy)
%    generates input data for ARI homework No.3 - 2012 from student's date of
%    birth
% 
% 	Instructions:	
%        Run this function with your date of birth to obtain homework assignement.
%
%   Summary: 
%		Run this function with date of birth of the student and function will return homework assignement.
%
%	Inputs: 
%		required: 
%			date of birth (dd - day, mm - month, yy - year)
%
%	Outputs:
%		y - response for homework assignment
%			
%   Example:
%           y = hw_3b_std(25, 1, 54);

%	Author: 
%		Kamil Dolinsky (last modified 27.6.2011)
%       Michael Sebek  (modified 07-Feb-2012)

	[dd, mm, yy] = parseInput(dd, mm, yy);
	[num,den] = get_parameters(dd,mm,yy);
	
    sys = tf(num,den);
	[y,t] = step(sys);
	plot(0:length(y)-1,y,'LineWidth',2),xlabel('t[s]'),ylabel('y[-]'),title('Measured response'),grid on
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

function dob = get_dob(dd,mm)

	month = [31 28 31 30 31 30 31 31 30 31 30 31];

	if mm > 1
		dob = sum(month(1:mm-1)) + dd;
	else
		dob = dd;
	end
end

function [num,den] = get_parameters(dd,mm,yy)
	dob = get_dob(dd,mm);
	
	alpha = -(mod(dob+mm,9) + mod(dd,4) + 1)/(mod(dob+5,8) + 1);
	omega = mod(dob*3+mm,13) + mod(mm*5+dd*3+yy*2,13) + 1;
	k_2 = mod(mm*2+dd*6+yy+dob,7) + 1;
	
	den = poly([alpha-i*omega alpha+i*omega]);
	num = den(1,3)*k_2;
end
