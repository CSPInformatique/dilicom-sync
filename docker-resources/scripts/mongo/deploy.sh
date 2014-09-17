#!/bin/bash

docker rm -f ldf-mongodb

echo "Launching docker instance for mongodb."

docker run -d -p 27017:27017 --name ldf-mongodb cspinformatique/mongodb
