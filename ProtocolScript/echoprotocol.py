import os, string, sys, subprocess, psutil, time, requests, json
from time import sleep

createGameUrl = "http://localhost:8080/api/v2/createPubGame"
updateGameUrl = "http://localhost:8080/api/v2/updateGame"

def getEchoJson():

    URL = "http://localhost/session"

    response = requests.get(url = URL)

    return  response.json()

def postGameCreationPublic():
    ##data = getEchoJson()
    ##Code goes here
    ##IMEPLEMENT ME KUNGGG


    gameData = getEchoJson()




    a = requests.post(url = createGameUrl, json  = gameData)

    while True:
        sleep(1)
        gameData = getEchoJson()
        aa = requests.post(url = updateGameUrl, json  = gameData)
    ##startGameUpdateLoop(uniqueId)

    return

def genEchoResponseBody():
    gameData = getEchoJson()
    jsonFormated = ""


    return jsonFormated


def startGameUpdateLoop():
    return


def startEchoProtocol():


    echoArgs = sys.argv[1].split(':')

    action = echoArgs[1]


    if action == "launch":
        launchGame(echoArgs[2])
    if action == "spec":
       launchGameSpectator(echoArgs[2])
    if action == "createpub":
       postGameCreationPublic()
    sys.exit()



startEchoProtocol()



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