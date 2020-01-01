import easygui, winreg, os, string


def getOculusInstallPath():
    path = os.popen('echo %OculusBase%').read().strip()
    return path


def setTxtExeLocation(path):
    file = open("EchoExeLocation.txt", "w")
    file.write(path)
    return
    

def setRegistryToLaunchScript():
    #TODO Implement me

    return

def goInstall():
    hasEchoMoved = easygui.ynbox('Have you modified ECHO VRs install location?', 'EchoRegistryInstaller', ('Yes', 'No'))


    if hasEchoMoved:
        #TODO Add non native exe support
        print('Non Native installs are not yet supported')
        echoInstallLocation = getOculusInstallPath() + r'Software\Software\ready-at-dawn-echo-arena\bin\win7\echovr.exe'
    else:
        echoInstallLocation = getOculusInstallPath() + r'Software\Software\ready-at-dawn-echo-arena\bin\win7\echovr.exe'

    setTxtExeLocation(echoInstallLocation)
    
    setRegistryToLaunchScript();
    
    return




goInstall();

