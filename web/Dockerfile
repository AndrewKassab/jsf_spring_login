FROM tomcat:9-jdk11

WORKDIR /usr/local/tomcat

RUN rm -rf webapps/*

COPY target/JsfLogin.war /usr/local/tomcat/webapps/ROOT.war

EXPOSE 8080

CMD ["catalina.sh", "run"]
