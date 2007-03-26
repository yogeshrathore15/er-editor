@echo off
set classpath=../..

start rmiregistry
start java examples.rmi.Server