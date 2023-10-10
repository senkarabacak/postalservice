#!/bin/bash

echo "################## WARNING ##################"
echo "it can take time for Springboot to connect to database and activeMQ, please wait 10 sec to click refresh button"
echo "################## WARNING ##################"

docker-compose up -d
java -jar gui/out/artifacts/gui_jar/gui.jar
