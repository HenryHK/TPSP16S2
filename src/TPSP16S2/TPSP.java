package TPSP16S2;

public class TPSP {

	public static void main(String[] args) {
		
		String cardsFile = args[0];
		String instructionFile = args[1];
		String resultFile = args[2];
		String reportFile = args[3];
		
		MainSystem s = new MainSystem(resultFile, reportFile);
		s.loadCards(cardsFile);
		s.printCards();
		s.loadInstructions(instructionFile);
		s.printInstructions();
		s.processInstructions();
		s.sortLoadedCards();
		s.writeToResult();

	}

}
