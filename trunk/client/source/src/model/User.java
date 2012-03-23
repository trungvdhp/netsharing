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
	public String sex;
	public String email;
	public String phone;
	public String address;
	public String avatar;
	public String createDate;
	public String state;
	public String startDay;
	public String permission;
	
	Html html=new Html();
	Topic topic = new Topic();
	Group group = new Group();
	TopicGroup topicgroup = new TopicGroup();
	Comment comment = new Comment();
	Request request = new Request();
	
	ArrayList topics;
	ArrayList groups;
	ArrayList requests;
	
	public User()
	{
		
	}
	
	public User(String username,String password)
	{
		this.username=username;
		this.password=password;
	}

	public User(String id,String username,String firstName,String lastName)
	{
		this.userId = id;
		this.username=username;
		this.firstName = firstName;
		this.lastName = lastName;
	}
	
	public User(ArrayList data)
	{
		this.userId= data.get(0).toString();
		this.username = data.get(1).toString();
		this.password = data.get(2).toString();
		this.firstName = data.get(3).toString();
		this.lastName = data.get(4).toString();
		this.birthday = data.get(5).toString();
		this.sex = data.get(6).toString();
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
			String data=html.SendRequest("",
					new String[] {"CVM","xTaiKhoan","xMatKhau"},
					new String[] {"DangNhap",username,password}
					);
			if(data.indexOf("TKTD")<0)
				return false;
			userId=data;
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
	public boolean Register()
	{
		try
		{
			String data=html.SendRequest("",
					new String[] {"CVM","xTaiKhoan","xMatKhau"},
					new String[] {"DangKyTaiKhoan",username,password}
					);
			if(data.indexOf("false") >= 0)
				return false;
			return true;
		}
		catch(Exception ex)		
		{
			return false;
		}
	}
	//Lấy thông tin tài khoản của bạn
	public boolean GetInfo()
	{
		ArrayList data = new ArrayList();		
		try
		{
			String s = html.SendRequest("",
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
			this.sex = data.get(5).toString();
			this.phone = data.get(6).toString();
			this.avatar = data.get(7).toString();
			this.address = data.get(8).toString();
			this.startDay = data.get(9).toString();
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
		ArrayList data = new ArrayList();		
		try
		{
			String s = html.SendRequest("",
					new String[] {Constants.Case,"xMaTaiKhoan"},
					new String[] {"BaiVietMoi", userId}
					);
			if(s.indexOf("false")>=0)
				return null;
			data = UtilString.Split(s, Constants.KyTuChiaTruongDL);
			int len = data.size();
			int i=0;
			while(i<len)
			{
				TopicGroup t = new TopicGroup(data.get(i).toString(),data.get(i+1).toString(),
						data.get(i+2).toString(),data.get(i+3).toString(),
						data.get(i+4).toString(),data.get(i+5).toString(), 
						data.get(i+6).toString(),data.get(i+7).toString(),data.get(i+8).toString());
				topics.add(t);
				i += 9;
			}
			return topics;
		}
		catch(Exception ex)
		{
			return null;
		}
	}
	//Lấy danh sách các bài viết của bạn
	public ArrayList GetMyTopics()
	{
		ArrayList data = new ArrayList();		
		try
		{
			String s = html.SendRequest("",
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
				TopicGroup t = new TopicGroup(data.get(i).toString(),data.get(i+1).toString(),
						data.get(i+2).toString(),data.get(i+3).toString(),
						data.get(i+4).toString(),data.get(i+5).toString(), 
						data.get(i+6).toString(),data.get(i+7).toString(),data.get(i+8).toString());
				topics.add(t);
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
			String s = html.SendRequest("",
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
				Group t = new Group(data.get(i).toString(),data.get(i+1).toString(),
						data.get(i+2).toString(),data.get(i+3).toString(),
						data.get(i+4).toString(),data.get(i+5).toString(), data.get(i+6).toString());
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
			String s = html.SendRequest("",
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
				Group t = new Group(data.get(i).toString(),data.get(i+1).toString(),
						data.get(i+2).toString(),data.get(i+3).toString(),
						data.get(i+4).toString(),data.get(i+5).toString(), data.get(i+6).toString());
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
		if(groups!=null) return groups;
		groups=new ArrayList();
		ArrayList data = new ArrayList();
		
		try
		{
			String s = html.SendRequest("",
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
				Group t = new Group(data.get(i).toString(),data.get(i+1).toString(),
						data.get(i+2).toString(),data.get(i+3).toString(),
						data.get(i+4).toString(),data.get(i+5).toString(), data.get(i+6).toString());
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
	public ArrayList GetJoinRequests()
	{
		ArrayList data = new ArrayList();		
		try
		{
			String s = html.SendRequest("",
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
				Request t = new Request(data.get(i).toString(),data.get(i+1).toString(),
						data.get(i+2).toString(),data.get(i+3).toString(),
						data.get(i+4).toString(),data.get(i+5).toString());
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
	//Lấy danh sách các yêu cầu tham gia nhóm của bạn
	public ArrayList GetMyJoinRequests()
	{
		ArrayList data = new ArrayList();		
		try
		{
			String s = html.SendRequest("",
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
				Request t = new Request(data.get(i).toString(),data.get(i+1).toString(),
						data.get(i+2).toString(),data.get(i+3).toString(),
						data.get(i+4).toString(),data.get(i+5).toString());
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
	public Request CreateRequest(String groupId)
	{
		request = new Request(userId, groupId);
		request.Create();
		return request;
	}
	//Xác nhận yêu cầu tham gia nhóm
	public boolean ConfirmRequest(String requestId)
	{
		request = new Request(requestId);
		return request.Confirm();
	}
	//Xóa yêu cầu tham gia nhóm
	public boolean DeleteRequest(String requestId)
	{
		request = new Request(requestId);
		return request.Delete();
	}
	//Sửa thông tin tài khoản
	public boolean Update()
	{
		try
		{
			String data=html.SendRequest("",
					new String[] {"CVM","xMaTaiKhoan","xMatKhau","xHoDem","xTen","xNgaySinh","xGioiTinh",
					"xEmail","xDienThoai","xDiaChi","xNgayTao","xNgayVaoTruong"},
					new String[] {"CapNhatTaiKhoan",userId,password,firstName,lastName,birthday,sex,email,phone,
					address,createDate,startDay}
					);
			if(data.indexOf("false") >= 0)
				return false;
			userId=data;
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
		group = new Group(userId, groupName);
		group.Create();
		return group;
	}
	//Sửa nhóm
	public boolean UpdateGroup(String groupId, String groupName, String groupDescription, String groupRule)
	{
		group = new Group(groupId, groupName, groupDescription, groupRule);
		return group.Update();
	}
	//Xóa nhóm
	public boolean DeleteGroup(String groupId)
	{
		group = new Group(groupId);
		return group.Delete();
	}
	//Tạo bài viết
	public Topic CreateTopic(String topicTitle, String topicContent)
	{
		topic = new Topic(topicTitle, topicContent);
		topic.Create(userId);
		return topic;
	}
	//Sửa bài viết
	public boolean UpdateTopic(String topicId, String topicTitle, String topicContent)
	{
		topic = new Topic(topicId, topicTitle, topicContent);
		return topic.Update();
	}
	//Xóa bài viết
	public boolean DeleteTopic(String topicId)
	{
		topic = new Topic(topicId);
		return topic.Delete();
	}
	//Tạo và chia sẻ bài viết
	public TopicGroup CreateAndShare(String topicTitle, String topicContent, String groupId)
	{
		topicgroup = new TopicGroup(userId, topicTitle, topicContent, groupId);
		topicgroup.Create();
		return topicgroup;
	}
	//Chia sẻ bài viết
	public TopicGroup ShareTopic(String topicId, String groupId)
	{
		topicgroup = new TopicGroup(userId, topicId, groupId);
		topicgroup.Share();
		return topicgroup;
	}
	//Xóa chía sẻ bài viết
	public boolean DeleteShareTopic(String topicGroupId)
	{
		topicgroup = new TopicGroup(topicGroupId);
		return topicgroup.Delete();
	}
	//Tạo bình luận
	public Comment CreateComment(String topicGroupId, String commentContent)
	{
		comment = new Comment(userId, commentContent);
		comment.Create(topicGroupId);
		return comment;
	}
	//Tạo bình luận
	public boolean UpdateComment(String commentId, String commentContent)
	{
		comment = new Comment(commentId, "", commentContent);
		return comment.Update();
	}
	//Xóa bình luận
	public boolean DeleteComment(String commentId, String commentContent)
	{
		comment = new Comment(commentId);
		return comment.Delete();
	}
}
