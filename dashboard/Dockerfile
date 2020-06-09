# Dockerfile
FROM node:10.16.3-alpine
MAINTAINER Diego Leonel Cañete Crescini

ENV HOME=/usr/src/app
RUN mkdir /usr/src
RUN mkdir $HOME
WORKDIR $HOME

COPY ./package.json $HOME
RUN npm config set registry http://registry.npmjs.org/
RUN npm install
COPY ./ $HOME

CMD ["npm", "start"]