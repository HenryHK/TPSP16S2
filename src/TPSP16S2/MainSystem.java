package TPSP16S2;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.TimeUnit;

public class MainSystem {
	
	private ArrayList<Card> loadedCards = new ArrayList<Card>();
	private ArrayList<Instruction> instructions = new ArrayList<Instruction>();
	public static HashMap<String, Attraction> attractions = new HashMap<String, Attraction>();
	final private Attraction SPIDERMANESCAPE = new Attraction("Spiderman Escape", 100, Integer.MAX_VALUE, 8);
	final private Attraction ICEAGEADVENTURE = new Attraction("Ice Age Adventure", 0, 200, 8);
	final private Attraction CANYONBLASTER = new Attraction("Canyon Blaster", 120, Integer.MAX_VALUE, 8);
	final private Attraction FOURDTHEATRE = new Attraction("4D Theatre", 0, Integer.MAX_VALUE, 0);
	final private Attraction FLOWRIDER = new Attraction("Flow Rider", 100, Integer.MAX_VALUE, 0);
	final private Attraction CAROUSEL = new Attraction("Carousel", 0, 200, 0);
	
	private String reportFileName;
	private String resultFileName;
	
	public MainSystem(String result, String report){
		this.reportFileName = report;
		this.resultFileName = result;
		attractions.put(SPIDERMANESCAPE.getName(), SPIDERMANESCAPE);
		attractions.put(ICEAGEADVENTURE.getName(), ICEAGEADVENTURE);
		attractions.put(CANYONBLASTER.getName(),CANYONBLASTER);
		attractions.put(FOURDTHEATRE.getName(),FOURDTHEATRE);
		attractions.put(FLOWRIDER.getName(), FLOWRIDER);
		attractions.put(CAROUSEL.getName(), CAROUSEL);
	}
	
	/**
	 * load card file
	 * @param fileName cardFile name
	 */
	public void loadCards(String fileName){
		BufferedReader br;
		ArrayList<String> fileContent = new ArrayList<String>();
		
		/*file read-in*/
		try{
			String current = null;
			br = new BufferedReader(new FileReader(fileName));
			while((current = br.readLine())!=null){
				fileContent.add(current);
			}	
		}catch(Exception e){
			e.printStackTrace();
		}
		
		/*refine the arraylist, omit extra blank lines*/
		String previous = null;
		ArrayList<Integer> marks = new ArrayList<Integer>();
		for(int index=0; index<fileContent.size(); index++){
			if(previous!=null&&fileContent.get(index).trim().length()==0&&previous.trim().length()==0){
				fileContent.remove(index);
				index--;
			}else{
				previous = fileContent.get(index);
			}
			
		}

		int count = 0;
		
		for(int i=0; i<fileContent.size(); ++i){
			String name = null;
			String ID = null;
			String birthday = null;
			int height = 0;
			String address = null;
			ArrayList<String> visitedAttractions = new ArrayList<String>();
			boolean acceptable = true;
			
			do{
				
				String current = fileContent.get(i);
				if(current.contains("ID")){ //get ID
					 ID = current.replace("ID", "").trim();
					 acceptable = acceptable & Utils.validateID(ID);
				 }else if(current.contains("name")){ //get Name
					 name = current.replace("name", "").replaceAll("^\\s+","").replaceAll("\\s*$", "");
					 acceptable = acceptable & Utils.validateName(name);
				 }else if(current.contains("birthday")){ //get birthday
					 birthday = current.replace("birthday", "").replaceAll("^\\s+","").replaceAll("\\s*$", "");
					 acceptable = acceptable & Utils.validateDate(birthday);
				 }else if(current.contains("height")){ //get height
					 try {
							height = Integer.parseInt(current.replace("height", "").replace("cm", "").trim());
						} catch (NumberFormatException e) {
							//height = -1;
						}
				 }else if(current.contains("address")){ //get address
					 address = current.replace("address", "").replaceAll("^\\s+","");
					 while(true){ //get the whole address part
						 i++;
						 if(i>=fileContent.size()){
							 i--;
							 break;
						 }
						 current = fileContent.get(i);
						 if((current.contains("name"))
								 ||(current.contains("ID"))||(current.contains("birthday"))
								 ||(current.contains("height"))||(current.contains("Spiderman"))
								 ||(current.contains("Adventure"))||(current.contains("Canyon"))
								 ||(current.contains("Theatre"))||(current.contains("Rider"))
								 ||(current.contains("Carousel")||(current.contains("address"))||(current.trim().length()==0))){//break if there are any other info no address
							 i--;
							 break;
						 }else{
							 address = address + " "+ current.replace("address", "").replaceAll("^\\s+","");
						 }					
					 }
				 }else{//get attraction
					 if(current!=null){
						 if(current.contains("Spiderman Escape")||current.contains("Ice Age Adventure")
								 ||current.contains("Canyon Blaster")||current.contains("4D Theatre")
								 ||current.contains("Flow Rider")||current.contains("Carousel")){
							visitedAttractions.add(current);
						 }
					 }
					 while(true){ //get the remaining attractions
						 i++;
						 if(i>=fileContent.size()){
							 i--;
							 break;
						 }
						 current = fileContent.get(i);
						 if((current.contains("name"))
								 ||(current.contains("ID"))&&(current.contains("birthday"))
								 ||(current.contains("height"))||(current.contains("address"))||(current.trim().length()==0)){
							 i--;
							 break;
						 }else{
							 if(current.contains("Spiderman Escape")||current.contains("Ice Age Adventure")
									 ||current.contains("Canyon Blaster")||current.contains("4D Theatre")
									 ||current.contains("Flow Rider")||current.contains("Carousel")){
								visitedAttractions.add(current);
							 }
						 }		
					 }
				 }
				i++;
				if(i>=fileContent.size()){
					break;
				}
				
			}while(fileContent.get(i)!=null&&fileContent.get(i).trim().length()!=0);
			if (acceptable) {
				/*init new card*/
				Card newCard = new Card(ID, name, birthday);
				if (height > 0) {
					newCard.setHeight(height);
				}
				if (address != null) {
					newCard.setAddress(address);
				}
				if (visitedAttractions != null) {
					for (String s : visitedAttractions){
						newCard.setVisitingAttractions(s);
					}
				}
				/*add card into loaded cards collections*/
				loadedCards.add(newCard);
			}
		}
	}
	
	public void loadInstructions(String fileName){
		
		BufferedReader br;
		ArrayList<String> content = new ArrayList<String>();
		
		/*file read-in*/
		try{
			String current = null;
			br = new BufferedReader(new FileReader(fileName));
			while((current = br.readLine())!=null){
				content.add(current);
			}	
		}catch(Exception e){
			e.printStackTrace();
		}
		
		
		/*seperate instructions*/
		for(int i=0; i<content.size(); ++i){
			if(content.get(i).trim().length()>0){
				String ins[] = content.get(i).split(" ", 2);
				Instruction newInstruction = new Instruction(ins[0], ins[1]);
				instructions.add(newInstruction);
			}
		}

	}
	
	public void processInstructions(){
		for (Instruction i : instructions){
			boolean result = processInstruction(i);
		}
	}
	
	public boolean processInstruction(Instruction i){
		String command = i.getCommand();
		if(command.equals("add")){
			String cardInfo[] = i.getBody().split(";");
			String name = null;
			String ID = null;
			String birthday = null;
			int height = 0;
			String address = null;
			ArrayList<String> visitedAttractions = new ArrayList<String>();
			boolean acceptable = true;
			for(String s : cardInfo){
				if(s.contains("name")){
					name = s.replace("name", "").replaceAll("^\\s+","").replaceAll("\\s*$", "");
					acceptable = acceptable & Utils.validateName(name);
				}else if(s.contains("ID")){
					ID = s.replace("ID", "").trim();
					acceptable = acceptable & Utils.validateID(ID);
				}else if(s.contains("address")){
					address = s.replace("address", "").replaceAll("^\\s+","");
				}else if(s.contains("height")){
					try {
						height = Integer.parseInt(s.replace("height", "").replace("cm", "").trim());
					} catch (NumberFormatException e) {
						//e.printStackTrace();
					}
				}else if(s.contains("birthday")){
					birthday = s.replace("birthday", "").replaceAll("^\\s+","").replaceAll("\\s*$", "");
					acceptable = acceptable & Utils.validateDate(birthday);
				}else{//need to be modified
					s = s.replaceAll("^\\s+","");
					String[] splitedS = s.split(" ");
					//System.out.println(splitedS[1]);
					String current = "";
					for(int index=0; index<splitedS.length; index++){
							current = current + " " + splitedS[index];
							if(current.contains("Spiderman Escape")||current.contains("Ice Age Adventure")
									 ||current.contains("Canyon Blaster")||current.contains("4D Theatre")
									 ||current.contains("Flow Rider")||current.contains("Carousel")){
								if(index+1<splitedS.length){
									String attraction = (current + " " + splitedS[index+1]).replaceAll("^\\s+","");
									visitedAttractions.add(attraction);
									current = "";
								}
							}
					}
				}//end of else
			}
			
			if (acceptable) {
				Card newCard = new Card(ID, name, birthday);
				if (height > 0) {
					newCard.setHeight(height);
				}
				if (address != null) {
					newCard.setAddress(address);
				}
				if (visitedAttractions != null) {
					//newCard.setVisitingAttractions(visitedAttractions);
					for (String s : visitedAttractions){
						newCard.setVisitingAttractions(s);
					}
				}
				boolean mark = true;
				int count = -1;
				int index = -1;
				for (Card card : loadedCards) {
					count++;
					
					//newCard.getID();
					if (card.getID()!=null&&card.getID().equals(newCard.getID())) {
						mark = false;
						index = count;
					}
				}
				if (mark){
					if(ID!=null&&name!=null&&birthday!=null)
						loadedCards.add(newCard);
				}
				else{//update
					if(newCard.getName()!=null){
						loadedCards.get(index).setName(newCard.getName());;
					}
					if(newCard.getHeight()>0){
						loadedCards.get(index).setHeight(newCard.getHeight());
					}
					if(newCard.getBirthday()!=null){
						loadedCards.get(index).setBirthday(newCard.getBirthday());
					}
					if(newCard.getAddress()!=null){
						loadedCards.get(index).setAddress(newCard.getAddress());
					}
					if(newCard.getAttractions().size()>0){
						for(String s : newCard.getAttractions()){
							loadedCards.get(index).setVisitingAttractions(s);
						}
					}
				}
			}
		}else if(command.equals("delete")){
			String ID = i.getBody().replace("ID", "").trim();
			//System.out.println("ID: "+ID);
			int mark = -1;
			for(int j=0; j<loadedCards.size(); j++){
				if (loadedCards.get(j).getID()!=null) {
					if (loadedCards.get(j).getID().equals(ID)) {
						mark = j;
					} 
				}
			}
			if(mark!=-1)
				loadedCards.remove(mark);
		}else if(command.equals("request")){
			String body = i.getBody();
			String[] splitedBody = body.split(";");
			String ID = null;
			String attraction = null;
			Date date = null;
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yy");
			ID = splitedBody[0].replace("ID", "").trim();
			attraction = splitedBody[1].replaceAll("^\\s+","");
			try {
				date = sdf.parse(splitedBody[2].replace("-", "/").trim());
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				//e.printStackTrace();
			}
			request(ID, attraction, date);
		}else if(command.equals("query")){
			String body = i.getBody();
			if(body.contains("name")){
				String name = body.replace("name", "").replaceAll("^\\s+","");
				queryName(name);
			}else if(body.contains("ID")){
				String[] splitedBody = body.split(";");
				//System.out.println(Utils.stringToDate(splitedBody[0].trim()) + " " + Utils.stringToDate(splitedBody[1].trim()) +" "+ splitedBody[2].replace("ID", "").trim());
				queryID(Utils.stringToDate(splitedBody[0].trim()), Utils.stringToDate(splitedBody[1].trim()), splitedBody[2].replace("ID", "").trim());
			}else if(body.contains("age")){
				String[] splitedBody = body.split(";");
				Date d1 = Utils.stringToDate(splitedBody[0].trim());
				Date d2 = Utils.stringToDate(splitedBody[1].trim());
				queryAge(d1, d2);
			}else{
				
			}
		}
		return false;
	}
	
	private void request(String ID, String attraction, Date date){
		boolean heightResult = false;
		boolean ageResult = false;
		String reportContent = null;
		String requestedBirthday = null;
		int requestedHeight = 0;
		int mark = -1;
		int count = -1;
		for (Card c : loadedCards){
			count++;
			if(c.getID().equals(ID)){ //get the requested Card
				requestedBirthday = c.getBirthday();
				requestedHeight = c.getHeight();
				mark = count;
			}
		}
		
		/*see whether the age limit is satisfied*/
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		sdf.setLenient(false);
		Date birthday = null;
		requestedBirthday = requestedBirthday.replace("-", "/");
		try {
			birthday = sdf.parse(requestedBirthday);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		long days = 0;
		if(birthday!=null)
			days = Utils.getDateDiff(birthday, date, TimeUnit.DAYS);
		
//		System.out.println("The age is " + days/365);
		
		/*check height and age*/
		if(requestedHeight>=attractions.get(attraction).getHeightLower()
				&&requestedHeight<=attractions.get(attraction).getHeightUpper()){
			heightResult = true;
		}
//		else{
//			System.out.println("The height limit is not satisfied lower: "+ attractions.get(attraction).getHeightLower() + " upper: "+ attractions.get(attraction).getHeightUpper() + " now: "+requestedHeight);
//		}
		
		if(days/365>attractions.get(attraction).getAgeLower()){
			//System.out.println("The age limit is satisfied");
			ageResult = true;
		}
		
		/*if permitted, add visit history to card, else write to report*/
		if(heightResult&&ageResult){
			//System.out.println("The request is permited");
			boolean hasHistory = true;
			for(int i=0; i<loadedCards.get(mark).getAttractions().size(); i++){
				if(loadedCards.get(mark).getAttractions().get(i).contains(attraction)){
					System.out.println("there are previous visiting history");
					String original = loadedCards.get(mark).getAttractions().get(i);
					loadedCards.get(mark).getAttractions().set(i, original+" "+sdf.format(date));
					hasHistory = false;
				}
			}
			if(hasHistory)
				loadedCards.get(mark).setVisitingAttractions(attraction + " " + sdf.format(date));
		}else{
			if(!heightResult&&!ageResult){
				reportContent = "---request ID " + ID + "; " + attraction + "; " + sdf.format(date) + "---\r\n"
						+ "Request Denied: " + attraction + " " + sdf.format(date) + "\r\n"
						+ "Reasons: age requirements not met\r\n"
						+ "         height requirements not met\r\n";
			}else if(!heightResult){
				reportContent = "---request ID " + ID + "; " + attraction + "; " + sdf.format(date) + "---\r\n"
						+ "Request Denied: " + attraction + " " + sdf.format(date) + "\r\n"
						+ "Reasons: height requirement not met\r\n";
			}else if(!ageResult){
				reportContent = "---request ID " + ID + "; " + attraction + "; " + sdf.format(date) + "---\r\n"
						+ "Request Denied: " + attraction + " " + sdf.format(date) + "\r\n"
						+ "Reasons: age requirement not met\r\n";
			}else{
				
			}
			writeToReport(reportContent);
		}
		
	}
	
	private void queryName(String name){
		ArrayList<Card> result = new ArrayList<Card>();
		//System.out.println(name);
		for(Card c:loadedCards){
			
			if(c.getName().equals(name)){
				System.out.println("matched");
				result.add(c);
			}
		}
		Collections.sort(result);
		//print the result to report
		//TODO
		String reportContent = "-----query name " + name + "-----\r\n";
		for(Card c : result){
			reportContent += "ID " + c.getID()+"\r\n";
			reportContent += "Visited attractions:\r\n";
			for (String a : c.getAttractions()){
				reportContent += a+"\r\n";
				System.out.println("printed");
			}
		}
		writeToReport(reportContent);
	}
	
	private void queryID(Date d1, Date d2, String ID){
		//TODO
		HashMap<String, Integer> record = new HashMap<String, Integer>();
		for(Card c : loadedCards){
			if(c.getID().equals(ID)){
				System.out.println("Card ID" + c.getID() + " provided ID " + ID);
				for(String a : c.getAttractions()){
					int count = 0;
					
					String[] splitedA = a.split(" ");
					ArrayList<String> splitedAttractions = new ArrayList<String>();
					String attractionName = "";
					
					for(String s : splitedA){
						if(!Utils.validateDate(s)){
							attractionName = attractionName + " " + s;
						}else{
							splitedAttractions.add(s);
						}
					}
					attractionName = attractionName.replaceAll("^\\s+","");
					//System.out.println("Visited Attractions: " + attractionName);
					splitedAttractions.add(0, attractionName);
					for(int i=1; i<splitedAttractions.size(); i++){
						//System.out.println(splitedAttractions.get(i));
						
						if(!d1.after(Utils.stringToDate(splitedAttractions.get(i)))&&!d2.before(Utils.stringToDate(splitedAttractions.get(i)))){
							count++;
						}
					}
					record.put(splitedAttractions.get(0), count);
				}
				break;
			}
			
		}

		for(Map.Entry<String,Integer> entry : record.entrySet()){
			System.out.println("Key " + entry.getKey() + " value: " + entry.getValue());
		}
		
        MapValueComparator mvc = new MapValueComparator(record);
        TreeMap<String, Integer> sortedMap = new TreeMap<String, Integer>(mvc);
        sortedMap.putAll(record);
        
        int total = 0;
        String first = null;
        String second = null;
        boolean gotSecond = false;
        int firstTimes = 0;
        int secondTimes = 0;
        int index = 0;
        for(Map.Entry<String,Integer> entry : sortedMap.entrySet()){
        	String key = entry.getKey();
        	Integer value = entry.getValue();
        	if(index==0){
        		first = key + " " + value + " ";
        		firstTimes = value;
        		index++;
        		total += value;
        		continue;
        	}else{
        		if(value == firstTimes){
        			first = first + key + " " + value + " ";
        		}else{
        			if(!gotSecond){
        				second = key;
        				secondTimes = value;
        				gotSecond = true;
        			}
        		}
        		index++;
        		total += value;
        	}
        	
        }
        //System.out.println(d1);
        String reportContent = "-----query " + Utils.dateToString(d1) + "; " + Utils.dateToString(d2) + "; ID " + ID+"-----\r\n"
        		+"Total visits: " + total + "\r\n"
        		+"Most-visited: " + first + "\r\n"
        		+"2nd-most-visited: " + second + " " + secondTimes + "\r\n";
        writeToReport(reportContent);
	}
	
	private void queryAge(Date d1, Date d2){
		//System.out.println(d1 + " "  +d2);
		int total = 0;
		int range1 = 0;
		int range2 = 0;
		int range3 = 0;
		int range4 = 0;
		boolean mark = false;
		for(int i=0; i<loadedCards.size(); ++i){
			mark = false;
			for(String s : loadedCards.get(i).getAttractions()){
				String[] splited = s.split(" ");
				for(String ss : splited){
					System.out.println(ss);
					if(Utils.validateDate(ss)){
						if(!d1.after(Utils.stringToDate(ss))&&!d2.before(Utils.stringToDate(ss))){
							total++;
							int age = loadedCards.get(i).getAge(d2);
							if(age<8){
								range1++;
							}else if(age<18){
								range2++;
							}else if(age<65){
								range3++;
							}else{
								range4++;
							}
						}
						mark = true;
						break;
					}
				}
				if(mark)
					break;
			}
		}
		
		String reportContent="";
		if (total!=0) {
			reportContent = "----query " + Utils.dateToString(d1) + "; " + Utils.dateToString(d2) + "; age----\r\n"
					+ "Population size: " + total + "\r\n" + "Age Profile\r\n" + "Below 8: " + range1 * 100 / total
					+ "%\r\n" + "Over 8 and below 18: " + range2 * 100 / total + "%\r\n" + "Over 18 and below 65: "
					+ range3 * 100 / total + "%\r\n" + "Over 65: " + range4 * 100 / total + "%\r\n";
		}else{
			reportContent = "----query " + Utils.dateToString(d1) + "; " + Utils.dateToString(d2) + "; age----\r\n"
					+ "Population size: " + total + "\r\n" 
					+ "Age Profile\r\n" + "Below 8: " + range1 + "%\r\n" 
					+ "Over 8 and below 18: " + range2 + "%\r\n" 
					+ "Over 18 and below 65: " + range3 + "%\r\n" 
					+ "Over 65: " + range4 + "%\r\n";
		}
		writeToReport(reportContent);
	}
	
	public void writeToResult(){

		try {
			FileWriter file = new FileWriter(this.resultFileName, true);
			PrintWriter writer = new PrintWriter(new BufferedWriter(file));
			for(Card c : loadedCards){
				writer.println("ID "+c.getID());
				writer.println("name "+c.getName());
				writer.println("birthday "+c.getBirthday());
				if(c.getHeight()>0)
					writer.println("height "+c.getHeight()+"cm");
				if(c.getAddress()!=null)
					writer.println("address "+c.getAddress());
				if(c.getAttractions()!=null){
					for(String a : c.getAttractions()){
						writer.println(a);
					}
				}
					
				writer.println();
			}
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	public void writeToReport(String reportContent){
		try {
			FileWriter file = new FileWriter(reportFileName, true);
			PrintWriter writer = new PrintWriter(new BufferedWriter(file));
			writer.print(reportContent);
			writer.println("--------------------------------------------------------------------");
			writer.println();
			writer.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void sortLoadedCards(){
		for(Card c:loadedCards){
			if(c.getAttractions()!=null){
				for(int i=0; i<c.getAttractions().size(); i++){
					String updated = Utils.sortVisitingDate(c.getAttractions().get(i));
					c.getAttractions().set(i, updated);
				}
			}
		}
	}
	
	/*move this part to Utils*/
//	public void sortVisitingDate(String history){
//		String[] splited = history.split(" ");
//		ArrayList<String> sortedDates = new ArrayList<String>();
//		String attractionName = "";
//		boolean isFirst = true;
//		for(String s : splited){
//			if(!Utils.validateDate(s)){
//				attractionName = attractionName + " " + s;
//			}else{
//				if(isFirst){
//					sortedDates.add(s);
//				}
//				else{
//					if(Utils.stringToDate(s).before(Utils.stringToDate(sortedDates.get(0)))){
//						sortedDates.add(s);
//					}else{
//						int index = 0;
//						while(sortedDates.get(index)!=null&&Utils.stringToDate(s).after(Utils.stringToDate(sortedDates.get(index)))){
//							index++;
//						}
//						sortedDates.add(index, s);
//					}
//				}
//			}
//		}
//		attractionName = attractionName.replaceAll("^\\s+","");
//		String updatedHistory = attractionName;
//		for(String date : sortedDates){
//			updatedHistory = updatedHistory + " " + date;
//		}
//		System.out.println(updatedHistory);
//		//splitedAttractions.add(0, attractionName);
//	}
	
	public void printInstructions(){
		int count = 0;
		for (Instruction i : instructions){
			System.out.println("Instruction No." + ++count + " with command " + i.getCommand() + " body " + i.getBody());
		}
	}
	
	public void printCards(){
		int count = 0;
		for (Card c : loadedCards){
			System.out.println("The No." + ++count + " card with ID " + c.getID() + " name: " + c.getName() + " birthday: " + c.getBirthday());
		}
	}
	
}
