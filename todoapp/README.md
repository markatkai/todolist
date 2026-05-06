# My TODO list app

React application built using React Router. Should work as a single page app, so, server side rendering has been disabled.

## Getting Started

### Installation

Install the dependencies:

```bash
npm install
```

### Development

Start the development server with HMR:

```bash
npm run dev
```

Your application will be available at `http://localhost:5173`.

Application will try to contact the backend in hardcoded location http://localhost:8080. The address can be changed in vite.config.ts.

## Building for Production

Create a production build:

```bash
npm run build
```

## Deployment

### Docker Deployment

To build and run using Docker:

```bash
docker build -t my-app .

# Run the container
docker run -p 3000:3000 my-app
```

## Icons

Vectors and icons by <a href="https://www.svgrepo.com" target="_blank">SVG Repo</a>

https://www.svgrepo.com/collection/web-9/

