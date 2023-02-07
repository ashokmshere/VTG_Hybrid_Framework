@echo off

Set proj=C:\Users\JKUMARRA\git\Selenium-Hybrid-Framework_dev_May\selenium-hybrid-application

pushd %proj%

gradle runTests

if ERRORLEVEL 1 exit 1

pause