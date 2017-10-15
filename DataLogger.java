import java.io.*;
import java.math.*;

public class PeterKokalov_assignment2_15pk13 {
	
	// the createDoubleArrays function creates 2 arrays - one for relevant amp values and the second for corresponding second times
	public static void createDoubleArrays() {
		String csvFile = "Logger.csv"; // setting the name of our csv file
		BufferedReader br = null; // init buffer
		String line = ""; // line breaks
		
		int max = 1000; // our total rows
		try { // try-catch start
			for (int i = 1; i < 8; i++) { 
				double[] current = new double[max]; // creating current array for the currents per motor
				int[] times = new int[max];
				
				for (int sec = 0; sec < 999; sec++) {
					times[sec] = sec;
				}
					
				br = new BufferedReader(new FileReader(csvFile)); // reading the file (csvFile is the string we created)
				
				int index = 0; // index count for printCheck
				
				while ((line = br.readLine()) != null) { // before we run out of lines
					String[] second = line.split(","); // comma-separated-values - split by ','
					double adder = printCheck(second, i); // calls printCheck which checks if values > 1.0
					current[index] = adder; // index our current array for values > 1.0
					
					index += 1; // add 1 to our index count
				}
				
				textWriter(current, times, i); // calling our textWriter() method to create text files!
			}
		} catch (FileNotFoundException err) { // FilenotFoundException catch
			System.out.println(err.getMessage()); // print our error message
			
		} catch (IOException err) { // IOException catch
			System.out.println(err.getMessage()); // print our error message
		}  
	}
	// the method textWriter() writes our text files and saves them to the assignment folder
	// it takes 3 args - our list of currents, our list of seconds, and the motor we are currently inspecting
	public static void textWriter(double[] current, int[] times, int i) throws IOException {
		String number = Integer.toString(i); // so we can format into our FileWriter
		FileWriter writer = new FileWriter("C:\\Users\\PeterKokalov\\Desktop\\Documents\\Queen's\\Second Year\\CISC124\\Assignment_2\\Motor"+number+".txt");
		if (current.length == 0 || current == null) { // if our currents array is empty, we know the motor wasn't being used! 
			writer.write("Not used.");
			writer.close();
		}
		else {
			writer.write("start (sec), finish (sec), current (amps)\r\n"); // header for our text file
			double average = 0.0; // init average
			int start = 0; // init start time
			int end = 0; // init end time
			int k = 0; // k will be our "tracker" variable so we can navigate through times
			for (int j = 0; j < current.length; j++) { 
				try { // try-catch statement
					if (current[j] > 1.0) { // if we are currently at relevant data
						if (current[j+1] < 1.0) { // make sure we don't overcount
							end = times[j+1]; 
							average = average / k; // finding our average
							BigDecimal bd = new BigDecimal(average); // converting to 3-sig digits as asked
							bd = bd.setScale(3, RoundingMode.HALF_UP);
							double avg = bd.doubleValue();
							if (average < 8.0) { // checking to see if we exceed our current 
								writer.write(start+", "+end+", "+avg+"\r\n");
							}
							else {
								writer.write(start+", "+end+", "+avg+", "+"***Current Exceeded***\r\n");
							}
							k = 0; // reset our value k so we can iterate properly
							average = 0; // reset our average counter as well!
						}
						else {
							start = times[j-k];
							average+= current[j]; // adding to our average counter
							k = k + 1;
						}
					}
				}
				catch (IndexOutOfBoundsException error) { // our Index error for the first time the loop runs since we're looking at ix - 1 at 0
					continue;
				}
			}
		}
		writer.close(); // close our txt file
	}
	
	// this function checks if our value is greater than 1.0 (i.e. the motor is being used)
	public static double printCheck(String[] second, int i) {
		String row = second[i]; // our row (motor data)
		double rowdoub = Double.parseDouble(row); // parseDouble to turn string into double from csv file
		if (rowdoub > 1.0) {
			double current = rowdoub;
			return current; // returning the row
		}
		else {
			return 0.0; // returning 0.0
		}		
	}

	public static void main(String[] args) throws FileNotFoundException {
		createDoubleArrays(); // calls the function to create our two arrays
		System.out.println("Program Complete."); // completion of program!
	}
}