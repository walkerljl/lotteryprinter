
@echo off
echo starting ...
setlocal

::set JAVA_HOME=C:\Program Files\Java\jdk1.8.0_45
set JAVA_HOME=jre
set CLASSPATH=.;%JAVA_HOME%\lib;%JAVA_HOME%\lib\tools.jar
set PATH=%JAVA_HOME%\bin;%PATH%

echo processing ...
echo Current JAVA_HOME=%JAVA_HOME%

java -jar lotteryprinter-client.jar

endlocal
echo ended ...

pause
