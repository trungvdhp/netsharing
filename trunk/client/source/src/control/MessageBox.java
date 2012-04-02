package control;

import de.enough.polish.ui.Alert;
import de.enough.polish.ui.AlertType;
import de.enough.polish.ui.Display;
import de.enough.polish.ui.Displayable;
import de.enough.polish.ui.SimpleScreenHistory;

public class MessageBox {
	
	private static Display _display;
	private static SimpleScreenHistory _screenHistory;
	public static void setDisplay(Display display,SimpleScreenHistory screenHistory)
	{
		_display=display;
		_screenHistory=screenHistory;
	}
	public static Alert Show(String content,AlertType type)
	{
		//#style alertBox
		Alert alert=new Alert("",content,null,type);
		_display.setCurrent(alert,_screenHistory.getCurrent());
		return alert;
	}
	public static Alert Show(String content)
	{
		return Show(content,AlertType.INFO);
	}
}
