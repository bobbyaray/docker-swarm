FROM centos

RUN yum install -y java

VOLUME /tmp
ADD /dbservice-1.0.jar dbservice-1.0.jar
RUN sh -c 'touch /dbservice-1.0.jar'
ENTRYPOINT ["java", "-Djava.security.egd=file:/dev/./urandom", "-jar", "/dbservice-1.0.jar"]