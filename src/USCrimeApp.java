/* File: USCrimeApp.java
 * Author: Olivia-Mei McDowell
 * Date: 12-14-2020
 * Description: The USCrimeApp class reads in and parses a CSV file.
 * The menu system is displayed at the command prompt, and continues 
 * to redisplay after results are returned or until Q is selected. 
 * App provides statistical results on data options provided by UI menu.
 * Time elapsed is tracked from user starting to user quitting program.
 */

// Import required Java classes
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;
import java.text.*;

public class USCrimeApp {

	// Declare class fields
	private String[] crimeData;
	private ArrayList<USCrime> crimeDataNeeded;
	double growthRate;
	Scanner year1Input;
	Scanner year2Input;
    private int year1Int;
	private int year2Int;
	private int[] growthYearRange;
	private Duration timeElapsedMenuSelection;
	private Instant startTime;
	
	/**
	 * Method that reads in and parses file.
	 * File name is passed as a command line argument.
	 * Creates and returns array list of USCrime objects.
	 */
	public ArrayList<USCrime> parseFile(String[] args) { 
		startTime = Instant.now();
		String csvFileName = null;
		String line = "";
		String cvsSplitBy = ",";

		// Pass name of US Crime Data file via cmd line args
		if (0 < args.length) {
			csvFileName = args[0];
			File file = new File(csvFileName);
		}
		// Create ArrayList object that will hold USCrimeClass objects
		crimeDataNeeded = new ArrayList<USCrime>();
		/*
		* Use instance of BufferedReader to read data from file.
		* Declare BufferReader instance resource in the try-with-resources statement.
		* Use try-catch to handle file input errors.
		*/
		try (BufferedReader br = new BufferedReader(new FileReader(csvFileName))) {
			String headerLine = br.readLine();
			while ((line = br.readLine()) != null) {
				// Use comma as separator
				crimeData = line.split(cvsSplitBy);
				// Create array list of USCrime objects, pass corresponding array indexes through constructor
				USCrime crimeObject = new USCrime(crimeData[0], crimeData[1], crimeData[5], crimeData[9], crimeData[19]);
				crimeDataNeeded.add(crimeObject);
			} // End while loop
		// Catch statement handles reader IO errors
		} catch (IOException e) {
            e.printStackTrace();
			System.out.println("Error encountered...");
        } // End try-catch
		return crimeDataNeeded;
	} // End getFile() method
	
	/** Getter method for array list that holds USCrime objects */
	public ArrayList<USCrime> getcrimeDataNeeded(ArrayList<USCrime> crimeDataNeeded) { 
		return this.crimeDataNeeded;
    }
	
	/** Getter method that reads in user input for desired year range and stores in int array */
	public int[] getGrowthRateYears() {
		String year2Entered;
		String year1Entered;	
		year1Input = new Scanner(System.in);
		year2Input = new Scanner(System.in);
		growthYearRange = new int[2]; 
		System.out.println("\nEnter the two years you want the population growth returned. "
							+ "(Enter Q to quit the program)");
		do {
			System.out.print("Enter year 1: ");
			year1Entered = year1Input.nextLine();
			// Use try-catch to handle non-integer input error
			try {
				year1Int = Integer.parseInt(year1Entered);
				break;
		    } catch (NumberFormatException e) {
		        System.out.println("Year entered must be an integer unless you entered Q to quit the program.");
		    } // End try-catch
			/*
			* If user input = "Q" then stop prompting user for entries.
			* End time tracker and display total program execution time results.
			* Display goodbye message and exit program.
			*/
			if (year1Entered.toLowerCase().trim().equals("q")) {
				Instant endTime = Instant.now();
				timeElapsedMenuSelection = Duration.between(startTime, endTime);
				System.out.println("\n**** Thank you for trying the US Crimes Statistics Program *****\n"
									+ "Your total time elapsed for this session is " 
									+ timeElapsedMenuSelection.getSeconds() + " seconds.");
				System.exit(1);
			} // End if statement
		} // Complete do-while loop with while statement below
		while (!(year1Entered.toLowerCase().trim().equals("q")));
		do {
			System.out.print("\nEnter year 2: ");
			year2Entered = year2Input.nextLine();
			// Use try-catch to handle non-integer input error
			try {
				year2Int = Integer.parseInt(year2Entered);
				break;
		    } catch (NumberFormatException e) {
		        System.out.println("Year entered must be an integer unless you entered Q to quit the program.");
		    } // End try-catch
			/*
			* If user input = "Q" then stop prompting user for entries.
			* End time tracker and display total program execution time results.
			* Display goodbye message and exit program.
			*/
			if (year2Entered.toLowerCase().trim().equals("q")) {
				Instant endTime = Instant.now();
				timeElapsedMenuSelection = Duration.between(startTime, endTime);
				System.out.println("\n**** Thank you for trying the US Crimes Statistics Program *****\n"
									+ "Your total time elapsed for this session is " 
									+ timeElapsedMenuSelection.getSeconds() + " seconds.");
				System.exit(1);
			} // End if statement		
		} // Complete do-while loop with while statement below
		while (!(year2Entered.toLowerCase().trim().equals("q")));
		growthYearRange[0] = year1Int; 
		growthYearRange[1] = year2Int;
		System.out.println("\nYear Range Entered: " + Arrays.toString(growthYearRange));
        return growthYearRange; // Return 2 element array
    }

	/** Method calculates and returns population growth rate based on year input parameters */
	public double getPopGrowthRate(int year1Int, int year2Int) {
		int minYear = crimeDataNeeded.get(0).getYear();
		int year1Index = this.year1Int - minYear;		
		int year2Index = this.year2Int - minYear;
		DecimalFormat df = new DecimalFormat("##.##%");
		// Use try-catch to handle invalid year range
		try {
			double population1 = crimeDataNeeded.get(year1Index).getPopulation();
			double population2 = crimeDataNeeded.get(year2Index).getPopulation();
			// Growth rate formula
			growthRate = (((double)(population2 - population1)) / population1);
		} catch (IndexOutOfBoundsException exception) {
		    System.out.println("The year(s) you entered are out of range. Please try again...");
			getGrowthRateYears();
			System.out.println(df.format(this.getPopGrowthRate(growthYearRange[0], growthYearRange[1])));
		}
		return growthRate;
	}
	
	public int getMaxMurderYear() {
		int maxMurderYear = 0;
		double maxMurderRate = 0;
		for (USCrime currentUSCrimeObj: crimeDataNeeded){
			if (maxMurderRate < currentUSCrimeObj.getMurderRate()) {
				maxMurderRate = currentUSCrimeObj.getMurderRate();
				maxMurderYear = currentUSCrimeObj.getYear();
			}
		}
		return maxMurderYear;
	}
  
	public int getMinMurderYear() {
		int minMurderYear = 0;
		double minMurderRate;
		for (USCrime currentUSCrimeObj: crimeDataNeeded){
			minMurderRate = currentUSCrimeObj.getMurderRate();
			if (minMurderRate >= currentUSCrimeObj.getMurderRate()) {
				minMurderRate = currentUSCrimeObj.getMurderRate();
				minMurderYear = currentUSCrimeObj.getYear();
			}
		}
		return minMurderYear;
	}
	
	public int getMaxRobberyYear() {
		double maxRobberyRate = 0;
		int maxRobberyYear = 0;
		for (USCrime currentUSCrimeObj: crimeDataNeeded){
			if (maxRobberyRate < currentUSCrimeObj.getRobberyRate()) {
				maxRobberyRate = currentUSCrimeObj.getRobberyRate();
				maxRobberyYear = currentUSCrimeObj.getYear();
			}
		}
		return maxRobberyYear;
	}
	
	public int getMinRobberyYear() {
		double minRobberyRate;
		int minRobberyYear = 0;
		for (USCrime currentUSCrimeObj: crimeDataNeeded){
			minRobberyRate = currentUSCrimeObj.getRobberyRate();
			if (minRobberyRate >= currentUSCrimeObj.getRobberyRate()) {
				minRobberyRate = currentUSCrimeObj.getRobberyRate();
				minRobberyYear = currentUSCrimeObj.getYear();
			}
		}
		return minRobberyYear;
	}
	
	public int getMaxVehicleTheftYear() {
		double maxVehicleTheftRate = 0;
		int maxVehicleTheftYear = 0;
		for (USCrime currentUSCrimeObj: crimeDataNeeded){
			if (maxVehicleTheftRate < currentUSCrimeObj.getVehicleTheftRate()) {
				maxVehicleTheftRate = currentUSCrimeObj.getVehicleTheftRate();
				maxVehicleTheftYear = currentUSCrimeObj.getYear();
			}
		}
		return maxVehicleTheftYear;
	}
	
	public int getMinVehicleTheftYear() {
		double minVehicleTheftRate;
		int minVehicleTheftYear = 0;
		for (USCrime currentUSCrimeObj: crimeDataNeeded){
			minVehicleTheftRate = currentUSCrimeObj.getVehicleTheftRate();
			if (minVehicleTheftRate >= currentUSCrimeObj.getVehicleTheftRate()) {
				minVehicleTheftRate = currentUSCrimeObj.getVehicleTheftRate();
				minVehicleTheftYear = currentUSCrimeObj.getYear();
			}
		}
		return minVehicleTheftYear;
	}
	
	public void getMenuSelection() {
		boolean selectionFound;
		String selectionEntered;
		// Declare and define main menu
		String[] menuPromptArray = {"1. What was the percentage in population growth between "
											+ "any two years from 1994 and 2013? *User sets year range*\n",
									"2. What year was the murder rate the highest?\n",
									"3. What year was the murder rate the lowest?\n",
									"4. What year was the robbery rate the highest?\n",
									"5. What year was the robbery rate the lowest?\n",
									"6. What year was the vehicle theft rate the highest?\n",
									"7. What year was the vehicle theft rate the lowest?\n",
									"Q. Quit the program.\n"};
		Scanner userSelectionInput = new Scanner(System.in);
		// Format as decimal percentage
		DecimalFormat df = new DecimalFormat("##.##%");
		System.out.println("************* Welcome to the US Crime Statistical Application *************");
		/*
		* Use do-while loop to continuously prompt user to enter desired prompt selection.
		* If valid input recognized, corresponding methods run and results are displayed.
		* Menu re-prompts user until user enters 'Q' to quit program.
		*/
		do {
			selectionFound = false; // Reset found=false at start of each loop iteration
			System.out.print("\nEnter the number of the question you want answered. "
								+ "Enter Q to quit the program: \n" +
								menuPromptArray[0] +
								menuPromptArray[1] +
								menuPromptArray[2] +
								menuPromptArray[3] +
								menuPromptArray[4] +
								menuPromptArray[5] +
								menuPromptArray[6] +
								menuPromptArray[7] +
								"\nEnter Selection: ");
			selectionEntered = userSelectionInput.nextLine();
			// Use for loop to assess user input menu selection
			for(int i=0; i < menuPromptArray.length; i++){
				try {
					selectionEntered = selectionEntered.trim();
					if(selectionEntered.equals("1")) {
						selectionFound = true;
						this.getGrowthRateYears();
						System.out.println("\nPopulation Growth Rate between " + growthYearRange[0] + " and " + growthYearRange[1] + ": " 
											+ df.format(this.getPopGrowthRate(growthYearRange[0], growthYearRange[1])));
					break;
					}
					if(selectionEntered.equals("2")) {
						selectionFound = true;
						System.out.println("\nThe murder rate was highest in " + this.getMaxMurderYear());
					break;
					}
					if(selectionEntered.equals("3")) {
						selectionFound = true;
						System.out.println("\nThe murder rate was lowest in " + this.getMinMurderYear());
					break;
					}
					if(selectionEntered.equals("4")) {
						selectionFound = true;
						System.out.println("\nThe robbery rate was highest in " + this.getMaxRobberyYear());
					break;
					}
					if(selectionEntered.equals("5")) {
						selectionFound = true;
						System.out.println("\nThe robbery rate was lowest in " + this.getMinRobberyYear());
					break;
					}
					if(selectionEntered.equals("6")) {
						selectionFound = true;
						System.out.println("\nThe vehicle theft rate was highest in " + this.getMaxVehicleTheftYear());
					break;
					}
					if(selectionEntered.equals("7")) {
						selectionFound = true;
						System.out.println("\nThe vehicle theft rate was lowest in " + this.getMinVehicleTheftYear());
					break;
					}
					
			} catch (NumberFormatException e) {
		        System.err.println("User input must be one of the provided options...");
		    } // End try-catch
		} // End for loop
		/*
		* Use if statement to handle invalid user input within do-while loop
		* in order to trigger re-prompting user to enter valid menu selection.
		*/
		if (!selectionFound 
			&& !selectionEntered.toLowerCase().trim().equals("q")) { 
			System.out.println("Entry not recognized... "
						+ "Please enter a valid option ('1', '2', '3', 'Q', etc.) ");
		}
		/*
		* If user input = "Q" then stop prompting user for entries.
		* End time tracker and display total program execution time results.
		* Display goodbye message and exit program
		*/
		if (selectionEntered.toLowerCase().trim().equals("q")) {
			Instant endTime = Instant.now();
			timeElapsedMenuSelection = Duration.between(startTime, endTime);
			System.out.println("\n**** Thank you for trying the US Crimes Statistics Program *****\n"
					+ "\nYour total time elapsed for this session is " + timeElapsedMenuSelection.getSeconds() + " seconds.");
		System.exit(1);
		}
	} // Complete do-while loop with while statement below.
	while (!(selectionEntered.toLowerCase().trim().equals("q")));	
	userSelectionInput.close();
	}
} // End USCrimeClass