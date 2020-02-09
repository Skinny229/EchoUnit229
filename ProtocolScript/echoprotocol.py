import os, string, sys, subprocess, psutil, time, requests, json


liveListingsUrl = "http://localhost:8080/api/v2/publicListing"
closeLiveListUrl = "http://localhost:8080/api/v2/closePublicListing"
privateConfirmUrl = "http://localhost:8080/api/v2/confirmPrivateGame"

confirmCode = -1
id = 0
failCounter = 0
maxFailedGetEchoJsonUpdateLoop = 3


def getEchoJson():

    URL = "http://localhost/session"
    try:
        response = requests.get(url = URL)
    except:
        return ""
    return response.json()


def startPublicGameCreationProcess(echoArgs):
    gameData = getEchoJson()

    if not gameData:
        print("It seems either you dont have echo arena open or you're still in the lobby. Please join a private game")
        time.sleep(10)
        sys.exit()

   ## if not gameData['private_match']:
     ##  print("Live updates are not enabled for public games")
      ## sys.exit()


    print("SENDING REQUEST TO ENABLE LIVE UPDATES PLEASE WAIT.....")
    response = requests.post(url = liveListingsUrl, json  = genEchoLiveRequestJson(echoArgs, gameData))
    print("Response to POST REQUEST: " + response.text)
    if response.text == '"ALREADY REPORTED"':
        print("Uh oh. Someone seems to still be running live updated. We don't need you... for now")
        time.sleep(10)
        sys.exit()
    if response.text == '"CONFLICT"':
        print("Hmmm it seems like this request is expired. Use -creategame public")
        time.sleep(10)
        sys.exit()
    print("Entering update loop. I'll keep you posted if anything changes")
    startGameUpdateLoop()

    return

def genEchoLiveRequestJson(echoArgs, gameData):

    liveRequestDetails = {

        'confirmation_code' : echoArgs[5],
        'id': echoArgs[4],
        'discord_user_id' : echoArgs[3],
        'guild_id' : echoArgs[2],
        'sessionid' : gameData['sessionid'],
        'client_name' : gameData['client_name']

    }
    print(liveRequestDetails)

    return liveRequestDetails

def genEchoResponseBody():
    data = getEchoJson()

    if not data:
        return ""

    if 'players' in data['teams'][1] and 'players' in data['teams'][0]:
        players = [player['name'] for team in data['teams'] for player in team['players']]
    elif 'players' in data['teams'][1]:
        players = [player['name'] for player in data['teams'][1]['players']]
    elif 'players' in data['teams'][0]:
        players = [player['name'] for player in data['teams'][0]['players']]
	

    matchdetails = {
        'sessionid' : data['sessionid'],
        'client_name' : data['client_name'],
        'game_clock_display' : data['game_clock_display'],
        'game_status' : data['game_status'],
        'players' : players,
        'orange_points' : data['orange_points'],
        'blue_points' : data['blue_points']
    }
    return matchdetails




def startGameUpdateLoop():
    while True:
        echoupdatebody = genEchoResponseBody()
        if not echoupdatebody:
            print("It seems getting data has failed trying again ....")
            failCounter +=1
            if failCounter == maxFailedGetEchoJsonUpdateLoop:
                print("Max fail rate reached. exiting....")
                x = {
                    'id': id,
                    'confirmation_code': confirmCode
                }
                requests.post(URL = closeLiveListUrl,  json = x)
                time.sleep(10)
                sys.exit()



        response = requests.put(url = liveListingsUrl, json  = echoupdatebody)
        if response.text == '"MOVED PERMANENTLY "':
            print('Game has been deleted, thanks for using EchoUnit229!')
            time.sleep(5)
            break
        if response.text == '"BAD REQUEST"':
            print("It seems you left the private game. Leaving.....")
            time.sleep(5)
            break
    return



###dont worry bout below code

processName = "echovr"


def getOculusInstallPath():
    path = os.popen('echo %OculusBase%').read().strip()
    return path


def getEchoExe():
    echoPath = getOculusInstallPath() + "Software\Software\\ready-at-dawn-echo-arena\\bin\win7\echovr.exe"
    return echoPath

#Credit goes to ThisPointer.com -- Varun for this function
def restartEchoIfRunning():
    '''
    Check if there is any running process that contains the given name processName.
    '''
    #Iterate over the all the running process
    for proc in psutil.process_iter():
        try:
            # Check if process name contains the given name string.
            if processName.lower() in proc.name().lower():
                proc.kill()
                return True
        except (psutil.NoSuchProcess, psutil.AccessDenied, psutil.ZombieProcess):
            pass
    return False

def isEchoRunning():
    '''
    Check if there is any running process that contains the given name processName.
    '''
    #Iterate over the all the running process
    for proc in psutil.process_iter():
        try:
            # Check if process name contains the given name string.
            if processName.lower() in proc.name().lower():
                return True
        except (psutil.NoSuchProcess, psutil.AccessDenied, psutil.ZombieProcess):
            pass
    return False

def launchGame(lobbyid):
    restartEchoIfRunning()
    time.sleep(2)
    print("Launchin game....")
    subprocess.run([getEchoExe(),"-lobbyid", lobbyid])
    return


def confirmPrivateGame(echoArgs):

    gameData = getEchoJson()

    privateConfirmObject = {
        'sessionid': gameData['sessionid'],
        'discord_user_id': echoArgs[2],
        'confirmation_code': echoArgs[4],
        'id': echoArgs[3]
    }

    requests.post(url = privateConfirmUrl , json = privateConfirmObject)
        
    return

def launchGameSpectator(lobbyid):
    restartEchoIfRunning()
    time.sleep(2)
    print("Spectating game.....")
    subprocess.run([getEchoExe(),"-lobbyid", lobbyid, "-spectatorstream"])
    return

def startEchoProtocol():

    print("\n" +" ######   ####   #    #   ####\n" +" #       #    #  #    #  #    #\n" + " #####   #       ######  #    #\n" +" #       #       #    #  #    #\n" +" #       #    #  #    #  #    #\n" +" ######   ####   #    #   ####\n" +"\n")
    print("\n" + " #####   #####    ####    #####   ####    ####    ####   #\n" +  " #    #  #    #  #    #     #    #    #  #    #  #    #  #\n" +  " #    #  #    #  #    #     #    #    #  #       #    #  #\n" +    " #####   #####   #    #     #    #    #  #       #    #  #\n" +     " #       #   #   #    #     #    #    #  #    #  #    #  #\n" +   " #       #    #   ####      #     ####    ####    ####   ######\n" +"\n")
    print("Version 1.0 Coded by Skinny assisted by Kungg")

    echoArgs = sys.argv[1].split(':')

    action = echoArgs[1]

    if action == "//launch/":
        launchGame(echoArgs[2])
    if action == "//spec/":
        launchGameSpectator(echoArgs[2])
    if action == "//createpub/":
        startPublicGameCreationProcess(echoArgs)
    if action == "//confirmPrivate/":
        confirmPrivateGame(echoArgs)
    sys.exit()


startEchoProtocol()