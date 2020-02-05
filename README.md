
![](https://cdn.discordapp.com/attachments/662944114291245082/671778076178382908/EchoUnitProfile.png)
# EchoUnit229 



### Authors 
* Rigo 'Skinny' L.
* Kungg



## Requirements
* Have EchoVR installed and at least opened once without the assistance of this application
* Have EchoVR files under the Oculus Directory(Regardless of where the Oculus directory is)
  * Expect to have this limitation fixed in a later update
	* Confirmed for version 1.1


## Installation of the repository

* Recommended editor of intelliJ
* Install python 3+ if you wish to test the echoprotocol
	* Required pip installs
		* `requests`
		* `psutil`
		* `winreg`
		* `pyinstaller` for building the exe -- `pyinstaller -F echoprotocol.py` will place the echoprotocol under the dist folder
* Install local mysql server and assign enviroment variable SQL_URL point it to the local server
* Assign `AUTH_TOKEN` to the discord bot secret and `SQL_PASS` to the root mysql server password
* Build project with maven with command `mvn clean install -DskipTests`


## Usage


### Setup the Echo Protocol
    
     The web launcher will not work if these steps are not completed
     1. Download the latest release
     2. Unzip the release anywhere(but I highly recommend the desktop)
     3. Run RegisterEchoProtocol.exe as ADIMINISTRATOR

### To create a public game
Verify that you're in a private game
##### Only current Method
* Type in a discord text channel `-creategame public`
	* You will be sent a confirmation link, click on it once you're inside a private echo arena game
	* After that, the echo unit will place your game under the public listings with live updates
	* The game will ONLY be displayed in the server you call the command in, so be aware of this

### To create a "private" private game

	private listings have disabled due to a targeting bug, and will be brought back with no updates to the echoprotocol being necessary in the next rollout


## Alpha comments

This is currently a big WIP project. Any feedback will be greatly appreciated. Please report all bugs to skinny, thank you for your continued support.
