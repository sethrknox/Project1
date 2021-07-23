# Story Pitch Management System

## Project Description

SPMS, or Story Pitch Management System, is a full-stack web application that allows authors to submit story pitches to a publishing company. The pitch can then be approved or rejected by editors at various levels and in various committees based on genre and story length before the author is able to submit a draft of the story. The authors are able to track the status of their pitches throughout the process.

## Using the SPMS as a User 

---

### Register a new account, and login to get started.

---


> ### Navigating the website.

---

Upon Login the user will be at a blank home page. From this page a user can navigate to their forms page, a form creation page, or their request page. The forms page will display all previous submitted story pitches along with their information. A new form can be submitted on the creation page. A user can manage information requests from editors in their request management tab.

## Using the SPMS as an Admin

---

### Login to pre-created admin account to get started.

---

An admin will land on the home page after login which displays the genres committees that you are in. From this page, an admin can navigate to manage forms, drafts, or requests.

## Technologies Used

> ### Frameworks
> 
- [Bootstrap](https://getbootstrap.com/)
- [Hibernate](https://hibernate.org/orm/documentation/5.4/)

> ### Cloud Services

- [AWS](https://aws.amazon.com/)
  - RDS

> ### Core Technologies

- [Java 8](https://docs.oracle.com/javase/8/docs/)
- [HTML](https://developer.mozilla.org/en-US/docs/Web/HTML)
- [JavaScript](https://devdocs.io/javascript/)

> ### Database

- [PostgreSQL](https://www.postgresql.org/docs/)

## Getting Started

- Clone the repository
- `git clone [repo-link]`
- Import the maven project into Eclipse using the pom.xml
- Create a new Tomcat server on localhost, and move the project into the server's configuration
- Run the server
- Go to http://localhost:8080/SPMS
- The application should be running
