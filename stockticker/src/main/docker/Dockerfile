FROM centos

RUN yum install -y java

VOLUME /tmp
ADD /stockticker-1.0.jar stockticker-1.0.jar
RUN sh -c 'touch /stockticker-1.0.jar'
ENTRYPOINT ["java", "-Djava.security.egd=file:/dev/./urandom", "-jar", "/stockticker-1.0.jar"]