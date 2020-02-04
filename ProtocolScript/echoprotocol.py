import os, string, sys, subprocess, psutil, time, requests, json


liveListingsUrl = "http://localhost:8080/api/v2/publicListing"



def getEchoJson():

    URL = "http://localhost/session"

    response = requests.get(url = URL)

    return  response.json()


def startPublicGameCreationProcess(echoArgs):
    gameData = getEchoJson()

    if not gameData:
        print("It seems either you dont have echo arena open or you're still in the lobby. Please join a private game")
        sys.exit()

   ## if not gameData['private_match']:
     ##   print("Live updates are not enabled for public games")
       ## sys.exit()



    response = requests.post(url = liveListingsUrl, json  = genEchoLiveRequestJson(echoArgs, gameData))

    print("Response to POST REQUEST: " + response.text)
    if response.text == '"CONFLICT"':
        print("Uh oh. Someone seems to still be running live updated. We don't need you... for now")
        sys.exit()
    startGameUpdateLoop()

    return

def genEchoLiveRequestJson(echoArgs, gameData):

    print(echoArgs)
    liveRequestDetails = {

        'confirmation_code' : echoArgs[4],
        'discord_user_id' : echoArgs[3],
        'guild_id' : echoArgs[2],
        'sessionid' : gameData['sessionid'],
        'client_name' : gameData['client_name']

    }

    return liveRequestDetails

def genEchoResponseBody():
    data = getEchoJson()

    if 'players' in data['teams'][1]:
        players = [player['name'] for team in data['teams'] for player in team['players']]
    else:
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
        requests.put(url = liveListingsUrl, json  = genEchoResponseBody())
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
    subprocess.run([getEchoExe(),"-lobbyid", lobbyid])
    return

def launchGameSpectator(lobbyid):
    restartEchoIfRunning()
    time.sleep(2)
    subprocess.run([getEchoExe(),"-lobbyid", lobbyid, "-spectatorstream"])
    return

def startEchoProtocol():


    echoArgs = sys.argv[1].split(':')

    action = echoArgs[1]

    if action == "//launch":
        launchGame(echoArgs[2])
    if action == "//spec":
        launchGameSpectator(echoArgs[2])
    if action == "//createpub/":
        startPublicGameCreationProcess(echoArgs)
    sys.exit()


startEchoProtocol()