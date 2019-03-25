import java.io.*;
import java.util.*;

public class CPU {
	public MMU mmu;

	public CPU() {
	}

	public void readInstruction(int virtualPage, int pageOffset, boolean write, int data) {
		try {
			Driver.mmu.processInstruction(virtualPage, pageOffset, write, data);
		} catch (Exception e) {
			throw e;
		}
	}

	public void resetRbits() {
		Driver.mmu.resetRbits();
	}

	public void unsetDbit(int virtualPageIndex) {
		Driver.mmu.unsetDbit(virtualPageIndex);
	}
}