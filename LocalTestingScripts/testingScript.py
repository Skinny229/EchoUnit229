import sys, string, subprocess

def getEchoExe():
    file = open("EchoExeLocation.txt", "r")
    exe = file.readline()
    print(exe)
    return exe

def launchGame():

    idandspec = sys.argv[1][11:]

    split = idandspec.split("/")

    lobbyid = split[0]
    
    
    specSel = split[1]

    print(lobbyid)
    print(specSel)

    lobbyArg = '-lobbyid ' + lobbyid

    if specSel == 'y':
        subprocess.run([getEchoExe(), lobbyArg,"-spectatorstream"])
        return
    if specSel == 'n':
        subprocess.run([getEchoExe(), lobbArg])
    else:
        print("Defaulting....")
        subprocess.run([getEchoExe(), "-spectatorstream"])

    return

launchGame()
    
