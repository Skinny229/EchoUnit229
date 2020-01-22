import os, string, sys, subprocess, psutil


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

def postGameCreationPublic():
    return

def postGameCreationPrivate():
    return


def sendGameUpdate():
    return


def launchGame(lobbyid):
    subprocess.run([getEchoExe(),"-lobbyid", lobbyid])
    return

def launchGameSpectator(lobbyid):
    subprocess.run([getEchoExe(),"-lobbyid", lobbyid, "-spectatorstream"])
    return

def startEchoProtocol():
    return


startEchoProtocol()

