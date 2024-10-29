# Project-M295-Note-App
#M295 [[REST (Representaitional State Transfer)]]
## Quick Idea Summary
I want to maka a Note App that has a **User System** a **Category System** and of course **Taking Notes**:
1. **User System**: Supports user registration, login/logout, and profile management.
2. **Category System**: Allows users to create, edit, and delete categories to organize notes, with filtering options.
3. **Note-Taking**: Users can create, edit, delete, and search notes, with basic formatting options like bold and bullet points.
## User Stories
The User Stories are sorted after the **Priority**
### User Registration:
As a **user**, I want to **create an account** so that i can securely **access my notes**.
#### Acceptance Criteria
- The system **must** accept a **username**, **email**, and **password** for **registration**.
- The system **must** **validate** that the **email and username** is **unique** and **correctly formatted**.
### Edit/Delete User:
As a **user**, I want to **edit or delete my account** so I can keep **my Information up to date** or **delete my Personal information**.
### Create Note:
As a **user**, I want to **create notes** so i can save **important Information**
#### Acceptance Criteria
- The user **must** be able to **enter a title and body** for the **note**.
- The system **must** **save** the **note** and **display it in the user's notes** list.
- The user **must** receive a **confirmation message** upon **successful** creation.
- The system **must** reject **invalid notes** upon note creation.
### Edit/Delete Notes:
As a **user**, I want to **edit or delete notes** to keep my **notes relevant and accurate**.
#### Acceptance Criteria
- The user **must** be able to **edit** the **title and body** of an **existing note**.
- The system **must** **update** the **note** and **reflect changes** in the **notes list**.
- The user **must** be able to **delete a note**, with **a confirmation prompt before deletion**.
- the system **must reject** **invalid notes** upon note creation
### Category Creation:
As a **user**, I want to **create categories** so that I can **organize my notes** by topic.
#### Acceptance Criteria
- The user **must** be able to **create a new category** by entering a name.
- The system **must** **save the category** and **display it in the user's category list**.
- The user **must** receive a **confirmation message** upon successful **category creation**.
### Edit/Delete Categories:
As a **user**, I want to **edit or delete categories** to manage my **Category preferences**.
#### Acceptance Criteria
- The user **must** be able to **edit the name** of an **existing category**.
- The system **must** **update the category name** and **reflect changes in the category list**.
- The user **must** be able to **delete a category**, with a **confirmation prompt** before deletion.
### Filter by Category:
As a **user**, I want to **filter notes by category** so I can **easily find related notes**.
#### Acceptance Criteria
- The user **must** be able to **select a category** to **filter the notes** displayed.
- The system **must** **only show notes** that belong to the **selected category**.
- The user **must** be able to **reset the filter** to **view all notes**.

#### Acceptance Criteria
- The user **must** be able to **update** **their profile** information (username, email).
- The system **must** **validate** the **email** format and ensure the **username is unique**.
- The user **must** receive a **confirmation message** upon **successful profile update**.
## Low Priority / Optional: 
these have no acceptance criteria because they are bonus features that i will implement at the end
### Format Notes:
As a **user**, I want to **format my notes** with bold text, bullet points, and links to **make them clearer**.
### Tagging:
As a **user**, I want to **tag notes** with custom tags for **additional organization**.
### User Login:
As a **user**, I want to **Log in and out** of my account so that only **I can see my Notes**.

## Class Diagramm
With these User Stories i created a Class Diagramm on the most important Classes: ![image of the class Diagramm](images/Diagram.svg)
## Timeplan
if i want to spend more time on a day i'm going to do that 
### Day 2 (Wednesday): User-related endpoints (4 hours)
- **1 Hour**: Set up the development environment and initialize the project repository.
- **1-4 Hours**: Implement all necessary user-related endpoints (registration, profile editing, and deletion).
### Day 3 (Thursday): Note endpoints (4 hours)
- **1-4 Hours**: Implement endpoints for note creation, editing, deleting, and fetching notes.
### Day 4 (Friday): Category endpoints(4 hours)
- **1-4 Hours**: Implement endpoints for category creation, editing, deleting, and fetching notes by category.
### Day 5 (Saturday): Testing/Unit Testing (4 hours)
- **1-4 Hours**: Write tests for user-related endpoints (registration and edit/delete).
### Day 6 (Sunday): Testing/Unit Testing (8 Hours)
 - **5-8 Hours**: Write tests for note and category endpoints.
### Day 8 (Tuesday): Finishing touches
- **1-8 Hours:** Finalizing the work and optimize the code for the requirements of the project. do some Frontend (Optional)