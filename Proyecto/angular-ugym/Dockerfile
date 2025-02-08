FROM node:alpine

WORKDIR /web

COPY . .

RUN npm i -g @angular/cli@19

RUN npm i

EXPOSE 4200

ENTRYPOINT ["ng", "serve", "--host", "0.0.0.0"]