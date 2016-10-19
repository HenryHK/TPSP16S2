package TPSP16S2;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.TimeUnit;

public class UtilsTest {
	public static void main(String[] args) {
		
		/*Name validation*/
//		System.out.println(Utils.validateName("Henry Han"));
//		System.out.println(Utils.validateName("Henry1 Han"));
//		System.out.println(Utils.validateName("H3enry Han"));
//		System.out.println(Utils.validateName("HenryHan"));
//		System.out.println(Utils.validateName("Henry H%an"));
		
		/*Date Validation*/
//		System.out.println(Utils.validateDate("1/2/1994"));
//		System.out.println(Utils.validateDate("1/12/1994"));
//		System.out.println(Utils.validateDate("01-1-1994"));
//		System.out.println(Utils.validateDate("11-13-1994"));
		
		/*split test*/
//		String s = "add ID 201391; birthday 20/12/2976; name Pac Man";
//		String ss = "add ID 201391";
//		String s1[] = ss.split(" ", 2);
//		String s2[] = s1[1].split(";");
//		System.out.println(s1[0]);
//		System.out.println(s1[1]);
//		System.out.println(s2[0]);
//		System.out.println(s2[1]);
//		System.out.println(s2[2]);
		
		/*ID validation*/
//		System.out.println(Utils.validateID("00927398176"));
//		System.out.println(Utils.validateID("0092sd9837"));
		
		/*Date print*/
//		String s = "9/7/2016";
//		SimpleDateFormat df = new SimpleDateFormat("dd/MM/yy");
//		try {
//			System.out.println(df.format(df.parse(s)));
//		} catch (ParseException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		
		/*age calculation*/
//		SimpleDateFormat df = new SimpleDateFormat("dd/MM/yy");
//		Date date = null;
//		try {
//			date = df.parse("15/10/2016");
//		} catch (ParseException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		Date birthday = null;
//		try {
//			birthday = df.parse("15/10/1994");
//		} catch (ParseException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		
//		long days = 0;
//		days = Utils.getDateDiff(birthday, date, TimeUnit.DAYS);
//		
//		long years = days/365;
//		
//		System.out.println("Years: " + years);
		
		/*sort card list*/
//		Card c1 = new Card("012344", "Henry", "28/04/94");
//		Card c2 = new Card("12345", "Han", "29/04/94");
//		
//		ArrayList<Card> list = new ArrayList<Card>();
//		list.add(c2);
//		list.add(c1);
//		for(Card c : list){
//			System.out.println(c.getID());
//		}
//		Collections.sort(list);
//		for(Card c : list){
//			System.out.println(c.getID());
//		}
		
		/*sort HashMap*/
//		HashMap<String, Integer> record = new HashMap<String, Integer>();
//		record.put("test1", 2);
//		record.put("test2", 1);
//		record.put("test3", 5);
//		record.put("test4", 3);
//		System.out.println("unsorted: " + record);
//		MapValueComparator mvc = new MapValueComparator(record);
//        TreeMap<String, Integer> sortedMap = new TreeMap<String, Integer>(mvc);
//        sortedMap.putAll(record);
//        System.out.println("sorted: " + sortedMap);
//        for(Map.Entry<String,Integer> entry : sortedMap.entrySet()){
//        	String key = entry.getKey();
//        	Integer value = entry.getValue();
//        	System.out.println(key + " => " + value);
//        }
        
		/*getAge method*/
//		Card c = new Card("0001", "Han", "10-10-2015");
//		Date date = null;
//		date = Utils.stringToDate("10/11/2016");
//		System.out.println(c.getAge(date));
	
//		/*sort visiting dates*/
//		String history1 = "4D Theater 19/6/2015 18/7/2013 17/4/2016 11/11/2010";
//		Utils.sortVisitingDate(history1);
	}
}
