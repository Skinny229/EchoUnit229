import os, string, sys, subprocess
     
def getOculusInstallPath():
    path = os.popen('echo %OculusBase%').read().strip()
    return path


def getEchoExe():
    echoPath = getOculusInstallPath() + "Software\Software\\ready-at-dawn-echo-arena\\bin\win7\echovr.exe"
    return echoPath

def setUpEnviromentVariable():
    ##TO BE Implemented in ALPHA 0.2
      ##subprocess.call(['setx.exe', 'htp])
    return

def launchGame():

    lobbyid = sys.argv[1][13:]
    print(lobbyid)
    lobbyArg = " -lobbyid %s" %(lobbyid)
    print(lobbyArg)
    subprocess.run([getEchoExe(),"-lobbyid", lobbyid])
    return

launchGame()
    
