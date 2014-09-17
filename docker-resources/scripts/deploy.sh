#!/bin/bash
SCRIPTS_FOLDER="/docker-workspace/dilicom-sync/docker-resources/scripts"

cd $SCRIPTS_FOLDER

elasticsearch/deploy.sh
mongo/deploy.sh
dilicom-sync/deploy.sh
