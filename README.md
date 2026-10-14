# Auth0 Native Components — Android Sample

An Android sample app demonstrating Auth0 authentication and MFA management using [Auth0 Universal Components](https://auth0.com/docs/libraries/auth0-android). Built with Kotlin, Jetpack Compose, and Material 3.

⚠️ This project is intended for demonstration purposes only and is not meant for production use. ⚠️


## What this sample shows

- Login and logout via Auth0's hosted login page (`WebAuthProvider`)
- Session persistence using `CredentialsManager`
- User profile display (name, email) after authentication
- MFA authenticator management via the `AuthenticatorSettingsComponent` pre-built UI component

## Tech stack

- **Language:** Kotlin 2.2.20
- **UI:** Jetpack Compose with Material 3
- **Architecture:** MVVM with `StateFlow` for reactive auth state
- **Navigation:** Navigation Compose
- **Auth:** Auth0 Android SDK 3.13.0 + Universal Components 1.0.0-beta.0
- **Min SDK:** 33 (Android 13) | **Target SDK:** 36 (Android 16)

## Prerequisites

- Android Studio Ladybug or later
- An Auth0 account with an application configured for **Native** type
- Android device or emulator running API 33+

## Setup

1. Clone the repo and open it in Android Studio.

2. In your Auth0 dashboard, create a Native application and note the **Domain** and **Client ID**.

3. Add a callback URL to your Auth0 app's **Allowed Callback URLs**:

   ```
   app://{YOUR_AUTH0_DOMAIN}/android/com.jmdevlabs.auth0nativecomponents/callback
   ```

4. Add the same URL to **Allowed Logout URLs**.

5. Replace the placeholder credentials in `app/src/main/res/values/strings.xml`:

   ```xml
   <string name="com_auth0_domain">YOUR_AUTH0_DOMAIN</string>
   <string name="com_auth0_client_id">YOUR_CLIENT_ID</string>
   ```

6. Build and run the app.

## Project structure

```
app/src/main/java/com/jmdevlabs/auth0nativecomponents/
├── MainApplication.kt       Auth0 SDK and Universal Components initialization
├── MainActivity.kt          Navigation host; routes based on auth state
├── AuthViewModel.kt         Auth state management (Loading, LoggedIn, LoggedOut)
└── ui/
    ├── LoginScreen.kt       Sign-in button; triggers WebAuthProvider flow
    ├── HomeScreen.kt        Welcome screen with user profile and navigation
    └── SettingsScreen.kt    MFA settings via AuthenticatorSettingsComponent
```

## Key screens

| Screen | Description |
|---|---|
| Login | Initiates the Auth0 hosted login page via `WebAuthProvider.login()` |
| Home | Displays authenticated user's name and email; links to account security settings |
| Settings | Renders Auth0's pre-built `AuthenticatorSettingsComponent` for managing MFA |

## OAuth scopes

The app requests `openid profile email offline_access`. The `offline_access` scope enables refresh tokens for silent session renewal via `CredentialsManager`.

## License

Copyright 2026 Okta, Inc. Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License. You may obtain a copy of the License at: [http://www.apache.org/licenses/LICENSE-2.0](http://www.apache.org/licenses/LICENSE-2.0)
