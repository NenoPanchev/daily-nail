
![HdpBLp1.jpg](https://iili.io/HdpBLp1.jpg)

This is my final project for the Spring Advanced course in SoftUni. 
It is based on MVC structure. Using Spring Framework, MySQL as database, Thymeleaf template engine, Responsive Web Page Design based on Bootstrap, Cloudinary to store images, TinyMCE: "What you see is what you get" HTML Editor.
The project is also based on a list of requirements that are detailly described below with some examples of how they're implemented.
The same project without the Unit/Integration tests is deployed on Heroku with populated data where you can get an idea of the application.


## Features
DailyNail is a news website populated with articles spread over different categories and subcategories.
It offers different sets of views and fully implemented management functionality. The main functionality is centered around creation, editing and posting of articles, daily jokes, setting of date and time when the drafted articles should appear on the website, if they should be in the top panel, should their comments be disabled and other options, regulating comments etc. The application also gathers data of the users preferences by view count of different categories of articles. It offers accounts role management by the admin. The entities data can and is regurarly exported in json files and can be imported in case of need.

[![HdpXTj1.md.png](https://iili.io/HdpXTj1.md.png)](https://freeimage.host/i/HdpXTj1)
[![H239TTF.md.png](https://iili.io/H239TTF.md.png)](https://freeimage.host/i/H239TTF)

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
    - Has access to statistics page about what are the most read categories of articles 


## General Requirements

Your Web application should use the following technologies, frameworks, and development techniques:


•	The application must be implemented using Spring Framework.                                             ✓  
•	The application must have at least 12 web pages (views/components).                         ✓  
•	The application must have at least 5 independent entity models.                             ✓  
•	The application must have at least 5 controllers.                                           ✓  
•	The application must have at least 5 services.                                              ✓  
•	The application must have at least 5 repositories.                                          ✓  

•	Use **Thymeleaf** template engine or make the Front-End using JavaScript, 			
consuming REST services from a Web API.                                                         ✓  
•	Use MySQL / Oracle / PostgreSQL / MariaDB as a database.                                    ✓  
&nbsp;&nbsp;&nbsp;- Using MySQL in the project and PostgreSQL in the demo deployed on Heroku.

•	Use Spring Data to access your database.                                                    ✓  
•	Use Hibernate or any other provider as a JPA implementation.                                ✓  

•	Implement Responsive Web Page Design based on Bootstrap / Google Material Design.           ✓  
•	Use the standard Spring Security for managing users and roles.                              ✓  
•	Your registered users should have at least these roles: user and administrator.             ✓  
•	User roles should be manageable from the application.                                       ✓  
•	Make sure the role management is secured and error safe.                                    ✓  
•	Users and administrators should be able to edit their usernames.                            ✓  

•	Use Fetch to asynchronously load and display data somewhere in your application.            ✓  
&nbsp;&nbsp;&nbsp;- Fetching some weather data from `https://api.openweathermap.org` [Go to image](#weather)  
•	Write tests (Unit & Integration) for your logic, services, repository query methods, helpers, etc.  
o	You should have at least 70% coverage on your business logic (Line Coverage). ✓ [Go to Coverage](#tests)       

•	Implement Error Handling and Data Validation to avoid crashes when invalid data is entered 
(both client-side and server-side). ✓ [Go to Errors](#errors)								                      
o	When validation data, show appropriate messages to user. ✓  [Go to Validation](#validation)

•	Use at least 2 Interceptors.										                ✓  
&nbsp;&nbsp;&nbsp;- Using one to count articles views from authorized vs unauthorized users.  
&nbsp;&nbsp;&nbsp;- And another one for redirecting users to a maintenance page during a specific time /of the backup/.  
•	Schedule jobs that impact the whole application running e.g., once/twice a day.		✓  
&nbsp;- Once a day backs up entities data in json files.  
•	Use ModelМapper or another mapping library.								            ✓  



## Additional Requirements
•	Follow the best practices for Object Oriented design and high-quality code for the Web application:  
    o	Use data encapsulation.  
    o	Use exception handling properly.  
    o	Use inheritance, abstraction, and polymorphism properly.  
    o	Follow the principles of strong cohesion and loose coupling.  
    o	Correctly format and structure your code, name your identifiers and make the code readable.  
    o	Follow the concept of thin controllers.  

•	Well looking user interface (UI).✓  
•	Good user experience (UX).✓  
•	Use a source control system by choice, e.g., GitHub, BitBucket.✓  



## Assessment Criteria
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
•	Use Spring Event somewhere in your application.	  						            ✓  
&nbsp;&nbsp;&nbsp;- Using ApplicationStartedEvent that checks and populates prepared data from json files on an empty db.  
•	Implement one or more Advice (AOP).					  				                ✓  
&nbsp;&nbsp;&nbsp;- *Handling different kinds of errors.*  
•	Implement HATEOAS.  
•	Using Spring WebFlux.  
•	Using Angular/React/Vue for Front-End  
•	Host the application in a cloud environment.								        ✓  
&nbsp;&nbsp;&nbsp;- Demo on Heroku: https://daily-nail-heroku.herokuapp.com/  
•	Use a file storage cloud API, e.g., Cloudinary, Dropbox,   
        Google Drive or other for storing the files.	                                ✓  
&nbsp;&nbsp;&nbsp;- Using Cloudinary to store images.  
•	Implement Microservice architecture in your application.  
•	Anything that is not described in the assignment is a bonus if it has some practical use.   

## Screenshots
[![H23zXn4.md.png](https://iili.io/H23zXn4.md.png)](https://freeimage.host/i/H23zXn4)
[![H23R1hg.md.png](https://iili.io/H23R1hg.md.png)](https://freeimage.host/i/H23R1hg)

#### Weather
[![H298Dxf.png](https://iili.io/H298Dxf.png)](https://freeimage.host/bg)  
[Go back to requirements](#general-requirements)

#### Tests
Unit testing of services and Integration tests  
[![H2H3uxs.png](https://iili.io/H2H3uxs.png)](https://freeimage.host/bg)
[![H2HFnBj.md.png](https://iili.io/H2HFnBj.md.png)](https://freeimage.host/i/H2HFnBj)  
[Go back to requirements](#general-requirements)  

#### Errors
[![H2Hi0sn.md.png](https://iili.io/H2Hi0sn.md.png)](https://freeimage.host/i/H2Hi0sn)
[![H2HsIh7.md.png](https://iili.io/H2HsIh7.md.png)](https://freeimage.host/i/H2HsIh7)
[![H2HLAwN.md.png](https://iili.io/H2HLAwN.md.png)](https://freeimage.host/i/H2HLAwN)
[![H2HDt1t.md.png](https://iili.io/H2HDt1t.md.png)](https://freeimage.host/i/H2HDt1t)  
[Go back to requirements](#general-requirements) 

#### Validation
There is client side validation  
[![H2J7pcB.md.png](https://iili.io/H2J7pcB.md.png)](https://freeimage.host/i/H2J7pcB)
[![H2JY6fR.md.png](https://iili.io/H2JY6fR.md.png)](https://freeimage.host/i/H2JY6fR)  
The server side validation is done once in the binding models coming from the input another time in the service layer in case the input comes in a different way and in the entities themselves if there are any specific requirements.  
[![H2dePpe.md.png](https://iili.io/H2dePpe.md.png)](https://freeimage.host/i/H2dePpe)
[![H2d8p3l.md.png](https://iili.io/H2d8p3l.md.png)](https://freeimage.host/i/H2d8p3l)
[![H2deq79.md.png](https://iili.io/H2deq79.md.png)](https://freeimage.host/i/H2deq79)
[![H22KiQt.md.png](https://iili.io/H22KiQt.md.png)](https://freeimage.host/i/H22KiQt)
[![H22oXyJ.md.png](https://iili.io/H22oXyJ.md.png)](https://freeimage.host/i/H22oXyJ)
[![H22YQb1.md.png](https://iili.io/H22YQb1.md.png)](https://freeimage.host/i/H22YQb1)
[![H22EidJ.png](https://iili.io/H22EidJ.png)](https://freeimage.host/bg)
[![H220hQ9.png](https://iili.io/H220hQ9.png)](https://freeimage.host/bg)  
[Go back to requirements](#general-requirements) 
## Demo

You can check the project deployed on Heroku: https://daily-nail-heroku.herokuapp.com/

User credentials: 
e-mail: `user@user.bg` 
password: 1234

