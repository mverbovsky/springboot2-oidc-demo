# KEYCLOAK-DEMO

Docker image with preconfigured Keycloak for demo purpose.
It contains:
- realm `demo`
- client `demo`
- user `john` with password `123456`

## How to build

```shell
$ docker build -t keycloak-demo .
```

## How to run

```shell
$ docker run -d -p 8090:8080 keycloak-demo
```


## Links

- [Administrative console](http://localhost:8090/auth/admin/master/console/) (user:admin, password: admin)
- [User account](http://localhost:8090/auth/realms/demo/account)
