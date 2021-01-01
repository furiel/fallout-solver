# fallout-solver

# Deployment

This project consists of static files only.
The static files can be collected into a `deployment` directory by:

```sh
lein fig:min
just prepare
```

You can deploy the newly created `deployment` directory with your favorite webserver.

A very simple nginx configuration:
```
   location /fallout-solver {
	alias /srv/deployment/;
	charset   utf-8;
	try_files $uri $uri/ =404;
   }
```
