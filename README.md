# MAD-Assignment

# Name, StudentID
# Tran Huy Minh, S10223485
# Javier Koh, S10221847
# Ayrton, S10223501
# Ash, S10222280

# Description of app
Cookverse is an app that allows Users to find and create (TBA) Recipes of their liking. Documentation of features and user guide is as follows:

Upon opening the app, Users are greeted with a splash screen that adds all the data to their local database. 
They are then taken to the login page where they can either login with previously created credentials or click to sign up for an account.
Signing up for an account leads them to a page where they can enter their preferred credentials which will be saved into a database.
After logging in, Users will see a Home Page displaying Recipes which they can scroll through to find the recipe that they want.
Clicking on a recipe leads them to a different page displaying the steps and ingredients needed to make complete the recipe.
Liking the recipe would save the recipe to the user's profile page where they can easily find and view it again.
The number of likes the recipe has will also increase allowing users to find recipes that other users have liked a lot.
Adding the ingredients of the recipe to a shopping list will give users the ability to easily view the ingredients that they are planning to buy in their profile page.
Going back to the home page, the fragment at the bottom view allows Users to toggle between different pages.
Toggling to the Discover page, Users can scroll through or search for a recipe where it will display recipes that are similar to what they may be searching for.
Using the toggle filters, users can hone into specific recipe types such as vegan, healthy, etc.
The create page allows Users to add their own recipes to the database where other Users will also be able to view and like. They can revisit their created recipes inside the profile page.

# Roles and contributions of each member
Tran Huy Minh - Creation of Realtime Firebase Database and Local Database, DBHandler, FBHandler, Splash Screen and CreateFragment as well as related classes such as
imageloadtask and DBHandlerUtility. Focus primarily on everything related to databases and create recipe feature. Involved in everything that utilizes databases from other activities. Too much bug fixing for whole app. The above description of app.

Javier Koh - Creation of homepage, separation of online recipe and user created recipe, horizontal recyclerview, switch between steps and ingredients, helping when there is error, event handling

Ayrton - Creation of discover page, recyclerview, recipe activity, search functionality, helping when there is error, switch between steps and ingredients, multimedia, event handling

Ash - Login page, signup page, helping when there is error, event handling

Kok Kai - Creation of user profile, all fragments, bottom navigation bar, helping when there is error, event handling, multimedia
