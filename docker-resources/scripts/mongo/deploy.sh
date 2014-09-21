#!/bin/bash
ES_DATA_DIR="/docker-volumes/ldf-mongodb"

docker rm -f ldf-mongodb

echo "Launching docker instance for mongodb."

docker run -d -p 27017:27017 --name ldf-mongodb -v $ES_DATA_DIR:/data cspinformatique/mongodb
