package model;

public class Topic {
	public String topicId;
	public int commentsCount;
	private String content;
	private String title;
	public Topic(String topicId)
	{
		this.topicId=topicId;
		title="";
		content = "";
		commentsCount = -1;
	}
	public void getContent()
	{
		if(content.length()==0)
		{
			
		}
		else
		{
			
		}
	}
	public void getTitle()
	{
		if(content.length()==0)
		{
			
		}
		else
			return;
	}
}
