# Project Name: shopping-jewel
--------------------------------------------------------------
The project is build using following stacks
- Backend: Java Spring-BootHTML
- JWT Token
- Frontend: HTML, CSS and JavaScript
- Database: MySQL 8

# Build process:
1️⃣ Run Backend (Spring Boot)
Open a terminal and go to the backend folder:

cd path\to\shopping-cart-backend

If you have Maven installed:

mvn spring-boot:run

Or if you already built it:

mvn clean package
java -jar target/shopping-cart-0.0.1-SNAPSHOT.jar

The backend should now run at:
http://localhost:8080

2️⃣ Run Frontend (ReactJS)
Open another terminal and go to the frontend folder:

cd path\to\shopping-cart-frontend

Install dependencies (first time only):

npm install

Start the development server:

npm start

The frontend will run at:
http://localhost:3000

3️⃣ Connecting Frontend & Backend
In your frontend project, make sure API_BASE_URL in your config (e.g., src/config.js or .env) points to:

http://localhost:8080

If using .env in React:
REACT_APP_API_BASE_URL=http://localhost:8080

4️⃣ Access in Browser
Open http://localhost:3000 to see the shopping cart UI.

It will call the backend API at http://localhost:8080.

# Debug Tips
--------------------------------------------------------------
# Build modules separately:
- Backend: mvn clean package && mvn spring-boot:run
- Frontend: Build and Run docker container
docker build -t shopping-frontend .
docker run -p 3000:3000 --name shopping-frontend --rm shopping-frontend
- Mysql directory: docker-compose down && docker-compose up

docker build -t shopping-frontend .
docker run -p 3000:80 --add-host=host.docker.internal:host-gateway --rm shopping-frontend
Or
docker run -p 3000:80 --rm shopping-frontend

# Start and Stop Nginx to run static html on localhost:
nginx -c $(pwd)/nginx.conf

nginx -s stop

# Build all modules under same network through docker-compose.yml
docker-compose up
docker-compose down

# Build respective service after any code change
docker compose build backend --no-cache
docker compose up -d
Or
docker-compose up --build backend

Note: docker compose or docker-compose both commands work.

# Logs
docker logs -f bhagyalaxmi-jewel-backend-1
docker-compose logs -f backend
docker-compose logs -f backend | grep --line-buffered "ERROR"

# Login to DB
docker exec -it shopping-mysql mysql -u root -p

USE shopping_cart;

SHOW TABLES;

SELECT * FROM users;

INSERT INTO users (username, email, password, roles) 
VALUES (
  'abc',
  'abc@abc.com',
  '$2a$10$7Q0MZLhH1YQaFWTplozR6uI0K5D26ldqO2FxwhpYXcYlUfDFWRD7K', 
  'ROLE_USER'
);

UPDATE users
SET password = '$2y$10$DHKyKz//ML6D8u3q1iancejiGAs97GFcEA4mAxWj1JSMmoXVWxsSa'
WHERE username = 'abc';

Note: 
- Password 'abcpass' has hash key $2y$10$DHKyKz//ML6D8u3q1iancejiGAs97GFcEA4mAxWj1JSMmoXVWxsSa
- Link https://bcrypt.online/ to generate Hash key for text password 

# API call
- Login: Outside container
curl -X POST http://localhost:8080/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{"username":"abc", "password":"abcpass"}'

- Login: Inside container
docker exec -it bhagyalaxmi-jewel-backend-1 curl -X POST http://localhost:8080/api/auth/login \
 -H "Content-Type: application/json" \
 -d '{"username":"abc","password":"abcpass"}'

- Product: Outside container
curl -f http://localhost:8080/api/products
curl http://localhost:8080/api/products

- Product: Inside frontend container
docker exec -it bhagyalaxmi-jewel-frontend-1 sh
apk add --no-cache curl
curl http://backend:8080/api/products

Note: If this works, your Nginx proxy will also work.

- Register:
curl -X POST http://localhost:8080/api/auth/register \
-H "Content-Type: application/json" \
-d '{"username":"testuser", "email":"admin@example.com", "password":"admin123"}'

# Check asset files inside frontend container
docker exec -it shopping-frontend sh
ls -R /usr/share/nginx/html

# Find file from a folder
find src -name "SecurityConfig.java"


# Start everything with just:
--------------------------------------------------------------
docker compose up --build

That will:

    Build the backend and frontend images (so changes are picked up)

    Start MySQL, SMTP, backend, and frontend containers in the right order

    Stream all logs to your terminal

If you want it to run in the background:

docker compose up --build -d

And to stop + clean up containers, networks, and volumes in one shot:

docker compose down -v

# Do same using allias
For Bash or Zsh (Linux, macOS)
Edit your ~/.bashrc or ~/.zshrc and add:

alias dcup='docker compose up --build -d'
alias dcdown='docker compose down -v'
alias dclogs='docker compose logs -f'

Then reload your shell config:

source ~/.bashrc   # or source ~/.zshrc

For Windows PowerShell
Add this to your PowerShell profile:

Set-Alias dcup "docker compose up --build -d"
Set-Alias dcdown "docker compose down -v"
Set-Alias dclogs "docker compose logs -f"

After that:

    dcup → builds and starts everything in background

    dcdown → stops and removes everything

    dclogs → follows logs for all containers


# End of session
--------------------------------------------------------------
- Frontend side: Login page works
- Backend side: All api endpoints work

From "Why is everything unhealthy?!" to a fully working MySQL + backend + frontend + JWT login stack.

Now got fixed:

    CORS fixed ✅

    Dockerfile paths fixed ✅

    Health checks working ✅

    JWT secret secured ✅

    One-command startup ✅
