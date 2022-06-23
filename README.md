# Authors
Dor Sror, 207271875  
Eldor Zang, 315232942  
# Messaging Platform
This project is a messaging platform android app with a corresponding api server.

# Existing users:
User Name | Nick Name | Password | Contacts
--- | --- | --- | --- |
bob123 | Bob | bob_pass | alice123, oliver123, olivia123
alice123 | Alice | alice_pass | bob123
oliver123 | Oliver | oliver_pass | bob123
olivia123 | Olivia | olivia_pass | bob123


# Preparing all files

**Api Server:**  
1. Create a project using the template ASP.NET CORE Web API and call it ApiServer.
2. Delete WeatherForecast files from project folder and Controllers folder.
4. Copy Models, Hubs, Controllers and Services folder from git to project's folder (overwrite new files).
5. Copy program.cs file to project's folder.
6. Check Api Server address **WITHOUT TRAILING SLASH** (usually something like https://localhost:7050) - easiest way is to run the server and check its address.
7. Open ApiServer/Controller/apiController.cs and change line 20 with new Api server address.
8. Install Microsoft.EntityFrameworkCore from NuGet (In VS:Tools->NuGet Package Manager->Mange NuGet packages for solution.
9. Install Firebase Admin from NuGet (In VS:Tools->NuGet Package Manager->Mange NuGet packages for solution.
10. Create Firebase project and retrieve json key file from: https://console.firebase.google.com/project/_/settings/serviceaccounts/adminsdk
11. Rename the key file key.json and copy it to project's folder (the folder with the file Program.cs).
12. Add a new migration.
13. Update databse

**Android App:**  
1. Copy AndroidApp folder.
2. Clean project. 

# Running
1. Navigate to server folder.
2. Run:  
`dotnet run`
(Alternativly, it is more preffered to run with VS)
3. Run android app (prefferably with Android Studio) 
4. 
# Notes
1. Server needs to be updated via the settings menu.
2. Users pictures will be displayed only for this device registered users (due to the unsupport of photos in the api).
3. In Google Chrome (in opposed to Firefox) forms aren't automatically out-focused when disabled buttons are clicked. Therefore using Google Chrome browser requires clicking the background (or any other not-focused element) before clicking the login button.
4. The api implements some more neccessary http calls (such as register/login). Please do not manually send those new requests.
