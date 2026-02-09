# Spring Boot GraphQL API

A complete Spring Boot application demonstrating GraphQL with queries and mutations for managing books and authors.

## Features

- **GraphQL Queries**: Retrieve books and authors
  - `allBooks`: Get all books
  - `bookById`: Get a specific book by ID
  - `booksByAuthor`: Search books by author name
  - `allAuthors`: Get all authors
  - `authorById`: Get a specific author by ID
  - `authorByName`: Search author by name

- **GraphQL Mutations**: Create, update, and delete books
  - `createBook`: Add a new book with input validation
  - `updateBook`: Update book details
  - `deleteBook`: Remove a book by ID

- **GraphQL Schema**: Type-safe schema with input types and queries
- **GraphiQL UI**: Interactive GraphQL explorer at `/graphiql`

## Project Structure

```
src/main/
├── java/com/graphql/
│   ├── api/                    # Main application class
│   ├── model/                  # Domain models (Book, Author)
│   ├── service/                # Business logic layer
│   ├── resolver/               # GraphQL resolvers (Queries & Mutations)
│   └── dto/                    # Data transfer objects for inputs
└── resources/
    ├── graphql/schema.graphqls # GraphQL schema definition
    └── application.yml         # Application configuration
```

## Prerequisites

- Java 17 or higher
- Maven 3.6+

## Getting Started

### 1. Build the Project

```bash
mvn clean install
```

### 2. Run the Application

```bash
mvn spring-boot:run
```

The application starts on `http://localhost:8080`

### 3. Access GraphQL UI

Open your browser and navigate to:
```
http://localhost:8080/graphiql
```

## Example Queries

### Get All Books

```graphql
query {
  allBooks {
    id
    title
    author
    price
    pages
    description
  }
}
```

### Get Book by ID

```graphql
query {
  bookById(id: "1") {
    title
    author
    isbn
    price
  }
}
```

### Get Books by Author

```graphql
query {
  booksByAuthor(author: "George Orwell") {
    id
    title
    price
    pages
  }
}
```

### Get All Authors

```graphql
query {
  allAuthors {
    id
    name
    email
    country
    booksPublished
  }
}
```

## Example Mutations

### Create a New Book

```graphql
mutation {
  createBook(input: {
    title: "The Hobbit"
    author: "J.R.R. Tolkien"
    isbn: "978-0547928197"
    price: 9.99
    pages: 310
    description: "A fantasy adventure novel"
  }) {
    id
    title
    author
    price
  }
}
```

### Update a Book

```graphql
mutation {
  updateBook(input: {
    id: "1"
    title: "The Great Gatsby - Revised Edition"
    price: 12.99
    description: "Updated classic novel"
  }) {
    id
    title
    price
    description
  }
}
```

### Delete a Book

```graphql
mutation {
  deleteBook(id: "1")
}
```

## API Endpoints

- **GraphQL Endpoint**: `POST /graphql`
- **GraphiQL UI**: `GET /graphiql`

## Sample Data

The application comes with pre-loaded sample data:
- 3 books (The Great Gatsby, To Kill a Mockingbird, 1984)
- 3 authors (F. Scott Fitzgerald, Harper Lee, George Orwell)

Data is stored in memory and resets on application restart.

## Technologies Used

- Spring Boot 3.2.0
- Spring GraphQL Starter
- Lombok
- Maven

## Notes

- Data is stored in-memory using ArrayLists
- For production, integrate a database (e.g., Spring Data JPA with PostgreSQL/MySQL)
- GraphQL schema is loaded from `classpath:graphql/schema.graphqls`
- GraphiQL is enabled for easy interactive testing

## Future Enhancements

- Add database persistence with JPA
- Add authentication and authorization
- Add pagination and filtering
- Add subscriptions for real-time updates
- Add error handling and validation
- Add file upload support