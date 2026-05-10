# 🚀 Crypto Portfolio Tracker

A scalable microservices-based cryptocurrency portfolio tracking platform built using Spring Boot, Spring Cloud, and modern distributed system architecture.

The platform allows users to:
- Register and authenticate securely using JWT
- Manage crypto holdings
- Track real-time portfolio value
- Fetch live cryptocurrency prices
- Create automated price alerts
- Trigger notifications when alert conditions are met

---

# 🏗️ Microservices Architecture

The project follows a distributed microservices architecture using:

- Spring Boot
- Spring Cloud
- Eureka Service Discovery
- Spring Cloud Gateway
- Config Server
- OpenFeign
- MySQL
- JWT Authentication

---

# 📌 Services Overview

| Service | Port | Responsibility |
|---|---|---|
| config-server | 8888 | Centralized configuration management |
| eureka-server | 8761 | Service discovery |
| api-gateway | 8080 | Routing + JWT validation |
| auth-service | 8081 | Authentication & authorization |
| holding-service | 8082 | Holdings CRUD operations |
| price-service | 8083 | Live crypto price fetching |
| portfolio-service | 8084 | Portfolio aggregation & valuation |
| alert-service | 8085 | Price alert management |
| notification-service | 8086 | Notification handling |

---

# 🧠 System Architecture

```text
                    ┌────────────────────┐
                    │   Config Server    │
                    └─────────┬──────────┘
                              │
                    ┌─────────▼──────────┐
                    │   Eureka Server    │
                    └─────────┬──────────┘
                              │
                    ┌─────────▼──────────┐
                    │    API Gateway     │
                    │ JWT Validation     │
                    └─────────┬──────────┘
                              │
        ┌─────────────────────┼─────────────────────┐
        │                     │                     │
        ▼                     ▼                     ▼

 ┌──────────────┐     ┌──────────────┐     ┌──────────────┐
 │ Auth Service │     │HoldingService│     │ PriceService │
 └──────────────┘     └──────────────┘     └──────────────┘
                              │
                              ▼
                    ┌──────────────────┐
                    │ PortfolioService │
                    └──────────────────┘
                              │
                              ▼
                    ┌──────────────────┐
                    │  Alert Service   │
                    └──────────────────┘
                              │
                              ▼
                    ┌──────────────────┐
                    │NotificationService│
                    └──────────────────┘
```

---

# 🔐 Authentication Flow

1. User logs in using `auth-service`
2. JWT token is generated
3. Client sends token to API Gateway
4. Gateway validates JWT
5. Gateway forwards trusted identity headers:
   - `X-User-Email`
   - `X-User-Role`
6. Internal services trust gateway headers

---

# 🔄 Inter-Service Communication

The project uses:

- OpenFeign Clients
- Eureka Service Discovery

for internal service communication.

Example:

```text
portfolio-service
        ↓
holding-service

portfolio-service
        ↓
price-service
```

---

# 📦 Features Implemented

## ✅ Authentication & Authorization

- User Registration
- User Login
- JWT Token Generation
- BCrypt Password Encryption
- Role-based structure
- Gateway-level authentication

---

## ✅ Holding Service

Users can:
- Add holdings
- Update holdings
- Delete holdings
- Fetch holdings

Example:

```json
{
  "symbol": "BTC",
  "quantity": 0.5
}
```

---

## ✅ Price Service

- Fetches live cryptocurrency prices
- External API integration
- Real-time valuation support

---

## ✅ Portfolio Service

Aggregates:
- User holdings
- Current crypto prices

Calculates:
- Asset value
- Total portfolio valuation

Example Response:

```json
{
  "userEmail": "akh@test.com",
  "assets": [
    {
      "symbol": "BTC",
      "quantity": 0.5,
      "currentPrice": 104523.45,
      "totalValue": 52261.72
    }
  ],
  "totalPortfolioValue": 52261.72
}
```

---

## ✅ Alert Service

Users can create alerts such as:

```text
Notify me when BTC > $120000
```

Supported conditions:
- ABOVE
- BELOW

Example:

```json
{
  "symbol": "BTC",
  "targetPrice": 120000,
  "conditionType": "ABOVE"
}
```

---

## ✅ Notification Service

Handles:
- Notification processing
- Alert notification logging

Current implementation:
- Console logging notifications

Future enhancement:
- Email notifications
- SMS notifications
- Push notifications

---

## ✅ Automated Alert Scheduler

Background scheduler:
- Runs periodically
- Fetches active alerts
- Checks current prices
- Triggers notifications automatically

---

# 🛠️ Tech Stack

## Backend
- Java 17
- Spring Boot 3
- Spring Cloud

## Security
- Spring Security
- JWT
- BCrypt

## Database
- MySQL

## Cloud & Distributed Systems
- Eureka Server
- Config Server
- OpenFeign
- API Gateway

## Build Tool
- Maven

---

# 📂 Project Structure

```text
Crypto-Portfolio-Tracker/
│
├── config-server/
├── eureka-server/
├── api-gateway/
├── services/
    ├──auth-service/
    ├── holding-service/
    ├── portfolio-service/
    ├── price-service/
    ├── alert-service/
    ├── notification-service/
```

---

# ⚙️ Running the Project

## Start Services in Order

```text
1. config-server
2. eureka-server
3. api-gateway
4. auth-service
5. holding-service
6. price-service
7. portfolio-service
8. notification-service
9. alert-service
```

---

# 🌐 Eureka Dashboard

```text
http://localhost:8761
```

---

# 🔑 Sample APIs

---

## Register User

```http
POST /api/auth/register
```

Body:

```json
{
  "username": "Akhil",
  "email": "akh@test.com",
  "password": "123456",
  "role": "USER"
}
```

---

## Login

```http
POST /api/auth/login
```

---

## Add Holding

```http
POST /api/holdings
```

---

## Get Portfolio

```http
GET /api/portfolio
```

---

## Create Alert

```http
POST /api/alerts
```

---

# 🚀 Future Enhancements

- RabbitMQ / Kafka Integration
- Redis Caching
- Docker & Kubernetes Deployment
- CI/CD Pipeline
- Email Notifications
- OAuth2 Authentication
- Rate Limiting
- Monitoring & Observability
- Circuit Breaker Pattern
- Distributed Tracing

---
