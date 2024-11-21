# Temperature App

The Temperature App allows users to create their own accounts, log in, and edit their profiles. Once logged in, users can search for the temperature of any city around the world. The temperature page provides detailed weather information, including icons to show whether it's day or night in the city, current temperature, geographic coordinates, and a forecast that covers various aspects of the weather conditions.

## Features
- **User Authentication**: Create an account, log in, and manage your profile.
- **City Temperature Search**: Search for the temperature and weather details of cities worldwide.
- **Detailed Weather Information**: Includes current temperature, observation time, weather conditions, wind direction and speed, atmospheric pressure, relative humidity, cloud cover, "feels like" temperature, and UV radiation level.

## Getting Started

### Prerequisites
To stand up the Temperature App, ensure you have the following installed on your local machine:
- **Node.js**: Version 14 or higher
- **npm**: Comes with Node.js for managing dependencies
- **MongoDB**: A local or remote instance to store user information

### Installation
Follow these steps to install and run the application locally:

1. **Clone the Repository**
   ```sh
   git clone https://github.com/mingwangrrc/weather.git
   cd weather
   ```

2. **Install Dependencies**
   Use npm to install the required dependencies for the application:
   ```sh
   npm install
   ```

3. **Set Up Environment Variables**
   Create a `.env` file in the root of your project with the following values:
   ```env
   PORT=3000
   MONGODB_URI=your_mongodb_connection_string
   WEATHER_API_KEY=your_weather_api_key
   ```
   - Replace `your_mongodb_connection_string` with the URI for your MongoDB instance.
   - Replace `your_weather_api_key` with your API key from the weather service provider.

4. **Run the Application**
   Start the server using the command:
   ```sh
   npm start
   ```
   By default, the application will run on `http://localhost:3000`.

### Running in Development Mode
To run the app in development mode with auto-reloading, use:
```sh
npm run dev
```
This requires `nodemon`, which will monitor for changes and restart the server automatically.

### Accessing the Application
- **Sign Up**: Visit `http://localhost:3000/signup` to create a new account.
- **Log In**: Log in with your credentials at `http://localhost:3000/login`.
- **Search Temperature**: Once logged in, navigate to the search page to explore weather data.

## Usage
- **Sign Up**: Create your user account by providing basic information.
- **Login**: After logging in, you can search for any city's weather details.
- **Edit Profile**: Manage your profile information from your account settings.
- **Temperature Details**: Input a city name to get the current temperature, weather icons for day or night, coordinates, and detailed forecasts including wind, pressure, humidity, and UV levels.

## Technologies Used
- **Backend**: Node.js, Express.js
- **Database**: MongoDB
- **Frontend**: HTML, CSS, JavaScript
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

