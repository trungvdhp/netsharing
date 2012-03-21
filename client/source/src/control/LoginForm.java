package control;

import app.App;

import model.User;
import de.enough.polish.ui.*;
import de.enough.polish.util.Locale;

public class LoginForm extends Form implements CommandListener {
	private TextField txtUsername = new TextField("Tên đăng nhập: ","", 50,TextField.ANY);
	private TextField txtPassword = new TextField("Mật khẩu: ","",50, TextField.PASSWORD);
	
	private Command cmdLogin = new Command("Đăng nhập",Command.SCREEN,1);
	private Command cmdRegister = new Command("Đăng ký", Command.SCREEN,1);
	private Command cmdExit = new Command(Locale.get("cmd.exit"), Command.EXIT, 10);
	public User user;
	private ChoiceGroup cgRemember = new ChoiceGroup("", ChoiceGroup.MULTIPLE);
	private final App midlet;
	private Display display;
	public LoginForm(App midlet)
	{
		//#style userForm
		super("Login");
		
		this.midlet = midlet;
		display = Display.getDisplay(midlet);
		//#style textField 
		append(txtUsername);
		//#style textField
		append(txtPassword);
		//#style checkBoxItem
		cgRemember.append("Ghi nhớ mật khẩu", null);
		append(cgRemember);
		
		addCommand(cmdLogin);
		addCommand(cmdRegister);
		addCommand(cmdExit);
		setCommandListener(this);
		
	}
	public void commandAction(Command cmd,Displayable disp)
	{
		if(cmd==cmdLogin)
		{
			user = new User(txtUsername.getString(),txtPassword.getString());
			if(user.Login())
			{
				showMessage("Đăng nhập thành công", this, AlertType.INFO);
			}
			else
			{
				showMessage("Tên đăng nhập hoặc mật khẩu không chính xác", this, AlertType.ERROR);
			}
		}
		else if(cmd==cmdRegister)
		{
			
		}
	}
	public void showMessage(String content,Displayable disp,AlertType type)
	{
		//#style alertBox
		Alert alert=new Alert("",content,null,type);
		this.display.setCurrent(alert,disp);
	}
}
