cd favouriteservice
source ./env.sh
mvn clean package
docker build -t favourite
cd..
cd userservice
source ./env.sh
mvn clean package
docker build -t userauth
cd..
