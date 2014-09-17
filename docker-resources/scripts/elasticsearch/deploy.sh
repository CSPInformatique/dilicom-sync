#!/bin/bash
WORKSPACE="/docker-workspace/dilicom-sync"
ES_DATA_DIR="/docker-data/es-dilicom-sync/data"
ES_CONFIG_DIR="/docker-data/es-dilicom-sync/config"

echo "Deploying Elasticsearch for dilicom-sync."
mkdir -p $ESDATADIR

echo "Copying configuration file to mounted volume."
cp "$WORKSPACE/docker-resources/elasticsearch.yml" "$ES_CONFIG_DIR/elasticsearch.yml"

echo "Launching docker instance of Elasticsearch"

CMD="docker run -d -p 9200:9200 -p 9300:9300 -v $ESDATADIR:/data dockerfile/elasticsearch /elasticsearch/bin/elasticsearch -Des.config=/data/elasticsearch.yml"

echo "	Cmd : $CMD"

$CMD