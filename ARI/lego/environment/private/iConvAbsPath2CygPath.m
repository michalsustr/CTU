% iConvAbsPath2CygPath
%   replaces MS-DOS abusolute path to Cygwin
%   make 3.8.1 acceptable path expression (i.e. 'C:/' to '/cygdrive/c/')

%   Copyright 2010 The MathWorks, Inc.

function path = iConvAbsPath2CygPath(abs_path)

% This is a workaround if MATLAB was installed under Program Files directory
abs_path = regexprep(abs_path, 'Program Files', 'Progra~1');

path = ['/cygdrive/' abs_path(1) abs_path(3:end)];
% End of function
