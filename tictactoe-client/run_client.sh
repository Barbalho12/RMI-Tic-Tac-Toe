#!/bin/bash

IP=$1
PORT=$2

javac -d bin src/core/*.java &&
cd bin && java core.GameClient $IP $PORT