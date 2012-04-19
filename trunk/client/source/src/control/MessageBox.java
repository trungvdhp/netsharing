package control;

import de.enough.polish.ui.Alert;
import de.enough.polish.ui.AlertType;
import de.enough.polish.ui.Display;
//import de.enough.polish.ui.Displayable;
import model.Html;
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
		/*try {
			while(_display.getCurrent()==alert&&Html.isBusy)
				Thread.sleep(100);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		return alert;
	}
	public static Alert Show(String content)
	{
		return Show(content,AlertType.INFO);
	}
	public static Alert ShowBusy(String content)
	{
		//#style alertBox
				Alert alert=new Alert("",content+"   ",null,AlertType.INFO);
				_display.setCurrent(alert,_screenHistory.getCurrent());
				try {
					int i=0;
					Thread.sleep(200);
					while(_display.getCurrent()==alert&&Html.isBusy)
					{
						if(i%3==0)
							alert.setString(content+".  ");
						else if(i%3==1)
							alert.setString(content+".. ");
						else if(i%3==2)
							alert.setString(content+"...");
						i++;
						Thread.sleep(600);
					}
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				return alert;
	}
}
