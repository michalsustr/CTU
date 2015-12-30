% iGenerateMakefile
%   generate a Makefile to invoke GNU-ARM toolchain

%   Copyright 2010 The MathWorks, Inc.

function iGenerateMakefile(cmd, model, target_name, ert_rtw_dir, env_dir, xcp_bt)

global GNUARM_ROOT   ;
global NEXTTOOL_ROOT ;
global MATLAB_ROOT   ;
global ENV_DIR       ;

try
    fid = fopen('Makefile', 'w');
    if fid == -1
        error('NXTBuild:Can not open Makefile');
    end

    % add XCP C files, include path, GCC macro and option
    % it assuems that XCP folder must be allocated in environment directoy
    if xcp_bt == 1
        XCP_FILES = 'xcp_par.c $(ENV_DIR)/XCP/xcpBasic.c $(ENV_DIR)/XCP/xcp_interface.c ';
        XCP_INC_PATH = '$(ENV_DIR)/XCP ';
        XCP_DEF = 'XCP_ON_BLUETOOTH '; % this makes bluetooth Tx/Rx to be stubs
        XCP_DEF = [XCP_DEF 'kXcpMaxEvent=' num2str(iGetNumOfPeriodicalTasks(model))];
        USER_C_OPT = 'USER_C_OPT = -g'; % need debug info for ASAP2 file
    else
        XCP_FILES = '';
        XCP_INC_PATH = '';
        XCP_DEF = '';
        USER_C_OPT = '';
    end

    % path settings macro
    fprintf(fid, ['GNUARM_ROOT = ' GNUARM_ROOT '\n']);
        fprintf(fid, ['NEXTTOOL_ROOT = ' NEXTTOOL_ROOT '\n']);
    
    fprintf(fid, ['MATLAB_ROOT = ' MATLAB_ROOT '\n']);
    fprintf(fid, ['ENV_DIR = ' ENV_DIR '\n']);
    fprintf(fid, ['ERT_RTW = ../' ert_rtw_dir '\n']);
    fprintf(fid, [ 'ROOT = $(ENV_DIR)/' iGetPlatformFolderName(env_dir) '\n']);
    fprintf(fid, [USER_C_OPT '\n']);
    fprintf(fid, ['USER_INC_PATH= $(ERT_RTW) '...
        XCP_INC_PATH ...
        '$(MATLAB_ROOT)/simulink/include ' ...
        '$(MATLAB_ROOT)/extern/include '...
        '$(MATLAB_ROOT)/rtw/c/libsrc' '\n']);
    fprintf(fid, ['TARGET = ' target_name '\n']);

    % OSEK specific macros
    %if isequal(iGetPlatform(model), 'OSEK')
        % TOPPERS/OSEK SG accepts only DOS path expression
        TOPPERS_OSEK_ROOT_SG = [regexprep(env_dir, '\', '/') '/' iGetPlatformFolderName(env_dir) '/toppers_osek'];
        fprintf(fid, ['TOPPERS_OSEK_ROOT_SG = ' TOPPERS_OSEK_ROOT_SG '\n']);
        fprintf(fid, 'TOPPERS_OSEK_OIL_SOURCE = ./ecrobot.oil\n');
        % support floating point and fixed-point code generation
        if isequal('on', get_param(model, 'PurelyIntegerCode'))
            fprintf(fid, ['ECROBOT_DEF = OSEK INTEGER_CODE ' XCP_DEF '\n']);
            fprintf(fid, ['TARGET_SOURCES = '...
                'ecrobot_main.c ' ...
                iGetRTWECGenCfiles(ert_rtw_dir) ' ' ...
                XCP_FILES ...
                iGetIntegerLibsrcFiles() '\n']);
        else
            fprintf(fid, ['ECROBOT_DEF = OSEK ' XCP_DEF '\n']);
            fprintf(fid, ['TARGET_SOURCES = '...
                'ecrobot_main.c ' ...
                iGetRTWECGenCfiles(ert_rtw_dir) ' ' ...
                XCP_FILES ...
                iGetLibsrcFiles() '\n']);
        end
%     % JSP specific macros
%     elseif isequal(iGetPlatform(model), 'JSP')
%         fprintf(fid, 'TOPPERS_KERNEL = NXT_JSP\n');
%         fprintf(fid, 'TOPPERS_JSP_CFG_SOURCE = ./ecrobot.cfg\n');
%         % support floating point and fixed-point code generation
%         if isequal('on', get_param(model, 'PurelyIntegerCode'))
%             fprintf(fid, ['ECROBOT_DEF = JSP INTEGER_CODE ' XCP_DEF '\n']);
%             fprintf(fid, ['TARGET_SOURCES = '...
%                 'ecrobot_main.c ' ...
%                 iGetRTWECGenCfiles(ert_rtw_dir) ' ' ...
%                 XCP_FILES ...
%                 iGetIntegerLibsrcFiles() '\n']);
%         else
%             fprintf(fid, ['ECROBOT_DEF = JSP ' XCP_DEF '\n']);
%             fprintf(fid, ['TARGET_SOURCES = '...
%                 'ecrobot_main.c ' ...
%                 iGetRTWECGenCfiles(ert_rtw_dir) ' ' ...
%                 XCP_FILES ...
%                 iGetLibsrcFiles() '\n']);
%         end
%     end

    % WAV files macro
    if iHasSoundWavWriteBlock(model, target_name) == 1
        wavfiles = iGetWavFileNames(model, target_name);
        fprintf(fid, 'WAV_SOURCES = \\\n');
        for i = 1:length(wavfiles)
            wavfilepath = which([wavfiles{i} '.wav']);
            if ~isequal(wavfilepath, '')
                fprintf(fid, [iConvAbsPath2CygPath(regexprep(wavfilepath, '\', '/')) ' \\\n']);
            else
                error(['NXTBuild: ' wavfiles{i} 'does not exist in MATLAB path.']);
            end
        end
        fprintf(fid, '\n');
    end

    % BUILD_MODE macro
    if isequal(cmd, 'buildrxe')
        fprintf(fid, 'BUILD_MODE = RXE_ONLY\n');
    elseif isequal(cmd, 'buildrom')
        fprintf(fid, 'BUILD_MODE = ROM_ONLY\n');
    elseif isequal(cmd, 'buildram')
        fprintf(fid, 'BUILD_MODE = RAM_ONLY\n');
    end

    fprintf(fid, 'O_PATH ?= build\n');
    fprintf(fid, ['include $(ENV_DIR)/' iGetPlatformFolderName(env_dir) '/ecrobot/ecrobot.mak\n']);
    fclose(fid);
catch
    fclose(fid);
    disp(lasterr);
    error('### Failed to generate Makefile');
end
% End of function
