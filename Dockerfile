FROM pwittchen/alpine-java12
MAINTAINER lk
VOLUME /data/upload
ADD /target/pluploadJava.jar pluploadJava.jar
EXPOSE 5005
CMD java -jar -agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=*:5005 pluploadJava.jar