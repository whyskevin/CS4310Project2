import java.io.*;
import java.util.*;
import java.lang.Math;

public class CPU {
	public static final File test_data = new File("/Users/Kevin/eclipse-workspace/CS4310Proj2/Project2_test_and_page_files/test_files");
	public File [] listOfFiles = null;
	public static Process[][] test_processes = new Process[4][1000];	//[test #index][Assume there are a max of 1000 processes in the test_data]
	public MMU mmu;

	public CPU() {
		listOfFiles = test_data.listFiles();
	}
	
	public void readProcesses(MMU mmu) throws FileNotFoundException{
		boolean read = false;
		int data = 0;
		int testFileIndex = 0;
		int processIndex = 0;
		
		//Writes in all files in the directory
		for(File file: listOfFiles) {
			Scanner in = new Scanner(file);
			while(in.hasNext()) {
//				for(int i = 0; i < 10; i++) {	//Used by CPU to unset R bits
					if (!in.hasNext())
						break;
					int instruction = in.nextInt();

					//grabbing virtual address and splitting
					String virtualAddress = in.next();
					String virtualPageStr = virtualAddress.substring(0,2);
					String pageOffsetStr = virtualAddress.substring(2,4);

					int virtualPage = Integer.valueOf(virtualAddress.substring(0,2), 16);
					int pageOffset = Integer.valueOf(virtualAddress.substring(2,2), 16);
					boolean write = instruction == 1;

					data = 0;
					if (write)
						data = in.nextInt();
					Process readProcess = new Process(instruction,virtualPage,pageOffset,data);	//Saves the values into a Process
//					System.out.println(readProcess);
					test_processes[testFileIndex][processIndex] = readProcess;	//Store in an array
					processIndex++;
//					mmu.processInstruction(virtualPage, pageOffset, write, data);
//				}
			}
			System.out.println(processIndex);
			testFileIndex++;
			processIndex = 0;
		}		
	}
	
	public void resetRbits() {
		mmu.resetRbits();
	}

	public void unsetDbit(int virtualPageIndex) {
		mmu.unsetDbit(virtualPageIndex);
	}
}
