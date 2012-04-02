/*
 * Created on Dec 15, 2010 at 9:19:09 AM.
 * 
 * Copyright (c) 2010 Robert Virkus / Enough Software
 *
 * This file is part of J2ME Polish.
 *
 * J2ME Polish is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or
 * (at your option) any later version.
 * 
 * J2ME Polish is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with J2ME Polish; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
 * 
 * Commercial licenses are also available, please
 * refer to the accompanying LICENSE.txt or visit
 * http://www.j2mepolish.org for details.
 */
package control;

import java.io.IOException;
import javax.microedition.lcdui.Image;

import util.UtilString;
import view.*;
import model.*;
import app.App;


import de.enough.polish.event.ThreadedCommandListener;
import de.enough.polish.io.RmsStorage;
import de.enough.polish.ui.Alert;
import de.enough.polish.ui.ChoiceTextField;
import de.enough.polish.ui.DateField;
import de.enough.polish.ui.AlertType;
import de.enough.polish.ui.ChoiceGroup;
import de.enough.polish.ui.Command;
import de.enough.polish.ui.CommandListener;
import de.enough.polish.ui.Display;
import de.enough.polish.ui.Gauge;
import de.enough.polish.ui.List;
import de.enough.polish.ui.ScreenInfo;
import de.enough.polish.ui.StringItem;
import de.enough.polish.ui.TextField;
import de.enough.polish.ui.Displayable;
import de.enough.polish.ui.SimpleScreenHistory;
import de.enough.polish.ui.UiAccess;
import de.enough.polish.ui.splash2.ApplicationInitializer;
import de.enough.polish.ui.splash2.InitializerSplashScreen;
import de.enough.polish.util.ArrayList;
import de.enough.polish.util.Locale;

/**
 * <p>Controls the UI of the mobile app</p>
 *
 * <p>Copyright Enough Software 2010</p>
 * @author Robert Virkus, j2mepolish@enough.de
 */
public class Controller
implements ApplicationInitializer, CommandListener
{

	private final App midlet;
	private Display display;
	private Configuration configuration;
	private RmsStorage storage;
	
	private TextField txtUsername = new TextField("Tên đăng nhập: ","", 50,TextField.ANY);
	private TextField txtPassword = new TextField("Mật khẩu: ","",32, TextField.PASSWORD);
	private TextField txtOldPassword = new TextField("Mật khẩu cũ: ","",32, TextField.PASSWORD);
	private TextField txtNewPassword = new TextField("Mật khẩu mới: ","",32, TextField.PASSWORD);
	private TextField txtConfirm = new TextField("Xác nhận mật khẩu: ","",32, TextField.PASSWORD);
	private TextField txtFirstName = new TextField("Họ đệm: ","", 32,TextField.ANY);
	private TextField txtLastName = new TextField("Tên: ","", 32,TextField.ANY);
	private TextField txtAddress = new TextField("Địa chỉ: ","", 100,TextField.ANY);
	private TextField txtPhone = new TextField("Điện thoại: ","", 20,TextField.PHONENUMBER);
	
	private TextField txtSearch = new TextField("Từ khóa: ","", 50,TextField.ANY);
	private TextField txtTopicTitle = new TextField("Tiêu đề: ","", 100,TextField.ANY);
	private TextField txtTopicContent = new TextField("Nội dung: ","", 512,TextField.ANY);
	private TextField txtGroupName = new TextField("Tên nhóm: ","", 50,TextField.ANY);
	private TextField txtDescription = new TextField("Mô tả: ","", 150,TextField.ANY);
	private TextField txtGroupRule = new TextField("Quy tắc: ","", 300,TextField.ANY);
	
	private StringItem strCreateDate = new StringItem("Ngày chỉnh sửa cuối: ","");
	
	private CommandListener commandListener;
	
	private ChoiceGroup cgRemember = new ChoiceGroup("", ChoiceGroup.MULTIPLE);
	private ChoiceGroup cgGender = new ChoiceGroup("Giới tính: ", ChoiceGroup.MULTIPLE);
	
	private ChoiceTextField email;
	//private DateField dateBirthday = new DateField("Ngày sinh: ", DateField.DATE);
	
	private Command cmdLogin = new Command("Đăng nhập",Command.SCREEN,1);
	private Command cmdLogout = new Command("Đăng xuất",Command.SCREEN,1);
	private Command cmdRegister = new Command("Đăng ký", Command.SCREEN,1);
	private Command cmdChangePassword = new Command("Đổi mật khẩu", Command.SCREEN,1);
	private Command cmdExit = new Command(Locale.get("cmd.exit"), Command.EXIT, 10);
	private Command cmdBack = new Command(Locale.get("cmd.back"), Command.BACK, 2);
	private Command cmdCreateGroup=new Command("Tạo nhóm mới",Command.SCREEN,1);
	private Command cmdCreateTopic=new Command("Đăng bài mới",Command.SCREEN,1);
	private Command cmdDeleteTopic=new Command("Xóa bài viết",Command.SCREEN,1);
	private Command cmdDeleteGroup=new Command("Xóa nhóm",Command.SCREEN,1);
	private Command cmdViewTopic=new Command("Xem bài viết",Command.OK,1);
	private Command cmdConfirm=new Command("Xác nhận",Command.SCREEN,1);
	private Command cmdReject=new Command("Từ chối",Command.SCREEN,1);
	private Command cmdMyGroup=new Command("Nhóm của bạn",Command.SCREEN,1);
	private Command cmdUpdate=new Command("Cập nhật",Command.SCREEN,1);
	private Command cmdDetail=new Command("Chi tiết",Command.SCREEN,1);
	private Command cmdGroup =new Command("Nhóm",Command.SCREEN,1);
	private Command cmdSendRequest =new Command("Tham gia",Command.OK,1);
	
	private MainMenuList screenMainMenu;
	private SimpleScreenHistory screenHistory;
	private UserForm frmLogin;
	private UserForm frmRegister;
	private UserForm frmProfile;
	private UserForm frmChangePassword;
	private User user;
	private UserList frmGroup;
	private UserForm frmCreateGroup;
	private UserForm frmTopicDetail;
	private UserList frmGroupDetail;
	private UserList frmNewTopic;
	private UserForm frmCreateTopic;
	private UserList frmJoinRequest;
	private UserForm frmJoinRequestDetail;
	private Alert frmConfirmDelGroup;
	private UserForm frmUpdateGroup;
	private UserForm frmSearch;
	private UserList frmSearchGroup;
	
	private void openProfileForm() {
		// TODO Auto-generated method stub
		user.GetInfo();
		frmProfile=new UserForm("Tài khoản",null);
		
		frmProfile.addTextField(txtFirstName);
		frmProfile.addTextField(txtLastName);
		//frmProfile.addDateField(dateBirthday);
		initEmailField();
		frmProfile.addEmailField(email);
		//#style checkBoxItem
		cgGender = new ChoiceGroup("Giới tính", ChoiceGroup.MULTIPLE);
		cgGender.append(" Nữ", null);
		
		frmProfile.addCheckBox(cgGender);
		frmProfile.addTextField(txtPhone);
		frmProfile.addTextField(txtAddress);
		frmProfile.addTextLabel(strCreateDate);
		
		txtFirstName.setString(user.firstName);
		txtLastName.setString(user.lastName);
		strCreateDate.setText(user.createDate);
		email.setString(user.email);
		if(user.gender.equals("0"))
		{
			cgGender.setSelectedIndex(0,false);
		}
		else
		{
			cgGender.setSelectedIndex(0,true);
		}
		txtPhone.setString(user.phone);
		txtAddress.setString(user.address);
		
		frmProfile.addMenu(cmdUpdate);
		frmProfile.addMenu(cmdChangePassword);
		frmProfile.addMenu(cmdLogout);
		frmProfile.addMenu(cmdBack);
		frmProfile.setCommandListener(this.commandListener);
		screenHistory.show(frmProfile);
	}
	
	private void initEmailField()
	{
		String[] mailServices = new String[] {"gmail.com", "yahoo.com", "vimaru.edu.vn", "msn.com", "somewhere.com" };
		boolean allowFreeText = true;
		boolean appendSelectedChoice = true;
		String appendDelimiter = ";";
		email = new ChoiceTextField("Email: " , "", 100, TextField.EMAILADDR, 
			mailServices, allowFreeText, appendSelectedChoice, appendDelimiter );
		char choiceTriggerChar = '@';
		boolean allowChoicesBeforeChoiceTriggerHasBeenEntered = false;
		email.setChoiceTrigger( choiceTriggerChar, allowChoicesBeforeChoiceTriggerHasBeenEntered );
	}
	
	private void openChangePasswordForm() {
		// TODO Auto-generated method stub
		txtOldPassword.setString("");
		txtNewPassword.setString("");
		txtConfirm.setString("");
		frmChangePassword=new UserForm("Đổi mật khẩu",null);
		frmChangePassword.addTextField(txtOldPassword);
		frmChangePassword.addTextField(txtNewPassword);
		frmChangePassword.addTextField(txtConfirm);
		frmChangePassword.addMenu(cmdConfirm);
		frmChangePassword.addMenu(cmdBack);
		frmChangePassword.setCommandListener(this.commandListener);
		
		screenHistory.show(frmChangePassword);
	}
	
	/**
	 * Creates a new controller.
	 * @param midlet the main application
	 */
	public Controller(App midlet) {
		this.midlet = midlet;
		this.display = Display.getDisplay(midlet);
		this.screenHistory = new SimpleScreenHistory(this.display);
		this.commandListener = new ThreadedCommandListener(this);
		MessageBox.setDisplay(display, screenHistory);
	}

	/**
	 * Lifecycle: starts the application for the first time.
	 */

	public void appStart() {
		String splashUrl = "/Splash.png";
		Image splashImage = null;
		try {
			splashImage = Image.createImage(splashUrl);
		} catch (Exception e) {
			//#debug error
			System.out.println("Unable to load splash image " + splashUrl +  e);
		}
		int backgroundColor = 0xffffff;
		InitializerSplashScreen splash = new InitializerSplashScreen(splashImage, backgroundColor,  this);
		this.display.setCurrent( splash );
		
	}
	/*public void MessageBox.Show(String content,Displayable disp,AlertType type)
	{
		//#style alertBox
		Alert alert=new Alert("",content,null,type);
		this.display.setCurrent(alert,disp);
	}*/
	
	public void openLoginForm()
	{
		frmLogin=new UserForm("Đăng nhập",null);
		frmLogin.addTextField(txtUsername);
		frmLogin.addTextField(txtPassword);
		//#style checkBoxItem
		cgRemember.append("Ghi nhớ mật khẩu", null);
		frmLogin.addCheckBox(cgRemember);
		
		frmLogin.addMenu(cmdLogin);
		frmLogin.addMenu(cmdRegister);
		frmLogin.addMenu(cmdExit);
		frmLogin.setCommandListener(this.commandListener);
		if(configuration.get("remember").equals("true"))
		{
			txtUsername.setString(configuration.get("username"));
			txtPassword.setString(configuration.get("password"));
			cgRemember.setSelectedFlags(new boolean[]{true});
		}
		screenHistory.show(frmLogin);
	}
	
	public void openRegisterForm()
	{
		frmRegister = new UserForm("Đăng ký tài khoản",null);
		frmRegister.addTextField(txtUsername);
		frmRegister.addTextField(txtPassword);
		frmRegister.addTextField(txtConfirm);
		frmRegister.addMenu(cmdConfirm);
		frmRegister.addMenu(cmdBack);
		frmRegister.setCommandListener(this.commandListener);
		screenHistory.show(frmRegister);
	}
	
	public void openGroupForm(ArrayList groups)
	{
		frmGroup=new UserList("Nhóm tham gia");
		frmGroup.addCommand(cmdGroup);
		UiAccess.addSubCommand(cmdDetail, cmdGroup,frmGroup);
		UiAccess.addSubCommand(cmdUpdate, cmdGroup,frmGroup);
		UiAccess.addSubCommand(cmdDeleteGroup, cmdGroup,frmGroup);
		frmGroup.addCommand(cmdCreateGroup);
		frmGroup.addCommand(cmdMyGroup);
		frmGroup.addCommand(cmdBack);
		//MessageBox.Show("S", screenMainMenu, AlertType.INFO);
		for(int i=0;i<groups.size();i++)
		{
			Group group=(Group)groups.get(i);
			frmGroup.addEntry(new UserItem(group.groupName,group),"group");
		}
		
		frmGroup.setCommandListener(this.commandListener);
		screenHistory.show(frmGroup);
	}
	public void openSearchGroupForm(ArrayList groups)
	{
		frmSearchGroup=new UserList("Nhóm tham gia");
		frmSearchGroup.addCommand(cmdSendRequest);
		frmSearchGroup.addCommand(cmdBack);
		//MessageBox.Show("S", screenMainMenu, AlertType.INFO);
		for(int i=0;i<groups.size();i++)
		{
			Group group=(Group)groups.get(i);
			frmSearchGroup.addEntry(new UserItem(group.groupName,group),"group");
		}
		
		frmSearchGroup.setCommandListener(this.commandListener);
		screenHistory.show(frmSearchGroup);
	}
	public void openGroupDetail(Group group)
	{
		
		frmGroupDetail = new UserList(group.groupName);
		ArrayList topics=group.GetTopics();
		for(int i=0;i<topics.size();i++)
		{
			TopicGroup t=(TopicGroup)topics.get(i);
			UserItem topic=new UserItem(t.topic.title,t);
			frmGroupDetail.addEntry(topic,"topic");
		}
		
		frmGroupDetail.addCommand(cmdViewTopic);
		frmGroupDetail.addCommand(cmdCreateTopic);
		frmGroupDetail.addCommand(cmdDeleteTopic);
		frmGroupDetail.addCommand(cmdBack);
		frmGroupDetail.setCommandListener(this.commandListener);
		screenHistory.show(frmGroupDetail);
	}
	/**
	 * Lifecycle: pauses the application, e.g. when there is an incoming call.
	 */
	public void appPause() {
		// TODO implement pauseApp, e.g. stop streaming
	}

	/**
	 * Lifecycle: continues the application after it has been paused.
	 */
	public void appContinue() {
		// TODO implement continueApp, e.g. start streaming again
	}

	/**
	 * Initializes this application in a background thread that is called from within the splash screen.
	 */
	public void initApp() {
		long initStartTime = System.currentTimeMillis();
		this.storage = new RmsStorage();
		this.configuration = configurationLoad();
		
		long currentTime = System.currentTimeMillis();
		if (currentTime - initStartTime < 2000) { // show the splash at least for 2000 ms / 2 seconds:
			try {
				Thread.sleep(2000 - currentTime + initStartTime);
			} catch (InterruptedException e) {
				// ignore
			}
		}
		openLoginForm();
		
	}
	
	private MainMenuList createMainMenu() {
		MainMenuList list = new MainMenuList();
		list.setCommandListener(this.commandListener);
		list.addCommand(this.cmdExit);
		list.addEntry("Tin mới");
		list.addEntry("Nhóm");
		list.addEntry("Yêu cầu");
		list.addEntry("Tìm kiếm");
		list.addEntry("Hộp thư");
		list.addEntry("Tài khoản");
		list.addEntry("Cài đặt");
		return list;
	}

	/**
	 * Loads the configuration of this app.
	 * @return the loaded configuration or an new one.
	 */
	private Configuration configurationLoad() {
		try {
			Configuration cfg = (Configuration) this.storage.read(Configuration.KEY);
			return cfg;
		} catch (IOException e) {
			//#debug info
			System.out.println("Unable to load configuration" + e);
		}
		return new Configuration();
	}

	/**
	 * Persists the configuration.
	 * @return true when saving was successful, otherwise false is returned.
	 */
	private boolean configurationSave() {
		try {
			this.storage.save(this.configuration, Configuration.KEY);
			return true;
		} catch (IOException e) {
			//#debug error
			System.out.println("Unable to store the configuration" + e);
			return false;
		}
	}

	/*
	 * (non-Javadoc)
	 * @see de.enough.polish.ui.CommandListener#commandAction(de.enough.polish.ui.Command, de.enough.polish.ui.Displayable)
	 */
	public void commandAction(Command cmd, Displayable disp) 
	{
		if (cmd == this.cmdExit) {
			if (this.configuration.isDirty()) {
			configurationSave();
			}
			this.midlet.exit();
		} else if (disp == this.screenMainMenu) {
			if (handleCommandMainMenu()) {
				return;
			}
			if (cmd == this.cmdBack){
				screenHistory.show(frmLogin);
			}
		} else if (cmd == this.cmdBack) {
			if (this.screenHistory.hasPrevious()) {
				this.screenHistory.showPrevious();
			} else {
				screenHistory.show(this.screenMainMenu);
			}
		} else if(cmd == this.cmdLogin) {
			String username = txtUsername.getString();
			String password = txtPassword.getString();
			this.user=new User(username,password);
			if(user.Login())
			{
				boolean[] gets=new boolean[1];
				cgRemember.getSelectedFlags(gets);
				if(gets[0]){
					configuration.set("remember", "true");
					configuration.set("username", txtUsername.getString());
					configuration.set("password", txtPassword.getString());
				}
				else
				{
					configuration.set("remember", "false");
					configuration.set("username", "");
					configuration.set("password", "");
				}
				this.screenMainMenu = createMainMenu();
				screenHistory.show(screenMainMenu);
			}
			else
			{
				MessageBox.Show("Đăng nhập thất bại!",AlertType.INFO);
			}
		}
		else if(cmd == this.cmdRegister)
		{
			openRegisterForm();
		} 
		else if(cmd == this.cmdLogout)
		{
			openLoginForm();
		}
		else if(cmd == this.cmdChangePassword)
		{
			openChangePasswordForm();
		}
		else if(cmd == cmdConfirm)
		{
			if(disp==frmRegister){
				if(txtPassword.getString().equals(txtConfirm.getString()))
				{
					user = new User(txtUsername.getString(),txtPassword.getString());
					String rs = user.Register();
					if(rs.equals("true"))
					{
						MessageBox.Show("Bạn đã đăng ký thành công!",  AlertType.INFO);
					}
					else if(rs.equals("TonTai"))
					{
						MessageBox.Show("Tài khoản này đã tồn tại!",  AlertType.INFO);
					}
					else
					{
						MessageBox.Show("Đăng ký tài khoản thất bại!",  AlertType.ERROR);
					}
				}
				else
				{
					MessageBox.Show("Xác nhận mật khẩu không khớp",  AlertType.ERROR);
				}
			}
			else if(disp==frmChangePassword)
			{
				if(txtOldPassword.getString().equals(user.password))
				{
					if(txtNewPassword.getString().equals(txtConfirm.getString()))
					{
						User u = new User(user.userId, "", txtNewPassword.getString());
						if(u.ChangePassword())
						{
							MessageBox.Show("Bạn đã thay đổi mật khẩu thành công!",  AlertType.INFO);
							user.password = txtNewPassword.getString();
							txtPassword.setString(user.password);
						}
						else
						{
							MessageBox.Show("Thay đổi mật khẩu thất bại!",  AlertType.ERROR);
						}
					}
					else
					{
						MessageBox.Show("Xác nhận mật khẩu mới không khớp",  AlertType.ERROR);
					}
				}
				else
				{
					MessageBox.Show("Nhập mật khẩu cũ không chính xác!",  AlertType.ERROR);
				}
			}
			else if(disp==frmJoinRequest)
			{
				UserItem item=(UserItem)frmJoinRequest.getCurrentItem();
				Request r=(Request)item.data;
				if(!user.ConfirmRequest(r))
					MessageBox.Show("Có lỗi xảy ra. Yêu cầu tạm thời không thể thực hiện!",  AlertType.ERROR);
				else
					MessageBox.Show("Đã chấp nhận yêu cầu tham gia nhóm!",  AlertType.INFO);
			}
			else if(disp==frmCreateTopic)
			{
				
				if(user.CreateTopic(txtTopicTitle.getString(), txtTopicContent.getString(),(Group)frmCreateTopic.data)!=null);
				MessageBox.Show("Đã tạo topic",  AlertType.INFO);
			}
			else if(disp==frmCreateGroup)
			{
				Group g=user.CreateGroup(txtGroupName.getString());
				
				if(!g.groupId.equals(""))
				{
					txtGroupName.setString("");
					MessageBox.Show("Tạo nhóm mới thành công!",  AlertType.INFO);
				}
				else
				{
					MessageBox.Show("Không thể tạo nhóm mới!",  AlertType.ERROR);
				}
			}
			else if(disp==frmConfirmDelGroup)
			{
				UserItem item=(UserItem)frmGroup.getCurrentItem();
				Group g=(Group)item.data;
				if(user.DeleteGroup(g))
				{
					MessageBox.Show("Nhóm đã được xóa!",  AlertType.INFO);
				}
				else
				{
					MessageBox.Show("Không thể xóa nhóm!",  AlertType.ERROR);	
				}
					
			}
			else if(disp==frmUpdateGroup)
			{
				Group g=(Group)frmUpdateGroup.data;
				if(user.UpdateGroup(g.groupId, txtGroupName.getString(), txtDescription.getString(), txtGroupRule.getString()))
				{
					MessageBox.Show("Cập nhật thành công!",  AlertType.INFO );
				}
				else
				{
					MessageBox.Show("Cập nhật không thành công!",  AlertType.ERROR);
				}
			}
			else if(disp==frmSearch)
			{
				ArrayList groups = user.SearchGroup(txtSearch.getString());
				openSearchGroupForm(groups);
			}
		}
		else if(cmd==List.SELECT_COMMAND)
		{
			if(disp==screenMainMenu)
				handleCommandMainMenu();
			else if(disp==frmGroup)
			{
				UserItem item=(UserItem)frmGroup.getCurrentItem();
				openGroupDetail((Group)item.data);
			}
			else if(disp==frmGroupDetail)
			{
				
				UserItem item=(UserItem)frmGroupDetail.getCurrentItem();
				TopicGroup t=(TopicGroup)item.data;
				//MessageBox.Show("A", screenMainMenu, AlertType.INFO);
				openTopicDetailForm(t);
			}
			else if(disp==frmNewTopic)
			{
				UserItem item=(UserItem)frmNewTopic.getCurrentItem();
				TopicGroup t=(TopicGroup)item.data;
				openTopicDetailForm(t);
				user.ViewedNewTopic(t);
			}
			else if(disp==frmJoinRequest)
			{
				UserItem t=(UserItem)frmJoinRequest.getCurrentItem();
				openJoinRequestDetail((Request)t.data);
			}
			else if(disp==frmSearchGroup)
			{
				//UserItem item=(UserItem)
			}
		}
		else if(cmd==cmdCreateGroup)
		{
			openCreateGroupForm();
		}
		else if(cmd==cmdCreateTopic)
		{
			openCreateTopicForm();
		}
		else if(cmd==cmdConfirm)
		{
			if(disp==frmJoinRequest)
			{
				//user.ConfirmRequest();
			}
		}
		else if(cmd==cmdDeleteGroup)
		{
			UserItem item=(UserItem)frmGroup.getCurrentItem();
			Group g=(Group)item.data;
			if(!g.leader.userId.equals(user.userId))
			{
				MessageBox.Show("Không thể xóa nhóm không phải do bạn tạo!",  AlertType.ERROR);
				return;
			}
			frmConfirmDelGroup=MessageBox.Show("Bạn có chắc chắn muốn xóa nhóm "+g.groupName+" không?",AlertType.CONFIRMATION);
			frmConfirmDelGroup.addCommand(cmdConfirm);
			frmConfirmDelGroup.addCommand(cmdBack);
			frmConfirmDelGroup.setCommandListener(this.commandListener);
			//screenHistory.show(frmConfirmDelGroup);
		}
		else if(cmd==cmdUpdate)
		{
			if(disp==frmProfile)
			{
				String gender = cgGender.isSelected(0)?"1":"0";
				User u = new User(user.userId, txtFirstName.getString(), txtLastName.getString()
						, email.getString(), gender, txtPhone.getString(), txtAddress.getString());
				if(u.Update())
				{
					MessageBox.Show("Bạn đã cập nhật thông tin tài khoản thành công!",  AlertType.INFO);
				}
				else
				{
					MessageBox.Show("Cập nhật thông tin tài khoản thất bại!",  AlertType.ERROR);
				}
			}
			else
			{
				openUpdateGroupForm();
			}
			
		}
		else if(cmd==cmdSendRequest)
		{
			UserItem item=(UserItem)frmSearchGroup.getCurrentItem();
			Group g=(Group)item.data;
			if(!user.CreateRequest(g))
				MessageBox.Show("Có lỗi xảy ra. Yêu cầu tạm thời không thể thực hiện!",  AlertType.ERROR);
			else
				MessageBox.Show("Đã gửi yêu cầu thành công!",  AlertType.INFO);
		}
		else if(cmd==cmdReject)
		{
			UserItem item=(UserItem)frmJoinRequest.getCurrentItem();
			Request r=(Request)item.data;
			if(!user.DeleteRequest(r))
				MessageBox.Show("Có lỗi xảy ra. Yêu cầu tạm thời không thể thực hiện!",  AlertType.ERROR);
			else
				MessageBox.Show("Đã từ chối yêu cầu tham gia nhóm!",  AlertType.INFO);
		}
		else if(cmd==cmdViewTopic)
		{
			commandAction(List.SELECT_COMMAND,disp);
		}
		
	}

	private void openUpdateGroupForm() {
		// TODO Auto-generated method stub
		UserItem item=(UserItem)frmGroup.getCurrentItem();
		Group g=(Group)item.data;
		if(!g.leader.userId.equals(user.userId))
		{
			MessageBox.Show("Không thể sửa nhóm không phải do bạn tạo!",  AlertType.ERROR);
			return;
		}
		g.GetDetails(display,frmGroup);
		frmUpdateGroup = new UserForm(g.groupName,g);
		txtGroupName.setString(g.groupName);
		txtDescription.setString(g.description);
		txtGroupRule.setString(g.rule);
		frmUpdateGroup.addTextField(txtGroupName);
		frmUpdateGroup.addTextField(txtDescription);
		frmUpdateGroup.addTextBox(txtGroupRule);
		frmUpdateGroup.addCommand(cmdConfirm);
		frmUpdateGroup.addCommand(cmdBack);
		frmUpdateGroup.setCommandListener(this.commandListener);
		screenHistory.show(frmUpdateGroup);
	}

	private void openJoinRequestDetail(Request request) {
		// TODO Auto-generated method stub
		
		frmJoinRequestDetail.addMenu(cmdConfirm);
		frmJoinRequestDetail.addMenu(cmdReject);
		frmJoinRequestDetail.setCommandListener(this.commandListener);
		screenHistory.show(frmJoinRequestDetail);
	}

	private void openCreateTopicForm() {
		// TODO Auto-generated method stub
		UserItem item=(UserItem)frmGroup.getCurrentItem();
		Group g=(Group)item.data;
		frmCreateTopic=new UserForm("Viết bài mới",g);
		frmCreateTopic.addTextField(txtTopicTitle);
		frmCreateTopic.addTextBox(txtTopicContent);
		frmCreateTopic.addCommand(cmdConfirm);
		frmCreateTopic.addCommand(cmdBack);
		frmCreateTopic.setCommandListener(this.commandListener);
		screenHistory.show(frmCreateTopic);
	}

	private void openCreateGroupForm() {
		// TODO Auto-generated method stub
		frmCreateGroup = new UserForm("Tạo nhóm mới",null);
		txtGroupName.setString("");
		frmCreateGroup.addTextField(txtGroupName);
		frmCreateGroup.addMenu(cmdConfirm);
		frmCreateGroup.addMenu(cmdBack);
		frmCreateGroup.setCommandListener(this.commandListener);
		screenHistory.show(frmCreateGroup);
	}

	/**
	 * Handles commands for the main menu
	 * @return true when a command was handled
	 */
	private boolean handleCommandMainMenu()
	{
		int selectedItem = screenMainMenu.getSelectedIndex();
		/*		list.addEntry("Tin mới");
		list.addEntry("Nhóm");
		list.addEntry("Yêu cầu");
		list.addEntry("Tìm kiếm");
		list.addEntry("Hộp thư");
		list.addEntry("Cá nhân");
		list.addEntry("Cài đặt");*/
		switch(selectedItem)
		{
		case 0:
			ArrayList newTopics=user.GetNewTopics();
			openNewTopicForm(newTopics);
			break;
		case 1:
			ArrayList group=user.GetMyGroups();
			openGroupForm(group);
			break;
		case 2:
			ArrayList requests=user.GetJoinRequests();
			openJoinRequestsForm(requests);
			break;
		case 3:
			openSearchForm();
			break;
		case 4:
			MessageBox.Show("Chức năng đang xây dựng", AlertType.INFO);
			break;
		case 5:
			openProfileForm();
			break;
		case 6:
			openSettingForm();
		}
		return false;
	}

	private void openSettingForm() {
		// TODO Auto-generated method stub
		
	}

	

	private void openSearchForm() {
		// TODO Auto-generated method stub
		frmSearch=new UserForm("Tìm kiếm",null);
		
		frmSearch.addTextField(txtSearch);
		frmSearch.addMenu(cmdConfirm);
		frmSearch.addMenu(cmdBack);
		screenHistory.show(frmSearch);
		frmSearch.setCommandListener(this.commandListener);
	}

	private void openJoinRequestsForm(ArrayList requests) {
		// TODO Auto-generated method stub
		frmJoinRequest=new UserList("Danh sách yêu cầu");
		for(int i=0;i<requests.size();i++)
		{
			Request r = (Request)requests.get(i);
			frmJoinRequest.addEntry(new UserItem(r.user.username+ " - " +r.group.groupName,r),"request");
		}
		
		frmJoinRequest.addCommand(cmdConfirm);
		frmJoinRequest.addCommand(cmdReject);
		frmJoinRequest.addCommand(cmdBack);
		frmJoinRequest.setCommandListener(this.commandListener);
		screenHistory.show(frmJoinRequest);
	}

	private void openNewTopicForm(ArrayList topics) {
		// TODO Auto-generated method stub
		frmNewTopic = new UserList("Bài viết mới");
		
		for(int i=0;i<topics.size();i++)
		{
			TopicGroup t=(TopicGroup)topics.get(i);
			UserItem topic=new UserItem(t.topic.title,t);
			frmNewTopic.addEntry(topic,"topic");
		}
		
		frmNewTopic.addCommand(cmdViewTopic);
		frmNewTopic.addCommand(cmdBack);
		frmNewTopic.setCommandListener(this.commandListener);
		screenHistory.show(frmNewTopic);
	}
	private void openTopicDetailForm(TopicGroup t)
	{
		
		frmTopicDetail = new UserForm(t.topic.title,t);
		String body="";
		body+="Chia sẻ bởi: "+t.shareUser.username+"\n"+
				"Ngày chia sẻ: "+t.shareDate+"\n"+
				t.topic.content+"\n"+
				t.commentsCount+" bình luận\n";
		frmTopicDetail.append(new StringItem("Đăng bởi: "+t.topic.author.username, body));
		frmTopicDetail.addCommand(cmdBack);
		frmTopicDetail.setCommandListener(this.commandListener);
		screenHistory.show(frmTopicDetail);
	}
}
