package TPSP16S2;
import java.util.ArrayList;
import java.util.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Utils {
	
	
	public static boolean validateName(String name){
		boolean result = true;
		for(char c : name.toCharArray()){
			if((c<65)||(c>90&&c<97)||(c>122)){
				if(c!=' '){
					result = false;
				}
			}
		}
		return result;
	}
	
	public static boolean validateDate(String date){
		
		//check format
		boolean result = false;
		String pattern = "\\d{1,2}(-|/)\\d{1,2}(-|/)\\d{4}";
        Pattern p = Pattern.compile(pattern);     
        Matcher m = p.matcher(date);
        if(m.matches()){
            result = true;
        }
        
        //check date
        SimpleDateFormat sdf1 = new SimpleDateFormat("dd/MM/yyyy");
        SimpleDateFormat sdf2 = new SimpleDateFormat("dd-MM-yyyy");
		sdf1.setLenient(false);
		sdf2.setLenient(false);
		
		if(date.contains("-")){
			try {
				Date parsedDate = sdf2.parse(date);
			} catch (ParseException e) {
				result = false;
			}
		}

		if(date.contains("/")){
			try {
				Date parsedDate = sdf1.parse(date);
			} catch (ParseException e) {
				result = false;
			}
		}
        
		return result;
	}
	
	public static boolean validateID(String ID){
		boolean result = false;
		String pattern = "\\d*";
        Pattern p = Pattern.compile(pattern);     
        Matcher m = p.matcher(ID);
        if(m.matches()){
            result = true;
        }
		return result;
	}
	
	public static ArrayList<String> sortAttractions(ArrayList<String> arractions){
		ArrayList<String> result = new ArrayList<String>();
		
		
		return result;
	}
	
	public static boolean validateHeight(String height){
		return false;
	}
	
	public static long getDateDiff(Date date1, Date date2, TimeUnit timeUnit) {
	    long diffInMillies = date2.getTime() - date1.getTime();
	    return timeUnit.convert(diffInMillies,TimeUnit.MILLISECONDS);
	}
	
	public static Date stringToDate(String s){ //supposing s is in format of (d)d/(M)M/(yy)yy or (d)d-(M)M-(yy)yy
		Date date = null;
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yy");
		s = s.replace("-", "/");
		try {
			date = sdf.parse(s);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			System.out.println("String: " + s + " " + e);
		}
		return date;
	}
	
	public static String dateToString(Date date){
		SimpleDateFormat df = new SimpleDateFormat("dd/MM/yy");
		SimpleDateFormat df1 = new SimpleDateFormat("dd-MM-yy");
		String result;
		try{
			result = df.format(date);
		}catch(Exception e){
			result = df1.format(date);
		}
		return result;
	}
	
	public static String sortVisitingDate(String history){
		String[] splited = history.split(" ");
		ArrayList<String> sortedDates = new ArrayList<String>();
		String attractionName = "";
		boolean isFirst = true;
		for(String s : splited){
			if(!Utils.validateDate(s)){
				attractionName = attractionName + " " + s;
			}else{
				if(isFirst){
					sortedDates.add(s);
					isFirst = false;
				}
				else{
					if(Utils.stringToDate(s).before(Utils.stringToDate(sortedDates.get(0)))){
						sortedDates.add(0,s);
					}else{
						int index = 0;
						while(index<sortedDates.size()&&Utils.stringToDate(s).after(Utils.stringToDate(sortedDates.get(index)))){
							index++;
						}
						sortedDates.add(index, s);
					}
				}
			}
		}
		attractionName = attractionName.replaceAll("^\\s+","");
		String updatedHistory = attractionName;
		for(String date : sortedDates){
			updatedHistory = updatedHistory + " " + date;
		}
		//System.out.println(updatedHistory);
		return updatedHistory;
		//splitedAttractions.add(0, attractionName);
	}
}
