#!/bin/bash
WORKSPACE="/docker-workspace/dilicom-sync"
ES_DATA_DIR="/docker-volumes/ldf-elasticsearch"

docker rm -f ldf-elasticsearch

echo "Deploying Elasticsearch for dilicom-sync."
mkdir -p $ES_DATA_DIR

CMD="cp $WORKSPACE/docker-resources/config/elasticsearch.yml $ES_DATA_DIR/elasticsearch.yml"

echo "Copying configuration file to mounted volume."
echo "	Cmd : $CMD"

$CMD

echo "Launching docker instance of Elasticsearch"

CMD="docker run --name=ldf-elasticsearch -d -p 9200:9200 -p 9300:9300 -v $ES_DATA_DIR:/data dockerfile/elasticsearch /elasticsearch/bin/elasticsearch -Des.config=/data/elasticsearch.yml"

echo "	Cmd : $CMD"

$CMD
