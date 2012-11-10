@ECHO off

REM creates xml, 16 and 32 icons based on given name from sample files
REM josef sustacek (theoden@seznam.cz)
 
set NAME=%1

ECHO === creating new code snippet files for '%NAME%' from sample === 

@ECHO on

copy _sample.xml %NAME%.xml 
copy _sample16.png %NAME%16.png
copy _sample32.png %NAME%32.png
