
# 🏨 Hotel Reservation System - Spring Boot Backend

This is the backend for the **Hotel Reservation System**, built using **Spring Boot** and **GraphQL**. It provides APIs for managing hotels, guests, bookings, and reviews, and is integrated with an AI service to generate review summaries.

---

## 🚀 Features

- 🏨 Hotel listing and filtering
- 📅 Booking creation and management
- 👤 Guest information and validation
- ✍️ Hotel reviews and AI-generated summaries
- ⚙️ GraphQL API using Spring Boot
- 🛠️ Connected with an Android app frontend using Retrofit

---

## 🛠 Tech Stack

- Java 17
- Spring Boot 3
- GraphQL Java
- Spring Data JPA
- PostgreSQL / MySQL (choose your DB)
- Azure OpenAI API (for AI-generated summaries)
- Gradle (or Maven)

---

## 📁 Project Structure

```
src/
├── main/
│   ├── java/com/example/hotel/
│   │   ├── controller/
│   │   ├── model/
│   │   ├── repository/
│   │   ├── service/
│   │   └── HotelReservationApplication.java
│   └── resources/
│       ├── application.yml
│       └── schema.graphqls
```

---

## 📦 Getting Started

### Prerequisites

- Java 17+
- Gradle or Maven
- PostgreSQL or MySQL installed
- IntelliJ / VSCode
- Docker (optional)

### Installation

1. **Clone the repo**

```bash
git clone https://github.com/your-username/hotel-reservation-backend.git
cd hotel-reservation-backend
```

2. **Configure your `application.yml`**

```yaml
spring:
  datasource:
    url: jdbc:postgresql://localhost:5432/hotel_db
    username: your_user
    password: your_password
  jpa:
    hibernate:
      ddl-auto: update
    show-sql: true

graphql:
  servlet:
    mapping: /graphql
    enabled: true

openai:
  endpoint: https://your-azure-openai-endpoint/
  key: YOUR_OPENAI_API_KEY
```

3. **Run the app**

```bash
./gradlew bootRun
```

---

## 🧪 Testing the API

### Access GraphQL Playground

Once running, navigate to:

```
http://localhost:8080/graphiql
```

### Sample Query

```graphql
query {
  hotels {
    id
    name
    location
    pricePerNight
  }
}
```

### Sample Mutation

```graphql
mutation {
  addReview(hotelId: 1, comment: "Amazing experience!") {
    id
    comment
  }
}
```

---

## 🤖 AI Review Summary

Every 24 hours, the backend uses Azure OpenAI to generate a summarized review for each hotel. This is triggered automatically via scheduled tasks.

---

## 🧹 Future Improvements

- Authentication & authorization (Spring Security + JWT)
- Admin panel for hotel management
- Stripe/PayPal payment integration
- Email confirmations

---

## 📄 License

MIT License. Feel free to use and contribute.

---

## 🤝 Contributing

Pull requests are welcome! For major changes, please open an issue first.

---

## 👨‍💻 Maintainer

Built by [Sriram Ramesh](https://github.com/Sriram247)
