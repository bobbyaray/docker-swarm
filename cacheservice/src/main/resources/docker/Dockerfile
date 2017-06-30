FROM centos

RUN yum install -y java

VOLUME /tmp
ADD /cacheservice-1.0.jar cacheservice-1.0.jar
RUN sh -c 'touch /cacheservice-1.0.jar'
ENTRYPOINT ["java", "-Djava.security.egd=file:/dev/./urandom", "-jar", "/cacheservice-1.0.jar"]