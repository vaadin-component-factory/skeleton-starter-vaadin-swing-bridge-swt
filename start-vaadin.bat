@echo off
rem Runs the starter on http://localhost:8888. Every user's SWT application
rem runs in this JVM, on a hidden desktop of its own. Can be double-clicked:
rem the window stays open when the server stops.
setlocal
rem pushd, not cd: it also works when this folder is a UNC path.
pushd "%~dp0"

call mvnw.cmd -B -ntp spring-boot:run

popd
pause
