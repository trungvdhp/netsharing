package model;
import base.Constants;
import util.UtilString;
import de.enough.polish.util.ArrayList;
public class Topic {
	public String topicId;
	public String title;
	public String intro;
	public String content;
	public String createDate;
	public User author;
	public Topic(String topicId)
	{
		this.topicId=topicId;
	}
	public Topic(String title, String content, User author)
	{
		this.title = title;
		this.content = content;
		this.author = author;
	}
	public Topic(String title, String content)
	{
		this.title = title;
		this.content = content;
	}
	
	public Topic(String id, String title, String content)
	{
		this.topicId = id;
		this.title = title;
		this.content = content;
	}
	
	public Topic(String id, String title, String content, String createDate)
	{
		this.topicId = id;
		this.title = title;
		this.content = content;
		this.createDate = createDate;
	}
	public Topic(User author,String id,String title,String content,String createDate)
	{
		this.author=author;
		this.topicId = id;
		this.title = title;
		this.content = content;
		this.createDate = createDate;
	}
	public boolean GetSummary()
	{
		ArrayList data = new ArrayList();
		try
		{
			String s = Html.SendRequest("",
					new String[] {Constants.Case,"xMaBaiViet"},
					new String[] {"SoLuocBaiViet", topicId}
					);
			if(s.indexOf("false")>=0)
				return false;
			data = UtilString.Split(s, Constants.KyTuChiaTruongDL);
			this.title = data.get(0).toString();
			this.intro = data.get(1).toString();
			this.createDate = data.get(2).toString();
			return true;
		}
		catch(Exception ex)
		{
			return false;
		}
	}
	
	public boolean GetContent()
	{
		try
		{
			String s = Html.SendRequest("",
					new String[] {Constants.Case,"xMaBaiViet"},
					new String[] {"NoiDungBaiViet", topicId}
					);
			if(s.indexOf("false")>=0)
				return false;
			this.content = s;
			return true;
		}
		catch(Exception ex)
		{
			return false;
		}
	}
	
	public boolean GetDetails()
	{
		ArrayList data = new ArrayList();
		try
		{
			String s = Html.SendRequest("",
					new String[] {Constants.Case,"xMaBaiViet"},
					new String[] {"ChiTietBaiViet", topicId}
					);
			if(s.indexOf("false")>=0)
				return false;
			data =  UtilString.Split(s, Constants.KyTuChiaTruongDL);
			this.title = data.get(0).toString();
			this.content = data.get(1).toString();
			this.createDate = data.get(2).toString();
			return true;
		}
		catch(Exception ex)
		{
			return false;
		}
	}
	
	//Tạo bài viết và chia sẻ cho một nhóm đã chọn
	public static Topic Create(User u,String title,String content,Group group)
	{
		try
		{
			String buf=Html.SendRequest("",
					new String[] {"CVM","xMaTaiKhoan","xMaNhom", "xTieuDe","xNoiDung"},
					new String[] {"TaoBaiVietNhom",u.userId,group.groupId,title,content}
					);
			if(buf.indexOf("false")>=0) return null;
			ArrayList data=UtilString.Split(buf, Constants.KyTuChiaTruongDL);
			String id=data.get(0).toString();
			String createDate = UtilString.GetTimeString();
			return new Topic(u, id, title, content, createDate);
		}
		catch(Exception ex)
		{
			return null;
		}
	}
	//Tạo bài viết và chia sẻ cho các nhóm đã chọn
	public static Topic Create(User u,String title,String content,String groupIds)
	{
		try
		{
			String buf=Html.SendRequest("",
					new String[] {"CVM","xMaTaiKhoan","xMaNhom", "xTieuDe","xNoiDung"},
					new String[] {"TaoBaiVietCacNhom",u.userId, groupIds,title,content}
					);
			if(buf.indexOf("false")>=0) return null;
			ArrayList data=UtilString.Split(buf, Constants.KyTuChiaTruongDL);
			String id=data.get(0).toString();
			String createDate = UtilString.GetTimeString();
			return new Topic(u, id, title, content, createDate);
		}
		catch(Exception ex)
		{
			return null;
		}
	}
	
	//Chia sẻ cho các nhóm đã chọn
	public boolean Share(User u, String groupIds)
	{
		try
		{
			String buf=Html.SendRequest("",
					new String[] {"CVM","xMaTaiKhoan","xMaBaiViet", "xMaNhom"},
					new String[] {"ChiaSeBaiVietCacNhom",u.userId, this.topicId, groupIds}
					);
			if(buf.indexOf("false")>=0) return false;
			return true;
		}
		catch(Exception ex)
		{
			return false;
		}
	}
	
	public boolean Update()
	{
		try
		{
			String id=Html.SendRequest("",
					new String[] {"CVM", "xMaBaiViet", "xTieuDe", "xNoiDung"},
					new String[] {"SuaBaiViet", topicId, title, content}
					);
			if(id.indexOf("false")>=0) return false;
			createDate = UtilString.GetTimeString();
			return true;
		}
		catch(Exception ex)
		{
			return false;
		}
	}
	
	public static boolean Delete(Topic t)
	{
		try
		{
			String id=Html.SendRequest("",
					new String[] {"CVM", "xMaBaiViet"},
					new String[] {"XoaBaiViet",t.topicId}
					);
			if(id.indexOf("false")>=0) return false;
			return true;
		}
		catch(Exception ex)
		{
			return false;
		}
	}
}
