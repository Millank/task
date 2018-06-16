call runcrud.bat 
if "%ERRORLEVEL%" == "0" goto :runbrowser
echo.
echo %~n0%~x0: runcrud.bat has errors - breaking work
goto fail

:runbrowser
@echo %~n0%~x0: delay for server startup... 
ping localhost -n 10 >NUL
start "start" "http://localhost:8080/crud/v1/tasks"
goto end

:fail
echo.
echo %~n0%~x0: there were errors

:end
echo.
echo %~n0%~x0: work is finished.
