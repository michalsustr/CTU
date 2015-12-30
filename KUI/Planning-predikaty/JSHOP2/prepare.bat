REM set CLASSPATH=
REM set PATH=

REM Zde musí být platný absolutní odkaz na podpùrné knihovny:
set CLASSPATH=%CLASSPATH%;C:\JSHOP2\bin\antlr.jar;C:\JSHOP2\bin\JSHOP2.jar;

REM Zde pozor na správnou verzi Java Development Kitu (èíslo za 1.6.0_??). Pozor také na 32b vs 64b verzi.
set PATH=%PATH%;c:\Program Files\Java\jdk1.6.0_23\bin;

echo %CLASSPATH%

echo %PATH%

make.bat c

REM cls