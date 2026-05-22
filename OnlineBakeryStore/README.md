# OnlineBakeryStore

Spring Boot bakery storefront with login, signup, profile, and admin user management.

## What it does

- Customer signup and login
- Profile view and account deletion
- Admin dashboard for viewing and editing users
- File-backed user storage with a default admin account
- Responsive Thymeleaf pages for bakery marketing content

## Default admin account

- Username: `admin`
- Password: `admin123`

The app stores user data outside the repo in:

`%USERPROFILE%\.online-bakery-store\Profile_Details.txt`

## Run locally

If you have Maven installed:

```bash
mvn spring-boot:run
```

Or build a jar:

```bash
mvn clean package
java -jar target/OnlineBakeryStore-0.0.1-SNAPSHOT.jar
```

The app listens on port `8080` by default.

## Docker run

```bash
docker build -t online-bakery-store .
docker run -p 8080:8080 -e PORT=8080 online-bakery-store
```

## Deploy

This project is ready for container-based deployment on platforms such as Render, Railway, or any VPS that can run Docker.

Recommended start command:

```bash
java -Dserver.port=${PORT:-8080} -jar /app/app.jar
```

## Notes

- The repo does not include the Maven Wrapper jar, so use local Maven or Docker for builds.
- User data is persisted in a text file, which is suitable for demos and small deployments. For production, move to a database such as PostgreSQL.
