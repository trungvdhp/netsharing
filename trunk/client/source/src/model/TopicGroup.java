package model;
import util.UtilString;
import base.Constants;
import de.enough.polish.util.ArrayList;

public class TopicGroup {
	public String topicGroupId;
	public Topic topic;
	public Group group;
	public String shareUserId;
	public String shareUserFullname;
	public String shareDate;
	public String commentsCount;
	public ArrayList comments;

	private Html html = new Html();
	
	public TopicGroup()
	{
		this.topicGroupId="";
		this.topic = new Topic();
		this.group = new Group();
		this.shareUserId = "";
		this.shareUserFullname = "";
		this.shareDate = "";
		this.commentsCount = "0";
	}
	
	public TopicGroup(String topicGroupId)
	{
		this.topicGroupId = topicGroupId;
	}
	
	public TopicGroup(String topicGroupId, String topicId, String groupId, String shareUserId, 
			String shareUserFullname, String shareDate, String commentsCount)
	{
		this.topicGroupId = topicGroupId;
		this.topic = new Topic(topicId);
		this.group = new Group(groupId);
		this.shareUserId = shareUserId;
		this.shareUserFullname = shareUserFullname;
		this.shareDate = shareDate;
		this.commentsCount = commentsCount;
	}
	
	public boolean GetDetails()
	{
		ArrayList data = new ArrayList();
		try
		{
			String s = html.SendRequest("",
					new String[] {Constants.Case,"xMaBaiVietNhom"},
					new String[] {"ChiTietBaiVietNhom", topicGroupId}
					);
			if(s.indexOf("false")>=0)
				return false;
			data =  UtilString.Split(s, Constants.KyTuChiaTruongDL);
			topic.content = data.get(0).toString();
			comments = new ArrayList();
			int len = data.size();
			int i=1;
			while(i<len)
			{
				Comment comment = new Comment(data.get(i).toString(),data.get(i+1).toString(),
						data.get(i+2).toString(),data.get(i+3).toString(),data.get(i+4).toString());
				comments.add(comment);
				i += 5;
			}
			return true;
		} 
		catch(Exception ex)
		{
			return false;
		}
	}
}
