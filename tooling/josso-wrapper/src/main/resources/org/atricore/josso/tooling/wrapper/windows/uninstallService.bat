cd %~dp0
call wrapper-setenv.bat
%wrapper_bat% -r %conf_file%
pause