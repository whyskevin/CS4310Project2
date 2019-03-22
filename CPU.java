import java.io.*;
import java.util.*;
import java.lang.Math;

public class CPU {
	public static final File test_data = new File("/Users/Kevin/eclipse-workspace/CS4310Proj2/Project2_test_and_page_files/test_files");
	public File [] listOfFiles = null;
	public static Process[][] test_processes = new Process[4][1000];	//[test #index][Assume there are a max of 1000 processes in the test_data]
	public MMU mmu;

	public CPU(int tlbSize, int numVirtualPages, int numPhysicalPages, int pageSize) {
		listOfFiles = test_data.listFiles();
		mmu = new MMU(tlbSize, numVirtualPages, numPhysicalPages, pageSize);
	}
	
	public void readProcesses() throws FileNotFoundException{
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

					int virtualPage = convertHexToDecimal(virtualPageStr);
					int pageOffset = convertHexToDecimal(pageOffsetStr);

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
	
	public int convertHexToDecimal(String hex) {
		int decimal = 0;		

		for (int i = 0; i < hex.length(); ++i) {
			char hexChar = hex.charAt(hex.length() - 1 - i);
			int offset = '0';
			if (hexChar >= 'A' && hexChar <= 'F')
				offset = 'A';
			
			decimal += Math.pow(16,i) * (hexChar - offset);
		}
		return decimal;
	}

	public void resetRbits() {
		mmu.resetRbits();
	}

	public void unsetDbit(int virtualPageIndex) {
		mmu.unsetDbit(virtualPageIndex);
	}
	
	
	public static void testCPU() throws FileNotFoundException {
		CPU test = new CPU(16,256,16,256) ;
		test.readProcesses();

	
	}
	
	public static void main (String [] args) {
		try {
			testCPU();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
