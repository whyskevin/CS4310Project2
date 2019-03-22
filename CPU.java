import java.io.*;
import java.util.*;
import java.lang.Math;

public class CPU {
	public final File test_data = new File("/Project2_test_and_page_files.test_files");
	public static File [] listOfFiles = null;

	public CPU() {
		listOfFiles = test_data.listFiles();
	}
	
	public void readProcesses(MMU mmu) throws FileNotFoundException{
		boolean read = false;
		int data = 0;
		
		//Writes in all files in the directory
		for(File file: listOfFiles) {
			Scanner in = new Scanner(file);
			while(in.hasNext()) {
				for(int i = 0; i < 10; i++) {
					if (!in.hasNext())
						break;
					int instruction = in.nextInt();

					//grabbing virtual address and splitting
					String virtualAddress = in.next();
					int virtualPage = Integer.parseInt(virtualAddress.substring(0,2), 16);
					int pageOffset = Integer.parseInt(virtualAddress.substring(2,2), 16);

					boolean write = instruction == 1;

					int data = 0;
					if (write)
						data = in.nextInt();

					mmu.processInstruction(virtualPage, pageOffset, write, data);
				}
			
			}
		}
	}
}
