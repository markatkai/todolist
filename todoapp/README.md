# TODO list app

React application built using React Router. Should work as a single page app, so, server side rendering has been disabled.

## Installation of dependencies

Install the dependencies:

```bash
npm install
```
or install exact versions
```bash
npm ci
```

## Development

Start the development server using vite as the routing compontent to forward /api calls to the backend:

```bash
npm run dev
```

The application will be available at `http://localhost:5173`.

Application will try to contact the backend in hardcoded location http://localhost:8080. The address can be changed in vite.config.ts.

## Running with docker compose

Whole the stack can be started with the /docker docker compose files. If those are used for running this projects, there will be an nginx running on the front taking in requests and loading static app for root call, and forwarding /api calls to the backend. The configuration for routing in nginx is defined in file nginx.conf.

Dockerfile of this front thus only builds the app and actually runs nginx for the web server and exposes port 80.

## Icons

Icons for "mark as done" and "trash" and the favicon.ico were found from svgrepo:
Vectors and icons by <a href="https://www.svgrepo.com" target="_blank">SVG Repo</a>
Exact collection:
https://www.svgrepo.com/collection/web-9/

