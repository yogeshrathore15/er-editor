set classpath=../..
set path=c:/program files/java/jdk1.6.0/bin/

call javac Server.java Client.java
call rmic -d ../.. examples.rmi.AccountImpl examples.rmi.BankImpl