package model;

import control.MessageBox;
import de.enough.polish.ui.Display;
import de.enough.polish.ui.Displayable;
import de.enough.polish.util.ArrayList;

import util.UtilString;
import base.Constants;

public class User {
	public String userId;
	public String username;
	public String password;
	public String firstName;
	public String lastName;
	public String birthday;
	public String gender;
	public String email;
	public String phone;
	public String address;
	public String avatar;
	public String createDate;
	public String state;
	public String startDay;
	public String permission;
	
	ArrayList topics;
	ArrayList groups;
	ArrayList requests;
	
	public User(String username,String password)
	{
		this.username=username;
		this.password=password;
	}
	
	public User(String id, String username, String password)
	{
		this.userId = id;
		this.username = username;
		this.password = password;
	}
	
	public User(String id,String username,String firstName,String lastName)
	{
		this.userId = id;
		this.username=username;
		this.firstName = firstName;
		this.lastName = lastName;
	}
	
	public User(String id, String firstName, String lastName, String email, String gender, String phone, String address)
	{
		this.userId = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.gender = gender;
		this.phone = phone;
		this.address = address;
	}
	
	public User(ArrayList data)
	{
		this.userId= data.get(0).toString();
		this.username = data.get(1).toString();
		this.password = data.get(2).toString();
		this.firstName = data.get(3).toString();
		this.lastName = data.get(4).toString();
		this.birthday = data.get(5).toString();
		this.gender = data.get(6).toString();
		this.email = data.get(7).toString();
		this.phone = data.get(8).toString();
		this.address = data.get(9).toString();
		this.createDate = data.get(10).toString();
		this.startDay = data.get(11).toString();
	}
	//Đăng nhập
	public boolean Login()
	{
		try
		{
			String data=Html.SendRequest("",
					new String[] {"CVM","xTaiKhoan","xMatKhau"},
					new String[] {"DangNhap",username,password}
					);
			//String data=Html.SendRequest("");
			userId=data;
			if(data.indexOf("TKTD")<0)
				return false;

			return true;
		}
		catch(Exception ex)
		{
			return false;
		}
	}
	//Đăng xuất
	public void Logout()
	{
		userId = "";
	}
	//Đăng ký tài khoản
	public String Register()
	{
		try
		{
			return Html.SendRequest("",
					new String[] {"CVM","xTaiKhoan","xMatKhau"},
					new String[] {"DangKyTaiKhoan",username,password}
			);
		}
		catch(Exception ex)		
		{
			return "false";
		}
	}
	//Lấy thông tin tài khoản của bạn
	public boolean GetInfo()
	{
		ArrayList data = new ArrayList();		
		try
		{
			String s = Html.SendRequest("",
					new String[] {Constants.Case,"xMaTaiKhoan"},
					new String[] {"ThongTinTaiKhoan", userId}
					);
			if(s.indexOf("false")>=0)
				return false;
			data = UtilString.Split(s, Constants.KyTuChiaTruongDL);
			this.username = data.get(0).toString();
			this.firstName = data.get(1).toString();
			this.lastName = data.get(2).toString();
			this.birthday =data.get(3).toString();
			this.email = data.get(4).toString();
			this.gender = data.get(5).toString();
			this.phone = data.get(6).toString();
			this.avatar = data.get(7).toString();
			this.address = data.get(8).toString();
			this.createDate = data.get(9).toString();
			this.startDay = data.get(10).toString();
			return true;
		}
		catch(Exception ex)
		{
			return false;
		}
	}
	//Danh sách các bài viết mới
	public ArrayList GetNewTopics()
	{
		topics=new ArrayList();
		ArrayList data = new ArrayList();		
		try
		{
			String s = Html.SendRequest("",
					new String[] {Constants.Case,"xMaTaiKhoan"},
					new String[] {"DanhSachBaiVietMoi", this.userId}
					);
			if(s.indexOf("false")>=0)
				return null;
			data = UtilString.Split(s, Constants.KyTuChiaTruongDL);
			int len = data.size();
			int i=0;
			
			while(i<len)
			{
				TopicGroup t=new TopicGroup(
						data.get(i).toString(), 
						new Topic(new User(data.get(i+5).toString(),data.get(i+9).toString(),"",""),
								data.get(i+1).toString(), data.get(i+2).toString(), data.get(i+3).toString(), data.get(i+4).toString()),
						new User(data.get(i+7).toString(), data.get(i+10).toString(),"",""),
						data.get(i+6).toString(),
						data.get(i+8).toString() );
				
				topics.add(t);
				i += 11;
			}
			
			return topics;
		}
		catch(Exception ex)
		{
			//MessageBox.Show(ex.toString(), display, disp);
			return topics;
		}
	}
	//Lấy danh sách các bài viết của bạn
	public ArrayList GetMyTopics()
	{
		ArrayList data = new ArrayList();		
		try
		{
			String s = Html.SendRequest("",
					new String[] {Constants.Case,"xMaTaiKhoan"},
					new String[] {"BaiVietCuaToi", userId}
					);
			if(s.indexOf("false")>=0)
				return null;
			data = UtilString.Split(s, Constants.KyTuChiaTruongDL);
			int len = data.size();
			int i=0;
			while(i<len)
			{
				/*TopicGroup t = new TopicGroup(data.get(i).toString(),data.get(i+1).toString(),
						data.get(i+2).toString(),data.get(i+3).toString(),
						data.get(i+4).toString(),data.get(i+5).toString(), 
						data.get(i+6).toString(),data.get(i+7).toString(),data.get(i+8).toString());
				topics.add(t);*/
				i += 9;
			}
			return topics;
		}
		catch(Exception ex)
		{
			return null;
		}
	}
	//Lấy danh sách các nhóm mà bạn là nhóm trưởng
	public ArrayList GetOwnerGroups()
	{
		ArrayList data = new ArrayList();		
		try
		{
			String s = Html.SendRequest("",
					new String[] {Constants.Case,"xMaTaiKhoan"},
					new String[] {"NhomBanLaTruongNhom", userId}
					);
			if(s.indexOf("false")>=0)
				return null;
			data = UtilString.Split(s, Constants.KyTuChiaTruongDL);
			int len = data.size();
			int i=0;
			while(i<len)
			{
				Group t = null;
				groups.add(t);
				i += 7;
			}
			return groups;
		}
		catch(Exception ex)
		{
			return null;
		}
	}
	//Lấy danh sách các nhóm có bạn là thành viên
	public ArrayList GetMemberGroups()
	{
		ArrayList data = new ArrayList();		
		try
		{
			String s = Html.SendRequest("",
					new String[] {Constants.Case,"xMaTaiKhoan"},
					new String[] {"NhomBanLaThanhVien", userId}
					);
			if(s.indexOf("false")>=0)
				return null;
			data = UtilString.Split(s, Constants.KyTuChiaTruongDL);
			int len = data.size();
			int i=0;
			while(i<len)
			{
				Group t=null;
				
				groups.add(t);
				i += 7;
			}
			return groups;
		}
		catch(Exception ex)
		{
			return null;
		}
	}
	//Lấy danh sách tất cả các nhóm có bạn tham gia
	public ArrayList GetMyGroups(Display display, Displayable disp)
	{
		//MessageBox.Show("SS", display, disp);
		//if(groups!=null) return groups;
		groups=new ArrayList();
		ArrayList data = new ArrayList();
		
		try
		{
			
			String s = Html.SendRequest("",
					new String[] {Constants.Case,"xMaTaiKhoan"},
					new String[] {"NhomBanThamGia", userId}
					);
			if(s.indexOf("false")>=0)
				return null;
			
			data = UtilString.Split(s, Constants.KyTuChiaTruongDL);
			int len = data.size();
			int i=0;
			
			while(i<len)
			{
				Group t=new Group(data.get(i).toString(),
						data.get(i+1).toString(),
						new User(data.get(i+2).toString(),data.get(i+3).toString(),"",""),
						data.get(i+4).toString(),
						data.get(i+5).toString(),
						data.get(i+6).toString()
						);
				groups.add(t);
				i += 7;
			}
			return groups;
		}
		catch(Exception ex)
		{
			
			return null;
		}
	}
	//Lấy danh sách các yêu cầu tham gia
	public ArrayList GetJoinRequests(Display display,Displayable disp)
	{
		requests=new ArrayList();
		ArrayList data = new ArrayList();		
		try
		{
			String s = Html.SendRequest("",
					new String[] {Constants.Case,"xMaTaiKhoan"},
					new String[] {"DanhSachYeuCauThamGia", userId}
					);
			if(s.indexOf("false")>=0)
				return null;
			data = UtilString.Split(s, Constants.KyTuChiaTruongDL);
			int len = data.size();
			int i=0;
			while(i<len)
			{
				Request t = new Request(data.get(i).toString(),
						 new Group(data.get(i+3).toString(),data.get(i+4).toString(),"",""),
						 new User(data.get(i+1).toString(),data.get(i+2).toString(),"",""),
						 data.get(i+5).toString()
						);
				requests.add(t);
				i += 6;
			}
			return requests;
		}
		catch(Exception ex)
		{
			MessageBox.Show(ex.toString(), display, disp);
			return requests;
		}
	}
	//Lấy danh sách các yêu cầu tham gia nhóm của bạn
	public ArrayList GetMyJoinRequests()
	{
		ArrayList data = new ArrayList();		
		try
		{
			String s = Html.SendRequest("",
					new String[] {Constants.Case,"xMaTaiKhoan"},
					new String[] {"DanhSachYeuCauThamGiaCuaBan", userId}
					);
			if(s.indexOf("false")>=0)
				return null;
			data = UtilString.Split(s, Constants.KyTuChiaTruongDL);
			int len = data.size();
			int i=0;
			while(i<len)
			{
				Request t =null;/* new Request(data.get(i).toString(),data.get(i+1).toString(),
						data.get(i+2).toString(),data.get(i+3).toString(),
						data.get(i+4).toString(),data.get(i+5).toString());*/
				requests.add(t);
				i += 6;
			}
			return requests;
		}
		catch(Exception ex)
		{
			return null;
		}
	}
	//Tạo yêu cầu tham gia nhóm
	public boolean CreateRequest(Group g)
	{
		
		return Request.Create(this, g);
	}
	//Xác nhận yêu cầu tham gia nhóm
	public boolean ConfirmRequest(Request r)
	{
		return Request.Confirm(r);
	}
	//Xóa yêu cầu tham gia nhóm
	public boolean DeleteRequest(Request r)
	{
		return Request.Delete(r);
	}
	//Sửa thông tin tài khoản
	public boolean Update()
	{
		try
		{
			String data=Html.SendRequest("",
					new String[] {"CVM","xMaTaiKhoan","xHoDem","xTen","xEmail","xGioiTinh","xDienThoai","xDiaChi"},
					new String[] {"CapNhatTaiKhoan",userId,firstName,lastName,email, gender,phone,address}
					);
			if(data.indexOf("false") >= 0)
				return false;
			return true;
		}
		catch(Exception ex)		{
			//return "";
			return false;
		}
	}
	//Đổi mật khẩu
	public boolean ChangePassword()
	{
		try
		{
			String data=Html.SendRequest("",
					new String[] {"CVM","xMaTaiKhoan","xMatKhau"},
					new String[] {"DoiMatKhau",userId,password}
					);
			if(data.indexOf("false") >= 0)
				return false;
			return true;
		}
		catch(Exception ex)		{
			//return "";
			return false;
		}
	}
	//Tạo nhóm
	public Group CreateGroup(String groupName)
	{
		return Group.Create(this,groupName);
	}
	//Sửa nhóm
	public boolean UpdateGroup(String groupId, String groupName, String groupDescription, String groupRule)
	{
		Group group = new Group(groupId, groupName, groupDescription, groupRule);
		return group.Update();
	}
	//Xóa nhóm
	public boolean DeleteGroup(Group g)
	{
		return Group.Delete(g);
	}
	//Tạo bài viết
	public Topic CreateTopic(String title, String content,Group g)
	{
		return Topic.Create(this, title, content,g);
	}
	//Sửa bài viết
	public boolean UpdateTopic(String topicId, String topicTitle, String topicContent)
	{
		Topic topic = new Topic(topicId, topicTitle, topicContent);
		return topic.Update();
	}
	//Xóa bài viết
	public boolean DeleteTopic(Topic t)
	{
		return Topic.Delete(t);
	}
	/*//Tạo và chia sẻ bài viết
	public TopicGroup CreateAndShare(String topicTitle, String topicContent, String groupId)
	{
		topicgroup = new TopicGroup(userId, topicTitle, topicContent, groupId);
		topicgroup.Create();
		return topicgroup;
	}*/
	//Chia sẻ bài viết
	public TopicGroup ShareTopic(Topic t,Group g)
	{
		try
		{
			String topicGroupId=Html.SendRequest("",
					new String[]{"CVM","xMaBaiViet","xMaNhom","xTaiKhoan"},
					new String[]{"ChiaSeBaiViet",t.topicId,g.groupId,this.username}
					);
			return new TopicGroup(topicGroupId);
		}
		catch(Exception ex)
		{
			return null;
		}
	}
	//Xóa chía sẻ bài viết
	public boolean DeleteShareTopic(String topicGroupId)
	{
		return TopicGroup.Delete(new TopicGroup(topicGroupId));
	}
	//Tạo bình luận
	public Comment CreateComment(TopicGroup t, String commentContent)
	{
		return Comment.Create(t, this, commentContent);
	}
	//Tạo bình luận
	public boolean UpdateComment(Comment c, String commentContent)
	{
		c.content = commentContent;
		return c.Update();
	}
	//Xóa bình luận
	public boolean DeleteComment(Comment c)
	{
		return Comment.Delete(c);
	}
	public ArrayList SearchGroup(String keyword)
	{
		ArrayList groups=new ArrayList();
		try
		{
			String s=Html.SendRequest("", 
					new String[]{"CVM","xTuKhoa","xMaTaiKhoan"},
					new String[]{"TimKiemNhom",keyword,this.userId}
			);
			if(s.indexOf("false")>=0)
				return groups;
			
			ArrayList data = UtilString.Split(s, Constants.KyTuChiaTruongDL);
			int len = data.size();
			int i=0;
			
			while(i<len)
			{
				Group t=new Group(data.get(i).toString(),
						data.get(i+1).toString(),
						new User(data.get(i+2).toString(),data.get(i+3).toString(),"",""),
						data.get(i+4).toString(),
						data.get(i+5).toString(),
						data.get(i+6).toString()
						);
				groups.add(t);
				i += 7;
			}
		}
		catch(Exception ex)
		{
			
		}
		return groups;
	}

	public boolean ViewedNewTopic(TopicGroup t) {
		try
		{
			String s=Html.SendRequest("", 
					new String[]{"CVM","xMaTaiKhoan"},
					new String[]{"XemBaiVietMoi",this.userId}
			);
			if(s.indexOf("true")>=0)return true;
			return false;
		}
		catch(Exception ex)
		{
			return false;
		}
	}
}
