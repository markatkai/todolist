# Build single pages
FROM node:20-alpine AS build-stage

WORKDIR /app

COPY package.json package-lock.json ./
RUN npm ci

COPY . .
RUN npm run build

# Enable nginx for routing to static pages and the backend
FROM nginx:alpine

COPY nginx.conf /etc/nginx/conf.d/default.conf

COPY --from=build-stage /app/build/client /usr/share/nginx/html

EXPOSE 80
