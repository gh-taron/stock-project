# Stock Project

Web application for stock management between buyer and supplier enterprises, built on a microservices architecture.

## Overview

Platform allowing buyer enterprises to browse available suppliers, place orders and manage their internal users. Supplier enterprises can manage their product catalog, stock levels and process incoming orders.

## Architecture

Backend split into independent microservices, orchestrated through a Eureka server for service discovery.

- **eureka-server** — Service discovery (port 9000)
- **authentications** — JWT authentication & registration (port 8081)
- **users** — User management, profile pictures, favorite suppliers (port 8082)
- **enterprises** — Enterprise management (port 8083)
- **products** — Product catalog (port 8084)
- **orders** — Orders & order details (port 8085)
- **stocks** — Per-owner stock tracking (port 8086)

Each microservice has its own business logic and REST controller, sharing the PostgreSQL schema `stock_project`.

## Tech stack

**Frontend**
- Angular 17 (module-based)
- Bootstrap 5 + Bootstrap Icons

**Backend**
- Java 17
- Spring Boot 3
- Spring Cloud Netflix Eureka (service discovery)
- Spring Cloud OpenFeign (inter-service REST calls)
- Spring Data JPA / Hibernate
- PostgreSQL
- JWT (auth0/java-jwt) + BCrypt
- Lombok
- Maven

**Database**
- PostgreSQL
