# 🏥 Medical Assistant API
This project is a RESTful API designed to manage doctor, patient, and medication prescription data for a medical assistant system. It utilizes a Java Spring Boot backend, Maven for dependency management, and PostgreSQL as the database.

## ✨ Features
The API provides the following core functionalities:

Doctor Management: Doctors can manage patients in the "waiting room" and update prescriptions.

Patient Registration: New patients register and are automatically placed into a "waiting room" state. When they do not have a doctor.

Patient Acceptance: Doctors can accept a patient from the waiting room to begin treatment.

Medication Management: Doctors can prescribe or remove medications for an accepted patient.

Data Retrieval: Patients can view their assigned doctor's information and their current list of prescribed medications.

Patient Removal: Doctors can remove a patient from their care (discharge).

## 💻 Technologies Used
Backend Framework: Java Spring Boot 

Build Tool: Maven

Database: PostgreSQL (For tests in memory db is used **Don't forget to use dev profile for tests**)

ORM/Data Access: Spring Data JPA/Hibernate

