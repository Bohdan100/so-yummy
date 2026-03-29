# So-Yummy Application

**So-Yummy** is a production-grade **RESTful API** service developed with **Kotlin** and the **Spring Boot** framework,
crafted to celebrate healthy cooking through effortless recipe and ingredient management. Containerized using **Docker**
for consistent deployment across development, testing, and production environments, the platform transforms how users
discover, organize, and share nutritious meals—making healthy eating both accessible and enjoyable.
The application leverages **MongoDB** as its primary NoSQL database for flexible, document-based data storage, while
implementing **token-based authentication** via **JSON Web Tokens (JWTs)** to ensure secure user access. Through its
comprehensive RESTful API, users can create, search, and manage personalized collections of favorite recipes and
ingredients, enabling a complete culinary ecosystem from discovery to preparation.

## Features

* **Authentication API** powered by **Spring Security** with a custom **JWT filter** for secure user registration,
  login, and logout.
* **Secure Token-Based Authentication** implemented using **JSON Web Tokens (JWTs)** to ensure secure user access to
  application resources.
* **Modern Development with Kotlin:** Leverage Kotlin's concise syntax, null safety, and data classes to enhance code
  readability, reduce boilerplate, and improve maintainability.
* Structured and organized JSON data storage backed by **Mongo DB**.
* **Containerized Deployment with Docker:** Seamlessly build and run the entire ecosystem—including the Spring Boot
  application and MySQL database—using Docker and Docker Compose for consistent, "one-command" environment setup.
* **CRUD operations** available through RESTful API endpoints for the following collections:
    - Recipes
    - Ingredients
    - Shopping lists
    - Favorite recipes
    - Favorite ingredients
    - Subscribes
* Reduced boilerplate code through the use data classes.
* Streamlined setup and build processes using **Gradle**.

## Requirements

The following configurations are required to launch the project:

- **Java Platform (JDK)**: 25
- **Gradle**: 9.2.0
- **Spring Boot**: 4.0.2
- **Docker**: 29.1.3
- **Kotlin**: 2.3.0

## Getting Started

1. Clone the repository:
   ```bash
   git clone https://github.com/Bohdan100/so-yummy
   cd so-yummy

2. Build and Run the Application Using Docker in Terminal:
   ```bash
   docker-compose up -d --build app

3. Make your HTTPS requests using the following endpoints (e.g., via **Postman**), for authentification:

### 1. Authentication Endpoints

- **Register**: [http://localhost:8080/api/v1/auth/register](http://localhost:8080/api/v1/auth/register)
    - `POST`: Registers a new user. Requires a JSON body with user credentials.
      Request body:
  ```json lines
    {
     "name": "User",
     "email": "user@example.com",
     "password": "secret123456"
     }
  ```
- **Login**: [http://localhost:8080/api/v1/auth/login](http://localhost:8080/api/v1/auth/login)
    - `POST`: Logs in an existing user. Returns a Bearer token.
  ```json lines
    {
     "email": "user@example.com",
     "password": "secret123456"
     }
  ```
- **Logout**: [http://localhost:8080/api/v1/auth/logout](http://localhost:8080/api/v1/auth/logout)
    - `POST`: Logs out the current user. Requires `Authorization` header with Bearer token.

---

### Important: After successful authentication, include the Bearer token in the Authorization header for all subsequent requests:

### 2. Ingredients Endpoints

- **Get All Ingredients**: [http://localhost:8080/api/v1/ingredients](http://localhost:8080/api/v1/ingredients)
    - `GET`: Retrieves the list of all ingredients.

- **Get Ingredient by ID
  **: [http://localhost:8080/api/v1/ingredients/{id}](http://localhost:8080/api/v1/ingredients/{id})
    - `GET`: Retrieves a single ingredient by its ID, for example "id": "640c2dd963a319ea671e3661".

- **Search Ingredients by Title
  **: [http://localhost:8080/api/v1/ingredients/search?title={title}](http://localhost:8080/api/v1/ingredients/search?title={title})
    - `GET`: Searches for ingredients containing a specific title.

- **Create Ingredient**: [http://localhost:8080/api/v1/ingredients](http://localhost:8080/api/v1/ingredients)
    - `POST`: Adds a new ingredient to the database. Requires a JSON body.

- **Update Ingredient**: [http://localhost:8080/api/v1/ingredients/{id}](http://localhost:8080/api/v1/ingredients/{id})
    - `PUT`: Updates the details of an ingredient by ID. Requires a JSON body.

- **Delete Ingredient**: [http://localhost:8080/api/v1/ingredients/{id}](http://localhost:8080/api/v1/ingredients/{id})
    - `DELETE`: Deletes an ingredient by ID.

---

### 3. Recipes Endpoints

- **Get All Recipes**: [http://localhost:8080/api/v1/recipes](http://localhost:8080/api/v1/recipes)
- `GET`: Retrieves the list of all recipes.

- **Get Recipe by ID**: [http://localhost:8080/api/v1/recipes/{id}](http://localhost:8080/api/v1/recipes/{id})
    - `GET`: Retrieves a single recipe by its ID, for example "id": "640cd5ac2d9fecf12e8898cc".

- **Search Recipes**: [http://localhost:8080/api/v1/recipes/search](http://localhost:8080/api/v1/recipes/search)
    - `GET`: Searches for recipes by optional parameters:
        - `title`: Partial or full recipe title.
        - `category`: Recipe category (e.g., "Main Dish").
        - `area`: Geographic area (e.g., "Italian").
        - `tag`: A specific tag associated with recipes.

  Example query:  
  [http://localhost:8080/api/v1/recipes/search?title=pasta](http://localhost:8080/api/v1/recipes/search?title=pasta)

- **Create Recipe**: [http://localhost:8080/api/v1/recipes](http://localhost:8080/api/v1/recipes)
    - `POST`: Creates a new recipe. Requires a JSON body with recipe details based on `RecipeCreateDto`.

- **Update Recipe**: [http://localhost:8080/api/v1/recipes/{id}](http://localhost:8080/api/v1/recipes/{id})
    - `PUT`: Updates an existing recipe by ID. Requires a JSON body with updated details based on `RecipeUpdateDto`.

- **Delete Recipe**: [http://localhost:8080/api/v1/recipes/{id}](http://localhost:8080/api/v1/recipes/{id})
    - `DELETE`: Deletes a recipe by its ID.

### 3. Additional Endpoints

**Collection**: **Base URL**

* Recipe Favorites: http://localhost:8080/api/v1/recipefavorites
* User Favorites: http://localhost:8080/api/v1/userfavorites
* Shopping Lists: http://localhost:8080/api/v1/shoppinglists
* Subscribes: http://localhost:8080/api/v1/subscribes

**Available Operations:**

- GET / - Get all items
- GET /{id} - Get item by ID
- POST / - Create new item
- PUT /{id} - Update existing item
- DELETE /{id} - Delete item

All endpoints require Bearer token authentication.
