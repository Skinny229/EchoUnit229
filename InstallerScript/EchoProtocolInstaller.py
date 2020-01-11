import winreg, string, os

def registerEXEtoRegistry():

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
	except WindowsError:
		print("Failed to register key")
		return False

	return

registerEXEtoRegistry()
