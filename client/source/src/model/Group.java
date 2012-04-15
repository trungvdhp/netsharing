package model;

import control.Controller;
//import control.UserItem;
import util.UtilString;
//import de.enough.polish.ui.Display;
//import de.enough.polish.ui.Displayable;
import de.enough.polish.util.ArrayList;
import base.Constants;

public class Group {
	public String groupId;
	public String groupName;
	public User leader;
	public String description;
	public String rule;
	public String createDate;
	public String topicsCount;
	public String membersCount;
	public String requestsCount;
	public ArrayList topics;
	public ArrayList members;
	public ArrayList requests;
	//for delete action
	public Group(String groupId)
	{
		this.groupId = groupId;
	}
	public Group(String groupId, String groupName)
	{
		this.groupId = groupId;
		this.groupName = groupName;
	}
	//for update action
    public Group(String groupId, String groupName, String description, String rule)
    {
            this.groupId = groupId;
            this.groupName = groupName;
            this.description = description;
            this.rule = rule;
    }
    public Group(String groupId, String groupName, User leader)
    {
            this.groupId = groupId;
            this.groupName = groupName;
            this.leader = leader;
    }
  //for the action that show the list of group
    public Group(String groupId, String groupName, User leader, String createDate)
	{
		this.groupId = groupId;
		this.groupName = groupName;
		this.leader = leader;
		this.createDate = createDate;
	}
    
    //for the action that show the list and detail of group
	public Group(String groupId, String groupName, User leader, String createDate,
			String topicsCount, String membersCount)
	{
		this.groupId = groupId;
		this.groupName = groupName;
		this.leader = leader;
		this.createDate = createDate;
		this.topicsCount = topicsCount;
		this.membersCount = membersCount;
	}
	public boolean GetInfo()
	{
		ArrayList data = new ArrayList();
		try
		{
			String s = Html.SendRequest("",
					new String[] {Constants.Case,"xMaNhom"},
					new String[] {"ChiTietNhom", groupId}
					);
			if(s.equals("false"))
				return false;
			data = UtilString.Split(s, Constants.KyTuChiaTruongDL);
			this.description = data.get(0).toString();
			this.rule = data.get(1).toString();
			this.createDate = data.get(2).toString();
			this.topicsCount = data.get(3).toString();
			this.membersCount = data.get(4).toString();
			return true;
		}
		catch(Exception ex)
		{
			return false;
		}
	}
	
	public ArrayList GetMembers(int pageId, String tuKhoa)
	{
		ArrayList data = new ArrayList();
		members = new ArrayList();
		try
		{
			String s = Html.SendRequest("",
					new String[] {Constants.Case,"xMaNhom","xPageSize","xPageId","xTuKhoa"},
					new String[] {"DanhSachThanhVienNhom", groupId, Controller.configuration.get("pageSize"), "" + pageId, tuKhoa}
					);
			if(s.indexOf("false")>=0)
				return null;
			data = UtilString.Split(s, Constants.KyTuChiaTruongDL);
			int len = data.size();
			int i=0;
			while(i<len)
			{
				User t = new User(data.get(i).toString(),data.get(i+1).toString(),
						"", data.get(i+2).toString());
				members.add(t);
				i += 3;
			}
			return members;
		}
		catch(Exception ex)
		{
			return members;
		}
	}
	
	public ArrayList GetRequests()
	{
		ArrayList data = new ArrayList();
		requests = new ArrayList();
		try
		{
			String s = Html.SendRequest("",
					new String[] {Constants.Case,"xMaNhom"},
					new String[] {"DanhSachYeuCauThamGiaNhom", groupId}
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
						 data.get(i+5).toString());
				requests.add(t);
				i += 6;
			}
			return requests;
		}
		catch(Exception ex)
		{
			return requests;
		}
	}
	public ArrayList GetTopics(int pageId, Topic topic)
	{
		topics=new ArrayList();
		ArrayList data = new ArrayList();		
		try
		{
			String s = Html.SendRequest("",
					new String[] {Constants.Case,"xMaNhom","xPageSize","xPageId","xTieuDe","xNoiDung","xTaiKhoan"},
					new String[] {"DanhSachBaiVietTheoNhom", groupId, Controller.configuration.get("pageSize"), "" + pageId,
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
						data.get(i+8).toString()
						);
				topics.add(t);
				i += 11;
			}
			
			return topics;
		}
		catch(Exception ex)
		{
			return topics;
		}
	}
	public boolean Update()
	{
		try
		{
			String id=Html.SendRequest("",
					new String[] {"CVM", "xMaNhom", "xTenNhom", "xMoTa","xLuat"},
					new String[] {"SuaNhom", groupId, groupName, description, rule}
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
	
	public static boolean Delete(Group g)
	{
		try
		{
			String id=Html.SendRequest("",
					new String[] {"CVM", "xMaNhom"},
					new String[] {"XoaNhom", g.groupId}
					);
			if(id.indexOf("false")>=0) return false;
			return true;
		}
		catch(Exception ex)
		{
			return false;
		}
	}

	public static Group Create(User user, String groupName) {
		try
		{
			String id=Html.SendRequest("",
					new String[] {"CVM","xMaTaiKhoan","xTenNhom"},
					new String[] {"TaoNhom",user.userId,groupName}
					);
			if(id.indexOf("false")>=0) return null;
			return new Group(id);
		}
		catch(Exception ex)
		{
			return null;
		}
	}
	
}
