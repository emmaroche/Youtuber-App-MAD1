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

- Login and Sign Up activity screens were added with Firebase Authentication (users are stored in Firebase)
- Map feature added i.e. when adding a YouTuber a user can now add their work location 
- ALL YouTuber locations are displayed and can be viewed on map also
- All YouTubers can be deleted at the same time (A warning message to ask if the user is sure they want to delete the YouTuber(s) pops up beforehand now)
- YouTubers can be searched by name
- Animated Splash Screen added on app launch
- A filter toggle button was added that will only show the YouTubers marked as Favourite
- Added a feature so a user can now switch the app theme between Night and Day mode as they please
- YouTuber information is persisted with JSON and also stored in the Firebase Firestore Cloud Database

## GitHub workflow

The GitHub workflow used throughout the labs and assignment was:

_Issue - Branch - Commit(s) - Push - Pull Request - Merge - Close Issue - Delete Branch_

## User Interface

**Colour Theme**

The colour theme of this app is simply red, white and black. As YouTube is associated with red, white, and black, these colors were chosen to maintain a familiar YouTube theme.

**App Screens**

Add YouTuber Screen (empty)

<img width="290" alt="Screenshot 2023-03-05 at 14 53 52" src="https://user-images.githubusercontent.com/78028777/222968012-4d69660d-0b3d-4edf-a817-15e9eb517a9e.png">

Home Screen (with YouTubers added)

<img width="286" alt="Screenshot 2023-03-05 at 15 01 39" src="https://user-images.githubusercontent.com/78028777/222968388-60ee47d8-cd05-43b3-a037-da18b9adcf73.png">
<img width="293" alt="Screenshot 2023-03-05 at 15 02 52" src="https://user-images.githubusercontent.com/78028777/222968439-bc9f915b-ec72-4af3-9062-3e4c5e4c591d.png">

## References

To assist me in adding some of the app’s features, I found and used the following resources. These resources were incorporated into my app and were modified/troubleshooted appropriately to fit the content and layout of my app:

**Assignment 1 References:**

Resource used to help with adding a NumberPicker: https://tutorialwing.com/android-numberpicker-using-kotlin-with-example/ 

Resource used to help with adding the date/calendar picker and drop-down feature: https://www.youtube.com/watch?v=qCoidM98zNk&t=28s

Resource used to help with adding a clickable favourite icon to the listed cards: https://stackoverflow.com/questions/34259618/android-using-imageview-onclick-to-change-image-back-and-forth & https://stackoverflow.com/questions/11604476/it-is-possible-to-create-a-togglebutton-without-text

**Assignment 2 References:**
