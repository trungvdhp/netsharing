package model;

import control.MessageBox;
import util.UtilString;
import de.enough.polish.ui.Display;
import de.enough.polish.ui.Displayable;
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
	//for update action
    public Group(String groupId, String groupName, String description, String rule)
    {
            this.groupId = groupId;
            this.groupName = groupName;
            this.description = description;
            this.rule = rule;
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
	public boolean GetDetails(Display display,Displayable disp)
	{
		ArrayList data = new ArrayList();
		try
		{
			String s = Html.SendRequest("",
					new String[] {Constants.Case,"xMaNhom"},
					new String[] {"ChiTietNhom", groupId}
					);
			//MessageBox.Show(s, display, disp);
			if(s.equals("false"))
				return false;
			data = UtilString.Split(s, Constants.KyTuChiaTruongDL);
			this.groupName = data.get(0).toString();
			this.leader.username =data.get(6).toString();
			this.description = data.get(1).toString();
			this.rule = data.get(2).toString();
			this.createDate = data.get(3).toString();
			this.topicsCount = data.get(5).toString();
			this.membersCount = data.get(4).toString();
			return true;
		}
		catch(Exception ex)
		{
			//MessageBox.Show(ex.toString(), display, disp);
			return false;
		}
	}
	
	public ArrayList GetMembers()
	{
		ArrayList data = new ArrayList();		
		try
		{
			String s = Html.SendRequest("",
					new String[] {Constants.Case,"xMaNhom"},
					new String[] {"DanhSachThanhVienNhom", groupId}
					);
			if(s.indexOf("false")>=0)
				return null;
			data = UtilString.Split(s, Constants.KyTuChiaTruongDL);
			int len = data.size();
			int i=0;
			while(i<len)
			{
				User t = new User(data.get(i).toString(),data.get(i+1).toString(),
						data.get(i+2).toString(),data.get(i+3).toString());
				members.add(t);
				i += 4;
			}
			return members;
		}
		catch(Exception ex)
		{
			return data;
		}
	}
	
	public ArrayList GetRequests()
	{
		ArrayList data = new ArrayList();		
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
				Request t = null;//new Request(data.get(i).toString(),data.get(i+1).toString(),data.get(i+2).toString(),data.get(i+3).toString(),data.get(i+4).toString(),data.get(i+5).toString());
				requests.add(t);
				i += 6;
			}
			return requests;
		}
		catch(Exception ex)
		{
			return data;
		}
	}
	public ArrayList GetTopics()
	{
		return GetTopics(false);
	}
	public ArrayList GetTopics(boolean refresh)
	{
		if(topics!=null&&!refresh) return topics;
		topics=new ArrayList();
		ArrayList data = new ArrayList();		
		try
		{
			String s = Html.SendRequest("",
					new String[] {Constants.Case,"xMaNhom"},
					new String[] {"DanhSachBaiVietTheoNhom", groupId}
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
