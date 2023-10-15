# BlogKulinarnyMobileApp Client

## Overview
This project is a mobile application composed of a client application written in Java and a server responsible for transmitting records from a database, developed in Python, which can be found at [BlogKulinaryMobileApp-server](https://github.com/cptn3m012/BlogKulinarnyMobileApp-server). The application was designed with three main user roles in mind:: Administrator, Registered User, and Guest (unregistered user). It primarily focuses on browsing and managing culinary recipes, offering a user-friendly interface packed with a range of functionalities. The mobile application was developed based on the website, whose source code is available at [BlogKulinary](https://github.com/Desi451/BlogKulinarny). Both the website and the mobile app use the same database.

## Features

### Administrator Panel:
- **Recipe Oversight**: View all recipes and have the ability to lock or unlock specific ones.
- **Feedback Mechanism**: Provide comments and feedback regarding the decision to lock specific recipes.
- **User Account Management**: Approve or delete newly registered accounts.
- **Category Management**: Add new categories, block or unblock existing ones, and delete categories as needed.
- **Comprehensive Access**: Administrators can access all functionalities available in the user panel.

### User Panel:
- **Profile Customization**: Users can change their avatars, modify their email addresses, and update their usernames.
- **Security**: Update passwords for enhanced security.
- **Account Management**: Option to delete the user account.
- **Personal Recipe View**: Users can view their personal recipes, both those that are locked and those that are unlocked.

### General Panel:
- **User Authentication**: Features for user login and registration.
- **Recipe Browsing**: View a list of all approved recipes and access a detailed view of any specific recipe.
- **Recipe Search**: Filter and search for recipes based on various criteria.

## Extension:
* **AppCompat Library**: [androidx.appcompat:appcompat](https://developer.android.com/jetpack/androidx/releases/appcompat)
* **Material Design Components**: [com.google.android.material:material](https://material.io/develop/android/docs/getting-started/)
* **Constraint Layout**: [androidx.constraintlayout:constraintlayout](https://developer.android.com/reference/androidx/constraintlayout/widget/ConstraintLayout)
* **Compose UI**: [androidx.compose.ui:ui](https://developer.android.com/jetpack/androidx/releases/compose-ui)
* **Material 3**: [androidx.compose.material3:material3](https://developer.android.com/jetpack/androidx/releases/compose-material-3)
* **Picasso**: [com.squareup.picasso:picasso](https://square.github.io/picasso/)
* **OkHttp**: [com.squareup.okhttp3:okhttp](https://square.github.io/okhttp/)



## Clone and Install

1. **Clone the repo:**
```
git clone https://github.com/cptn3m012/BlogKulinarnyMobileApp-client.git
```

2. **Navigate to the project directory:**
```
cd BlogKulinarnyMobileApp-client
```

3. **Install the dependencies:**

   - **Using Gradle**:
     ```
     ./gradlew build
     ```

   - **Using Android Studio**:
     1. Launch `Android Studio`.
     2. Navigate to `File` > `Open`.
     3. Select the cloned `BlogKulinarnyMobileApp-client` directory.
     4. Allow Android Studio to automatically install the dependencies and set up the project.

4. **Run the app:**

   Connect an Android device or start an Android emulator, then:
   ```
   ./gradlew installDebug
   ```
   Or, you can run the app directly from Android Studio by pressing the `Run` button.

After following these steps, the client-side application should be up and running locally on your machine. To get the full experience, you need to configure the server [BlogKulinarnyMobileApp-server](https://github.com/cptn3m012/BlogKulinarnyMobileApp-server).


## License
This application is on MIT License.

