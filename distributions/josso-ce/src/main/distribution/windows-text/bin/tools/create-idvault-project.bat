@echo off
rem
rem
rem

if not "%ECHO%" == "" echo %ECHO%

setlocal
set DIRNAME=%~dp0%
set PROGNAME=%~nx0%
set ARGS=%*
set JOSSO2_VERSION=3.1.2-SNAPSHOT
set JOSSO2_REPO=http://repository.josso.org/m2-release-repository,http://repository.josso.org/m2-snapshot-repository

goto :BEGIN

:warn
    echo %PROGNAME%: %*
goto :EOF

:BEGIN

if "%GROUP_ID%" == "" set GROUP_ID=%1

if "%ARTIFACT_ID%" == "" set ARTIFACT_ID=%2

if not "%GROUP_ID%" == "" goto :CHECK_GROUP_END
    call :warn Usage group-id artifact-id
    goto END
:CHECK_GROUP_END

if not "%ARTIFACT_ID%" == "" goto :CHECK_ARTIFACT_END
    call :warn Usage group-id artifact-id
    goto END
:CHECK_ARTIFACT_END

if "%PACKAGE%" == "" set PACKAGE=%GROUP_ID%

:RUN
set ARGS=%1 %2 %3 %4 %5 %6 %7 %8
mvn -B  archetype:generate -Dmaven.repo.remote="%JOSSO2_REPO%" -DarchetypeGroupId="org.atricore.josso.archetypes" -DarchetypeArtifactId="josso2-idvault-archetype" -DarchetypeVersion="%JOSSO2_VERSION%" -DgroupId="%GROUP_ID%" -DartifactId="%ARTIFACT_ID%" -Dpackage="%PACKAGE%"

:END

endlocal

if not "%PAUSE%" == "" pause

:END_NO_PAUSE