# Dockerfile for GlassFish 5.0

# Use a base image with GlassFish 5.0
FROM glassfish:5.0

# Set environment variables for admin user and password
ENV ADMIN_USER=admin
ENV ADMIN_PASSWORD=admin

# Set environment variable for the default domain
ENV GLASSFISH_HOME ~/apps/glassfish5/glassfish

COPY target/movie-actor-api-1.0-SNAPSHOT.war $GLASSFISH_HOME/domains/domain1/autodeploy/

EXPOSE 8080 4848

CMD ["ãpps/glassfish5/glassfish/bin/asadmin", "start-domain", "--verbose"]

