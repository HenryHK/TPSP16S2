package TPSP16S2;


/**
 * Instructions class
 * contains instruction
 * include necessary set and get method
 * @author lhan
 *
 */

public class Instruction {

	private String command;
	private String body;
	/**
	 * constructor
	 * @param ins
	 */
	public Instruction(String command, String body){
		this.command = command;
		this.body = body;
	}
	
	/**
	 * get instruction
	 * @return instruction
	 */
	public String getCommand(){
		return this.command;
	}
	
	public String getBody(){
		return this.body;
	}
	
}
