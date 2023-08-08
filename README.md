CalSchedulingSystem

Description: 
- Takes input as a csv file with the columns "Topic Description"	"Date"	"StartTime"	"EndTime"	"List of Invitees".
- The application schedules meeting with the listed invitees with the topic description, on the given date from start time to end time.

Framework Used:
- Spring MVC

Technologies Used:
- Google API 

Installation:
- Application.properties file

Change the "spring.mail.username" and "spring.mail.password" to your gmail Id and password (App password available [here](https://myaccount.google.com/u/1/apppasswords?utm_source=google-account&utm_medium=myaccountsecurity&utm_campaign=tsv-settings&rapt=AEjHL4NbCJZrXOT24L96VfQlisw84p6-zeE-W-uFzTsn305dPz5CNp_Vci-Aj1THAA3zdoLYu3bmzN5-rL3L_VY2njALLYIsIw) in your gmail account

Change the "google.oauth2.clientId" and "google.oauth2.clientSecret" to your google API client ID and Secret key.

Finally,
1. Download the project as a jar file
2. Run the jar file using the command "java -jar /\<your-path>/CalSchedulingSystem.jar"
