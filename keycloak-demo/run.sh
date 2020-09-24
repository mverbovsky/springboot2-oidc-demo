#!/bin/bash

KC=/opt/jboss/keycloak/bin

function isKeycloakRunning() {
    local http_code=$(curl -s -o /dev/null -w "%{http_code}" http://localhost:8080/auth/admin/realms)
    if [[ $http_code -eq 401 ]]; then
        return 0
    else
        return 1
    fi
}

function initKeycloak() {
    until isKeycloakRunning; do
        echo Keycloak still not running, waiting 5 seconds
        sleep 5
    done

    login
    createRealm
    createClient01
    createClient02
    createUser
}

function login() {
    echo "KCADM configuring credentials"
    ${KC}/kcadm.sh config credentials --server http://localhost:8080/auth \
        --user admin \
        --password admin \
        --realm master
}

function createRealm() {
    echo "KCADM creating realm"
    ${KC}/kcadm.sh create realms -s realm=demo -s enabled=true -o
}

function createClient01() {
    echo "KCADM creating client"
    ${KC}/kcadm.sh create clients -r demo \
        -s clientId=demo01 \
        -s publicClient=true \
        -s directAccessGrantsEnabled=true \
        -s 'redirectUris=["http://localhost:8091/*"]' \
        -o
}

function createClient02() {
    echo "KCADM creating client"
    ${KC}/kcadm.sh create clients -r demo \
        -s clientId=demo02 \
        -s publicClient=true \
        -s directAccessGrantsEnabled=true \
        -s 'redirectUris=["http://localhost:8093/*"]' \
        -o
}

function createUser() {
    echo "KCADM creating user"
    ${KC}/kcadm.sh create users -r demo \
        -s username=john \
        -s firstName=John \
        -s lastName=Doe \
        -s email=john.doe@acme.com \
        -s enabled=true \
        -o
    ${KC}/kcadm.sh set-password -r demo \
        --username john \
        --new-password 123456
}

# TODO does not work
# touch: cannot touch 'keycloak-initialized': Permission denied
if [ ! -f keycloak-initialized ]; then
    touch keycloak-initialized
    initKeycloak &
fi

exec /opt/jboss/tools/docker-entrypoint.sh $@
exit $?
