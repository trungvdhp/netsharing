package util;
import de.enough.polish.util.Locale;
import de.enough.polish.util.ArrayList;
import java.util.Date;
import base.Constants;
public class UtilString {
	public static ArrayList Split(String inputString, String delimeter)
	{
		int beginIndex=0;
		int endIndex;
		int span = delimeter.length();
		ArrayList rs = new ArrayList();
		while((endIndex=inputString.indexOf(delimeter,beginIndex))>=0)
		{
			rs.add(inputString.substring(beginIndex, endIndex));
			beginIndex = endIndex+span;
		}
		if(beginIndex<inputString.length()-1) rs.add(inputString.substring(beginIndex));
		return rs;
	}
	
	 public static final String GetTimeString()
	 {
		 return Locale.formatDate(new Date(), Constants.dateFormat);
	 }
	 
	 public static final String GetTimeString(Date date)
	 {
		 return Locale.formatDate(date, Constants.dateFormat);
	 }
}
