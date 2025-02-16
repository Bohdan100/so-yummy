# So-Yummy Application
The **So-Yummy Application** is a Spring Boot application written in **Kotlin** that provides RESTful API endpoints to manage recipes and ingredients, empowering you to create a healthy cooking 
experience. The application leverages **MongoDB** as the primary database and offers secure user authentication with token-based security and functionalities for creating, searching, and managing 
favorite recipes and ingredients.

## Features
- **Authentication API** powered by **Spring Security** with a custom **JWT filter** for secure user registration, login, and logout.
- **Secure Token-Based Authentication** implemented using **JSON Web Tokens (JWTs)** to ensure secure user access to application resources.
- Structured and organized JSON data storage backed by **Mongo DB**.
- **CRUD operations** available through RESTful API endpoints for the following collections:
    - Recipes
    - Ingredients
    - Shopping lists
    - Favorite recipes
    - Favorite ingredients
    - Subscribes
- Reduced boilerplate code through the use of **Lombok**.
- Streamlined setup and build processes using **Gradle**.

## Requirements
The application is built using the following technologies:
- **Spring Boot**: 3.3.5
- **Kotlin**: 1.9.25
- **Java Platform (JDK)**: 21
- **MongoDB**: 2.0.0
- **JJWT**: 0.12.6
- **Lombok**: 1.18.36
- **JUnit**: 5
- **Gradle**: 8.8

## Database Setup
Before running the application, follow these steps to set up the database:

1. Create your own Cluster to work with the **MongoDB** database.
2. Install the MongoDB Compass application and connect it to the MongoDB Atlas cloud service:
3. Obtain the connection string for your Cluster. 
4. Create a collection named `so-yummy` in your Cluster. Inside this collection, create sub-collections: `ingredients`, `recipefavorites`, `recipes`, `shoppinglists`, `subscribes`, `userfavorites`,
   and `owners`. 
5. Create environment variables for working with the database according to the names specified in the `application.properties` file.

## Getting Started

1. Clone the repository:
   ```bash
   git clone https://github.com/Bohdan100/so-yummy
   cd so-yummy

2. Build and run the application in terminal using Gradle:
   ```bash
   .\gradlew bootRun     (Windows)
   ./gradlew bootRun     (Linux)

3. Build and run the application in terminal using JAR file:
   ```bash
   .\gradlew bootJar     (Windows)
   ./gradlew bootJar     (Linux)
   
   java -jar so-yummy-java.jar

4. Make your HTTPS requests using the following endpoints (e.g., via **Postman**), for example:
### 1. Authentication Endpoints
- **Register**: [http://localhost:8080/api/v1/auth/register](http://localhost:8080/api/v1/auth/register)
   - `POST`: Registers a new user. Requires a JSON body with user credentials.

- **Login**: [http://localhost:8080/api/v1/auth/login](http://localhost:8080/api/v1/auth/login)
   - `POST`: Logs in an existing user. Returns a Bearer token.

- **Logout**: [http://localhost:8080/api/v1/auth/logout](http://localhost:8080/api/v1/auth/logout)
   - `POST`: Logs out the current user. Requires `Authorization` header with Bearer token.

---

### 2. Ingredients Endpoints
- **Get All Ingredients**: [http://localhost:8080/api/v1/ingredients](http://localhost:8080/api/v1/ingredients)
   - `GET`: Retrieves the list of all ingredients.

- **Get Ingredient by ID**: [http://localhost:8080/api/v1/ingredients/{id}](http://localhost:8080/api/v1/ingredients/{id})
   - `GET`: Retrieves a single ingredient by its ID.

- **Search Ingredients by Title**: [http://localhost:8080/api/v1/ingredients/search?title={title}](http://localhost:8080/api/v1/ingredients/search?title={title})
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
   - `GET`: Retrieves a single recipe by its ID.

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