@echo off
setlocal enabledelayedexpansion

:: Generate unique folder
set /a RAND_NUM=%RANDOM%
set REPORT_PATH=C:\Automation\TestAutomationFramework\AutomationResult_!RAND_NUM!

:: Generate Allure HTML
allure generate target/allure-results --clean -o "!REPORT_PATH!"

:: Move console logs
if exist temp_console.log (
    move temp_console.log "!REPORT_PATH!\console.txt"
)

:: Open report
start "" "!REPORT_PATH!\index.html"

echo ✅ Report saved at: !REPORT_PATH!
