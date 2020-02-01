import os, string, sys, subprocess, psutil, time, requests, json



def startEchoProtocol():


    echoArgs = sys.argv[1].split(':')

    action = echoArgs[1]


    if action == "launch":
        launchGame(echoArgs[2])
    if action == "spec":
       launchGameSpectator(echoArgs[2])
    if action == "createpub":
       postGameCreationPublic(echoArgs[2])
    sys.exit()





createGameUrl = "localhost:8080/api/v2/createGame"

updateGameUrl = "localhost:8080/api/v2/updateGame"


def getEchoJson():

    URL = "http://localhost/session"

    response = requests.get(url = URL)

    return  response.json()


def postGameCreationPublic(uniqueId):
    data = getEchoJson()
    
    players = [player['name'] for team in data['teams'] for player in team['players']]
    matchdetails = {
    'sessionid' : data['sessionid'],
    'client_name' : data['client_name'],
    'game_clock_display' : data['game_clock_display'],
    'game_status' : data['game_status'],
    'players' : players,
    'orange_points' : data['orange_points'],
    'blue_points' : data['blue_points'],
    'uniqueId' : uniqueId
    }
    currentMatchDetails = json.dumps(matchdetails)
    print(currentMatchDetails)

    ##requests.post(url = createGameUrl, json  = )
    ##startGameUpdateLoop(uniqueId)

    return

def genEchoResponseBody(uniqueid):
    gameData = getEchoJson()
    jsonFormated = ""


    return jsonFormated


def startGameUpdateLoop(uniqueId):
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

startEchoProtocol()