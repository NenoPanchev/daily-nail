
# Daily Nail

This is my final project for the Spring Advanced course in SoftUni. 
It is based on MVC structure. Using Spring Framework, MySQL as database, Thymeleaf template engine, Responsive Web Page Design based on Bootstrap.
The project is also based on a list of requirements that are detailly described below with some examples of how they're implemented.
The same project without the Unit/Integrational tests is deployed on Heroku with populated data where you can get an idea of the application.

![Logo](https://prnt.sc/SQpGQKL_NuQe)


## Features
DailyNail is a news website populated with articles spread over different categories and subcategories.
It offers different sets of views and fully implemented management functionality. Users with specific roles can create/edit/delete articles daily jokes, set a date and time when the drafted articles should appear on the website, if they should be in the top panel, should their comments be disabled and other options. For text editing in the articles is used TinyMCE: "What you see is what you get" HTML Editor.


Brief description of user roles

Guests can:

    - View all kind of articles and read comments
Logged Users can do what guests can including:

    - Write comments under articles
    - Change profile data: name, email, password

Reporters can do all of the above including:
    
    - Create/Edit/Delete draft articles without being able to activate/post them
    - Create new jokes
    - Have access to all articles in admin panel, filtering them via time period, stats (Activated/Waiting), 
    authors, categories, key words

Editors can do all of the above including:

    - Create/Edit/Delete articles, activating/posting them at specified time.
    
Admin can do all of the above including:

    - Manage the user roles of everyone else
    - Can backup and import data on demand from and to json files
    - Has access to statistics page 


## General Requirements

Your Web application should use the following technologies, frameworks, and development techniques:

```bash
•	The application must be implemented using Spring Framework.						        ✓
•	The application must have at least 12 web pages (views/components).				    ✓
•	The application must have at least 5 independent entity models.				        ✓
•	The application must have at least 5 controllers.							                ✓
•	The application must have at least 5 services.							                  ✓
•	The application must have at least 5 repositories.                            ✓

•	Use Thymeleaf template engine or make the Front-End using JavaScript, 			
consuming REST services from a Web API.								                    ✓
•	Use MySQL / Oracle / PostgreSQL / MariaDB as a database.						    ✓
    - Using MySQL in the project and PostgreSQL in the demo deployed on Heroku.

•	Use Spring Data to access your database.                                            ✓
•	Use Hibernate or any other provider as a JPA implementation.					    ✓

•	Implement Responsive Web Page Design based on Bootstrap / Google Material Design.	✓
•	Use the standard Spring Security for managing users and roles.						✓
o	Your registered users should have at least these roles: user and administrator.		✓
o	User roles should be manageable from the application.						        ✓
o	Make sure the role management is secured and error safe.					        ✓
o	Users and administrators should be able to edit their usernames.				    ✓

•	Use Fetch to asynchronously load and display data somewhere in your application.	✓
    - Fetching some weather data from https://api.openweathermap.org ([Link to Header](#weather)image)
•	Write tests (Unit & Integration) for your logic, services, repository query methods, helpers, etc.
o	You should have at least 70% coverage on your business logic (Line Coverage).       ✓

•	Implement Error Handling and Data Validation to avoid crashes when invalid data is entered 
(both client-side and server-side).									                    ✓
o	When validation data, show appropriate messages to user					            ✓

•	Use at least 2 Interceptors.										                ✓
    - Using one to count articles views from authorized vs unauthorized users.
    - And another one for redirecting users to a maintenance page during a specific time /of the backup/.
•	Schedule jobs that impact the whole application running e.g., once/twice a day.		✓
    - Once a day backs up entities data in json files.
•	Use ModelМapper or another mapping library.								            ✓

```

## Additional Requirements
```bash
•	Follow the best practices for Object Oriented design and high-quality code for the Web application:
    o	Use data encapsulation.
    o	Use exception handling properly.
    o	Use inheritance, abstraction, and polymorphism properly.
    o	Follow the principles of strong cohesion and loose coupling.
    o	Correctly format and structure your code, name your identifiers and make the code readable.
    o	Follow the concept of thin controllers.

•	Well looking user interface (UI).										✓
•	Good user experience (UX).										✓
•	Use a source control system by choice, e.g., GitHub, BitBucket.						✓
```


## Assessment Criteria
```bash
General Requirements – 70% 
•	Functionality – 0…20
•	Implementing controllers correctly (controllers should only do their work) – 0...5
•	Implementing views correctly (using display and editor templates) – 0…5
•	Testing (unit test and integration tests for some of the controllers using mocking) – 0…10
•	Security (prevent SQL injection, XSS, CSRF, parameter tampering, etc.) – 0…5
•	Data validation (validation in the models and input models) – 0…10
•	Using model mapper and inversion of control – 0…5
•	Using layers with multiple layouts – 0…10
•	Code quality (well-structured code, following the MVC pattern, following SOLID principles, etc.) – 0…10

Answering Questions – 30 %
Answer questions about potential functionality outside the scope of the project.

Bonuses – up to 15 %
•	Use Spring Event somewhere in your application.							            ✓
        - Using ApplicationStartedEvent that checks and populates prepared data from json files on an empty db.
•	Implement one or more Advice (AOP).									                ✓
        - Handling different kinds of errors.
•	Implement HATEOAS.
•	Using Spring WebFlux.
•	Using Angular/React/Vue for Front-End
•	Host the application in a cloud environment.								        ✓
        - Demo on Heroku: https://daily-nail-heroku.herokuapp.com/
•	Use a file storage cloud API, e.g., Cloudinary, Dropbox, 
        Google Drive or other for storing the files.	                                ✓
        - Using Cloudinary to store images.
•	Implement Microservice architecture in your application.
•	Anything that is not described in the assignment is a bonus if it has some practical use. 

```
## Screenshots

#### Weather
![App Screenshot](https://prnt.sc/TeOXCWjVqUsO)

## Demo

You can check the project deployed on Heroku: https://daily-nail-heroku.herokuapp.com/

User credentials: 
e-mail: `user@user.bg` 
password: 1234

