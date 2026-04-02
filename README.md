# So-Yummy Application

**So-Yummy** is a production-grade **RESTful API** service developed with **Kotlin** and the **Spring Boot** framework,
crafted to celebrate healthy cooking through effortless recipe and ingredient management. Containerized using **Docker**
for consistent deployment across development, testing, and production environments, the platform transforms how users
discover, organize, and share nutritious meals — making healthy eating both accessible and enjoyable.

The application leverages **MongoDB** as its primary NoSQL database for flexible, document-based data storage, while
implementing **token-based authentication** via **JSON Web Tokens (JWTs)** to ensure secure user access. Through its
comprehensive RESTful API, users can create, search, and manage personalized collections of favorite recipes and
ingredients. The API features **pagination** across all collection endpoints for efficient data loading and *
*aggregation pipelines** for advanced analytics, including top-rated recipes, most-used ingredients, and
popularity-based rankings.

## Features

* **Authentication API** powered by **Spring Security** with a custom **JWT filter** for secure user registration,
  login, and logout.
* **Secure Token-Based Authentication** implemented using **JSON Web Tokens (JWTs)** to ensure secure user access to
  application resources.
* **Pagination Support:** All collection endpoints support pagination with customizable page size, page number, and
  sorting parameters for optimal data loading (`fun getAllIngredients`, `fun searchIngredients`, `fun getAllRecipes`,
  `fun searchRecipesByCategory`, etc.).
* **Aggregation Pipelines:** Advanced MongoDB aggregation queries for analytics:
    - Top recipes by popularity ranking (`fun getTopRecipesByPopularity`);
    - Most frequently used ingredients across recipes (`fun getTopIngredientsByUsage`);
    - Rating-based sorting for favorite recipes (`fun getFavoritesByRatingDesc()`);
* **Modern Development with Kotlin:** Leverage Kotlin's concise syntax, null safety, and data classes to enhance code
  readability, reduce boilerplate, and improve maintainability.
* Structured and organized JSON data storage backed by **MongoDB**.
* **Containerized Deployment with Docker:** Seamlessly build and run the entire ecosystem — including the Spring Boot
  application and MongoDB database — using Docker and Docker Compose for consistent, "one-command" environment setup.
* **CRUD operations** available through RESTful API endpoints for the following collections:
    - Recipes
    - Ingredients
    - Shopping lists
    - Recipe favorites
    - Subscribes
* **Advanced Bean Lifecycle Management:** Implemented Method Injection using `@Lookup` to handle stateful
  prototype-scoped components (`class ExecutionTracker`, `class RecipeValidator`, `class IngredientValidator`) within
  singleton services,
  enabling per-operation tracking and validation without shared state conflicts.
* Reduced boilerplate code through the use of data classes.
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
    User Registration Request Body:
  ```json lines
    {
     "name": "User",
     "email": "user@example.com",
     "password": "secret123456"
     }
  ```
    Admin Registration Request Body:
```json lines
    {
     "name": "Admin",
     "email": "admin@example.com",
     "password": "secret12345678", 
     "role": "ADMIN"
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

#### Important:

#### After successful authentication, include the Bearer token in the Authorization header for all subsequent requests:

### 2. Ingredients Endpoints

- **Get All Ingredients with Pagination**:
  **: [http://localhost:8080/api/v1/ingredients?page=3&size=4&sort=title,asc](http://localhost:8080/api/v1/ingredients?page=3&size=4&sort=title,asc)
    - `GET`: Retrieves the list of all ingredients with pagination by page and size, displaying up to 10 elements per
      page.

- **Get Ingredient by ID**:
  **: [http://localhost:8080/api/v1/ingredients/{id}](http://localhost:8080/api/v1/ingredients/{id})
    - `GET`: Retrieves a single ingredient by its ID, for example "id": "640c2dd963a319ea671e3669".

- **Get Top Ingredients by Usage Frequency**:
  **: [http://localhost:8080/api/v1/ingredients/top?page=0&size=5]( http://localhost:8080/api/v1/ingredients/top?page=0&size=5)
    - `GET`: Retrieves the most frequently used ingredients with pagination by page and size, displaying up to 10
      elements per page.

- **Search Ingredients by Title with Pagination**:
  **: [http://localhost:8080/api/v1/ingredients/search?title=beef&page=0&size=10](http://localhost:8080/api/v1/ingredients/search?title=beef&page=0&size=10)
    - `GET`: Searches for ingredients containing a specific title or phrase, with pagination by page and size,
      displaying up to 10 elements per page.

- **Create Ingredient**: [http://localhost:8080/api/v1/ingredients](http://localhost:8080/api/v1/ingredients)
    - `POST`: Adds a new ingredient to the database. Requires a JSON body with ingredient details based on
      `IngredientCreateDto`.

- **Update Ingredient**: [http://localhost:8080/api/v1/ingredients/{id}](http://localhost:8080/api/v1/ingredients/{id})
    - `PUT`: Updates the details of an existing ingredient by ID. Requires a JSON body with updated details based on
      `IngredientUpdateDto`.

- **Delete Ingredient**: [http://localhost:8080/api/v1/ingredients/{id}](http://localhost:8080/api/v1/ingredients/{id})
    - `DELETE`: Deletes an ingredient from the ingredients list by ID.

Pagination Parameters:
page: Page number (default: 0)
size: Items per page (default: 10)
sort: Sort field and direction, e.g., title,asc or title,desc

---

### 3. Recipes Endpoints

- **Get All Recipes with Pagination
  **: [http://localhost:8080/api/v1/recipes?page=0&size=10&sort=popularity,desc](http://localhost:8080/api/v1/recipes?page=0&size=10&sort=popularity,desc)
- `GET`: Retrieves the list of all recipes with pagination by page and size, displaying up to 10 elements per
  page.

- **Get Recipe by ID**: [http://localhost:8080/api/v1/recipes/{id}](http://localhost:8080/api/v1/recipes/{id})
    - `GET`: Retrieves a single recipe by its ID, for example "id": "640cd5ac2d9fecf12e8897f1".

- **Get Top Recipes by Popularity**:
  **: [http://localhost:8080/api/v1/recipes/top?page=0&size=8]( http://localhost:8080/api/v1/ingredients/top?page=0&size=8)
    - `GET`: Retrieves the most popularity recipes with pagination by page and size, displaying up to 10 elements per
      page.

- **Search Recipes
  **: [http://localhost:8080/api/v1/recipes/search?title=pasta&page=0&size=10](http://localhost:8080/api/v1/recipes/search?title=pasta&page=0&size=10)
    - `GET`: Searches for recipes by optional parameters with pagination by page and size, displaying up to 10 elements
      per page:
        - `title`: Partial or full recipe title.
        - `category`: Recipe category (e.g., "Main Dish").
        - `area`: Geographic area (e.g., "Italian").
        - `tag`: A specific tag associated with recipes.

- **Create Recipe**: [http://localhost:8080/api/v1/recipes](http://localhost:8080/api/v1/recipes)
    - `POST`: Creates a new recipe. Requires a JSON body with recipe details based on `RecipeCreateDto`.

- **Update Recipe**: [http://localhost:8080/api/v1/recipes/{id}](http://localhost:8080/api/v1/recipes/{id})
    - `PUT`: Updates an existing recipe by ID. Requires a JSON body with updated details based on `RecipeUpdateDto`.

- **Delete Recipe**: [http://localhost:8080/api/v1/recipes/{id}](http://localhost:8080/api/v1/recipes/{id})
    - `DELETE`: Deletes a recipe from the recipe list by ID.

### 4. Recipe Favorites Endpoints

- **Get All Favorites with Pagination**:
  **: [http://localhost:8080/api/v1/recipefavorites?page=0&size=10&sort=amount,desc](http://localhost:8080/api/v1/recipefavorites?page=0&size=10&sort=amount,desc)
- `GET`: Retrieves a paginated list of all favorite recipes with pagination by page and size, displaying up to 10
  elements per page.

- **Get Favorite by ID**:
  **: [http://localhost:8080/api/v1/recipefavorites/{id}](http://localhost:8080/api/v1/recipefavorites/{id})
    - `GET`: Retrieves a single favorite record by its ID, for example "id": "6431bb9c1834e4319c89772c".

- **Get Top 10 Favorites by Rating**:
  **: [http://localhost:8080/api/v1/recipefavorites/rating](http://localhost:8080/api/v1/recipefavorites/rating)
    - `GET`: Retrieves the top 10 most popular recipes, calculated via aggregation based on their overall rating.

- **Get Favorites by Recipe ID**:
  **: [http://localhost:8080/api/v1/recipefavorites/byRecipeId/{recipeId}](http://localhost:8080/api/v1/recipefavorites/byRecipeId/{recipeId})
    - `GET`: Retrieves a list of favorite records associated with a specific recipe ID.

- **Get Favorites by Amount**:
  **: [http://localhost:8080/api/v1/recipefavorites/byAmount/{amount}](http://localhost:8080/api/v1/recipefavorites/byAmount/{amount})
    - `GET`: Retrieves favorite records matching a specific "amount" value (e.g., 1, 2, 3, etc.).

- **Create Favorite**: [http://localhost:8080/api/v1/recipefavorites](http://localhost:8080/api/v1/recipefavorites)
    - `POST`: Adds a new recipe to the favorites list. Requires a JSON body with recipeId and amount based on
      `RecipeFavoriteCreateDto`.

- **Update Favorite**:
  **: [http://localhost:8080/api/v1/recipefavorites/{id}](http://localhost:8080/api/v1/recipefavorites/{id})
    - `PUT`: Updates the amount of an existing favorite record by ID. Requires a JSON body with amount based on
      `RecipeFavoriteUpdateDto`.

- **Delete Favorite**:
  **: [http://localhost:8080/api/v1/recipefavorites/{id}](http://localhost:8080/api/v1/recipefavorites/{id})
    - `DELETE`: Removes a recipe from the favorites list by ID.

Pagination Parameters:
page: Page number (default: 0)
size: Items per page (default: 10)
sort: Sort field and direction, e.g., title,asc or title,desc

---

### 5. Additional Endpoints

**Collection**: **base URL**

* Shopping Lists: http://localhost:8080/api/v1/shoppinglists
* Subscribes: http://localhost:8080/api/v1/subscribes

**Available Operations:**

- GET / - Get all items
- GET /{id} - Get item by ID
- POST / - Create new item
- PUT /{id} - Update existing item
- DELETE /{id} - Delete item

All endpoints require Bearer token authentication.
