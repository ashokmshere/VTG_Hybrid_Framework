Echo off
Title Server NODE on default port 4442

Echo Setting Project Location
set projectLocation=E:\Projects\VTG-Automation-HybridFramework\Utilities
pushd %projectLocation%

Echo Loading NODE on default port 4444
java -Dwebdriver.chrome.driver=chromedriver.exe -Dwebdriver.gecko.driver=geckodriver.exe -Dwebdriver.msedge.driver=msedgedriver.exe -jar selenium-server-4.1.2.jar node

REM -browser "browserName=chrome,version=ANY,platform=WINDOWS,maxInstances=5" -maxSession 20 -port 4442

pause
