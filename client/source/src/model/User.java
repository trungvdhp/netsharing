package model;

//import control.MessageBox;
//import de.enough.polish.ui.Display;
//import de.enough.polish.ui.Displayable;
//import de.enough.polish.ui.AlertType;
import control.Controller;
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
	public String joinDate;
	
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
	
	public User(String id,String username,String createDate,String joinDate)
	{
		this.userId = id;
		this.username=username;
		this.createDate = createDate;
		this.joinDate = joinDate;
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
					new String[] {"CVM","xMaTaiKhoan"},
					new String[] {"ThongTinTaiKhoan", userId}
					);
			if(s.equals("false"))
				return false;
			data = UtilString.Split(s, Constants.KyTuChiaTruongDL);
			this.firstName = data.get(0).toString();
			this.lastName = data.get(1).toString();
			this.birthday =data.get(2).toString();
			this.email = data.get(3).toString();
			this.gender = data.get(4).toString();
			this.phone = data.get(5).toString();
			this.address = data.get(6).toString();
			this.createDate = data.get(7).toString();
			return true;
		}
		catch(Exception ex)
		{
			return false;
		}
	}
	//Danh sách các bài viết mới
	public ArrayList GetNewTopics(int pageId, Topic topic)
	{
		topics=new ArrayList();
		ArrayList data = new ArrayList();		
		try
		{
			String s = Html.SendRequest("",
					new String[] {Constants.Case,"xMaTaiKhoan","xPageSize","xPageId","xTieuDe","xNoiDung","xTaiKhoan"},
					new String[] {"DanhSachBaiVietMoi", this.userId, Controller.configuration.get("pageSize"), "" + pageId,
					topic.title, topic.content, topic.author.username}
					);
			if(s.indexOf("false")>=0)
				return null;
			data = UtilString.Split(s, Constants.KyTuChiaTruongDL);
			int len = data.size();
			int i=0;
			
			while(i<len)
			{
				GroupTopic t=new GroupTopic(
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
	//Lấy danh sách các bài viết đã chia sẻ do bạn tạo
	public ArrayList GetSharedMyTopics(int pageId, Topic topic)
	{
		ArrayList data = new ArrayList();	
		topics = new ArrayList();
		try
		{
			String s = Html.SendRequest("",
					new String[] {Constants.Case,"xMaTaiKhoan","xPageSize","xPageId","xTieuDe","xNoiDung"},
					new String[] {"BaiVietBanTaoVaChiaSe", userId, Controller.configuration.get("pageSize"), "" + pageId,
					topic.title, topic.content}
					);
			if(s.indexOf("false")>=0)
				return null;
			data = UtilString.Split(s, Constants.KyTuChiaTruongDL);
			int len = data.size();
			int i=0;
			while(i<len)
			{
				Topic t = new Topic(new User(data.get(i+4).toString(), data.get(i+5).toString(),""),
						data.get(i).toString(),data.get(i+1).toString(),
						data.get(i+2).toString(),data.get(i+3).toString());
				topics.add(t);
				i += 6;
			}
			return topics;
		}
		catch(Exception ex)
		{
			return null;
		}
	}
	//Lấy danh sách các bài viết đã chia sẻ không phải do bạn tạo
	public ArrayList GetSharedOthersTopics(int pageId, Topic topic)
	{
		ArrayList data = new ArrayList();	
		topics = new ArrayList();
		try
		{
			String s = Html.SendRequest("",
					new String[] {Constants.Case,"xMaTaiKhoan","xPageSize","xPageId","xTieuDe","xNoiDung","xTaiKhoan"},
					new String[] {"BaiVietNguoiKhacChiaSe", userId, Controller.configuration.get("pageSize"), "" + pageId,
					topic.title, topic.content, topic.author.username}
					);
			if(s.indexOf("false")>=0)
				return null;
			data = UtilString.Split(s, Constants.KyTuChiaTruongDL);
			int len = data.size();
			int i=0;
			while(i<len)
			{
				Topic t = new Topic(new User(data.get(i+4).toString(), data.get(i+5).toString(),""),
						data.get(i).toString(),data.get(i+1).toString(),
						data.get(i+2).toString(),data.get(i+3).toString());
				topics.add(t);
				i += 6;
			}
			return topics;
		}
		catch(Exception ex)
		{
			return null;
		}
	}
	//Lấy danh sách các bài viết chưa chia sẻ của bạn
	public ArrayList GetNonSharedTopics(int pageId, Topic topic)
	{
		ArrayList data = new ArrayList();	
		topics = new ArrayList();
		try
		{
			String s = Html.SendRequest("",
					new String[] {Constants.Case,"xMaTaiKhoan","xPageSize","xPageId","xTieuDe","xNoiDung"},
					new String[] {"BaiVietBanChuaChiaSe", userId, Controller.configuration.get("pageSize"), "" + pageId,
					topic.title, topic.content}
					);
			if(s.indexOf("false")>=0)
				return null;
			data = UtilString.Split(s, Constants.KyTuChiaTruongDL);
			int len = data.size();
			int i=0;
			while(i<len)
			{
				Topic t = new Topic(new User(data.get(i+4).toString(), data.get(i+5).toString(),""),
						data.get(i).toString(),data.get(i+1).toString(),
						data.get(i+2).toString(),data.get(i+3).toString());
				topics.add(t);
				i += 6;
			}
			return topics;
		}
		catch(Exception ex)
		{
			return null;
		}
	}
	//Lấy danh sách các nhóm mà bạn là nhóm trưởng
	public ArrayList GetMyGroups(int pageId, Group group)
	{
		ArrayList data = new ArrayList();
		groups=new ArrayList();
		try
		{
			String s = Html.SendRequest("",
					new String[] {Constants.Case,"xMaTaiKhoan","xPageSize","xPageId", "xTenNhom"},
					new String[] {"NhomBanTao", userId, Controller.configuration.get("pageSize"), "" + pageId, group.groupName}
					);
			if(s.indexOf("false")>=0)
				return null;
			data = UtilString.Split(s, Constants.KyTuChiaTruongDL);
			int len = data.size();
			int i=0;
			while(i<len)
			{
				Group g=new Group(data.get(i).toString(),
						data.get(i+1).toString(),
						new User(data.get(i+2).toString(),data.get(i+3).toString(),"","")
						);
				groups.add(g);
				i += 4;
			}
			return groups;
		}
		catch(Exception ex)
		{
			return null;
		}
	}
	//Lấy danh sách các nhóm có bạn tham gia
	public ArrayList GetJoinGroups(int pageId, Group group)
	{
		ArrayList data = new ArrayList();
		groups=new ArrayList();
		try
		{
			String s = Html.SendRequest("",
					new String[] {Constants.Case,"xMaTaiKhoan","xPageSize","xPageId","xTenNhom","xTaiKhoan"},
					new String[] {"NhomBanThamGia", userId, Controller.configuration.get("pageSize"), "" + pageId,
					group.groupName, group.leader.username}
					);
			if(s.indexOf("false")>=0)
				return null;
			data = UtilString.Split(s, Constants.KyTuChiaTruongDL);
			int len = data.size();
			int i=0;
			while(i<len)
			{
				Group g=new Group(data.get(i).toString(),
						data.get(i+1).toString(),
						new User(data.get(i+2).toString(),data.get(i+3).toString(),"","")
						);
				groups.add(g);
				i += 4;
			}
			return groups;
		}
		catch(Exception ex)
		{
			return null;
		}
	}
	//Lấy danh sách các nhóm bạn đã chia sẻ bài viết
	public ArrayList GetSharedGroups(Topic t, int pageId)
	{
		ArrayList data = new ArrayList();
		groups=new ArrayList();
		try
		{
			String s = Html.SendRequest("",
					new String[] {Constants.Case,"xMaTaiKhoan","xMaBaiViet","xPageSize","xPageId"},
					new String[] {"NhomDaChiaSe", userId, t.topicId, Controller.configuration.get("pageSize"), "" + pageId}
					);
			if(s.indexOf("false")>=0)
				return null;
			data = UtilString.Split(s, Constants.KyTuChiaTruongDL);
			int len = data.size();
			int i=0;
			while(i<len)
			{
				Group g=new Group(data.get(i).toString(),
						data.get(i+1).toString(),
						new User(data.get(i+2).toString(),data.get(i+3).toString(),"","")
						);
				groups.add(g);
				i += 4;
			}
			return groups;
		}
		catch(Exception ex)
		{
			return null;
		}
	}
	//Lấy danh sách các nhóm bạn chưa chia sẻ bài viết
	public ArrayList GetNonSharedGroups(Topic t, int pageId)
	{
		ArrayList data = new ArrayList();
		groups=new ArrayList();
		try
		{
			String s = Html.SendRequest("",
					new String[] {Constants.Case,"xMaTaiKhoan","xMaBaiViet","xPageSize","xPageId"},
					new String[] {"NhomChuaChiaSe", userId, t.topicId, Controller.configuration.get("pageSize"), "" + pageId}
					);
			if(s.indexOf("false")>=0)
				return null;
			data = UtilString.Split(s, Constants.KyTuChiaTruongDL);
			int len = data.size();
			int i=0;
			while(i<len)
			{
				Group g=new Group(data.get(i).toString(),
						data.get(i+1).toString(),
						new User(data.get(i+2).toString(),data.get(i+3).toString(),"","")
						);
				groups.add(g);
				i += 4;
			}
			return groups;
		}
		catch(Exception ex)
		{
			return null;
		}
	}
	//Lấy danh sách tất cả các nhóm có bạn la thanh vien
	public ArrayList GetGroups(int pageId, Group group)
	{
		groups=new ArrayList();
		ArrayList data = new ArrayList();	
		try
		{
			
			String s = Html.SendRequest("",
					new String[] {Constants.Case,"xMaTaiKhoan","xPageSize","xPageId","xTenNhom","xTaiKhoan"},
					new String[] {"NhomBanLaThanhVien", userId, Controller.configuration.get("pageSize"), "" + pageId,
					group.groupName, group.leader.username}
					);
			if(s.indexOf("false")>=0)
				return null;
			
			data = UtilString.Split(s, Constants.KyTuChiaTruongDL);
			int len = data.size();
			int i=0;
			
			while(i<len)
			{
				Group g=new Group(data.get(i).toString(),
						data.get(i+1).toString(),
						new User(data.get(i+2).toString(),data.get(i+3).toString(),"","")
						);
				groups.add(g);
				i += 4;
			}
			return groups;
		}
		catch(Exception ex)
		{
			
			return null;
		}
	}
	//Lấy danh sách các yêu cầu tham gia
	public ArrayList GetMemberRequests(int pageId)
	{
		requests=new ArrayList();
		ArrayList data = new ArrayList();		
		try
		{
			String s = Html.SendRequest("",
					new String[] {Constants.Case,"xMaTaiKhoan","xPageSize","xPageId"},
					new String[] {"DanhSachYeuCauThamGiaNhomCuaBan", userId, Controller.configuration.get("pageSize"), "" + pageId}
					);
			if(s.indexOf("false")>=0)
				return null;
			data = UtilString.Split(s, Constants.KyTuChiaTruongDL);
			int len = data.size();
			int i=0;
			while(i<len)
			{
				Request r = new Request(data.get(i).toString(),
						 new Group(data.get(i+3).toString(),data.get(i+4).toString(),"",""),
						 new User(data.get(i+1).toString(),data.get(i+2).toString(),"",""),
						 data.get(i+5).toString()
						);
				requests.add(r);
				i += 6;
			}
			return requests;
		}
		catch(Exception ex)
		{
			return requests;
		}
	}
	//Lấy danh sách các yêu cầu tham gia nhóm của bạn
	public ArrayList GetMyRequests()
	{
		ArrayList data = new ArrayList();
		requests = new ArrayList();
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
				Request r=null;/* new Request(data.get(i).toString(),data.get(i+1).toString(),
						data.get(i+2).toString(),data.get(i+3).toString(),
						data.get(i+4).toString(),data.get(i+5).toString());*/
				requests.add(r);
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
			this.avatar = data;
			return true;
		}
		catch(Exception ex)		{
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
			return false;
		}
	}
	public boolean DeleteMember(Group g)
	{
		try
		{
			String data=Html.SendRequest("",
					new String[] {"CVM","xMaTaiKhoan","xMaNhom"},
					new String[] {"HuyTuCach",userId,g.groupId}
					);
			if(data.indexOf("false") >= 0)
				return false;
			return true;
		}
		catch(Exception ex)		{
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
	//Tạo bài viết và chia sẻ cho một nhóm đã chọn
	public Topic CreateTopic(String title, String content,Group g)
	{
		return Topic.Create(this, title, content, g);
	}
	//Tạo bài viết và chia sẻ cho các nhóm đã chọn
	public Topic CreateTopic(String title, String content,String groupIds)
	{
		return Topic.Create(this, title, content, groupIds);
	}
	//Chia sẻ bài viết
	public boolean ShareTopic(Topic topic, String groupIds)
	{
		return topic.Share(this, groupIds);
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
	public GroupTopic ShareTopic(Topic t,Group g)
	{
		try
		{
			String topicGroupId=Html.SendRequest("",
					new String[]{"CVM","xMaBaiViet","xMaNhom","xTaiKhoan"},
					new String[]{"ChiaSeBaiViet",t.topicId,g.groupId,this.username}
					);
			return new GroupTopic(topicGroupId);
		}
		catch(Exception ex)
		{
			return null;
		}
	}
	//Xóa chía sẻ bài viết
	public boolean DeleteShareTopic(String groupTopicId)
	{
		return GroupTopic.Delete(new GroupTopic(groupTopicId));
	}
	//Tạo bình luận
	public Comment CreateComment(GroupTopic t, String commentContent)
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
	public ArrayList SearchGroup(String keyword, int pageId)
	{
		ArrayList groups=new ArrayList();
		try
		{
			String s=Html.SendRequest("", 
					new String[]{"CVM","xTuKhoa","xMaTaiKhoan","xPageSize","xPageId"},
					new String[]{"TimKiemNhom",keyword,this.userId, Controller.configuration.get("pageSize"), "" + pageId}
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
						data.get(i+4).toString()
						);
				groups.add(t);
				i += 5;
			}
		}
		catch(Exception ex)
		{
			
		}
		return groups;
	}

	public boolean ViewedNewTopic(GroupTopic t) {
		try
		{
			String s=Html.SendRequest("", 
					new String[]{"CVM","xMaTaiKhoan","xMaBaiViet_Nhom"},
					new String[]{"XemBaiVietMoi",this.userId, t.groupTopicId}
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
