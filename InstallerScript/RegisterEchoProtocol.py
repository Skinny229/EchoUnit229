import winreg, string, os, sys
from time import sleep

def registerEXEtoRegistry():

	print("\n" +" ######   ####   #    #   ####\n" +" #       #    #  #    #  #    #\n" + " #####   #       ######  #    #\n" +" #       #       #    #  #    #\n" +" #       #    #  #    #  #    #\n" +" ######   ####   #    #   ####\n" +"\n")
	print("\n" + " #####   #####    ####    #####   ####    ####    ####   #\n" +  " #    #  #    #  #    #     #    #    #  #    #  #    #  #\n" +  " #    #  #    #  #    #     #    #    #  #       #    #  #\n" +    " #####   #####   #    #     #    #    #  #       #    #  #\n" +     " #       #   #   #    #     #    #    #  #    #  #    #  #\n" +   " #       #    #   ####      #     ####    ####    ####   ######\n" +"\n")
	print("Version 1.0 Coded by Skinny assisted by Kungg")
	print("Intalling......")

	EXEPATH = os.path.abspath("echoprotocol.exe")
	REG_PATH = r"echoprotocol\shell\open\command"
	REG_PROTOCOL = r"echoprotocol"
	commandDefaultValue = r'"'+EXEPATH+r'" "%1"'

	try:
		reg = winreg.CreateKey(winreg.HKEY_CLASSES_ROOT, REG_PATH)
		reg2 = winreg.CreateKey(winreg.HKEY_CLASSES_ROOT, REG_PROTOCOL)
		reg3 = winreg.CreateKey(winreg.HKEY_CLASSES_ROOT, REG_PROTOCOL)
		winreg.SetValue(reg2,'',winreg.REG_SZ,"URL:echoprotocol")
		winreg.SetValueEx(reg3,'URL Protocol', 0,winreg.REG_SZ,'')
		winreg.SetValue(reg,'',winreg.REG_SZ,commandDefaultValue)

		winreg.CloseKey(reg)
		winreg.CloseKey(reg2)
		winreg.CloseKey(reg3)

	except WindowsError:
		print("Failed to register key")
		return False
	return True


if not registerEXEtoRegistry():
	print("Install successful, this message will automatically explode in 10 seconds")
	sleep(10)
	sys.exit(0)
else:
	sys.exit(1)
