import java.io.*;
import java.util.*;
import java.lang.Math;

public class CPU {
    public static File testData = new File("Project2_test_and_page_files/test_files");
	public File [] listOfFiles;
	public MMU mmu;

	public CPU() {
//		File testData = new File(Driver.testDataDir);
		listOfFiles = testData.listFiles();
	}

	public void readInstruction(int virtualPage, int pageOffset, boolean write, int data) {
		try {
			Driver.mmu.processInstruction(virtualPage, pageOffset, write, data);
		} catch (Exception e) {
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