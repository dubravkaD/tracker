# Tracker - Product Origin Tracking App

Tracker is an Android app built with Kotlin for tracking the country of origin of various products. It allows users to easily find out where a product is manufactured and manage a list of their tracked products. The app supports Firebase Real-Time Database for storing product information and Firebase Storage for image storage.

## Features

- **User Authentication**: Users can sign up, log in, and log out of the app using their email and password.
- **Product Tracking**: Users can add products and view details like the name, manufacturer, barcode, country of origin, category, and images.
- **Product Management**: Users can update or delete products they’ve added.
- **Search**: A search bar allows users to search for products by name or other attributes.
- **Navigation**: The app has a bottom navigation bar with three tabs:
    - **Home**: Displays a list of all products with an option to add a new product.
    - **Favorites**: Allows users to view products they marked as favorites.
    - **Settings**: Provides options for product management and a logout button.

## Installation

To run the Tracker app locally on your Android device or emulator:

1. Clone the repository:
    ```bash
    git clone https://github.com/dubravkaD/tracker.git
    ```

2. Open the project in Android Studio.

3. Make sure you have the following dependencies installed:
    - Kotlin
    - Firebase SDKs for Realtime Database and Firebase Storage

4. Configure Firebase:
    - Set up a Firebase project at [Firebase Console](https://console.firebase.google.com/).
    - Add the `google-services.json` file to the `app/` directory.

5. Build and run the app on your Android device/emulator.

## Technologies Used

- **Kotlin**: Primary programming language for Android development.
- **Firebase**:
    - **Realtime Database**: For storing and retrieving product data.
    - **Firebase Storage**: For storing images related to products.
    - **Firebase Authentication**: For user authentication

## Usage

- **Home Fragment**: Displays a list of all products that the user has added. You can search for products or add a new product by clicking the "Add Product" button.
- **Favorites Fragment**: Lets users mark certain products as favorites (optional feature).
- **Settings Fragment**: Allows the user to view their account settings and log out.
- **Product Details**: Each product has a name, manufacturer, barcode, country of origin, category, and an optional image.
- **My Products**: Users can update product details or delete them from the list via the settings section.

## Data Structure

The app uses Firebase Realtime Database to store product information. Each product has the following fields:

- **Product ID**: Unique identifier for the product.
- **Name**: Name of the product.
- **User**: The user who added the product.
- **Manufacturer**: The manufacturer of the product.
- **Barcode**: Barcode of the product.
- **Country of Origin**: The country where the product is manufactured.
- **Category**: The category the product belongs to (e.g., electronics, food).
- **Image**: URL to the image of the product stored in Firebase Storage.

### Firebase Authentication

Users are required to sign up and sign in using their email and password. This allows them to track their own products and manage their data.
