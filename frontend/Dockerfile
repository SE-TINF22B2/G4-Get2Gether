#FROM node:18-alpine AS build
#WORKDIR /app
#COPY package*.json ./
#RUN npm install
#COPY . .
#RUN npm run build:production
#EXPOSE 4200
#CMD ["npm", "start"]


#FROM node:18-alpine AS build
#WORKDIR /app
#
#COPY . .
#RUN npm install
#RUN npm run build:production
## Serve Application using Nginx Server
#FROM nginx:alpine
#COPY --from=build /app/dist/frontend/ /usr/share/nginx/html
#EXPOSE 80

### STAGE 1:BUILD ###
# Defining a node image to be used as giving it an alias of "build"
# Which version of Node image to use depends on project dependencies
# This is needed to build and compile our code
# while generating the docker image
FROM node:18.13-alpine AS build
# Create a Virtual directory inside the docker image
WORKDIR /dist/src/app
# Copy files to virtual directory
# COPY package.json package-lock.json ./
# Run command in Virtual directory
RUN npm cache clean --force
# Copy files from local machine to virtual directory in docker image
#COPY ./ /dist/src/app/
COPY . .
RUN npm install
RUN npm run build:production


### STAGE 2:RUN ###
# Defining nginx image to be used
FROM nginx:latest AS ngi
# Copying compiled code and nginx config to different folder
# NOTE: This path may change according to your project's output folder
COPY --from=build /dist/src/app/dist/frontend/browser /usr/share/nginx/html
COPY /nginx.conf  /etc/nginx/conf.d/default.conf
# Exposing a port, here it means that inside the container
# the app will be using Port 80 while running
EXPOSE 80

