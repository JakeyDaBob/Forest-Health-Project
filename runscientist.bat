@echo off

set lib_path=lib
set postgre_name=postgresql-42.7.4.jar

echo Running Scientist...
echo.
java -cp "out;%lib_path%/%postgre_name%" Main -s
@echo Exiting...