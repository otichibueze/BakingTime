# BakingTime

Android Baking App that will allow resident baker-in-chief, Miriam, to share her recipes with the world. 
You will create an app that will allow a user to select a recipe and see video-guided steps for how to complete it.
This app has adaptive UI for phone and tablet devices

## Screenshots
![alt text](https://github.com/otichibueze/BakingTime/blob/master/screenshots/b.png)
![alt text](https://github.com/otichibueze/BakingTime/blob/master/screenshots/a.png)
![alt text](https://github.com/otichibueze/BakingTime/blob/master/screenshots/c.png)
![alt text](https://github.com/otichibueze/BakingTime/blob/master/screenshots/d.png)
![alt text](https://github.com/otichibueze/BakingTime/blob/master/screenshots/e.png)
![alt text](https://github.com/otichibueze/BakingTime/blob/master/screenshots/f.png)


## LIBRARIES:
- [Picasso](https://github.com/square/picasso)
- [ButterKnive](https://github.com/JakeWharton/butterknife)
- [Gson](https://github.com/google/gson)
- [Retrofit](https://github.com/square/retrofit)
- [Gson Converter](https://github.com/square/retrofit/tree/master/retrofit-converters/gson)
- [Setho](https://github.com/facebook/stetho)
- [Exoplayer](https://github.com/google/ExoPlayer)
- [timber](https://github.com/JakeWharton/timber)
- [Simonvt](https://github.com/SimonVT/schematic)
- [CardView](https://github.com/googlesamples/android-CardView)
- [Espresso](https://github.com/googlesamples/android-testing/tree/master/ui/espresso)



## Homescreen Widget
- Application has a companion homescreen widget.
- Widget displays ingredient list for desired recipe.

## Project Overview
You will productionize an app, taking it from a functional state to a production-ready state. 
This will involve finding and handling error cases, adding accessibility features, allowing for localization,
adding a widget, and adding a library.

## Why this Project?
As a working Android developer, you often have to create and implement apps where you 
are responsible for designing and planning the steps you need to take to create a production-ready app. 
Unlike Popular Movies where we gave you an implementation guide, 
it will be up to you to figure things out for the Baking App.

## What I Learned
- Use MediaPlayer/Exoplayer to display videos.
- Handle error cases in Android.
- Add a widget to your app experience.
- Leverage a third-party library in your app.
- Use Fragments to create a responsive design that works on phones and tablets.
- Unit Testing

## Rubric

### General App Usage
- App should display recipes from provided network resource.
- App should allow navigation between individual recipes and recipe steps.
- App uses RecyclerView and can handle recipe steps that include videos or images.
- App conforms to common standards found in the Android Nanodegree General Project Guidelines.

### Components and Libraries
- Application uses Master Detail Flow to display recipe steps and navigation between them.
- Application uses Exoplayer to display videos.
- Application properly initializes and releases video assets when appropriate.
- Application should properly retrieve media assets from the provided network links. It should properly handle network requests.
- Application makes use of Espresso to test aspects of the UI.
- Application sensibly utilizes a third-party library to enhance the app's features. That could be helper library to interface with Content Providers if you choose to store the recipes, a UI binding library to avoid writing findViewById a bunch of times, or something similar.



## Connect With Me On
[![N|Solid](https://github.com/otichibueze/jokeapp/blob/master/screenshots/linkedin.png)](https://www.linkedin.com/in/chibuezeoti)

## License
```
Copyright 2018 Chibusoft, Inc.

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

   http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```
