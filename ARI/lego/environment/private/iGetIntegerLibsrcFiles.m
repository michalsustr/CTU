% iGetIntegerLibsrcFiles
%   get RTW/RTW-EC standard libsrc files for
%   non floating point code generation

%   Copyright 2010 The MathWorks, Inc.

function files = iGetIntegerLibsrcFiles()
if iIsR2007bOrPrev() 
    cfiles = {'rt_sat_div_int8.c' ...
        'rt_sat_div_int16.c' ...
        'rt_sat_div_int32.c' ...
        'rt_sat_div_uint8.c' ...
        'rt_sat_div_uint16.c' ...
        'rt_sat_div_uint32.c' ...
        'rt_sat_prod_int8.c' ...
        'rt_sat_prod_int32.c' ...
        'rt_sat_prod_uint8.c' ...
        'rt_sat_prod_uint16.c' ...
        'rt_sat_prod_uint32.c'};
    
    files = [];
    for i=1:length(cfiles)
        files = [files ' ' iConvAbsPath2CygPath(regexprep(matlabroot, '\', '/')), ...
            '/rtw/c/libsrc/' cfiles{i}];
    end
else
    files = '';
end
% End of function
