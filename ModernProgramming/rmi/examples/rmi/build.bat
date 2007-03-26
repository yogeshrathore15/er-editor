@echo off
set classpath=../..

call javac Server.java Client.java
call rmic -d ../.. examples.rmi.AccountImpl examples.rmi.BankImpl