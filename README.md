# taskmaster
- create app with this pages 
## Homepage
- The main page should be built out to match the wireframe. In particular, it should have a heading at the top of the page, an image to mock the “my tasks” view, and buttons at the bottom of the page to allow going to the “add tasks” and “all tasks” page.
- there is more three button that connect with detail page that display task information when click the button
- and there is button that connect with setting page.

- for the task that display in RecyclerView we will get it from Room (it is like local database we store task is it )
not in static way so we will deal with dynamic list of data


![image description](screenshots/newHome.png)

## Add a Task
- On the “Add a Task” page, allow users to type in details about a new task, specifically a title and a body. When users click the “submit” button, show a “submitted!” label on the page.

- here I modify to store the data the user enter and store it in database and then display it home page 

![image description](screenshots/newAddTask.png)

## All Tasks
- The all tasks page should just be an image with a back button; it needs no functionality.

![image description](screenshots/allt.png)

## Task Detail Page
-  a Task Detail page. It  have a title at the top of the page, and a Lorem Ipsum description and state of task.

![image description](screenshots/detail1.png)

## Settings Page
-  a Settings page. It  allow users to enter their username and hit save.

![image description](screenshots/setting.png)


## Espresso testing 
- I do the ui testing form main activity , setting activity and detailsTask activity
- in main activity I tested if all component is display and check the text view if it has the same text.
- setting activity i check if the input text is display same in the main activity after I get back to it 
- detailsTask activity i choose one of the item in the recyclerView and check if it display correctly in the details activity

## Amplify
-  using AWS Amplify. We’ll work with Amplify to add more cloud functionality for the App.
- this will make store and retreive data from dataSotre using amplify.

## AWS
- connect app with aws amplify to make sign up and sign in page and to ensure the user can confirm his account after he create it 


# lab 37 
- add button to add task to make us add image for taskes 
![image description](screenshots/16.jpg)
- affter that add task 
- ![image description](screenshots/17.jpg)

# lab 38 
- add notification on app by using firebase

- ![image description](screenshots/20.png)

# lab 41 
- add ability to share file with my app from out side app

