REM set CLASSPATH=
REM set PATH=

REM Zde mus� b�t platn� absolutn� odkaz na podp�rn� knihovny:
set CLASSPATH=%CLASSPATH%;C:\JSHOP2\bin\antlr.jar;C:\JSHOP2\bin\JSHOP2.jar;

REM Zde pozor na spr�vnou verzi Java Development Kitu (��slo za 1.6.0_??). Pozor tak� na 32b vs 64b verzi.
set PATH=%PATH%;c:\Program Files\Java\jdk1.6.0_23\bin;

echo %CLASSPATH%

echo %PATH%

make.bat c

REM cls