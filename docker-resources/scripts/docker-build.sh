unzip ../../target/docker.zip -o -d ../target

docker build -t cspinformatique/dilicom-sync ../target/docker
