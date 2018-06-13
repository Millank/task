call gradlew build
if "%ERRORLEVEL%" == "0" goto stoptomcat
echo.
echo %~n0%~x0: GRADLEW BUILD has errors - breaking work
goto fail

:stoptomcat
call %CATALINA_HOME%\bin\shutdown.bat 

:copyfile
copy /Y build\libs\tasks-0.0.1-SNAPSHOT.war %CATALINA_HOME%\webapps\crud.war
if "%ERRORLEVEL%" == "0" goto runtomcat
echo %~n0%~x0: cannot copy war file
goto fail

:runtomcat
call %CATALINA_HOME%\bin\startup.bat
goto end

:fail
echo.
echo %~n0%~x0: there were errors

:end
echo.
echo %~n0%~x0: work is finished.
