FROM java:8-jre
WORKDIR usr/src
ENV MYSQL_DATABASE=newsdb
ENV MYSQL_USER=app_root
ENV MYSQL_PASSWORD=root123
ENV MYSQL_CI_URL=jdbc:mysql://localhost:3306/newsdb
ADD ./target/favouriteservice-1.0.jar /usr/src/favouriteservice-1.0.jar
ENTRYPOINT ["java","-jar","favouriteservice-1.0.jar"]


