# Weather App

The Temperature App allows users to create their own accounts, log in, and edit their profiles. Once logged in, users can search for the temperature of any city around the world. The temperature page provides detailed weather information, including icons to show whether it's day or night in the city, current temperature, geographic coordinates, and a forecast that covers various aspects of the weather conditions.

## Features
- **User Authentication**: Create an account, log in, and manage your profile.
- **City Temperature Search**: Search for the temperature and weather details of cities worldwide.
- **Detailed Weather Information**: Includes current temperature, observation time, weather conditions, wind direction and speed, atmospheric pressure, relative humidity, cloud cover, "feels like" temperature, and UV radiation level.

## Getting Started

### Prerequisites
To stand up the Temperature App, ensure you have the following installed on your local machine:
- **Android Studio**: To open and run the application
- **Android SDK**: Ensure your Android SDK is set up properly

### Installation
Follow these steps to install and run the application locally:

1. **Clone the Repository**
   ```sh
   git clone https://github.com/mingwangrrc/weather.git
   ```

2. **Open in Android Studio**
   - Open Android Studio.
   - Select "Open an Existing Project" and navigate to the cloned repository folder.

3. **Update SDK Location**
   - Navigate to `local.properties` in the project root.
   - Update the `sdk.dir` to point to your local Android SDK installation:
   ```properties
   sdk.dir=/path/to/your/android/sdk
   ```

4. **Build and Run**
   - Click on "Build" to make sure everything is set up properly.
   - Run the app using an emulator or a physical Android device.

### Accessing the Application
- **Sign Up**: Create a new account to get started.
- **Log In**: Log in with your credentials.
- **Search Temperature**: Once logged in, navigate to the search page to explore weather data.

## Usage
- **Sign Up**: Create your user account by providing basic information.
- **Login**: After logging in, you can search for any city's weather details.
- **Edit Profile**: Manage your profile information from your account settings.
- **Temperature Details**: Input a city name to get the current temperature, weather icons for day or night, coordinates, and detailed forecasts including wind, pressure, humidity, and UV levels.

## Technologies Used
- **Android**: Developed with Android Studio
- **Database**: MongoDB
- **Backend**: Node.js, Express.js
- **Weather API**: A third-party weather API to fetch the temperature and weather details

## Contributing
Contributions are welcome! To contribute:
1. Fork the repository.
2. Create your feature branch (`git checkout -b feature/YourFeature`).
3. Commit your changes (`git commit -m 'Add some feature'`).
4. Push to the branch (`git push origin feature/YourFeature`).
5. Open a pull request.

## License
This project is licensed under the MIT License.

## Contact
If you have any questions or issues, feel free to contact the project maintainer:
- **Email**: maintainer@example.com
- **GitHub**: [mingwangrrc](https://github.com/mingwangrrc)

