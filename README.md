# Authors
Dor Sror, 207271875  
Eldor Zang, 315232942  
# Messaging Platform
This project is a messaging platform android app with a corresponding api server.



# Preparing all files

**Local Api Server:**  
1. Copy ApiServer Folder.
2. Check Api Server address **WITHOUT TRAILING SLASH** (usually something like https://localhost:7050) - easiest way is to run the server and check its address.
3. Update MariaDB connection string default is root with an empty password) in ApiServer/ApiServer/Context/UsersContext.cs: line 9.
4. Open ApiServer/Controller/apiController.cs and change line 20 with new Api server address.
5. Install Microsoft.EntityFrameworkCore from NuGet (In VS:Tools->NuGet Package Manager->Mange NuGet packages for solution.
6. Install Firebase Admin from NuGet (In VS:Tools->NuGet Package Manager->Mange NuGet packages for solution.
7. Create Firebase project and retrieve json key file from: https://console.firebase.google.com/project/_/settings/serviceaccounts/adminsdk
8. Rename the key file key.json and copy it to project's folder (the folder with the file Program.cs).
9. Add a new migration.
10. Update databse 

**Api Server With BIU Server:** 
In addition, the server (hopefully won't get down by admins by the time of checking) can be accesses with the address: https://a226-132-70-1-38.eu.ngrok.io/  
(https://a226-132-70-1-38.eu.ngrok.io/swagger/index.html for the swagger interface)


**Sample users (optional):** 
In the API server folder there is a file named dbSampleUsers.txt.
Executing this file's content in the database named `db` (of MariaDB) will generate sample users and chats. 
Please note that the server's addresses in the file lines 15-20) need to be changed for working messagaing. 
User Name | Nick Name | Password | Contacts
--- | --- | --- | --- |
bob123 | Bob | bobpass | alice123, oliver123, olivia123
alice123 | Alice | alicepass | bob123
oliver123 | Oliver | oliverpass | bob123
olivia123 | Olivia | oliviapass | bob123

**Android App:**  
1. Copy MyApplication5 folder.
2. Clean project (In Android Studio: Build->Clean Project). 

# Running
1. Navigate to server folder.
2. Run:  
`dotnet run`
(Alternativly, it is more preffered to run with VS)
3. Run android app (prefferably with Android Studio) 

# Notes
1. Android app was tested with android 8.0 (API 26).
2. Server needs to be updated via the settings menu.
3. Users pictures will be displayed only for this device registered users (due to the unsupport of photos in the api).
4. In Google Chrome (in opposed to Firefox) forms aren't automatically out-focused when disabled buttons are clicked. Therefore using Google Chrome browser requires clicking the background (or any other not-focused element) before clicking the login button.
5. The api implements some more neccessary http calls (such as register/login). Please do not manually send those new requests.
