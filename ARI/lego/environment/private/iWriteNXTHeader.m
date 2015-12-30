% iWriteNXTHeader
%   write ecrobot_main.c file header

%   Copyright 2010 The MathWorks, Inc.

function iWriteNXTHeader(fid, model, sys)

% get the actual date and Simulink version
thisDate = datestr(now, 0);
slVer = ver('simulink');
rtwVer = ver('RTW');
ecVer = ver('ecoder');

fprintf(fid, '/');
for i=1:77
    fprintf(fid, '*');
end
fprintf(fid, '\n');

fprintf(fid, ' * FILE: ecrobot_main.c\n');
fprintf(fid, ' *\n');
fprintf(fid, ' * MODEL: %s.mdl\n', model);
fprintf(fid, ' *\n');
fprintf(fid, ' * APP SUBSYSTEM: %s\n', sys);
fprintf(fid, ' *\n');
fprintf(fid, ' * PLATFORM: %s\n', iGetPlatform(model)); 
fprintf(fid, ' *\n');
fprintf(fid, ' * DATE: %s\n', thisDate);
fprintf(fid, ' *\n');
fprintf(fid, ' * TOOL VERSION:\n');
fprintf(fid, ' *   Simulink: %s %s %s\n',...
    slVer.Version, slVer.Release, slVer.Date);
fprintf(fid, ' *   Real-Time Workshop: %s %s %s\n',...
    rtwVer.Version, rtwVer.Release, rtwVer.Date);
fprintf(fid, ' *   Real-Time Workshop Embedded Coder: %s %s %s\n',...
    ecVer.Version, ecVer.Release, ecVer.Date);

fprintf(fid, ' ');
for i=1:77
    fprintf(fid, '*');
end
fprintf(fid, '/\n');
% End of function
