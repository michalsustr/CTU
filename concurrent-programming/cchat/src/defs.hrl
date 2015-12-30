% This record defines the structure of the 
% client process. 
% 
% It contains the following fields: 
%
% gui: it stores the name (or Pid) of the GUI process.
% nick: current nick name
% serverPid: PID of the server
% channels: list of channel atoms
-record(cl_st, {gui, nick, serverPid, channels}).
    
% This record defines the structure of the 
% server process. 
%
% nicks - list of nicks
-record(server_st, {nicks}).

-record(channel, {name, users, nicks}).
-record(user, {nick, pid}).