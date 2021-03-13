/* File: USCrime.java
 * Author: Olivia-Mei McDowell
 * Date: 12-14-2020
 * Description: The USCrime class encapsulates 
 * properties and functionality of US Crime objects.
 */

public class USCrime {
	
	private int year;
	private int population;
	private double murderRate;
	private double robberyRate;
	private double vehicleTheftRate;

	// Custom constructor
	public USCrime(String year, String population, String murderRate, String robberyRate, String vehicleTheftRate) {
		this.year = Integer.parseInt(year);
		this.population = Integer.parseInt(population);
		this.murderRate = Double.parseDouble(murderRate);
		this.robberyRate = Double.parseDouble(robberyRate);
		this.vehicleTheftRate = Double.parseDouble(vehicleTheftRate);
		} // End constructor
	
	// Getter methods
	public int getYear() {
		return this.year;	
	}
	
	public int getPopulation() {
		return this.population;	
	}
	
	public double getMurderRate() {
		return this.murderRate;	
	}
	
	public double getRobberyRate() {
		return this.robberyRate;	
	}
	
	public double getVehicleTheftRate() {
		return this.vehicleTheftRate;	
	}				
		
	@Override // String override method to customize display format
	 public String toString() {
		return this.year + ", " + 
				this.population + ", " + 
				this.murderRate + ", " +
				this.robberyRate + ", " +
				this.vehicleTheftRate; 			
	} // End toString method
	
} // End USCrime class