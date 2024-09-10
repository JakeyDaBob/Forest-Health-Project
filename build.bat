@echo off
echo Building project...    

set source_path=src
set class_path=out
set lib_path=lib
set postgre_name=postgresql-42.7.4.jar

if not exist "%class_path%" mkdir "%class_path%"

cd %source_path%
javac -cp ..\%source_path%;..\%lib_path%\%postgre_name% -d ..\%class_path% *.java

if %errorlevel% neq 0 (
    echo Compilation failed!
    cd ..
    exit /b 1
)

cd ..
xcopy .\%source_path%\resources .\%class_path%\resources /E /I /Y /Q

if %errorlevel% neq 0 (
    echo Resource transfer failed!
    exit /b 2
)

echo Build succeeded!
exit /b 0