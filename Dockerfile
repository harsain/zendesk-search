FROM openjdk:12
VOLUME /tmp
COPY target/zendesk-search-0.0.1-SNAPSHOT.jar zendesk-search.jar
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/zendesk-search.jar"]