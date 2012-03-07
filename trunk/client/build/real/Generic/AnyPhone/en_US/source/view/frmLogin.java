package view;

import javax.microedition.lcdui.Font; 					import javax.microedition.lcdui.Graphics; 					import javax.microedition.lcdui.Image;  					import de.enough.polish.ui.*;

/*import de.enough.polish.ui.ChoiceTextField;
import de.enough.polish.ui.FilteredChoiceGroup;
import de.enough.polish.ui.Style;
import de.enough.polish.ui.StyleSheet;*/

//import de.enough.polish.ui.UiAccess;
//import de.enough.polish.ui.ScreenInfo;

public class frmLogin extends Form implements ItemCommandListener
{
	private TextField txtUsername;
	private TextField txtPassword;
	private ChoiceGroup cgRemembn;
	private Command cmdLogin = new Command("Login",Command.SCREEN,2);
	private Command cmdExit = new Command("Exit", Command.EXIT, 10);
	private Command cmdRegister = new Command("Register", Command.SCREEN, 2);
	private Command cmdOptions = new Command("Options",Command.SCREEN,1);
	public frmLogin(String title) {
		//#style frmLogin
		super(title, de.enough.polish.ui.StyleSheet.frmloginStyle );
		// TODO Auto-generated constructor stub
		txtUsername = new TextField("Tên đăng nhập:","",50,TextField.ANY);
		txtPassword = new TextField("Mật khẩu:","",50,TextField.PASSWORD);
		//#style input
		append(txtUsername, de.enough.polish.ui.StyleSheet.inputStyle );
		//#style input
		append(txtPassword, de.enough.polish.ui.StyleSheet.inputStyle );
		/*cgRemembn = new ChoiceGroup("", Choice.MULTIPLE);
		
		cgRemembn.append("Ghi nhớ tài khoản", null);
		//#style input
		append(cgRemembn, de.enough.polish.ui.StyleSheet.inputStyle );
		addCommand(cmdOptions);
		addCommand(cmdExit);
		UiAccess.addSubCommand(cmdLogin, cmdOptions, this);
		UiAccess.addSubCommand(cmdRegister, cmdOptions, this);*/
	}

	public void commandAction(Command arg0, Item arg1) {
		// TODO Auto-generated method stub
		
	}

}
