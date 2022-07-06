# Application Overview

This application is a single microservice that is responsible for reading and providing the data of posts, 
comments, and to-do’s from a single data source, manipulating and providing these data to other 
applications by REST API.

## Data Resource

The application catching data of the posts and comments and to-do's from these below data 
sources at the application start-up in a separated thread and persist data into corresponding tables in 
the local application database:
- Posts: https://jsonplaceholder.typicode.com/posts
- Comments: https://jsonplaceholder.typicode.com/comments
- ToDo’s: https://jsonplaceholder.typicode.com/todos

## REST APIS

The application must provide three REST API for Posts, Comments, ToDo’s like below:

### Posts API’s:

| HTTP Method | EndPoit            | Description |
| ----------- | ------------------ | ------------------ |
| GET         | /posts             | Get all posts(with pagination) |
| GET         | /posts/1           | Get post by post id |
| GET         | /posts/1/comments  | Get comments of specific post by post id |
| GET         | /posts?title=eos   | Get all posts that have the “eos” keyword in their title |
| POST        | /posts             | Create a post |
| PATCH       | /posts/1           | Update a post by post id |
| DELETE      | /posts/1           | Delete a post by post id |

### Comments API’s:

| HTTP Method | EndPoit            | Description |
| ----------- | ------------------ | ------------------ |
| GET         | /comments          | Get all comments(with pagination) |
| GET         | /comments?postId=1 | Get comments of specific post by post id |
| POST        | /comments          | Create a comment |
| PATCH       | /comments/1        | Update a comment by comment id |
| DELETE      | /comments/1        | Delete a comment by comment id |

### ToDo’s API’s:

| HTTP Method | EndPoit                        | Description |
| ----------- | ------------------             | ------------------ |
| GET         | /todos                         | Get all to-do’s |
| GET         | /todos?userId=1&completed=true | Get to-do’s of specific user by user id and completed field |

## Swagger

- Swagger UI URL: http://localhost:8080/swagger-ui/index.html
- API-Docs URL: http://localhost:8080/v3/api-docs/
