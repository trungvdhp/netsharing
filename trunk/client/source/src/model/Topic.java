package model;
import base.Constants;
import util.UtilString;
public class Topic {
	public String topicId;
	public String commentsCount;
	public String title;
	public String intro;
	public String content;
	public String createDate;
	private Html html=new Html();

	public Topic(String topicId)
	{
		this.topicId=topicId;
		intro = "";
		title="";
		content = "";
		commentsCount = "-1";
		createDate="";
	}
	
	public boolean getSummary()
	{
		String data[] = new String[4];
		try
		{
			String s = html.SendRequest("",
					new String[] {Constants.Case,"xMaBaiViet"},
					new String[] {"SoLuocBaiViet", topicId}
					);
			if(s.equalsIgnoreCase("false"))
				return false;
			data = UtilString.Split(s, Constants.KyTuChiaTruongDL);
			this.title = data[0];
			this.intro = data[1];
			this.createDate = data[2];
			this.commentsCount =data[3];
			return true;
		}
		catch(Exception ex)
		{
			return false;
		}
	}
	
	public boolean getContent()
	{
		try
		{
			String s = html.SendRequest("",
					new String[] {Constants.Case,"xMaBaiViet"},
					new String[] {"NoiDungBaiViet", topicId}
					);
			if(s.equalsIgnoreCase("false"))
				return false;
			this.content = s;
			return true;
		}
		catch(Exception ex)
		{
			return false;
		}
	}
	
	public boolean getDetails()
	{
		String data[] = new String[5];
		try
		{
			String s = html.SendRequest("",
					new String[] {Constants.Case,"xMaBaiViet"},
					new String[] {"ChiTietBaiViet", topicId}
					);
			if(s.equalsIgnoreCase("false"))
				return false;
			data = UtilString.Split(s, Constants.KyTuChiaTruongDL);
			this.title = data[0];
			this.intro = data[1];
			this.createDate = data[2];
			this.commentsCount =data[3];
			this.content = data[4];
			return true;
		}
		catch(Exception ex)
		{
			return false;
		}
	}
	
	public static void getNewTopics()
	{
		
	}
}
