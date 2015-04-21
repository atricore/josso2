@echo off
rem
rem
rem

if not "%ECHO%" == "" echo %ECHO%

setlocal
set DIRNAME=%~dp0%
set PROGNAME=%~nx0%
set ARGS=%*
set JOSSO2_VERSION=2.5.0-SNAPSHOT
set JOSSO2_REPO=http://repository.josso.org/m2-release-repository,http://repository.josso.org/m2-snapshot-repository

goto :BEGIN

:warn
    echo %PROGNAME%: %*
goto :EOF

:BEGIN

if "%GROUP_ID%" == "" set GROUP_ID=%1

if "%UI_ID%" == "" set UI_ID=%2

if not "%GROUP_ID%" == "" goto :CHECK_GROUP_END
    call :warn Usage group-id ui-id
    goto END
:CHECK_GROUP_END

if not "%UI_ID%" == "" goto :CHECK_UI_END
    call :warn Usage group-id ui-id
    goto END
:CHECK_UI_END

if "%VARIANT%" == "" set VARIANT=%UI_ID%

if "%PACKAGE%" == "" set PACKAGE=%GROUP_ID%

if "%ARTIFACT_ID%" == "" set ARTIFACT_ID=%UI_ID%

set VARIANT_SUFFIX = _%VARIANT%

:RUN
set ARGS=%1 %2 %3 %4 %5 %6 %7 %8
mvn -B  archetype:generate -Dmaven.repo.remote="%JOSSO2_REPO%" -DarchetypeGroupId="org.atricore.josso.archetypes" -DarchetypeArtifactId="josso2-ui-archetype" -DarchetypeVersion="%JOSSO2_VERSION%" -DgroupId="%GROUP_ID%" -DartifactId="%ARTIFACT_ID%" -Dpackage="%PACAKGE%" -DuiId="%UI_ID%" -Dvariatn="%VARIANT%" -DvariantSuffix="%VARIANT_SUFFIX%"

:END

endlocal

if not "%PAUSE%" == "" pause

:END_NO_PAUSE