# 🏺 Ceylon Antique Marketplace

Ceylon Antique Marketplace is a full-stack web application for buying and selling authentic Sri Lankan antiques and heritage items.  
The platform connects collectors, sellers, and buyers in a secure and user-friendly environment.

---

## ✨ Features
- 🔑 **User Authentication** – Secure login & registration with **JWT**
- 🛍️ **Antique Listings** – Add, edit, and browse antique items
- 🔎 **Search & Filter** – Find antiques by category, price, or era
- 📄 **Pagination** – Browse antiques with data divided into pages for better performance
- 📝 **Order Records** – Keep track of user purchases and transaction history
- 💳 **Payment Integration** – Pay online securely via **PayHere**
- 📧 **Email Notifications** – Automated emails using **SMTP**
- 🛡️ **Admin Dashboard** – Manage users, antiques, orders, and payments
- 🤖 **Chatbot Assistant** – Interactive chatbot to guide users and answer questions
- 📱 **Responsive UI** – Built with **HTML, CSS, JavaScript**

---

## 📸 Screenshots

**Login Page**  
![Login Page](screenshots/login.png)  
*Secure login and registration using JWT authentication.*

**User Side Pages**  
![Listings Page](screenshots/listings.png)  
*Browse, search, filter, select antiques, and add them to the cart.* 

**Admin Dashboard**  
![Admin Dashboard](screenshots/admin_dashboard.png)  
*Manage users, antiques, and transactions.*

**Payment Integration (PayHere)**  
![Payment Page](screenshots/payment.png)  
*Secure online payment processing with PayHere.*

**SMTP Email Notifications**  
![Email Notification](screenshots/email_notification.png)  
*Automated email notifications for registrations, purchases, and alerts.*

---

## 🛠️ Tech Stack

**Frontend**  
- HTML5, CSS3, JavaScript (Vanilla JS)  
- Bootstrap 5 for responsive design and UI components

**Backend**  
- Spring Boot (Java)  
- Spring Security + JWT for authentication  
- SMTP for email notifications  
- PayHere Payment Gateway integration  
- Chatbot integration (Dialogflow / Rasa / Custom Spring Boot API)

**Database**  
- MySQL

---

## ⚙️ Setup Instructions

### 1. Clone Repository
```bash
git clone https://github.com/your-username/Ceylon_antique_marketplace.git
cd Ceylon_antique_marketplace

Backend Setup (Spring Boot + MySQL)

Open backend/src/main/resources/application.properties and configure MySQL:

spring.datasource.url=jdbc:mysql://localhost:3306/antique_marketplace
spring.datasource.username=your_db_username
spring.datasource.password=your_db_password


Run the backend:

cd backend
mvn spring-boot:run
