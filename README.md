Original App Design Project
===

# NoteNav

## Table of Contents
1. [Overview](#Overview)
1. [Product Spec](#Product-Spec)
1. [Wireframes](#Wireframes)
2. [Schema](#Schema)

## Overview
### Description
Virtual note where shows the place that the user visits and makes a note.

### App Evaluation
- **Category:** Social Networking
- **Mobile:** For mobiles/tablets
- **Story:** Users can make a note and record their location
- **Market:** Any individual could choose to use this app
- **Habit:** This app could be used as often as the user wanted
- **Scope:** We start with making virtual note on the place user visit. Potential this can be used for tourism/business purposes

## Product Spec

### 1. User Stories (Required and Optional)

**Required Must-have Stories**

* User signs up or logs into their account
* User logs out 
* User sees a map around their location with markers on it
* User clicks on a marker to see the detail of the note at that location
* User makes a note with text 


**Optional Nice-to-have Stories**

* User can also add picture in note
* User can edit profile picture
* User can change name

### 2. Screen Archetypes

* Login/Sign Up screen
   * Users sign up or logs into their account
* Map screen
   * User sees a map around their location with markers on it
   * User clicks on a marker to see the detail of the note at that location
* Note screen
   * User makes a note with text and/or picture 
   * User submits the note 
* Profile screen
   * User logs out
   * User edits profile picture
   * User changes name
* Detail screen
   * User views the detail of a note

### 3. Navigation

**Tab Navigation** (Tab to Screen)

* Map
* Note
* Profile

**Flow Navigation** (Screen to Screen)

* Login/Sign Up screen
   * Map screen
* Map screen
   * Detail screen
   * Note screen
   * Profile screen
* Note screen
   * Map screen
   * Profile screen
* Profile screen
   * Map screen
   * Profile screen
   * Login/Sign Up screen
* Detail screen
   * Map screen

## Wireframes

### Digital Wireframes & Mockups
<img src="./wireframe.png" width=600>

### Interactive Prototype
<img src="./prototype.gif" width=600>

## Schema 
### Models
#### Post

   | Property      | Type     | Description |
   | ------------- | -------- | ------------|
   | objectId      | String   | unique id for the user post (default field) |
   | User        | Pointer to User| note's author |
   | Title         | String     |  note's title  |
   | Detail       | String   | note's detail |
   | createdAt     | DateTime | date when post is created (default field) |
   | updatedAt     | DateTime | date when post is last updated (default field) |
### Networking
#### List of network requests by screen
   - Home Feed Screen
      - (Read/GET) Query all posts where user is author
         ```swift
         let query = PFQuery(className:"Post")
         query.whereKey("author", equalTo: currentUser)
         query.order(byDescending: "createdAt")
         query.findObjectsInBackground { (posts: [PFObject]?, error: Error?) in
            if let error = error { 
               print(error.localizedDescription)
            } else if let posts = posts {
               print("Successfully retrieved \(posts.count) posts.")
           // TODO: Do something with posts...
            }
         }
         ```
   - Create Note Screen
      - (Create/POST) Create a new note object
   - Profile Screen
      -  logged out user object
   
#### [OPTIONAL:] Existing API Endpoints

