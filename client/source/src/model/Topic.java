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
	private Html html=new Html();

	public Topic()
	{
		this.topicId = "";
		this.intro = "";
		this.title="";
		this.content = "";
		this.createDate="";
	}
	public Topic(String topicId)
	{
		this.topicId=topicId;
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
	
	public boolean GetSummary()
	{
		ArrayList data = new ArrayList();
		try
		{
			String s = html.SendRequest("",
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
			String s = html.SendRequest("",
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
			String s = html.SendRequest("",
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
	
	public boolean Create(String userId)
	{
		try
		{
			String id=html.SendRequest("",
					new String[] {"CVM","xMaTaiKhoan", "xTieuDe","xNoiDung"},
					new String[] {"TaoBaiViet",userId,title,content}
					);
			if(id.indexOf("false")>=0) return false;
			topicId = id;
			createDate = UtilString.GetTimeString();
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
			String id=html.SendRequest("",
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
	
	public boolean Delete()
	{
		try
		{
			String id=html.SendRequest("",
					new String[] {"CVM", "xMaBaiViet"},
					new String[] {"XoaBaiViet",topicId}
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
