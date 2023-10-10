#!/bin/bash

docker-compose up -d
nohup java -jar gui/out/artifacts/gui_jar/gui.jar  & 
echo "################## WARNING ##################"
echo "it can take time for Springboot to connect to database and activeMQ, please wait 10 sec to click refresh button"
echo "################## WARNING ##################"
echo "JavaFX is running as detached..."
echo "Press 'q' to quit. Press 'd' to stop containers. Press 's' to run containers again. "
echo "Press 'l' to stop/start letterservice. Press 'p' to stop/start packageservice"
echo " "
echo " "
while true; do
    read -n 1 key
    if [[ $key == "q" ]]; then
        echo "You pressed 'q'. Exiting..."
        break
    fi
    if [[ $key == "d" ]]; then
        echo "You pressed 'd'. Stopping containers..."
	docker-compose down
    fi 
     if [[ $key == "l" ]]; then
        if [[ $(docker inspect -f '{{.State.Status}}' letterservice 2>/dev/null) != "running" ]]; then
            echo "You pressed 'l'. Starting letterservice..."
            docker start letterservice
        else
            echo "You pressed 'l'. Stopping letterservice..."
            docker stop letterservice
        fi  
        
    fi
    if [[ $key == "p" ]]; then
        if [[ $(docker inspect -f '{{.State.Status}}' packageservice 2>/dev/null) != "running" ]]; then
            echo "You pressed 'p'. Starting packageservice..."
            docker start packageservice
        else
            echo "You pressed 'p'. Stopping packageservice..."
            docker stop packageservice
        fi  
    fi

    if [[ $key == "s" ]]; then
        echo "You pressed 's'. Starting containers..."
        docker-compose up -d
    fi
done


java_pid=$(ps aux | grep "java -jar gui/out/artifacts/gui_jar/gui.jar" | grep -v grep | awk '{print $2}')
if [ -n "$java_pid" ]; then
    kill "$java_pid"
    echo "Java application has been stopped."
fi

