#!/bin/bash
WORKSPACE="/docker-workspace/dilicom-sync"

cd $WORKSPACE

echo "Launching maven build for dilicom-sync."

mvn clean install

cd docker-images

echo "Building Dockerfile for dilicom-sync."

sh docker-build.sh

CMD="docker run -d -p 8090:8080 --name ldf-dilicomsync cspinformatique/dilicom-sync"

echo "Launching docker instance for dilicom-sync."
echo "	Cmd : $CMD"

$CMD
