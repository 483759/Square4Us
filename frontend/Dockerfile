FROM node:12 AS build
WORKDIR /app
COPY package* ./
RUN npm install
COPY public ./public 
COPY src ./src 
COPY .env ./
COPY .env.production ./
RUN npm run build

# 2. 빌드 된 파일을 엔진엑스 컨테이너에 복사
FROM nginx:alpine
COPY --from=build /app/dist /usr/share/nginx/html