@echo off
call build

if %errorlevel% neq 0 (
    echo Can't build jar, build failed aye
    exit \b 1
)

jar cfm ForestHealthProject.jar MANIFEST.MF -C out .

if %errorlevel% neq 0 (
    echo Jar build failed
    exit \b 2
)

echo Jar built!