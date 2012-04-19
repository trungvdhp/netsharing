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

//import util.UtilString;
import view.*;
import model.*;
import app.App;


import de.enough.polish.event.ThreadedCommandListener;
import de.enough.polish.io.RmsStorage;
import de.enough.polish.ui.Alert;
import de.enough.polish.ui.ChoiceTextField;
//import de.enough.polish.ui.DateField;
import de.enough.polish.ui.AlertType;
import de.enough.polish.ui.ChoiceGroup;
import de.enough.polish.ui.Command;
import de.enough.polish.ui.CommandListener;
import de.enough.polish.ui.Display;
import de.enough.polish.ui.ListItem;
//import de.enough.polish.ui.Gauge;
import de.enough.polish.ui.List;
//import de.enough.polish.ui.ScreenInfo;
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
	public static  Configuration configuration;
	private RmsStorage storage;
	
	private TextField txtUsername = new TextField("Tên đăng nhập: ","", 50,TextField.ANY);
	private TextField txtPassword = new TextField("Mật khẩu: ","",32, TextField.PASSWORD);
	private TextField txtOldPassword = new TextField("Mật khẩu cũ: ","",32, TextField.PASSWORD);
	private TextField txtNewPassword = new TextField("Mật khẩu mới: ","",32, TextField.PASSWORD);
	private TextField txtConfirm = new TextField("Xác nhận mật khẩu: ","",32, TextField.PASSWORD);
	private TextField txtFirstName = new TextField("Họ đệm: ","", 32,TextField.ANY);
	private TextField txtLastName = new TextField("Tên: ","", 32,TextField.ANY);
	private TextField txtAddress = new TextField("Địa chỉ: ","", 150,TextField.ANY);
	private TextField txtPhone = new TextField("Điện thoại: ","", 20,TextField.PHONENUMBER);
	private TextField txtPageSize = new TextField("Số bản ghi / 1 trang: ","", 2,TextField.PHONENUMBER);
	private TextField txtCreateUsername = new TextField("Người tạo: ","", 50,TextField.ANY);
	//private TextField txtShareUsername = new TextField("Người chia sẻ: ","", 50,TextField.ANY);
	
	private TextField txtKeyword = new TextField("Từ khóa: ","", 50,TextField.ANY);
	private TextField txtTitle = new TextField("Tiêu đề: ","", 100,TextField.ANY);
	private TextField txtContent = new TextField("Nội dung: ","", 512,TextField.ANY);
	private TextField txtGroupName = new TextField("Tên nhóm: ","", 50,TextField.ANY);
	private TextField txtDescription = new TextField("Mô tả: ","", 150,TextField.ANY);
	private TextField txtGroupRule = new TextField("Quy tắc: ","", 300,TextField.ANY);
	
	private StringItem strTopicCreateDate = new StringItem("","");
	private StringItem strGroupCreateDate = new StringItem("","");
	private StringItem strCreateDate = new StringItem("","");
	
	private StringItem strFirstName = new StringItem("Họ đệm: ","");
	private StringItem strLastName = new StringItem("Tên: ","");
	private StringItem strEmail = new StringItem("Email: ","");
	private StringItem strGender = new StringItem("","");
	private StringItem strPhone = new StringItem("Điện thoại: ","");
	private StringItem strAddress = new StringItem("Địa chỉ: ","");
	private StringItem strTopicsCount = new StringItem("","");
	private StringItem strMembersCount = new StringItem("","");
	
	private StringItem strGroupLeader = new StringItem("","");
	private StringItem strGroupName = new StringItem("","");
	private StringItem strDescription = new StringItem("Mô tả: ","");
	private StringItem strRule = new StringItem("Quy tắc: ","");
	
	private StringItem strShareUser = new StringItem("","");
	private StringItem strAuthor = new StringItem("","");
	private StringItem strShareDate = new StringItem("","");
	private StringItem strTopicContent = new StringItem("Nội dung: ","");
	//private StringItem strCommentsCount = new StringItem("","");
	//private StringItem strSharedGroupsCount = new StringItem("","");
	//private StringItem strNonSharedGroupsCount = new StringItem("","");
	
	private CommandListener commandListener;
	
	private ChoiceGroup cgRemember;
	private ChoiceGroup cgGender;
	private ChoiceGroup cgNonSharedGroup;
	private ChoiceGroup cgSearchType;
	
	private ChoiceTextField email;
	//private DateField dateBirthday = new DateField("Ngày sinh: ", DateField.DATE);
	
	private ListItem lstComment;
	private ListItem lstSharedGroup;
	
	private Command cmdLogin = new Command("Đăng nhập",Command.SCREEN,1);
	private Command cmdLogout = new Command("Đăng xuất",Command.SCREEN,1);
	private Command cmdRegister = new Command("Đăng ký", Command.SCREEN,1);
	//private Command cmdViewUserProfile=new Command("Thông tin tài khoản",Command.OK,1);
	private Command cmdChangePassword = new Command("Đổi mật khẩu", Command.SCREEN,1);
	private Command cmdExit = new Command(Locale.get("cmd.exit"), Command.EXIT, 10);
	private Command cmdBack = new Command(Locale.get("cmd.back"), Command.BACK, 2);
	private Command cmdCreateGroup=new Command("Tạo nhóm mới",Command.SCREEN,1);
	private Command cmdViewGroupInfo=new Command("Thông tin nhóm",Command.OK,1);
	private Command cmdViewCreateTopic=new Command("Tạo bài mới",Command.SCREEN,1);
	private Command cmdDeleteTopic=new Command("Xóa bài viết",Command.SCREEN,1);
	private Command cmdViewUpdateTopic=new Command("Sửa bài viết",Command.SCREEN,1);
	private Command cmdViewShareTopic=new Command("Chia sẻ bài viết",Command.SCREEN,1);
	//private Command cmdDeleteGroup=new Command("Xóa nhóm",Command.SCREEN,1);
	//private Command cmdViewTopic=new Command("Xem bài viết",Command.OK,1);
	//private Command cmdViewSharedTopic=new Command("Bài viết đã chia sẻ",Command.SCREEN,1);
	private Command cmdConfirm=new Command("Xác nhận",Command.SCREEN,1);
	private Command cmdReject=new Command("Từ chối",Command.SCREEN,1);
	//private Command cmdConfirmAll=new Command("Xác nhận hết",Command.SCREEN,1);
	//private Command cmdRejectAll=new Command("Từ chối hết",Command.SCREEN,1);
	private Command cmdViewGroupRequest=new Command("Danh sách yêu cầu",Command.SCREEN,1);
	private Command cmdViewGroupMember=new Command("Danh sách thành viên",Command.SCREEN,1);
	private Command cmdViewGroup=new Command("Nhóm bạn là thành viên",Command.SCREEN,1);
	private Command cmdViewMyGroup=new Command("Nhóm bạn tạo",Command.SCREEN,1);
	private Command cmdViewJoinGroup=new Command("Nhóm bạn tham gia",Command.SCREEN,1);
	private Command cmdViewUpdateGroup=new Command("Sửa nhóm",Command.SCREEN,1);
	private Command cmdUpdate=new Command("Cập nhật",Command.SCREEN,1);
	private Command cmdDetail=new Command("Chi tiết",Command.SCREEN,1);
	private Command cmdGroupDetail=new Command("Chi tiết nhóm",Command.SCREEN,1);
	//private Command cmdRefresh = new Command("Làm mới", Command.SCREEN, 1);
	//private Command cmdUpdateGroup =new Command("Cập nhật nhóm",Command.SCREEN,1);
	private Command cmdSendRequest =new Command("Tham gia",Command.OK,1);
	private Command cmdViewCreateComment=new Command("Bình luận",Command.SCREEN,1);
	private Command cmdViewUpdateComment=new Command("Sửa bình luận",Command.SCREEN,1);
	private Command cmdDeleteComment=new Command("Xóa bình luận",Command.SCREEN,1);
	
	private Command cmdViewNewTopic=new Command("Bài viết mới",Command.SCREEN,1);
	private Command cmdViewSharedMyTopic=new Command("Bài bạn tạo và chia sẻ",Command.SCREEN,1);
	private Command cmdViewSharedOthersTopic=new Command("Bài người khác chia sẻ",Command.SCREEN,1);
	private Command cmdViewNonSharedMyTopic=new Command("Bài bạn chưa chia sẻ",Command.SCREEN,1);
	
	private Command cmdViewSearch=new Command("Tìm kiếm",Command.SCREEN,1);
	
	private Command cmdNext=new Command("Trang tiếp",Command.SCREEN,1);
	private Command cmdPrev=new Command("Trang trước",Command.SCREEN,1);
	
	private Command cmdSave = new Command("Lưu thay đổi", Command.SCREEN,1);
	private Command cmdDeleteMember = new Command("Hủy tư cách", Command.SCREEN,1);
	
	private User user;
	private Group group;
	private Topic topic;
	private MainMenuList screenMainMenu;
	private SimpleScreenHistory screenHistory;
	
	private UserForm frmLogin;
	private UserForm frmRegister;
	private UserForm frmUpdateProfile;
	private UserForm frmMemberProfile;
	private UserForm frmChangePassword;
	private UserForm frmGroupInfo;
	private UserForm frmCreateGroup;
	private UserForm frmUpdateGroup;
	private UserForm frmTopicDetail;
	private UserForm frmCreateTopic;
	private UserForm frmCreateAndShareTopic;
	private UserForm frmUpdateTopic;
	private UserForm frmShareTopic;
	private UserForm frmCreateComment;
	private UserForm frmUpdateComment;
	private UserForm frmSearch;
	private UserForm frmSearchGroup;
	private UserForm frmSearchTopic;
	private UserForm frmSearchMember;
	private UserForm frmSetting;
	//private UserForm frmJoinRequestDetail;
	//private Alert frmConfirmDelGroup;
	private Alert frmConfirmDelComment;
	private Alert frmConfirmDelTopic;
	private Alert frmConfirmDelMember;
	
	private UserList frmGroup;
	private UserList frmSearchGroupResult;
	private UserList frmGroupRequest;
	private UserList frmGroupMember;
	private UserList frmSearchMemberResult;
	private UserList frmTopic;
	private UserList frmMemberRequest;

	//Mở form tìm kiếm chung
	private void openSearchForm() {
		// TODO Auto-generated method stub
		frmSearch=new UserForm("Tìm kiếm",null);
		//#style checkBoxItem
		cgSearchType = new ChoiceGroup("Chọn loại tìm kiếm: ", ChoiceGroup.EXCLUSIVE);
		cgSearchType.append(" Nhóm bạn chưa tham gia", null);
		cgSearchType.append(" Bài viết nhóm",null);
		cgSearchType.append(" Thành viên",null);
		frmSearch.addCheckBox(cgSearchType);
		cgSearchType.setSelectedIndex(0, true);
		frmSearch.addTextField(txtKeyword);
		txtKeyword.setString("");
		frmSearch.addMenu(cmdConfirm);
		frmSearch.addMenu(cmdBack);
		screenHistory.show(frmSearch);
		frmSearch.setCommandListener(this.commandListener);
	}
	//Mở form tìm kiếm thành viên
	private void openSearchMemberForm() {
		// TODO Auto-generated method stub
		frmSearchMember=new UserForm("Tìm kiếm thành viên",null);
		frmSearchMember.addTextField(txtKeyword);
		frmSearchMember.addMenu(cmdConfirm);
		frmSearchMember.addMenu(cmdBack);
		screenHistory.show(frmSearchMember);
		frmSearchMember.setCommandListener(this.commandListener);
	}
	//Mở form tìm kiếm nhóm
	private void openSearchGroupFrom()
	{
		frmSearchGroup=new UserForm("Tìm kiếm nhóm");
		frmSearchGroup.addTextField(txtGroupName);
		txtGroupName.setString(group.groupName);
		frmSearchGroup.addTextField(txtCreateUsername);
		txtCreateUsername.setString(group.leader.username);
		frmSearchGroup.addMenu(cmdConfirm);
		frmSearchGroup.addMenu(cmdBack);
		screenHistory.show(frmSearchGroup);
		frmSearchGroup.setCommandListener(this.commandListener);
	}
	//Mở form tìm kiếm bài viết
	private void openSearchTopicForm()
	{
		// TODO Auto-generated method stub
		frmSearchTopic=new UserForm("Tìm kiếm bài viết");
		frmSearchTopic.addTextField(txtTitle);
		txtTitle.setString(topic.title);
		frmSearchTopic.addTextBox(txtContent);
		txtContent.setString(topic.content);
		frmSearchTopic.addTextField(txtCreateUsername);
		txtCreateUsername.setString(topic.author.username);
		frmSearchTopic.addMenu(cmdConfirm);
		frmSearchTopic.addMenu(cmdBack);
		screenHistory.show(frmSearchTopic);
		frmSearchTopic.setCommandListener(this.commandListener);
	}
	//Mở form cài đặt
	private void openSettingForm()
	{
		frmSetting = new UserForm("Cài đặt",null);
		frmSetting.addTextField(txtPageSize);
		txtPageSize.setString(configuration.get("pageSize"));
		frmSetting.addMenu(cmdSave);
		frmSetting.addMenu(cmdBack);
		frmSetting.setCommandListener(this.commandListener);
		screenHistory.show(frmSetting);
	}
	//Mở form cập nhật thông tin tài khoản
	private void openUpdateProfileForm() {
		// TODO Auto-generated method stub
		if(user.GetInfo())
		{
			frmUpdateProfile=new UserForm("Tài khoản: " + user.username,null);
			
			frmUpdateProfile.addTextField(txtFirstName);
			frmUpdateProfile.addTextField(txtLastName);
			//frmProfile.addDateField(dateBirthday);
			initEmailField();
			frmUpdateProfile.addEmailField(email);
			//#style checkBoxItem
			cgGender = new ChoiceGroup("Giới tính:", ChoiceGroup.EXCLUSIVE);
			cgGender.append(" Nam", null);
			cgGender.append(" Nữ", null);
			
			frmUpdateProfile.addCheckBox(cgGender);
			frmUpdateProfile.addTextField(txtPhone);
			frmUpdateProfile.addTextBox(txtAddress);
			frmUpdateProfile.addStringItemField(strCreateDate);
			
			txtFirstName.setString(user.firstName);
			txtLastName.setString(user.lastName);
			strCreateDate.setText("Ngày cập nhật: " + user.createDate);
			email.setString(user.email);
			if(user.gender.equals("0"))
			{
				cgGender.setSelectedIndex(0,true);
			}
			else
			{
				cgGender.setSelectedIndex(1,true);
			}
			txtPhone.setString(user.phone);
			txtAddress.setString(user.address);
			
			frmUpdateProfile.addMenu(cmdUpdate);
			frmUpdateProfile.addMenu(cmdChangePassword);
			frmUpdateProfile.addMenu(cmdLogout);
			frmUpdateProfile.addMenu(cmdBack);
			frmUpdateProfile.setCommandListener(this.commandListener);
			screenHistory.show(frmUpdateProfile);
		}
		else 
			ShowError();
	}
	//Thông báo lỗi chung
	private void ShowError()
	{
		MessageBox.Show("Có lỗi xảy ra. Yêu cầu tạm thời không thể thực hiện!",  AlertType.ERROR);
	}
	//Mở form thông tin thành viên
	private void openMemberProfileForm(User u) {
		// TODO Auto-generated method stub
		if(u.GetInfo())
		{
			frmMemberProfile=new UserForm(u.username,null);
			
			frmMemberProfile.addStringItemField(strFirstName);
			frmMemberProfile.addStringItemField(strLastName);
			//frmProfile.addDateField(dateBirthday);
			frmMemberProfile.addStringItemBox(strEmail);
			String gender = u.gender.equals("0")?"Nam":"Nữ";
			frmMemberProfile.addStringItemField(strGender);
			frmMemberProfile.addStringItemField(strPhone);
			frmMemberProfile.addStringItemBox(strAddress);
			frmMemberProfile.addStringItemField(strCreateDate);
			
			strFirstName.setText(u.firstName);
			strLastName.setText(u.lastName);
			strEmail.setText(u.email);
			strGender.setText("Giới tính: " + gender);
			strPhone.setText(u.phone);
			strAddress.setText(u.address);
			strCreateDate.setText("Ngày cập nhật: " + u.createDate);
			frmMemberProfile.addMenu(cmdBack);
			frmMemberProfile.setCommandListener(this.commandListener);
			screenHistory.show(frmMemberProfile);
		}
		else ShowError();
	}
	//Khởi tạo hộp nhập địa chỉ email của tài khoản
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
	//Mở form thay đổi mật khẩu
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
	//Mở form đăng nhập
	private void openLoginForm()
	{
		frmLogin=new UserForm("Đăng nhập",null);
		frmLogin.addTextField(txtUsername);
		frmLogin.addTextField(txtPassword);
		//#style checkBoxItem
		cgRemember = new ChoiceGroup("",ChoiceGroup.MULTIPLE);
		cgRemember.append(" Ghi nhớ mật khẩu", null);
		frmLogin.addCheckBox(cgRemember);
		frmLogin.addMenu(cmdLogin);
		frmLogin.addMenu(cmdRegister);
		frmLogin.addMenu(cmdExit);
		frmLogin.setCommandListener(this.commandListener);
		if(configuration.get("remember").equals("true"))
		{
			txtUsername.setString(configuration.get("username"));
			txtPassword.setString(configuration.get("password"));
			cgRemember.setSelectedIndex(0, true);
		}
		screenHistory.show(frmLogin);
	}
	//Mở form đăng ký
	private void openRegisterForm()
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
	//Mở form sửa thông tin nhóm
	private void openUpdateGroupForm(Group g) {
		// TODO Auto-generated method stub
		if(!g.leader.userId.equals(user.userId))
		{
			MessageBox.Show("Không thể sửa nhóm không phải do bạn tạo!",  AlertType.ERROR);
			return;
		}
		if(g.GetInfo())
		{
			frmUpdateGroup = new UserForm(g.groupName,g);
			txtGroupName.setString(g.groupName);
			txtDescription.setString(g.description);
			txtGroupRule.setString(g.rule);
			//strTopicsCount.setText(g.topicsCount);
			//strMembersCount.setText(g.membersCount);
			frmUpdateGroup.addTextField(txtGroupName);
			frmUpdateGroup.addTextBox(txtDescription);
			frmUpdateGroup.addTextBox(txtGroupRule);
			//frmUpdateGroup.addStringItemField(strTopicsCount);
			//frmUpdateGroup.addStringItemField(strMembersCount);
			frmUpdateGroup.addMenu(cmdConfirm);
			frmUpdateGroup.addMenu(cmdBack);
			frmUpdateGroup.setCommandListener(this.commandListener);
			screenHistory.show(frmUpdateGroup);
		}
		else ShowError();
	}
	//Mở form tạo nhóm mới
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
	//Mở form thông tin nhóm
	private void openGroupInfoForm(Group g) {
		// TODO Auto-generated method stub
		if(g.GetInfo())
		{
			frmGroupInfo=new UserForm(g.groupName,null);
			frmGroupInfo.addStringItemField(strGroupLeader);
			frmGroupInfo.addStringItemField(strGroupName);
			frmGroupInfo.addStringItemBox(strDescription);
			frmGroupInfo.addStringItemBox(strRule);
			frmGroupInfo.addStringItemField(strGroupCreateDate);
			frmGroupInfo.addStringItemField(strTopicsCount);
			frmGroupInfo.addStringItemField(strMembersCount);
			strGroupLeader.setText("Nhóm trưởng: "  + g.leader.username);
			strGroupName.setText("Tên nhóm: " + g.groupName);
			strDescription.setText(g.description);
			strRule.setText(g.rule);
			strTopicsCount.setText(g.topicsCount + " bài viết");
			strMembersCount.setText(g.membersCount + " thành viên");
			strGroupCreateDate.setText("Ngày cập nhật: " + g.createDate);
			frmGroupInfo.addMenu(cmdBack);
			frmGroupInfo.setCommandListener(this.commandListener);
			screenHistory.show(frmGroupInfo);
		}
		else ShowError();
	}
	//Mở form sửa bài viết
	private void openUpdateTopicForm(Topic t) {
		// TODO Auto-generated method stub
		if(!t.author.userId.equals(user.userId))
		{
			MessageBox.Show("Không thể sửa nội dung bài viết không phải do bạn tạo!",  AlertType.ERROR);
			return;
		}
		frmUpdateTopic = new UserForm(t.title,t);
		txtContent.setString(t.content);
		txtTitle.setString(t.title);
		frmUpdateTopic.addTextField(txtTitle);
		frmUpdateTopic.addTextBox(txtContent);
		frmUpdateTopic.addMenu(cmdConfirm);
		frmUpdateTopic.addMenu(cmdBack);
		frmUpdateTopic.setCommandListener(this.commandListener);
		screenHistory.show(frmUpdateTopic);
	}
	//Hiển thị trang nhóm đã chia sẻ bài viết tiếp theo
	private void nextSharedGroupPage(Topic t)
	{
		ArrayList groups = user.GetSharedGroups(t, frmTopicDetail.pageId[0]);
		if(groups != null && groups.size()>0)
		{
			lstSharedGroup.removeAll();
			for(int i=0; i<groups.size(); ++i)
			{
				Group g = (Group)groups.get(i);
				//#style groupItem
				lstSharedGroup.append(new UserItem(" " + g.groupName, g));
			}
			if(configuration.get("pageSize").equals("" + groups.size()))
				frmTopicDetail.addMenu(cmdNext);
			else
				frmTopicDetail.removeCommand(cmdNext);
			if(frmTopicDetail.pageId[0]==1)
				frmTopicDetail.addMenu(cmdPrev);
			if(frmTopicDetail.pageId[0]==0)
				frmTopicDetail.removeCommand(cmdPrev);
			frmTopicDetail.pageId[0]++;
		}
		else
		{
			frmTopicDetail.removeCommand(cmdNext);
		}
	}
	//Hiển thị trang nhóm đã chia sẻ bài viết trước đó
	private void prevSharedGroupPage(Topic t)
	{
		frmTopicDetail.pageId[0] -= 2;
		nextSharedGroupPage(t);
	}
	//Mở form bài viết chi tiết
	private void openTopicDetailForm(Topic t) {
		// TODO Auto-generated method stub
		frmTopicDetail = new UserForm(t.title,t,1);
		frmTopicDetail.addStringItemField(strAuthor);
		frmTopicDetail.addStringItemField(strTopicCreateDate);
		frmTopicDetail.addStringItemBox(strTopicContent);
		strAuthor.setText("Tạo bởi: " + t.author.username);
		strTopicCreateDate.setText("Ngày cập nhật: " + t.createDate);
		strTopicContent.setText(t.content);
		lstSharedGroup = new ListItem("Nhóm bạn đã chia sẻ bài viết:");
		frmTopicDetail.addListItem(lstSharedGroup);
		frmTopicDetail.addMenu(cmdConfirm);
		frmTopicDetail.addMenu(cmdViewGroupInfo);
		frmTopicDetail.addMenu(cmdViewGroupMember);
		frmTopicDetail.addMenu(cmdBack);
		nextSharedGroupPage(t);
		frmTopicDetail.setCommandListener(this.commandListener);
		screenHistory.show(frmTopicDetail);
	}
	//Mở form chia sẻ bài viết
	private void openTopicShareForm(Topic t) {
		// TODO Auto-generated method stub
		frmShareTopic = new UserForm("Chia sẻ: " + t.title,t);
		frmShareTopic.addStringItemField(strAuthor);
		frmShareTopic.addStringItemField(strTopicCreateDate);
		frmShareTopic.addStringItemBox(strTopicContent);
		strAuthor.setText("Tạo bởi: " + t.author.username);
		strTopicCreateDate.setText("Ngày cập nhật: " + t.createDate);
		strTopicContent.setText(t.content);
		cgNonSharedGroup = new ChoiceGroup("Nhóm để bạn chia sẻ bài viết: ", ChoiceGroup.MULTIPLE);
		frmShareTopic.addCheckBox(cgNonSharedGroup);
		frmShareTopic.addMenu(cmdConfirm);
		frmShareTopic.addMenu(cmdViewGroupInfo);
		frmShareTopic.addMenu(cmdViewGroupMember);
		frmShareTopic.addMenu(cmdBack);
		group = new Group("","",new User("",""));
		nextNonSharedGroupPageForShare(t);
		frmShareTopic.setCommandListener(this.commandListener);
		screenHistory.show(frmShareTopic);
	}
	//Hiển thị trang nhóm chưa chia sẻ bài viết tiếp theo
	private void nextNonSharedGroupPageForShare(Topic t)
	{
		ArrayList groups = user.GetNonSharedGroups(t, frmShareTopic.pageId[0]);
		if(groups != null && groups.size()>0)
		{
			cgNonSharedGroup.deleteAll();
			for(int i=0; i<groups.size(); ++i)
			{
				Group g = (Group)groups.get(i);
				//#style checkBoxItem
				cgNonSharedGroup.append(new UserItem(" " + g.groupName, List.MULTIPLE, g));
			}
			if(configuration.get("pageSize").equals("" + groups.size()))
				frmShareTopic.addMenu(cmdNext);
			else
				frmShareTopic.removeCommand(cmdNext);
			if(frmShareTopic.pageId[0]==1)
				frmShareTopic.addMenu(cmdPrev);
			if(frmShareTopic.pageId[0]==0)
				frmShareTopic.removeCommand(cmdPrev);
			frmShareTopic.pageId[0]++;
		}
		else
		{
			frmShareTopic.removeCommand(cmdNext);
		}
	}
	//Hiển thị trang nhóm chưa chia sẻ bài viết trước đó
	private void prevNonSharedGroupPageForShare(Topic t)
	{
		frmShareTopic.pageId[0] -= 2;
		nextNonSharedGroupPageForShare(t);
	}
	//Mở form tạo bài viết và chia sẻ cho nhóm đã chọn
	private void openCreateGroupTopicForm(Group g) {
		// TODO Auto-generated method stub
		frmCreateTopic=new UserForm("Viết bài mới",g);
		frmCreateTopic.addTextField(txtTitle);
		frmCreateTopic.addTextBox(txtContent);
		txtTitle.setString("");
		txtContent.setString("");
		frmCreateTopic.addMenu(cmdConfirm);
		frmCreateTopic.addMenu(cmdBack);
		frmCreateTopic.setCommandListener(this.commandListener);
		screenHistory.show(frmCreateTopic);
	}
	//Hiển thị trang nhóm chưa chia sẻ bài viết tiếp theo cho tạo và chia sẻ bài viết
	private void nextNonSharedGroupPageForCreate()
	{
		ArrayList groups = user.GetGroups(frmCreateAndShareTopic.pageId[0], group);
		if(groups != null && groups.size()>0)
		{
			cgNonSharedGroup.deleteAll();
			for(int i=0; i<groups.size(); ++i)
			{
				Group g = (Group)groups.get(i);
				//#style checkBoxItem
				cgNonSharedGroup.append(new UserItem(" " + g.groupName, List.MULTIPLE, g));
			}
			if(configuration.get("pageSize").equals("" + groups.size()))
				frmCreateAndShareTopic.addMenu(cmdNext);
			else
				frmCreateAndShareTopic.removeCommand(cmdNext);
			if(frmCreateAndShareTopic.pageId[0]==1)
				frmCreateAndShareTopic.addMenu(cmdPrev);
			if(frmCreateAndShareTopic.pageId[0]==0)
				frmCreateAndShareTopic.removeCommand(cmdPrev);
			frmCreateAndShareTopic.pageId[0]++;
		}
		else
		{
			frmCreateAndShareTopic.removeCommand(cmdNext);
		}
	}
	//Hiển thị trang nhóm chưa chia sẻ bài viết trước đó cho tạo và chia sẻ bài viết
	private void prevNonSharedGroupPageForCreate()
	{
		frmCreateAndShareTopic.pageId[0] -= 2;
		nextNonSharedGroupPageForCreate();
	}
	//Mở form tạo và chia sẻ bài viết cho các nhóm được chọn
	private void openCreateAndShareTopicForm() {
		// TODO Auto-generated method stub
		frmCreateAndShareTopic=new UserForm("Viết bài mới",null);
		frmCreateAndShareTopic.addTextField(txtTitle);
		frmCreateAndShareTopic.addTextBox(txtContent);
		txtTitle.setString("");
		txtContent.setString("");
		cgNonSharedGroup = new ChoiceGroup("Nhóm để bạn chia sẻ bài viết: ", ChoiceGroup.MULTIPLE);
		frmCreateAndShareTopic.addCheckBox(cgNonSharedGroup);
		frmCreateAndShareTopic.addMenu(cmdConfirm);
		frmCreateAndShareTopic.addMenu(cmdViewGroupInfo);
		frmCreateAndShareTopic.addMenu(cmdViewGroupMember);
		frmCreateAndShareTopic.addMenu(cmdBack);
		group = new Group("","",new User("",""));
		nextNonSharedGroupPageForCreate();
		frmCreateAndShareTopic.setCommandListener(this.commandListener);
		screenHistory.show(frmCreateAndShareTopic);
	}
	//Hiển thị trang các bình luận tiếp theo
	private void nextCommentPage(GroupTopic t)
	{
		if(t.GetDetails(frmTopicDetail.pageId[0]) && t.comments.size()>0)
		{
			lstComment.removeAll();
			for(int i=0; i<t.comments.size(); ++i)
			{
				Comment c = (Comment)t.comments.get(i);
				//#style commentItem
				lstComment.append(new UserItem(c.user.username + " : " + c.content + "\n" + c.createDate, c));
			}
			if(configuration.get("pageSize").equals("" + lstComment.size()))
				frmTopicDetail.addMenu(cmdNext);
			else
				frmTopicDetail.removeCommand(cmdNext);
			if(frmTopicDetail.pageId[0]==1)
				frmTopicDetail.addMenu(cmdPrev);
			if(frmTopicDetail.pageId[0]==0)
				frmTopicDetail.removeCommand(cmdPrev);
			frmTopicDetail.pageId[0]++;
		}
		else
		{
			frmTopicDetail.removeCommand(cmdNext);
		}
	}
	//Hiển thị trang các bình luận trước đó
	private void prevCommentPage(GroupTopic t)
	{
		frmTopicDetail.pageId[0] -= 2;
		nextCommentPage(t);
	}
	//Mở form chi tiết bài viết nhóm
	private void openTopicDetailForm(GroupTopic t)
	{
		frmTopicDetail = new UserForm(t.topic.title,t,0);
		frmTopicDetail.addStringItemField(strShareUser);
		frmTopicDetail.addStringItemField(strAuthor);
		frmTopicDetail.addStringItemField(strShareDate);
		frmTopicDetail.addStringItemBox(strTopicContent);
		lstComment = new ListItem("Các bình luận:");
		frmTopicDetail.addListItem(lstComment);
		strShareUser.setText("Chia sẻ bởi: " + t.shareUser.username);
		strAuthor.setText("Tạo bởi: " + t.topic.author.username);
		strShareDate.setText("Ngày chia sẻ: " + t.shareDate);
		strTopicContent.setText(t.topic.content);
		frmTopicDetail.addMenu(cmdViewCreateComment);
		frmTopicDetail.addMenu(cmdViewUpdateComment);
		frmTopicDetail.addMenu(cmdDeleteComment);
		frmTopicDetail.addMenu(cmdBack);
		nextCommentPage(t);
		frmTopicDetail.setCommandListener(this.commandListener);
		screenHistory.show(frmTopicDetail);
	}
	//Mở form tạo bình luận
	private void openCreateCommentForm(GroupTopic t) {
		// TODO Auto-generated method stub
		frmCreateComment = new UserForm("Tạo bình luận",t);
		txtContent.setString("");
		frmCreateComment.addTextBox(txtContent);
		frmCreateComment.addMenu(cmdConfirm);
		frmCreateComment.addMenu(cmdBack);
		frmCreateComment.setCommandListener(this.commandListener);
		screenHistory.show(frmCreateComment);
	}
	//Mở form sửa bình luận
	private void openUpdateCommentForm(Comment c) {
		// TODO Auto-generated method stub
		if(!user.userId.equals(c.user.userId))
		{
			MessageBox.Show("Không thể sửa bình luận không phải do bạn tạo!",  AlertType.ERROR);
			return;
		}
		frmUpdateComment = new UserForm("Sửa bình luận",c);
		txtContent.setString(c.content);
		frmUpdateComment.addTextBox(txtContent);
		frmUpdateComment.addMenu(cmdConfirm);
		frmUpdateComment.addMenu(cmdBack);
		frmUpdateComment.setCommandListener(this.commandListener);
		screenHistory.show(frmUpdateComment);
	}
	
	//private void openSettingForm() {
		// TODO Auto-generated method stub
		
	//}
	//Hiển thị danh sách nhóm
	private void addGroupList(ArrayList groups)
	{
		if(groups != null && groups.size()>0)
		{
			frmGroup.removeAllEntry();
			for(int i=0;i<groups.size();i++)
			{
				Group group=(Group)groups.get(i);
				frmGroup.addEntry(new UserItem(group.groupName,group),"group");
			}
			if(configuration.get("pageSize").equals("" + groups.size()))
				frmGroup.addMenu(cmdNext);
			else
				frmGroup.removeCommand(cmdNext);
			if(frmGroup.pageId==1)
				frmGroup.addMenu(cmdPrev);
			if(frmGroup.pageId==0)
				frmGroup.removeCommand(cmdPrev);
			frmGroup.pageId++;
		}
		else
		{
			frmGroup.removeCommand(cmdNext);
		}
	}
	//Hiển thị trang danh sách nhóm bạn là thành viên tiếp theo
	private void nextGroupListPage()
	{
		ArrayList groups = user.GetGroups(frmGroup.pageId, group);
		addGroupList(groups);
	}
	//Hiển thị trang danh sách nhóm bạn là thành viên trước đó
	private void prevGroupListPage()
	{
		frmGroup.pageId -= 2;
		nextGroupListPage();
	}
	private void initGroupList()
	{
		frmGroup = new UserList("");
		frmGroup.addMenu(cmdGroupDetail);
		UiAccess.addSubCommand(cmdViewGroupInfo, cmdGroupDetail,frmGroup);
		UiAccess.addSubCommand(cmdViewGroupMember, cmdGroupDetail,frmGroup);
		UiAccess.addSubCommand(cmdViewGroupRequest, cmdGroupDetail,frmGroup);
		frmGroup.addMenu(cmdCreateGroup);
		frmGroup.addMenu(cmdViewUpdateGroup);
		frmGroup.addMenu(cmdViewGroup);
		frmGroup.addMenu(cmdViewJoinGroup);
		frmGroup.addMenu(cmdViewMyGroup);
		frmGroup.addMenu(cmdViewSearch);
		frmGroup.addMenu(cmdBack);
		group = new Group("","",new User("",""));
		openGroupList();
		frmGroup.setCommandListener(this.commandListener);
		screenHistory.show(frmGroup);
	}
	//Mở form danh sách nhóm bạn là thành viên
	private void openGroupList()
	{
		frmGroup.removeAllEntry();
		frmGroup.setTitle("Nhóm bạn là thành viên");
		frmGroup.id = 0;
		frmGroup.pageId = 0;
		nextGroupListPage();
	}
	//Hiển thị trang danh sách nhóm bạn tạo tiếp theo
	private void nextMyGroupListPage()
	{
		ArrayList groups = user.GetMyGroups(frmGroup.pageId, group);
		addGroupList(groups);
	}
	//Hiển thị trang danh sách nhóm bạn tạo trước đó
	private void prevMyGroupListPage()
	{
		frmGroup.pageId -= 2;
		nextMyGroupListPage();
	}
	//Mở form danh sách nhóm bạn tạo
	private void openMyGroupList()
	{
		frmGroup.removeAllEntry();
		frmGroup.setTitle("Nhóm bạn tạo");
		frmGroup.id = 2;
		frmGroup.pageId = 0;
		nextMyGroupListPage();
	}
	//Hiển thị trang danh sách nhóm bạn tham gia tiếp theo
	private void nextJoinGroupListPage()
	{
		ArrayList groups = user.GetJoinGroups(frmGroup.pageId, group);
		addGroupList(groups);
	}
	//Hiển thị trang danh sách nhóm bạn tham gia trước đó
	private void prevJoinGroupListPage()
	{
		frmGroup.pageId -= 2;
		nextJoinGroupListPage();
	}
	//Mở form danh sách nhóm bạn tham gia
	private void openJoinGroupList()
	{
		frmGroup.removeAllEntry();
		frmGroup.setTitle("Nhóm bạn tham gia");
		frmGroup.id = 1;
		frmGroup.pageId = 0;
		nextJoinGroupListPage();
	}
	//Hiển thị trang các nhóm bạn chưa tham gia tiếp theo
	private void nextSearchGroupListPage()
	{
		ArrayList groups = user.SearchGroup(txtKeyword.getString(), frmSearchGroupResult.pageId);
		if(groups!=null && groups.size()>0)
		{
			frmSearchGroupResult.removeAllEntry();
			for(int i=0;i<groups.size();i++)
			{
				Group group=(Group)groups.get(i);
				frmSearchGroupResult.addEntry(new UserItem(group.groupName,group),"group");
			}
			if(configuration.get("pageSize").equals("" + groups.size()))
				frmSearchGroupResult.addMenu(cmdNext);
			else
				frmSearchGroupResult.removeCommand(cmdNext);
			if(frmSearchGroupResult.pageId==1)
				frmSearchGroupResult.addMenu(cmdPrev);
			if(frmSearchGroupResult.pageId==0)
				frmSearchGroupResult.removeCommand(cmdPrev);
			frmSearchGroupResult.pageId++;
		}
		else
		{
			frmSearchGroupResult.removeCommand(cmdNext);
		}
	}
	//Hiển thị trang các nhóm bạn chưa tham gia trước đó
	private void prevSearchGroupListPage()
	{
		frmSearchGroupResult.pageId -= 2;
		nextSearchGroupListPage();
	}
	private void openSearchGroupList()
	{
		frmSearchGroupResult=new UserList("Nhóm bạn chưa tham gia");
		frmSearchGroupResult.addMenu(cmdSendRequest);
		frmSearchGroupResult.addMenu(cmdBack);
		nextSearchGroupListPage();
		frmSearchGroupResult.setCommandListener(this.commandListener);
		screenHistory.show(frmSearchGroupResult);
	}
	//Hiển thị trang các thành viên tìm được tiếp theo
	private void nextSearchMemberListPage()
	{
		ArrayList members = user.SearchMembers(frmSearchMemberResult.pageId, txtKeyword.getString());
		if(members!=null && members.size()>0)
		{
			for(int i=0;i<members.size();i++)
			{
				User u=(User)members.get(i);
				frmSearchMemberResult.addEntry(new UserItem(u.username,u),"user");
			}
			if(configuration.get("pageSize").equals("" + (members.size() - 1)))
				frmSearchMemberResult.addMenu(cmdNext);
			else
				frmSearchMemberResult.removeCommand(cmdNext);
			if(frmSearchMemberResult.pageId==1)
				frmSearchMemberResult.addMenu(cmdPrev);
			if(frmSearchMemberResult.pageId==0)
				frmSearchMemberResult.removeCommand(cmdPrev);
			frmSearchMemberResult.pageId++;
		}
		else
		{
			frmSearchMemberResult.removeCommand(cmdNext);
		}
	}
	//Hiển thị trang các thành viên tìm được trước đó
	private void prevSearchMemberListPage()
	{
		frmSearchMemberResult.pageId -= 2;
		nextSearchMemberListPage();
	}
	//Mở form danh sách thành viên tìm kiếm được
	private void openSearchMemberList()
	{
		frmSearchMemberResult=new UserList("Kết quả tìm kiếm thành viên");
		frmSearchMemberResult.addMenu(cmdBack);
		nextSearchMemberListPage();
		frmSearchMemberResult.setCommandListener(this.commandListener);
		screenHistory.show(frmSearchMemberResult);
	}
	//Hiển thị trang danh sách thành viên nhóm tiếp theo
	private void nextGroupMemberListPage(Group group)
	{
		ArrayList members = group.GetMembers(frmGroupMember.pageId, txtKeyword.getString());
		User u=(User)members.get(0);
		if(members.size() > 1)
			frmGroupMember.removeAllEntry();
		if(frmGroupMember.size() == 0) 
			frmGroupMember.addEntry(new UserItem(u.username + " - Trưởng nhóm",u),"leader");
		if(members.size()==1)
		{
			frmGroupMember.removeCommand(cmdNext);
			return;
		}
		for(int i=1;i<members.size();i++)
		{
			u=(User)members.get(i);
			frmGroupMember.addEntry(new UserItem(u.username,u),"user");
		}
		if(configuration.get("pageSize").equals("" + (members.size() - 1)))
			frmGroupMember.addMenu(cmdNext);
		else
			frmGroupMember.removeCommand(cmdNext);
		if(frmGroupMember.pageId==1)
			frmGroupMember.addMenu(cmdPrev);
		if(frmGroupMember.pageId==0)
			frmGroupMember.removeCommand(cmdPrev);
		frmGroupMember.pageId++;
	}
	//Hiển thị trang danh sách thành viên nhóm trước đó
	private void prevGroupMemberListPage(Group group)
	{
		frmGroupMember.pageId -= 2;
		nextGroupMemberListPage(group);
	}
	//Khởi tạo form danh sách thành viên nhóm
	private void initGroupMemberList(Group group)
	{
		frmGroupMember=new UserList("Thành viên nhóm: " + group.groupName, group);
		frmGroupMember.addMenu(cmdViewSearch);
		frmGroupMember.addMenu(cmdDeleteMember);
		frmGroupMember.addMenu(cmdBack);
		txtKeyword.setString("");
		nextGroupMemberListPage(group);
		frmGroupMember.setCommandListener(this.commandListener);
		screenHistory.show(frmGroupMember);
	}
	//Mở form danh sách thành viên nhóm
	private void openGroupMemberList(Group group)
	{
		frmGroupMember.pageId = 0;
		frmGroupMember.removeAllEntry();
		nextGroupMemberListPage(group);
	}
	//Hiển thị danh sách bài viết nhóm
	private void addGroupTopicList(ArrayList topics)
	{
		if(topics!=null && topics.size()>0)
		{
			frmTopic.removeAllEntry();
			for(int i=0;i<topics.size();i++)
			{
				GroupTopic t=(GroupTopic)topics.get(i);
				UserItem topic=new UserItem(t.shareUser.username + " : " + t.topic.title + "\nNhóm: " + 
				t.group.groupName + "\n" + t.shareDate + ", " + t.commentsCount + " bình luận",t);
				frmTopic.addEntry(topic,"topic");
			}
			if(configuration.get("pageSize").equals("" + topics.size()))
				frmTopic.addMenu(cmdNext);
			else
				frmTopic.removeCommand(cmdNext);
			if(frmTopic.pageId==1)
				frmTopic.addMenu(cmdPrev);
			if(frmTopic.pageId==0)
				frmTopic.removeCommand(cmdPrev);
			frmTopic.pageId++;
		}
		else
		{
			frmTopic.removeCommand(cmdNext);
		}
	}
	//Hiển thị trang danh sách bài viết nhóm tìm kiếm đượctiếp theo
	private void nextSearchGroupTopicListPage()
	{
		ArrayList topics=user.SearchGroupTopics(frmTopic.pageId, txtKeyword.getString());
		addGroupTopicList(topics);
	}
	//Hiển thị trang danh sách bài viết nhóm tìm kiếm được trước đó
	private void prevSearchGroupTopicListPage()
	{
		frmTopic.pageId -= 2;
		nextSearchGroupTopicListPage();
	}
	//Mở form danh sách bài viết nhóm tìm kiếm được
	private void openSearchGroupTopicList()
	{
		frmTopic = new UserList("Kết quả tìm kiếm bài viết nhóm",5);
		frmTopic.addMenu(cmdViewShareTopic);
		frmTopic.addMenu(cmdBack);
		nextSearchGroupTopicListPage();
		frmTopic.setCommandListener(this.commandListener);
		screenHistory.show(frmTopic);
	}
	//Hiển thị trang danh sách bài viết nhóm tiếp theo
	private void nextGroupTopicListPage(Group group)
	{
		ArrayList topics=group.GetTopics(frmTopic.pageId, topic);
		if(topics!=null && topics.size()>0)
		{
			frmTopic.removeAllEntry();
			for(int i=0;i<topics.size();i++)
			{
				GroupTopic t=(GroupTopic)topics.get(i);
				UserItem topic=new UserItem(t.shareUser.username + " : " + t.topic.title + "\n" + 
				t.shareDate + ", " + t.commentsCount + " bình luận",t);
				frmTopic.addEntry(topic,"topic");
			}
			if(configuration.get("pageSize").equals("" + topics.size()))
				frmTopic.addMenu(cmdNext);
			else
				frmTopic.removeCommand(cmdNext);
			if(frmTopic.pageId==1)
				frmTopic.addMenu(cmdPrev);
			if(frmTopic.pageId==0)
				frmTopic.removeCommand(cmdPrev);
			frmTopic.pageId++;
		}
		else
		{
			frmTopic.removeCommand(cmdNext);
		}
	}
	//Hiển thị trang danh sách bài viết nhóm trước đó
	private void prevGroupTopicListPage(Group group)
	{
		frmTopic.pageId -= 2;
		nextGroupTopicListPage(group);
	}
	//Khởi tạo form danh sách bài viết nhóm
	private void initGroupTopicList(Group group)
	{
		frmTopic = new UserList(group.groupName, group);
		frmTopic.addMenu(cmdViewCreateTopic);
		frmTopic.addMenu(cmdViewUpdateTopic);
		frmTopic.addMenu(cmdViewShareTopic);
		frmTopic.addMenu(cmdViewSearch);
		frmTopic.addMenu(cmdBack);
		topic = new Topic("","", new User("",""));
		openGroupTopicList(group);
		frmTopic.setCommandListener(this.commandListener);
		screenHistory.show(frmTopic);
	}
	//Mở form danh sách bài viết nhóm
	private void openGroupTopicList(Group group)
	{
		frmTopic.removeAllEntry();
		frmTopic.id = 0;
		frmTopic.pageId = 0;
		nextGroupTopicListPage(group);
	}
	//Hiển thị trang các yêu cầu tham gia vào một nhóm của bạn tiếp theo
	private void nextGroupRequestListPage(Group group)
	{
		ArrayList requests = group.GetRequests();
		if(requests!=null && requests.size()>0)
		{
			for(int i=0;i<requests.size();i++)
			{
				Request r=(Request)requests.get(i);
				frmGroupRequest.addEntry(new UserItem(r.user.username + "\n" + r.requestDate,r),"request");
			}
			if(configuration.get("pageSize").equals("" + requests.size()))
				frmGroupRequest.addMenu(cmdNext);
			else
				frmGroupRequest.removeCommand(cmdNext);
			if(frmGroupRequest.pageId==1)
				frmGroupRequest.addMenu(cmdPrev);
			if(frmGroupRequest.pageId==0)
				frmGroupRequest.removeCommand(cmdPrev);
			frmGroupRequest.pageId++;
		}
		else
		{
			frmGroupRequest.removeCommand(cmdNext);
		}
	}
	//Hiển thị trang các yêu cầu tham gia vào một nhóm của bạn trước đó
	private void prevGroupRequestListPage(Group group)
	{
		frmGroupRequest.pageId -= 2;
		nextGroupRequestListPage(group);
	}
	//Mở form danh sách các yêu cầu tham gia một nhóm của bạn
	private void openGroupRequestList(Group group)
	{
		frmGroupRequest=new UserList("Yêu cầu tham gia nhóm: " + group.groupName, group);
		frmGroupRequest.addMenu(cmdConfirm);
		frmGroupRequest.addMenu(cmdReject);
		frmGroupRequest.addMenu(cmdBack);
		nextGroupRequestListPage(group);
		frmGroupRequest.setCommandListener(this.commandListener);
		screenHistory.show(frmGroupRequest);
	}
	//Hiển thị trang danh sách các yêu cầu tham gia tất cả các nhóm của bạn tiếp theo
	private void nextMemberRequestListPage()
	{
		ArrayList requests=user.GetMemberRequests(frmMemberRequest.pageId);
		if(requests!=null && requests.size()>0)
		{
			frmMemberRequest.removeAllEntry();
			for(int i=0;i<requests.size();i++)
			{
				Request r = (Request)requests.get(i);
				frmMemberRequest.addEntry(new UserItem(r.user.username + " - Nhóm: " 
				+r.group.groupName + "\n" + r.requestDate,r),"request");
			}
			if(configuration.get("pageSize").equals("" + requests.size()))
				frmMemberRequest.addMenu(cmdNext);
			else
				frmMemberRequest.removeCommand(cmdNext);
			if(frmMemberRequest.pageId==1)
				frmMemberRequest.addMenu(cmdPrev);
			if(frmMemberRequest.pageId==0)
				frmMemberRequest.removeCommand(cmdPrev);
			frmMemberRequest.pageId++;
		}
		else
		{
			frmMemberRequest.removeCommand(cmdNext);
		}
	}
	//Hiển thị trang danh sách các yêu cầu tham gia tất cả các nhóm của bạn trước đó
	private void prevMemberRequestListPage()
	{
		frmMemberRequest.pageId -= 2;
		nextMemberRequestListPage();
	}
	//Mở form danh sách các yêu cầu tham gia tất cả các nhóm của bạn
	private void openMemberRequestList() {
		// TODO Auto-generated method stub
		frmMemberRequest=new UserList("Danh sách yêu cầu");
		frmMemberRequest.addMenu(cmdConfirm);
		frmMemberRequest.addMenu(cmdReject);
		//frmMemberRequest.addMenu(cmdRefresh);
		//frmMemberRequest.addMenu(cmdConfirmAll);
		//frmMemberRequest.addMenu(cmdRejectAll);
		frmMemberRequest.addMenu(cmdBack);
		nextMemberRequestListPage();
		frmMemberRequest.setCommandListener(this.commandListener);
		screenHistory.show(frmMemberRequest);
	}
	//Hiển thị trang các bài viết mới tiếp theo
	private void nextNewTopicListPage()
	{
		ArrayList topics=user.GetNewTopics(frmTopic.pageId, topic);
		addGroupTopicList(topics);
	}
	//Hiển thị trang các bài viết mới trước đó
	private void prevNewTopicListPage()
	{
		frmTopic.pageId -= 2;
		nextNewTopicListPage();
	}
	//Khởi tạo form danh sách bài viết
	private void initTopicList()
	{
		frmTopic = new UserList("Bài viết mới");
		frmTopic.addMenu(cmdViewCreateTopic);
		frmTopic.addMenu(cmdViewShareTopic);
		frmTopic.addMenu(cmdViewNewTopic);
		frmTopic.addMenu(cmdViewSharedMyTopic);
		frmTopic.addMenu(cmdViewSharedOthersTopic);
		frmTopic.addMenu(cmdViewNonSharedMyTopic);
		frmTopic.addMenu(cmdViewSearch);
		frmTopic.addMenu(cmdBack);
		topic = new Topic("","", new User("",""));
		openNewTopicList();
		frmTopic.setCommandListener(this.commandListener);
		screenHistory.show(frmTopic);
	}
	//Mở form danh sách các bài viết mới
	private void openNewTopicList() {
		frmTopic.removeAllEntry();
		frmTopic.setTitle("Bài viết mới");
		frmTopic.id = 1;
		frmTopic.pageId = 0;
		frmTopic.removeCommand(cmdViewUpdateTopic);
		frmTopic.removeCommand(cmdDeleteTopic);
		nextNewTopicListPage();
	}
	//Hiển thị trang các bài viết bạn tạo và chia sẻ tiếp theo
	private void nextSharedMyTopicListPage()
	{
		ArrayList topics=user.GetSharedMyTopics(frmTopic.pageId, topic);
		addTopicList(topics);
	}
	//Hiển thị trang các bài viết bạn tạo và chia sẻ trước đó
	private void prevSharedMyTopicListPage()
	{
		frmTopic.pageId -= 2;
		nextSharedMyTopicListPage();
	}
	//Mở form danh sách các bài viết bạn tạo và chia sẻ
	private void openSharedMyTopicList() {
		// TODO Auto-generated method stub
		frmTopic.removeAllEntry();
		frmTopic.setTitle("Bài viết bạn tạo và chia sẻ");
		frmTopic.id = 2;
		frmTopic.pageId = 0;
		frmTopic.removeCommand(cmdDeleteTopic);
		frmTopic.addMenu(cmdViewUpdateTopic);
		nextSharedMyTopicListPage();
	}
	//Hiển thị trang các bài viết người khác chia sẻ tiếp theo
	private void nextSharedOthersTopicListPage()
	{
		ArrayList topics=user.GetSharedOthersTopics(frmTopic.pageId, topic);
		addTopicList(topics);
	}
	//Hiển thị trang các bài viết người khác chia sẻ trước đó
	private void prevSharedOthersTopicListPage()
	{
		frmTopic.pageId -= 2;
		nextSharedOthersTopicListPage();
	}
	//Mở form danh sách các bài viết người khác đã chia sẻ
	private void openSharedOthersTopicList() {
		// TODO Auto-generated method stub
		frmTopic.removeAllEntry();
		frmTopic.setTitle("Bài viết người khác chia sẻ");
		frmTopic.id = 3;
		frmTopic.removeCommand(cmdDeleteTopic);
		frmTopic.removeCommand(cmdViewUpdateTopic);
		frmTopic.pageId = 0;
		nextSharedOthersTopicListPage();
	}
	//Hiên thị danh sách bài viết
	private void addTopicList(ArrayList topics)
	{
		if(topics!=null && topics.size()>0)
		{
			frmTopic.removeAllEntry();
			for(int i=0;i<topics.size();i++)
			{
				Topic t=(Topic)topics.get(i);
				UserItem topic=new UserItem(t.author.username + ": " + t.title + "\n" + t.createDate,t);
				frmTopic.addEntry(topic,"topic");
			}
			if(configuration.get("pageSize").equals("" + topics.size()))
				frmTopic.addMenu(cmdNext);
			else
				frmTopic.removeCommand(cmdNext);
			if(frmTopic.pageId==1)
				frmTopic.addMenu(cmdPrev);
			if(frmTopic.pageId==0)
				frmTopic.removeCommand(cmdPrev);
			frmTopic.pageId++;
		}
		else
		{
			frmTopic.removeCommand(cmdNext);
		}
	}
	//Hiển thị trang các bài viết bạn chưa chia sẻ tiếp theo
	private void nextNonSharedTopicListPage()
	{
		ArrayList topics=user.GetNonSharedTopics(frmTopic.pageId, topic);
		addTopicList(topics);
	}
	//Hiển thị trang các bài viết bạn chưa chia sẻ trước đó
	private void prevNonSharedTopicListPage()
	{
		frmTopic.pageId -= 2;
		nextNonSharedTopicListPage();
	}
	//Mở form danh sách các bài viết chưa bạn chưa chia sẻ
	private void openNonSharedTopicList() {
		// TODO Auto-generated method stub
		frmTopic.removeAllEntry();
		frmTopic.setTitle("Bài viết bạn chưa chia sẻ");
		frmTopic.id = 4;
		frmTopic.addMenu(cmdViewUpdateTopic);
		frmTopic.addMenu(cmdDeleteTopic);
		frmTopic.pageId=0;
		nextNonSharedTopicListPage();
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
			initTopicList();
			break;
		case 1:
			initGroupList();
			break;
		case 2:
			openMemberRequestList();
			break;
		case 3:
			openSearchForm();
			break;
		case 4:
			MessageBox.Show("Chức năng đang xây dựng", AlertType.INFO);
			break;
		case 5:
			openUpdateProfileForm();
			break;
		case 6:
			//MessageBox.Show("Chức năng đang xây dựng", AlertType.INFO);
			openSettingForm();
		}
		return false;
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
		configuration = configurationLoad();
		long currentTime = System.currentTimeMillis();
		if (currentTime - initStartTime < 1000) { // show the splash at least for 1000 ms / 1 seconds:
			try {
				Thread.sleep(1000 - currentTime + initStartTime);
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
		list.addEntry("Bài viết");
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
			this.storage.save(configuration, Configuration.KEY);
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
			if (configuration.isDirty()) {
			configurationSave();
			}
			this.midlet.exit();
		} 
		else if (disp == this.screenMainMenu) {
			if (handleCommandMainMenu()) {
				return;
			}
		} 
		else if (cmd == this.cmdBack) {
			if (this.screenHistory.hasPrevious()) {
				this.screenHistory.showPrevious();
			} else {
				screenHistory.show(this.screenMainMenu);
			}
		} 
		else if(cmd == this.cmdLogin) {
			String username = txtUsername.getString();
			String password = txtPassword.getString();
			this.user=new User(username,password);
			if(user.Login())
			{
				//boolean[] gets=new boolean[1];
				//cgRemember.getSelectedFlags(gets);
				if(cgRemember.isSelected(0)==true){
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
		else if(cmd==cmdSave)
		{
			if(txtPageSize.getString()=="" || txtPageSize.getString() == "0")
				MessageBox.Show("Vui lòng nhập số bản ghi trên một trang > 0", AlertType.INFO);
			else
			{
				configuration.set("pageSize", txtPageSize.getString());
				screenHistory.show(screenMainMenu);
			}
		}
		else if(cmd == this.cmdRegister)
		{
			openRegisterForm();
		} 
		else if(cmd == this.cmdLogout)
		{
			screenHistory.show(frmLogin);
		}
		else if(cmd == this.cmdChangePassword)
		{
			openChangePasswordForm();
		}
		else if(cmd == cmdConfirm)
		{
			if(disp==frmRegister){
				if(txtPassword.getString().equals(""))
				{
					MessageBox.Show("Mật khẩu không được bỏ trống!",  AlertType.INFO);
				}
				else if(txtPassword.getString().equals(txtConfirm.getString()))
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
						MessageBox.Show("Đăng ký tài khoản thất bại!" + rs,  AlertType.ERROR);
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
					if(txtNewPassword.getString().equals(""))
					{
						MessageBox.Show("Mật khẩu mới không được bỏ trống!",  AlertType.INFO);
					}
					else if(txtNewPassword.getString().equals(txtConfirm.getString()))
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
			else if(disp==frmMemberRequest || disp==frmGroupRequest)
			{
				UserList ul = (UserList)disp;
				UserItem item=(UserItem)ul.getCurrentItem();
				Request r=(Request)item.data;
				if(!user.ConfirmRequest(r))
					ShowError();
				else
					MessageBox.Show("Đã chấp nhận yêu cầu tham gia nhóm!",  AlertType.INFO);
			}
			else if(disp==frmCreateTopic)
			{
				if(txtTitle.getString()=="")
				{
					MessageBox.Show("Tiêu đề bài viết không được bỏ trống!",  AlertType.INFO);
					return;
				}
				
				if(txtContent.getString()=="")
				{
					MessageBox.Show("Nội dung bài viết không được bỏ trống!",  AlertType.INFO);
					return;
				}

				if(user.CreateTopic(txtTitle.getString(), txtContent.getString(), (Group)frmCreateTopic.data) != null)
				{
					MessageBox.Show("Tạo bài viết mới thành công!",  AlertType.INFO);
				}
				else
				{
					MessageBox.Show("Không thể tạo bài viết mới!",  AlertType.ERROR);
				}
			}
			else if(disp==frmUpdateTopic)
			{
				if(txtTitle.getString()=="")
				{
					MessageBox.Show("Tiêu đề bài viết không được bỏ trống!",  AlertType.INFO);
					return;
				}
				if(txtContent.getString()=="")
				{
					MessageBox.Show("Nội dung bài viết không được bỏ trống!",  AlertType.INFO);
					return;
				}
				Topic t = (Topic)frmUpdateTopic.data;
				if(user.UpdateTopic(t.topicId, txtTitle.getString(), txtContent.getString()))
				{
					MessageBox.Show("Bạn đã cập nhật thông tin bài viết thành công!",  AlertType.INFO );
				}
				else
				{
					MessageBox.Show("Cập nhật thông tin bài viết thất bại!",  AlertType.ERROR);
				}
			}
			else if(disp==frmCreateGroup)
			{
				if(txtGroupName.getString() == "")
				{
					MessageBox.Show("Tên nhóm không được bỏ trống!",  AlertType.INFO);
					return;
				}
				Group g=user.CreateGroup(txtGroupName.getString());
				if(!g.groupId.equals(""))
				{
					//txtGroupName.setString("");
					MessageBox.Show("Tạo nhóm mới thành công!",  AlertType.INFO);
				}
				else
				{
					MessageBox.Show("Không thể tạo nhóm mới!",  AlertType.ERROR);
				}
			}
			else if(disp==frmConfirmDelMember)
			{
				UserItem item = (UserItem)frmGroupMember.getCurrentItem();
				User u = (User)item.data;
				if(user.DeleteMember((Group)frmGroupMember.data, u))
				{
					MessageBox.Show("Đã hủy tư cách thành viên!",  AlertType.INFO);
				}
				else
				{
					MessageBox.Show("Lỗi trong khi hủy tư cách thành viên!",  AlertType.ERROR);
				}
			}
			else if(disp==frmConfirmDelTopic)
			{
				UserItem item = (UserItem)frmTopic.getCurrentItem();
				Topic t = (Topic)item.data;
				if(user.DeleteTopic(t))
				{
					MessageBox.Show("Đã xóa bài viết!",  AlertType.INFO);
				}
				else
				{
					MessageBox.Show("Lỗi trong khi xóa bài viết!",  AlertType.ERROR);	
				}
			}
			else if(disp==frmConfirmDelComment)
			{
				UserItem item = (UserItem)lstComment.getFocusedChild();
				Comment c = (Comment)item.data;
				if(user.DeleteComment(c))
				{
					MessageBox.Show("Đã xóa bình luận!",  AlertType.INFO);
				}
				else
				{
					MessageBox.Show("Lỗi trong khi xóa bình luận!",  AlertType.ERROR);	
				}
			}
			else if(disp==frmUpdateGroup)
			{
				if(txtGroupName.getString() == "")
				{
					MessageBox.Show("Tên nhóm không được bỏ trống!",  AlertType.INFO);
					return;
				}
				Group g=(Group)frmUpdateGroup.data;
				if(user.UpdateGroup(g.groupId, txtGroupName.getString(), txtDescription.getString(), txtGroupRule.getString()))
				{
					MessageBox.Show("Bạn đã cập nhật thông tin nhóm thành công!",  AlertType.INFO );
				}
				else
				{
					MessageBox.Show("Lỗi trong khi cập nhật thông tin nhóm!",  AlertType.ERROR);
				}
			}
			else if(disp==frmCreateComment)
			{
				if(txtContent.getString() == "")
				{
					MessageBox.Show("Nội dung bình luận không được bỏ trống!",  AlertType.INFO);
					return;
				}
				GroupTopic t =(GroupTopic)frmCreateComment.data;
				if(user.CreateComment(t, txtContent.getString()) != null)
				{
					MessageBox.Show("Bạn đã tạo bình luận bài viết thành công!",  AlertType.INFO );
				}
				else
				{
					MessageBox.Show("Lỗi trong khi tạo bình luận bài viết!",  AlertType.ERROR);
				}
			}
			else if(disp==frmUpdateComment)
			{
				if(txtContent.getString() == "")
				{
					MessageBox.Show("Nội dung bình luận không được bỏ trống!",  AlertType.INFO);
					return;
				}
				Comment c =(Comment)frmUpdateComment.data;
				if(user.UpdateComment(c, txtContent.getString()))
				{
					MessageBox.Show("Bạn đã sửa bình luận bài viết thành công!",  AlertType.INFO );
				}
				else
				{
					MessageBox.Show("Lỗi trong khi sửa bình luận bài viết!",  AlertType.ERROR);
				}
			}
			else if(disp==frmCreateAndShareTopic)
			{
				if(txtTitle.getString()=="")
				{
					MessageBox.Show("Tiêu đề bài viết không được bỏ trống!",  AlertType.INFO);
					return;
				}
				
				if(txtContent.getString()=="")
				{
					MessageBox.Show("Nội dung bài viết không được bỏ trống!",  AlertType.INFO);
					return;
				}
				String groupIds="";
				for(int i=0; i<cgNonSharedGroup.size(); ++i)
				{
					if(cgNonSharedGroup.isSelected(i))
					{
						UserItem item = (UserItem)cgNonSharedGroup.getItem(i);
						Group group = (Group)item.data;
						groupIds += "|" + group.groupId;
					}
				}
				if(groupIds != "")
				{
					groupIds = groupIds.substring(1);
				}
				else groupIds = "null";
				if(user.CreateTopic(txtTitle.getString(), txtContent.getString(), groupIds)!=null)
				{
					MessageBox.Show("Tạo bài viết mới thành công!\n",  AlertType.INFO);
				}
				else
				{
					MessageBox.Show("Lỗi trong khi tạo bài viết mới!",  AlertType.ERROR);
				}
			}
			else if(disp==frmShareTopic)
			{
				String groupIds="";
				if(cgNonSharedGroup.size()==0)
				{
					return;
				}
				for(int i=0; i<cgNonSharedGroup.size(); ++i)
				{
					if(cgNonSharedGroup.isSelected(i))
					{
						UserItem item = (UserItem)cgNonSharedGroup.getItem(i);
						Group group = (Group)item.data;
						groupIds += "|" + group.groupId;
					}
				}
				if(groupIds != "")
				{
					groupIds = groupIds.substring(1);
				}
				else
				{
					MessageBox.Show("Vui lòng chọn một nhóm để chia sẻ!\n",  AlertType.INFO);
					return;
				}
				if(user.ShareTopic((Topic)frmShareTopic.data, groupIds))
				{
					MessageBox.Show("Chia sẻ bài viết thành công!\n",  AlertType.INFO);
				}
				else
				{
					MessageBox.Show("Lỗi trong khi chia sẻ bài viết!",  AlertType.ERROR);
				}
			}
			else if(disp==frmSearch)
			{
				int i = cgSearchType.getSelectedIndex();
				if(i==0)
				{
					openSearchGroupList();
				}
				else if(i==1)
				{
					openSearchGroupTopicList();
				}
				else if(i==2)
				{
					openSearchMemberList();
				}
			}
			else if(disp==frmSearchTopic)
			{
				topic = new Topic(txtTitle.getString(), txtContent.getString(), new User(txtCreateUsername.getString(),""));
				int id = frmTopic.id;
				if(id==0)
				{
					Group g = (Group)frmTopic.data;
					openGroupTopicList(g);
				}
				else if(id==1)
					openNewTopicList();
				else if(id==2)
					openSharedMyTopicList();
				else if(id==3)
					openNonSharedTopicList();
				else if(id==4)
					openSharedOthersTopicList();
				screenHistory.show(frmTopic);
			}
			else if(disp==frmSearchGroup)
			{
				group = new Group("",txtGroupName.getString(), new User(txtCreateUsername.getString(),""));
				int id = frmGroup.id;
				if(id==0)
					openGroupList();
				else if(id==1)
					openJoinGroupList();
				else if(id==2)
					openMyGroupList();
				screenHistory.show(frmGroup);
			}
			else if(disp==frmSearchMember)
			{
				openGroupMemberList((Group)frmGroupMember.data);
				screenHistory.show(frmGroupMember);
			}
		}
		else if(cmd==List.SELECT_COMMAND)
		{
			if(disp==screenMainMenu)
				handleCommandMainMenu();
			else if(disp==frmGroup)
			{
				UserList ul = (UserList)disp;
				UserItem item=(UserItem)ul.getCurrentItem();
				initGroupTopicList((Group)item.data);
			}
			else if(disp==frmTopic)
			{
				UserItem item=(UserItem)frmTopic.getCurrentItem();
				int id = frmTopic.id;
				if(id<2 || id==5)
				{
					GroupTopic t=(GroupTopic)item.data;
					openTopicDetailForm(t);
					if(id==1)
						user.ViewedNewTopic(t);
				}
				else
				{
					Topic t = (Topic)item.data;
					openTopicDetailForm(t);
				}
			}
			else if(disp==frmMemberRequest)
			{
				UserItem t=(UserItem)frmMemberRequest.getCurrentItem();
				openMemberProfileForm(((Request)t.data).user);
			}
			else if(disp==frmGroupRequest)
			{
				UserItem t=(UserItem)frmGroupRequest.getCurrentItem();
				openMemberProfileForm(((Request)t.data).user);
			}
			else if(disp==frmSearchGroupResult)
			{
				UserItem item=(UserItem)frmSearchGroupResult.getCurrentItem();
				Group g=(Group)item.data;
				openGroupInfoForm(g);
			}
			else if(disp==frmSearchMemberResult)
			{
				UserItem item=(UserItem)frmSearchMemberResult.getCurrentItem();
				User u=(User)item.data;
				openMemberProfileForm(u);
			}
			else if(disp==frmGroupMember)
			{
				UserItem item=(UserItem)frmGroupMember.getCurrentItem();
				User u=(User)item.data;
				openMemberProfileForm(u);
			}
		}
		else if(cmd==cmdCreateGroup)
		{
			openCreateGroupForm();
		}
		else if(cmd==cmdViewCreateTopic)
		{
			int id = frmTopic.id;
			if(id > 0)
			{
				openCreateAndShareTopicForm();
			}
			else
			{
				UserList ul = (UserList)disp;
				Group g=(Group)ul.data;
				openCreateGroupTopicForm(g);
			}
		}
		else if(cmd==cmdDeleteTopic)
		{
			UserList ul = (UserList)disp;
			UserItem item = (UserItem)ul.getCurrentItem();
			String id;
			if(frmTopic.getTitle().equals("Bài viết mới"))
			{
				GroupTopic t = (GroupTopic)item.data;
				id = t.topic.author.userId;
			}
			else
			{
				Topic t = (Topic)item.data;
				id = t.author.userId;
			}
			if(!user.userId.equals(id))
			{
				MessageBox.Show("Không thể xóa bài viết không phải do bạn tạo!",  AlertType.ERROR);
				return;
			}
			frmConfirmDelTopic=MessageBox.Show("Bạn có chắc chắn bạn muốn xóa bài viết này không?",AlertType.CONFIRMATION);
			frmConfirmDelTopic.addCommand(cmdConfirm);
			frmConfirmDelTopic.addCommand(cmdBack);
			frmConfirmDelTopic.setCommandListener(this.commandListener);
		}
		else if(cmd==cmdDeleteComment)
		{
			UserItem item = (UserItem)lstComment.getFocusedChild();
			Comment c = (Comment)item.data;
			if(!c.user.userId.equals(user.userId))
			{
				MessageBox.Show("Không thể xóa bình luận không phải do bạn tạo!",  AlertType.ERROR);
				return;
			}
			frmConfirmDelComment=MessageBox.Show("Bạn có chắc chắn bạn muốn xóa bình luận này không?",AlertType.CONFIRMATION);
			frmConfirmDelComment.addCommand(cmdConfirm);
			frmConfirmDelComment.addCommand(cmdBack);
			frmConfirmDelComment.setCommandListener(this.commandListener);
			//screenHistory.show(frmConfirmDelGroup);
		}
		else if(cmd==cmdDetail)
		{
			commandAction(List.SELECT_COMMAND,disp);
		}
		else if(cmd==cmdViewUpdateGroup)
		{
			UserList ul = (UserList)disp;
			UserItem item=(UserItem)ul.getCurrentItem();
			Group g=(Group)item.data;
			openUpdateGroupForm(g);
		}
		else if(cmd==cmdViewGroupInfo)
		{
			UserItem item;
			if(disp == frmCreateAndShareTopic || disp == frmShareTopic)
			{
				item = (UserItem)cgNonSharedGroup.getFocusedChild();
			}
			else
			{
				UserList ul = (UserList)disp;
				item=(UserItem)ul.getCurrentItem();
			}
			Group g=(Group)item.data;
			openGroupInfoForm(g);
		}
		else if(cmd==cmdViewGroupMember)
		{
			UserItem item;
			if(disp == frmCreateAndShareTopic || disp == frmShareTopic)
			{
				item = (UserItem)cgNonSharedGroup.getFocusedChild();
			}
			else
			{
				UserList ul = (UserList)disp;
				item=(UserItem)ul.getCurrentItem();
			}
			Group g=(Group)item.data;
			initGroupMemberList(g);
		}
		else if(cmd==cmdViewGroupRequest)
		{
			UserList ul = (UserList)disp;
			UserItem item=(UserItem)ul.getCurrentItem();
			Group g=(Group)item.data;
			if(!g.leader.userId.equals(user.userId))
			{
				MessageBox.Show("Không thể xem các yêu cầu tham gia nhóm không phải do bạn tạo!",  AlertType.ERROR);
				return;
			}
			openGroupRequestList(g);
		}
		else if(cmd==cmdViewGroup)
		{
			openGroupList();
		}
		else if(cmd==cmdViewMyGroup)
		{
			openMyGroupList();
		}
		else if(cmd==cmdViewJoinGroup)
		{
			openJoinGroupList();
		}
		else if(cmd==cmdViewUpdateTopic)
		{
			UserList ul = (UserList)disp;
			UserItem item = (UserItem)ul.getCurrentItem();
			if(frmTopic.id < 2)
				openUpdateTopicForm(((GroupTopic)item.data).topic);
			else
				openUpdateTopicForm((Topic)item.data);
		}
		else if(cmd==cmdUpdate)
		{
			String gender = cgGender.isSelected(0)?"0":"1";
			User u = new User(user.userId, txtFirstName.getString(), txtLastName.getString()
					, email.getString(), gender, txtPhone.getString(), txtAddress.getString());
			if(u.Update())
			{
				MessageBox.Show("Bạn đã cập nhật thông tin tài khoản thành công!",  AlertType.INFO);
			}
			else
			{
				MessageBox.Show("Lỗi trong khi cập nhật thông tin tài khoản!",  AlertType.ERROR);
			}
		}
		else if(cmd==cmdSendRequest)
		{
			UserItem item=(UserItem)frmSearchGroupResult.getCurrentItem();
			Group g=(Group)item.data;
			if(!user.CreateRequest(g))
				ShowError();
			else
				MessageBox.Show("Đã gửi yêu cầu thành công!",  AlertType.INFO);
		}
		else if(cmd==cmdReject)
		{
			UserList ul = (UserList)disp;
			UserItem item=(UserItem)ul.getCurrentItem();
			Request r=(Request)item.data;
			if(!user.DeleteRequest(r))
				ShowError();
			else
				MessageBox.Show("Đã từ chối yêu cầu tham gia nhóm!",  AlertType.INFO);
		}
		else if(cmd==cmdViewCreateComment)
		{
			GroupTopic t = (GroupTopic)frmTopicDetail.data;
			openCreateCommentForm(t);
		}
		else if(cmd==cmdViewUpdateComment)
		{
			UserItem item = (UserItem)lstComment.getFocusedChild();
			Comment c = (Comment)item.data;
			openUpdateCommentForm(c);
		}
		else if(cmd==cmdViewShareTopic)
		{
			UserList ul = (UserList)disp;
			UserItem item = (UserItem)ul.getCurrentItem();
			int id = frmTopic.id;
			if(id<2 || id==5)
				openTopicShareForm(((GroupTopic)item.data).topic);
			else
				openTopicShareForm((Topic)item.data);
		}
		else if(cmd==cmdViewNewTopic)
		{
			openNewTopicList();
		}
		else if(cmd==cmdViewSharedMyTopic)
		{
			openSharedMyTopicList();
		}
		else if(cmd==cmdViewSharedOthersTopic)
		{
			openSharedOthersTopicList();
		}
		else if(cmd==cmdViewNonSharedMyTopic)
		{
			openNonSharedTopicList();
		}
		else if(cmd == cmdNext)
		{
			if(disp == frmGroupMember)
			{
				Group g = (Group)frmGroupMember.data;
				nextGroupMemberListPage(g);
			}
			else if(disp == frmSearchGroupResult)
			{
				nextSearchGroupListPage();
			}
			else if(disp == frmSearchMemberResult)
			{
				nextSearchMemberListPage();
			}
			else if(disp == frmTopic)
			{
				int id = frmTopic.id;
				if(id==0)
				{
					Group g = (Group)frmTopic.data;
					nextGroupTopicListPage(g);
				}
				else if(id==1)
					nextNewTopicListPage();
				else if(id==2)
					nextSharedMyTopicListPage();
				else if(id==3)
					nextNonSharedTopicListPage();
				else if(id==4)
					nextSharedOthersTopicListPage();
				else if(id==5)
					nextSearchGroupTopicListPage();
			}
			else if(disp == frmGroupRequest)
			{
				nextGroupRequestListPage((Group)frmGroupRequest.data);
			}
			else if(disp == frmMemberRequest)
			{
				nextMemberRequestListPage();
			}
			else if(disp == frmGroup)
			{
				int id = frmGroup.id;
				if(id==0)
					nextGroupListPage();
				else if(id==1)
					nextMyGroupListPage();
				else
					nextJoinGroupListPage();
			}
			else if(disp == frmCreateAndShareTopic)
			{
				nextNonSharedGroupPageForCreate();
			}
			else if(disp == frmTopicDetail)
			{
				if(frmTopicDetail.id==0)
					nextCommentPage((GroupTopic)frmTopicDetail.data);
				else
					nextSharedGroupPage((Topic)frmTopicDetail.data);
			}
			else if(disp == frmShareTopic)
			{
				nextNonSharedGroupPageForShare((Topic)frmShareTopic.data);
			}
		}
		else if(cmd == cmdPrev)
		{
			if(disp == frmGroupMember)
			{
				Group g = (Group)frmGroupMember.data;
				prevGroupMemberListPage(g);
			}
			else if(disp == frmSearchGroupResult)
			{
				prevSearchGroupListPage();
			}
			else if(disp == frmSearchMemberResult)
			{
				prevSearchMemberListPage();
			}
			else if(disp == frmTopic)
			{
				int id = frmTopic.id;
				if(id==0)
				{
					Group g = (Group)frmTopic.data;
					prevGroupTopicListPage(g);
				}
				else if(id==1)
					prevNewTopicListPage();
				else if(id==2)
					prevSharedMyTopicListPage();
				else if(id==3)
					prevNonSharedTopicListPage();
				else if(id==4)
					prevSharedOthersTopicListPage();
				else if(id==5)
					prevSearchGroupTopicListPage();
			}
			else if(disp == frmGroupRequest)
			{
				prevGroupRequestListPage((Group)frmGroupRequest.data);
			}
			else if(disp == frmMemberRequest)
			{
				prevMemberRequestListPage();
			}
			else if(disp == frmGroup)
			{
				int id = frmGroup.id;
				if(id==0)
					prevGroupListPage();
				else if(id==1)
					prevMyGroupListPage();
				else
					prevJoinGroupListPage();
			}
			else if(disp == frmCreateAndShareTopic)
			{
				prevNonSharedGroupPageForCreate();
			}
			else if(disp == frmTopicDetail)
			{
				if(frmTopicDetail.id==0)
					prevCommentPage((GroupTopic)frmTopicDetail.data);
				else
					prevSharedGroupPage((Topic)frmTopicDetail.data);
			}
			else if(disp == frmShareTopic)
			{
				prevNonSharedGroupPageForShare((Topic)frmShareTopic.data);
			}
		}
		else if(cmd==cmdDeleteMember)
		{
			Group g = (Group)frmGroupMember.data;
			if(!g.leader.userId.equals(user.userId))
			{
				MessageBox.Show("Chỉ có trưởng nhóm mới được hủy tư cách thành viên!",  AlertType.ERROR);
				return;
			}
			if(frmGroupMember.getCurrentIndex()==0)
			{
				MessageBox.Show("Không thể hủy tư cách nhóm trưởng!",  AlertType.ERROR);
				return;
			}
			frmConfirmDelMember=MessageBox.Show("Bạn có chắc chắn bạn muốn hủy tư cách thành viên này không?",AlertType.CONFIRMATION);
			frmConfirmDelMember.addCommand(cmdConfirm);
			frmConfirmDelMember.addCommand(cmdBack);
			frmConfirmDelMember.setCommandListener(this.commandListener);
		}
		else if(cmd==cmdViewSearch)
		{
			if(disp==frmTopic)
				openSearchTopicForm();
			else if(disp==frmGroup)
				openSearchGroupFrom();
			else if(disp==frmGroupMember)
				openSearchMemberForm();
		}
	}
}
