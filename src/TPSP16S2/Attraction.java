package TPSP16S2;

public class Attraction {
	private String name;
	private int heightUpper;
	private int heightLower;
	private int ageLower;
	
	public Attraction(String name, int heightLower, int heightUpper, int ageLower){
		this.name = name;
		this.heightLower = heightLower;
		this.heightUpper = heightUpper;
		this.ageLower = ageLower;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getHeightUpper() {
		return heightUpper;
	}

	public void setHeightUpper(int heightUpper) {
		this.heightUpper = heightUpper;
	}

	public int getHeightLower() {
		return heightLower;
	}

	public void setHeightLower(int heightLower) {
		this.heightLower = heightLower;
	}

	public int getAgeLower() {
		return ageLower;
	}

	public void setAgeLower(int ageLower) {
		this.ageLower = ageLower;
	}
	
	
	
}
