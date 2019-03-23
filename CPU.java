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
	
	public void readInstruction(int virtualPage, int pageOffset, boolean write, int data) {
		try {
			Driver.mmu.processInstruction(virtualPage, pageOffset, write, data);
		} catch (Exception e) {
			System.out.println(e.toString());
			throw e;
		}
	}
	
	public void resetRbits() {
		mmu.resetRbits();
	}

	public void unsetDbit(int virtualPageIndex) {
		mmu.unsetDbit(virtualPageIndex);
	}
}
