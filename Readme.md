# 📚 Course Search Application (Spring Boot + Elasticsearch)

A professional-grade Spring Boot application designed to index and search educational course data using Elasticsearch. This backend system enables powerful filtering, pagination, and sorting capabilities through a RESTful API.

---

## 🚀 Features

- Indexes a set of sample course documents into Elasticsearch at application startup.
- Provides a flexible search API with:
  - Keyword-based full-text search (title & description)
  - Range filters: age, price
  - Exact match filters: category, type
  - Date filtering based on next session
  - Sorting by date or price
  - Pagination support

---

## ⚙️ Tech Stack

- **Spring Boot 2.7.18**
- **Elasticsearch 7.17.x** (via Docker)
- **Spring Data Elasticsearch**
- **Jackson** for JSON handling
- **Lombok** (for reducing boilerplate)

---

## 🛠️ Setup Instructions

### 1. Clone and Navigate

```bash
git clone <your-repo-url>
cd course-search
```

### 2. Start Elasticsearch with Docker

```bash
docker-compose up -d
```

Verify it's running:

```bash
curl http://localhost:9200
```

### 3. Build and Run the Application

```bash
mvn clean install
mvn spring-boot:run
```

---

## 📁 Sample Data

The file `src/main/resources/sample-courses.json` contains at least 50 diverse course records with fields like:

- `id`, `title`, `description`
- `category` (e.g., Math, Science, Art)
- `type` (ONE_TIME, COURSE, CLUB)
- `gradeRange`
- `minAge`, `maxAge`, `price`
- `nextSessionDate` (ISO-8601 format)

These records are indexed automatically at startup.

---

## 🔍 Search API Overview

### Endpoint

```
GET /api/search
```

### Supported Query Parameters

| Parameter     | Description                                 |
|---------------|---------------------------------------------|
| `q`           | Full-text keyword search                    |
| `minAge`      | Minimum student age                         |
| `maxAge`      | Maximum student age                         |
| `category`    | Course category                             |
| `type`        | Course type (CLUB, COURSE, ONE_TIME)        |
| `minPrice`    | Minimum price filter                        |
| `maxPrice`    | Maximum price filter                        |
| `startDate`   | Show courses on/after this session date     |
| `sort`        | `upcoming` (default), `priceAsc`, `priceDesc` |
| `page`        | Page number (default: 0)                    |
| `size`        | Page size (default: 10)                     |

### Sample Request

```bash
curl "http://localhost:8080/api/search?q=math&minAge=7&category=Math&sort=priceAsc&page=0&size=5"
```

### Sample Response

```json
{
  "total": 2,
  "courses": [
    {
      "id": "1",
      "title": "Math Basics",
      "category": "Math",
      "price": 49.99,
      "nextSessionDate": "2025-06-10T15:00:00Z"
    }
  ]
}
```

---

## 🧪 Testing

To validate functionality:

- Use Postman or `curl` to call `/api/search` with various combinations.
- Verify expected results, pagination, and sorting behaviors.

---

## 📌 Notes

- Elasticsearch index name: `courses`
- No authentication required on ES (runs on `localhost:9200`)
- Automatically indexes data on startup

---

## 👤 Author

Developed by **Priyank Tyagi**  
Aimed at showcasing backend search capabilities with Spring Boot and Elasticsearch.

---