package algorithm;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import javax.swing.plaf.basic.BasicComboPopup;

public class Attribute {

	private int numX;
	private int population;
	private int totalThread ;
	private int exchangeNum;
	private int totalRun;
	private int experimentTime;
	private int allRunTime;
	private boolean initialSolutionSet;
	private String algorithm;
	private String function;
	private int lineNum;
	private double errorRate;
	private double startVariable;
	private double endVariable;
	private boolean duplicate;
	BufferedReader br;

	public Attribute() {
		// TODO Auto-generated constructor stub
		String line;
		try {
			br = new BufferedReader(new FileReader(System.getProperty("user.dir") + "\\attribute\\attribute.txt"));
			line = br.readLine();
			String[] str = line.split(" ");
			experimentTime = Integer.parseInt(str[1]);

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void initialAttribute() {
		lineNum = 0;
		readFile();

	}

	public void readFile() {
		String line;
		try {
			while (lineNum != 14) {
				line = br.readLine();
				lineNum++;
				String[] str = line.split(" ");

				switch (str[0]) {

				case "startVariable:":
					startVariable = Double.parseDouble(str[1]);
					break;
				case "endVariable:":
					endVariable = Double.parseDouble(str[1]);
					break;				
				case "numX:":
					numX = Integer.parseInt(str[1]);
				case "population:":
					population = Integer.parseInt(str[1]);
					break;
				case "totalThread:":
					totalThread = Integer.parseInt(str[1]);
					break;
				case "exchangeNum:":
					exchangeNum = Integer.parseInt(str[1]);
					break;
				case "totalRun:":
					totalRun = Integer.parseInt(str[1]);
					break;
				case "algorithm:":
					algorithm = str[1];
					break;
				case "experimentTime:":
					experimentTime = Integer.parseInt(str[1]);
					break;
				case "allRunTime:":
					allRunTime = Integer.parseInt(str[1]);
					break;
				case "initialSolutionSet:":
					initialSolutionSet = Boolean.parseBoolean(str[1]);
					break;

				case "errorRate:":
					errorRate = Double.parseDouble(str[1]);				
					break;
				case "function:":
					function = str[1];				
					break;
				case "duplicate:":
					duplicate = Boolean.parseBoolean(str[1]);
					break;
					
					
				}
			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public int getNumX(){
		return numX;
	}
	
	public int getPopulation() {
		return population;
	}

	public int getTotalThread() {
		return totalThread;
	}

	public int getExchangeNum() {
		return exchangeNum;
	}

	public int getTotalRun() {
		return totalRun;
	}

	public int getExperimentTime() {
		return experimentTime;
	}

	public int getAllRunTime() {
		return allRunTime;
	}

	public String getAlgorithm() {
		return algorithm;
	}

	public boolean getInitialSolutionSet() {
		return initialSolutionSet;
	}
	public double getErrorRate(){
		return errorRate;
	}
	public String getFunction(){
		return function;
	}
	
	public double getStartVariable(){
		return startVariable;
	}
	public double getEndVariable(){
		return endVariable;
	}
	public boolean getDuplicate(){
		return duplicate;
	}
}
