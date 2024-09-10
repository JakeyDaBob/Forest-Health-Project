@echo off

set lib_path=lib
set postgre_name=postgresql-42.7.4.jar

echo Running...
echo.
java -cp "out;%lib_path%/%postgre_name%" Main
@echo Exiting...