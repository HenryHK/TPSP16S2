package TPSP16S2;


import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * card class, ID, birthday and name are compulsory fields
 * address, height and visited attractions are optional
 * contains necessary set and get methods
 * @author lhan
 *
 */

public class Card implements Comparable<Card>{
	private String ID;
	private String name;
	private String birthday;
	private int height;
	private String address;
	private ArrayList<String> attractions;
	
	/**
	 * constructor of card, ID name and birthday are must
	 * @param ID
	 * @param name
	 * @param birthday
	 */
	public Card(String ID, String name, String birthday){
		this.ID = ID;
		this.name = name;
		this.birthday = birthday;
		attractions = new ArrayList<String>();
	}
	
	
	public void setID(String ID){
		this.ID = ID;
	}
	
	public void setName(String name){
		this.name = name;
	}
	
	public void setBirthday(String birthday){
		this.birthday = birthday;
	}
	
	/**
	 * set address
	 * @param address
	 */
	public void setAddress(String address){
		this.address = address;
	}
	
	/**
	 * set addrres
	 * @param height
	 */
	public void setHeight(int height){
		this.height = height;
	}
	
	/**
	 * add visiting attractions
	 * @param attraction
	 */
	public void setVisitingAttractions(String a){
		attractions.add(a);
	}
	
	/**
	 * get ID
	 * @return ID
	 */
	
	public String getID(){
		return this.ID;
	}
	
	/**
	 * get Name
	 * @return name
	 */
	public String getName(){
		return this.name;
	}
	
	/**
	 * get birthday
	 * @return birthday
	 */
	public String getBirthday(){
		return this.birthday;
	}
	
	/**
	 * get height
	 * @return height
	 */
	public int getHeight(){
		return this.height;
	}
	
	/**
	 * get address
	 * @return address
	 */
	public String getAddress(){
		return this.address;
	}
	
	/**
	 * get attractions
	 * @return attractions
	 */
	public ArrayList<String> getAttractions(){
		return this.attractions;
	}

	
	public int getAge(Date date){
		int age=0;
		long days = Utils.getDateDiff(Utils.stringToDate(this.birthday), date, TimeUnit.DAYS);
		return (int) (days/365);
	}

	@Override
	public int compareTo(Card c) {
		int result = 0;
		int thisCardNum = Integer.parseInt(this.ID);
		int otherCardNum = Integer.parseInt(c.ID);
		if(thisCardNum > otherCardNum)
			return 1;
		else
			return -1;
	}
	

}
