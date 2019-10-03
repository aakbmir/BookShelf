FROM java:8-jre
WORKDIR usr/src
ENV MONGO_DATABASENAME=booksdb
ENV MONGO_URL=mongodb://localhost:27017/booksdb

ADD ./target/favouriteservice-1.0.jar /usr/src/favouriteservice-1.0.jar
ENTRYPOINT ["java","-jar","favouriteservice-1.0.jar"]
