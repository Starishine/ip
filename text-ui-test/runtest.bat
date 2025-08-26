@ECHO OFF

REM create bin directory if it doesn't exist
if not exist ..\bin mkdir ..\bin

REM delete old class files
if exist ..\bin\chatbot\leo\*.class del ..\bin\chatbot\leo\*.class
if exist ..\bin\chatbot\exceptions\*.class del ..\bin\chatbot\exceptions\*.class
if exist ..\bin\chatbot\inputreader\*.class del ..\bin\chatbot\inputreader\*.class
if exist ..\bin\chatbot\taskhandler\*.class del ..\bin\chatbot\taskhandler\*.class


REM delete output from previous run
if exist ACTUAL.TXT del ACTUAL.TXT

REM compile the code into the bin folder
javac  -cp ..\src\main\java -Xlint:none -d ..\bin ..\src\main\java\chatbot\leo\*.java
javac  -cp ..\src\main\java -Xlint:none -d ..\bin ..\src\main\java\chatbot\exceptions\*.java
javac  -cp ..\src\main\java -Xlint:none -d ..\bin ..\src\main\java\chatbot\inputreader\*.java
javac  -cp ..\src\main\java -Xlint:none -d ..\bin ..\src\main\java\chatbot\taskhandler\*.java
IF ERRORLEVEL 1 (
    echo ********** BUILD FAILURE **********
    exit /b 1
)
REM no error here, errorlevel == 0

REM run the program, feed commands from input.txt file and redirect the output to the ACTUAL.TXT
java -classpath ..\bin chatbot.leo.Leo < input.txt > ACTUAL.TXT

REM compare the output to the expected output
FC ACTUAL.TXT EXPECTED.TXT

REM Check if FC found any differences
IF %ERRORLEVEL% EQU 0 (
    echo Test result: PASSED
) ELSE (
    echo Test result: FAILED
)