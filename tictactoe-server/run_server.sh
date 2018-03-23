#!/bin/bash

IP=$1
PORT=$2

javac -d bin src/core/*.java &&
cd bin && java -Djava.rmi.server.hostname=$IP core.Core $IP $PORT
