import java.io.*;
import java.math.*;

public class PeterKokalov_assignment2_15pk13 {
	
	public static void createDoubleArrays() {
		String csvFile = "Logger.csv"; 
		BufferedReader br = null; 
		String line = ""; 
		
		int max = 1000; 
		try { 
			for (int i = 1; i < 8; i++) { 
				double[] current = new double[max]; 
				int[] times = new int[max];
				
				for (int sec = 0; sec < 999; sec++) {
					times[sec] = sec;
				}
					
				br = new BufferedReader(new FileReader(csvFile)); 
				
				int index = 0;
				
				while ((line = br.readLine()) != null) { 
					String[] second = line.split(","); 
					double adder = printCheck(second, i); 
					current[index] = adder; 
					
					index += 1; 
				}
				
				textWriter(current, times, i); 
			}
		} catch (FileNotFoundException err) { 
			System.out.println(err.getMessage()); 
			
		} catch (IOException err) { 
			System.out.println(err.getMessage()); 
		}  
	}
	
	public static void textWriter(double[] current, int[] times, int i) throws IOException {
		String number = Integer.toString(i); 
		FileWriter writer = new FileWriter("C:\\Users\\PeterKokalov\\Desktop\\Documents\\Queen's\\Second Year\\CISC124\\Assignment_2\\Motor"+number+".txt");
		if (current.length == 0 || current == null) { 
			writer.write("Not used.");
			writer.close();
		}
		else {
			writer.write("start (sec), finish (sec), current (amps)\r\n"); 
			double average = 0.0; 
			int start = 0; 
			int end = 0; 
			int k = 0; 
			for (int j = 0; j < current.length; j++) { 
				try { // try-catch statement
					if (current[j] > 1.0) { 
						if (current[j+1] < 1.0) { 
							end = times[j+1]; 
							average = average / k; 
							BigDecimal bd = new BigDecimal(average); 
							bd = bd.setScale(3, RoundingMode.HALF_UP);
							double avg = bd.doubleValue();
							if (average < 8.0) { 
								writer.write(start+", "+end+", "+avg+"\r\n");
							}
							else {
								writer.write(start+", "+end+", "+avg+", "+"***Current Exceeded***\r\n");
							}
							k = 0; 
							average = 0;
						}
						else {
							start = times[j-k];
							average+= current[j]; 
							k = k + 1;
						}
					}
				}
				catch (IndexOutOfBoundsException error) { 
					continue;
				}
			}
		}
		writer.close();
	}

	public static double printCheck(String[] second, int i) {
		String row = second[i]; 
		double rowdoub = Double.parseDouble(row); 
		if (rowdoub > 1.0) {
			double current = rowdoub;
			return current; 
		}
		else {
			return 0.0; 
		}		
	}

	public static void main(String[] args) throws FileNotFoundException {
		createDoubleArrays();
		System.out.println("Program Complete."); 
	}
}
