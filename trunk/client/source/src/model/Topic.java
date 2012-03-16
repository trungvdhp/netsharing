package model;
import base.Constants;

public class Topic {
	public String topicId;
	public String commentsCount;
	public String title;
	public String intro;
	public String content;
	public String createDate;
	
	public Topic(String topicId)
	{
		this.topicId=topicId;
		intro = "";
		title="";
		content = "";
		commentsCount = "-1";
		createDate="";
	}
	
	public void getSummary()
	{
		String data[] = new String[4];
		try
		{
			String s = html.SendRequest("",
					new String[] {Constants.Case,"xMaBaiViet"},
					new String[] {"SoLuocBaiViet", topicId}
					);
			data = s.split(Constants.KyTuChiaTruongDL);
			this.title = data[0];
			this.intro = data[1];
			this.createDate = data[2];
			this.commentsCount =data[3];
		}
		catch(Exception ex)
		{
			
		}
	}
	
	public void getContent()
	{
		try
		{
			this.content = html.SendRequest("",
					new String[] {Constants.Case,"xMaBaiViet"},
					new String[] {"NoiDungBaiViet", topicId}
					);
		}
		catch(Exception ex)
		{
			
		}
	}
	
	public void getDetails()
	{
		String data[] = new String[5];
		try
		{
			String s = html.SendRequest("",
					new String[] {Constants.Case,"xMaBaiViet"},
					new String[] {"ChiTietBaiViet", topicId}
					);
			data = s.split(Constants.KyTuChiaTruongDL);
			this.title = data[0];
			this.intro = data[1];
			this.createDate = data[2];
			this.commentsCount =data[3];
			this.content = data[4];
		}
		catch(Exception ex)
		{
			
		}
	}
}
