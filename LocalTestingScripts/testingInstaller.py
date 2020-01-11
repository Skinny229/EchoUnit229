import os, string

def getOculusInstallPath():
    path = os.popen('echo %OculusBase%').read().strip()
    return path


def goInstall():


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

