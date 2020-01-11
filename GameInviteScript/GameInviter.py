import requests, string, clipboard

def getPublicGameInvite():
	
	invite = "-nope"
	
	URL = "http://localhost/session"
	
	request = requests.get(url = URL)
	
	data = request.json()
	
	
	if data:##if not null
		lobbyid = data['sessionid']
		invite = "-creategame public " + lobbyid

	clipboard.copy(invite)
		
	return
	

getPublicGameInvite()