import java.io.*;
import java.util.*;
import java.lang.Math;

public class CPU {
	public File [] listOfFiles = null;
	public MMU mmu;

	public CPU() {
		listOfFiles = Driver.testData.listFiles();
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
