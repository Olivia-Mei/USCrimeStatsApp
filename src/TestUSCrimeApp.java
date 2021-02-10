/* File: TestUSCrimeApp.java
 * Author: Olivia-Mei McDowell
 * Date: 12-14-2020
 * Description: The TestUSCrimeApp class constructs a USCrime object, 
 * and all USCrimeApp class methods are used.
 */

public class TestUSCrimeApp {
	public static void main(String[] args) {
		// Test case object
		USCrimeApp populationDataReport = new USCrimeApp();
		populationDataReport.parseFile(args);
		populationDataReport.getMenuSelection();
	}
}