# TaskManagementAPI

Description
The Task Management API facilitates CRUD (Create, Read, Update, Delete) operations on tasks. Each task includes essential attributes such as a title, description, due date, and status (e.g., pending, In Progress, completed).


Requirements

A. User Authentication
Incorporate user authentication to ensure only authorized users can access and modify their tasks.
Grant admin users’ full access to all tasks.

B. Task Operations
Establish an API endpoint for adding new tasks.
Create an API endpoint to retrieve a list of tasks for a specific user (Admin users can access all tasks).
Implement features to update task details and mark tasks as completed or delete them.

C. Database
Employ a database of your preference.

D. Documentation
Furnish comprehensive documentation for your API, covering:
Authentication procedures
Available endpoints
Expected request and response formats

E. Additional
Integrate pagination into the task list API endpoint.
Include sorting and filtering options for the task list.
Ensure proper error handling and status codes in API responses.
Develop unit tests for critical components of your application.

Tech Stack

Spring Framework:

•	Utilized for building the backend of the Task Management API.
•	Provides a robust and modular platform for Java-based enterprise applications.
•	Offers support for building RESTful web services, making it suitable for creating APIs.

MongoDB Atlas:

•	Chosen as the preferred database for storing task-related data.
•	MongoDB Atlas is a cloud-based, fully-managed MongoDB service, offering scalability, security, and ease of use.
•	Enables seamless integration with Spring Data MongoDB for efficient data access.

Spring Data MongoDB:

•	Integrated for simplified interaction with MongoDB from the Spring Framework.
•	Facilitates the creation of MongoDB repositories and reduces boilerplate code for data access.
•	Provides support for mapping Spring domain objects to MongoDB documents.
