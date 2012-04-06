package model;
import util.UtilString;
import base.Constants;
import de.enough.polish.util.ArrayList;

public class GroupTopic {
	public String topicGroupId;
	public Topic topic;
	public User shareUser;
	public String shareDate;
	public String commentsCount;
	public ArrayList comments;
	
	public GroupTopic(String topicGroupId)
	{
		this.topicGroupId = topicGroupId;
	}
	public GroupTopic(String topicGroupId, Topic topic, User shareUser, 
			String shareDate, String commentsCount)
	{
		this.topicGroupId = topicGroupId;
		this.topic = topic;
		this.shareUser = shareUser;
		this.shareDate = shareDate;
		this.commentsCount = commentsCount;
	}
	
	public boolean GetDetails()
	{
		ArrayList data = new ArrayList();
		comments = new ArrayList();
		try
		{
			String s = Html.SendRequest("",
					new String[] {Constants.Case,"xMaBaiViet_Nhom"},
					new String[] {"DanhSachBinhLuanBaiViet", topicGroupId}
					);
			if(s.indexOf("false")>=0)
				return false;
			data =  UtilString.Split(s, Constants.KyTuChiaTruongDL);
			comments = new ArrayList();
			int len = data.size();
			int i=0;
			while(i<len)
			{
				Comment comment = new Comment(data.get(i).toString(),new User(data.get(i+1).toString(),
						data.get(i+2).toString()),data.get(i+3).toString(), data.get(i+4).toString());
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
	public static boolean Delete(GroupTopic t)
	{
		try
		{
			String id=Html.SendRequest("",
					new String[] {"CVM", "xMaBaiVietNhom"},
					new String[] {"XoaChiaSeBaiViet",t.topicGroupId}
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
