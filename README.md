# MyNews

MyNews is an Android application that retrieve news from the NewYorkTimes.  
**Purpose :** OpenClassrooms' project for Android Developer Course  
**Specs :** Developped in Kotlin  
**Version :** 1.0  


## Presentation

When the app is launched for the first time, it looks like this.
News are displayed by chronological order (more recents first).

<img src="https://i.imgur.com/G4USBkO.jpg" width="200" height="400" />

It's possible to refresh the screens at everytime by swiping down.

## Already read

When you click a news, it will displayed inside a Webview like the following screenshot.
Articles already read are higlightened.

<img src="https://i.imgur.com/bACc83n.png" />

## Search
Research are possible this way (throug the search icon on the top of the screen) :

<img src="https://i.imgur.com/mV8iaoL.png" />

At least a keyword and a category are needed. If not fill correctly, a message (type Toast) will be displayed to the user.

## Notifications
You can enable notifications (disabled by default). 
As for reasearch, you have to enter at least a keyword, and a category. The switch will not enable if you don't meet the requirements, and a Toast message will be displayed to you.
<img src="https://i.imgur.com/S1CbCjL.jpg" width="200" height="400" />

Notifications are planed every day around midday. They'll display the number of results found, according your search.
<img src="https://i.imgur.com/kBccYaT.png" width="400" height="200" />
