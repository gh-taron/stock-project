# Stock Project

Web application for stock management between enterprises and suppliers, built on a microservices architecture.

## Overview

Platform allowing enterprises to manage their users, browse available suppliers, place orders, while suppliers can manage their product catalog and stock levels.

## Architecture

Backend split into independent microservices, orchestrated through a Eureka server for service discovery.

- **eureka-server** — Service discovery (port 8761)
- **authentications** — JWT authentication & registration (port 8081)
- **users** — User management, photo upload, favorite suppliers (port 8082)
- **enterprises** — Enterprise management (port 8083)
- **products** — Product catalog (port 8084)
- **orders** — Enterprise ↔ supplier orders (port 8086)
- **stocks** — Per-supplier stock tracking (port 8087)

Each microservice has its own business logic, REST controller, and shares the PostgreSQL schema `stock_project`.

## Tech stack

**Frontend**
- Angular 17 (module-based)
- Bootstrap 5 + Bootstrap Icons

**Backend**
- Java 17
- Spring Boot 3
- Spring Cloud Netflix Eureka (service discovery)
- Spring Data JPA / Hibernate
- PostgreSQL
- JWT (auth0/java-jwt) + BCrypt
- Lombok
- Maven

**Database**
- PostgreSQL