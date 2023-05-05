# Youtuber-App-MAD1

Assignment for Mobile App Development 1

## App Overview

My Android Mobile Application is called the YouTuber App. This app offers a way for users to store information about their favourite YouTubers such as their name, channel name, date of birth, an image of them, their work location via Google Maps and a rating. 

I developed this app using Kotlin and Android Studio by combining practical lab notes from class, self-learning, and independent research.

## App Functionality

**Functionality added in Assignment 1:**

- YouTubers can be added with information including their name, channel name, date of birth, image and rating
- YouTubers are listed via a RecyclerView on the main page activity
- YouTubers information can be edited/updated
- YouTubers can be deleted
- Mark YouTubers as favourite 

**Functionality added in Assignment 2:**

-	Login and Sign-Up activity screens were added with Firebase Authentication (user account information is stored in Firebase)
-	Map feature added (when adding a YouTuber a user can now add their work location via Google Maps)
-	ALL YouTuber locations are displayed and can be viewed on map also
-	All YouTubers can be deleted at the same time (In order to prevent accidental deletion, a warning message appears beforehand to ask the user if they are sure that they want to delete the YouTuber(s))
-	Animated Splash Screen added on app launch
-	YouTubers can be searched by name via a SearchView item in the top menu
-	A filter toggle button was added that will only display the YouTubers marked as Favourite
-	Added a feature so a user can now switch the app theme between Night and Day mode as they please
-	YouTuber information is persisted with JSON and also stored in the projects Firebase Firestore Cloud Database

## GitHub workflow

The GitHub workflow used throughout the labs and assignment was:

_Issue - Branch - Commit(s) - Push - Pull Request - Merge - Close Issue - Delete Branch_

## User Interface

**Colour Theme**

The colour theme of this app is simply red, white and black. As YouTube is associated with red, white, and black, these colors were chosen to maintain a familiar YouTube theme.

**App Screens**

Login Screen

<img width="354" alt="Screenshot 2023-05-05 at 19 55 04" src="https://user-images.githubusercontent.com/78028777/236544529-dbd325a7-5ab7-4a45-bb28-af2d27cb27c2.png">

Sign-Up Screen

<img width="347" alt="Screenshot 2023-05-05 at 19 55 45" src="https://user-images.githubusercontent.com/78028777/236544683-0b071872-807c-4939-ac37-1bb23ab55ff6.png">

Home Screen (with YouTubers added)

<img width="340" alt="Screenshot 2023-05-05 at 20 24 04" src="https://user-images.githubusercontent.com/78028777/236551377-a83dc5f7-a267-4362-b57b-5356bbf64919.png">

Favourite Filter active 

<img width="376" alt="Screenshot 2023-05-05 at 19 59 20" src="https://user-images.githubusercontent.com/78028777/236545915-ab24b4e7-1e62-4b0b-9735-f65ffb1e18ac.png">

Add a YouTuber Screen

<img width="340" alt="Screenshot 2023-05-05 at 20 24 33" src="https://user-images.githubusercontent.com/78028777/236551439-067f9c86-ec14-44ae-9340-efd59cb2cc70.png">

<img width="384" alt="Screenshot 2023-05-05 at 20 24 58" src="https://user-images.githubusercontent.com/78028777/236551527-898d6089-c2bd-40f2-8af4-643e7aeb747d.png">

Viewing ALL YouTubers on Map Screen

<img width="348" alt="Screenshot 2023-05-05 at 20 29 22" src="https://user-images.githubusercontent.com/78028777/236552294-71aac5c9-c800-4e8d-9b25-730bf7e6c962.png">


Night Mode Enabled

<img width="356" alt="Screenshot 2023-05-05 at 20 25 42" src="https://user-images.githubusercontent.com/78028777/236551622-209ed04e-873f-4886-8ad5-487921aeaf9d.png">


## References

To assist me in adding some of the app’s features, I found and used the following resources. These resources were incorporated into my app and were modified/troubleshooted appropriately to fit the content and layout of my app:

**Assignment 1 References:**

Resource used to help with adding a NumberPicker: https://tutorialwing.com/android-numberpicker-using-kotlin-with-example/ 

Resource used to help with adding the date/calendar picker and drop-down feature: https://www.youtube.com/watch?v=qCoidM98zNk&t=28s

Resource used to help with adding a clickable favourite icon to the listed cards: https://stackoverflow.com/questions/34259618/android-using-imageview-onclick-to-change-image-back-and-forth & https://stackoverflow.com/questions/11604476/it-is-possible-to-create-a-togglebutton-without-text

**Assignment 2 References:**

Resource used to assist in creating login & sign-up functionality: https://www.youtube.com/watch?v=idbxxkF1l6k&list=WL&index=39&t=35s 

Resource used to help with adding the warning alert before deleting YouTubers: https://www.javatpoint.com/kotlin-android-alertdialog

Resource used to help create splash screen: https://www.geeksforgeeks.org/how-to-create-a-splash-screen-in-android-using-kotlin/ 

A resource shared to me after assignment 1 was used to help add searching functionality: https://www.geeksforgeeks.org/android-searchview-with-recyclerview-using-kotlin/ 

Resources used to help with the toggle button filtering: https://www.geeksforgeeks.org/togglebutton-in-kotlin/ & https://stackoverflow.com/questions/44098709/how-can-i-filter-an-arraylist-in-kotlin-so-i-only-have-elements-which-match-my-c 

Resource used to help add a change to Night Mode switch: https://www.youtube.com/watch?v=6toOIUQqy-Q&list=LL&index=1 

Resources used to store data in Firebase: https://www.youtube.com/watch?v=5UEdyUFi_uQ and https://ansarali-edugaon.medium.com/how-to-add-data-on-firebase-firestore-in-kotlin-android-fe114070d550 

