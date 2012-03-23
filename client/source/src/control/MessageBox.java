package control;

import de.enough.polish.ui.Alert;
import de.enough.polish.ui.AlertType;
import de.enough.polish.ui.Display;
import de.enough.polish.ui.Displayable;

public class MessageBox {
	public static Alert Show(String content)
	{
		//#style alertBox
		Alert a=new Alert("",content,null,AlertType.INFO);
		return a;
	}
	public static Alert Show(String content,AlertType alertType)
	{
		//#style alertBox
		Alert a=new Alert("",content,null,AlertType.INFO);
		return a;
	}
	public static void Show(String content,Display display,Displayable disp)
	{
		display.setCurrent(Show(content),disp);
	}
}
