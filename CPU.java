import java.io.*;
import java.util.*;

public class CPU {
	public final File test_data = new File("/Project2_test_and_page_files.test_files");
	public static File [] listOfFiles = null;
	
	public void readProcesses() throws FileNotFoundException{
		listOfFiles = test_data.listFiles();
		boolean read = false;
		int data = 0;
		
		//Writes in all files in the directory
		for(File file: listOfFiles) {
			Scanner in = new Scanner(file);
			while(in.hasNext()) {
				String valueString = in.next();
				for(int i = 0; i < 10; i++) {
					if(Integer.valueOf(valueString) == 1) {	//Writing data
						
					}
				}
			
			}
		}
	}
	
}
