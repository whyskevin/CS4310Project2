import java.io.*;
import java.util.*;
import java.lang.Math;

public class CPU {
	public final File test_data = new File("/Project2_test_and_page_files.test_files");
	public static File [] listOfFiles = null;
	public MMU mmu;

	public CPU(int tlbSize, int numVirtualPages, int numPhysicalPages, int pageSize) {
		listOfFiles = test_data.listFiles();
		mmu = new MMU(tlbSize, numVirtualPages, numPhysicalPages, pageSize);
	}
	
	public void readProcesses() throws FileNotFoundException{
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
	public int convertHexToDecimal(String hex) {
		int decimal = 0;		

		for (int i = 0; i < hex.size(); ++i) {
			char hexChar = hex[hex.size()-1-i];
			int offset = '0';
			if (hexChar >= 'A' && hexChar <= 'F')
				offset = 'A';
			
			decimal += Math.pow(16,i) * (hexChar - offset)
		}

		return decimal;
	}

	public void resetRbits() {
		mmu.resetRbits();
	}

	public void unsetDbit(int virtualPageIndex) {
		mmu.unsetDbit(virtualPageIndex);
	}
}
