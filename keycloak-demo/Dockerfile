FROM jboss/keycloak:11.0.2

ENV KEYCLOAK_USER=admin
ENV KEYCLOAK_PASSWORD=admin

ADD run.sh /opt/jboss

ENTRYPOINT [ "/opt/jboss/run.sh" ]

CMD ["-b", "0.0.0.0"]
