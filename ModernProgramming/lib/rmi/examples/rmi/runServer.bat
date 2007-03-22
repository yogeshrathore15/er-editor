@echo off
set classpath=../..

start rmiregistry
start javaw examples.rmi.Server