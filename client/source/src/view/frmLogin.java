package view;

import javax.microedition.lcdui.*;
import de.enough.polish.ui.ChoiceTextField;
import de.enough.polish.ui.FilteredChoiceGroup;
import de.enough.polish.ui.Style;
import de.enough.polish.ui.StyleSheet;

public class frmLogin extends Form implements ItemCommandListener
{
	private TextField txtUsername;
	private TextField txtPassword;
	private ChoiceGroup cgRemembn;
	public frmLogin(String title) {
		//#style frmLogin
		super(title);
		// TODO Auto-generated constructor stub
		txtUsername = new TextField("Tên đăng nhập:","",50,TextField.ANY);
		txtPassword = new TextField("Mật khẩu:","",50,TextField.PASSWORD);
		//#style input
		append(txtUsername);
		//#style input
		append(txtPassword);
		cgRemembn = new ChoiceGroup("", Choice.MULTIPLE);
		cgRemembn.append("Ghi nhớ tài khoản", null);
		append(cgRemembn);
	}

	public void commandAction(Command arg0, Item arg1) {
		// TODO Auto-generated method stub
		
	}

}
