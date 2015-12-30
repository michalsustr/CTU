GNUARM_ROOT = /cygdrive/C/LegoMindstorms/GNU_ARM_Compiler
NEXTTOOL_ROOT = /cygdrive/C/LegoMindstorms/NXT_Tools
MATLAB_ROOT = /cygdrive//pt/MATLAB/R2010b
ENV_DIR = /cygdrive//ome/jirka/Plocha/nxt/ecrobotNXT/environment
ERT_RTW = ../nxtway_app_ert_rtw
ROOT = $(ENV_DIR)/nxtOSEK

USER_INC_PATH= $(ERT_RTW) $(MATLAB_ROOT)/simulink/include $(MATLAB_ROOT)/extern/include $(MATLAB_ROOT)/rtw/c/libsrc
TARGET = nxtway_app
TOPPERS_OSEK_ROOT_SG = /home/jirka/Plocha/nxt/ecrobotNXT/environment/nxtOSEK/toppers_osek
TOPPERS_OSEK_OIL_SOURCE = ./ecrobot.oil
ECROBOT_DEF = OSEK 
TARGET_SOURCES = ecrobot_main.c  $(ERT_RTW)/nxtway_app.c $(ERT_RTW)/nxtway_app_data.c 
O_PATH ?= build
include $(ENV_DIR)/nxtOSEK/ecrobot/ecrobot.mak
