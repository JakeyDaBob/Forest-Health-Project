@echo off
call build
if %errorlevel% neq 0 (
    echo "Build Failed (%errorlevel%)"
    exit /b %errorlevel%
)
call run
