
public class MMU {
	
	private TlbEntries[] TLB;
	private VirtualPageTableEntries[] pageTable;
	private int[][] physicalMem;
	private int TLBPageReplacementCounter = 0;	//is "pageToReplaceInTLB" a counter?
	private static int SIZE_OF_TLB = 16;
	
	public MMU(TlbEntries[] TLB, VirtualPageTableEntries[] pageTable, 
			int[][] physicalMem) {
		this.TLB = TLB;
		this.pageTable = pageTable;
		this.physicalMem = physicalMem;
	}
	
	public TlbEntries[] getTLB() {
		return TLB;
	}
	
	public VirtualPageTableEntries[] getPageTable() {
		return pageTable;
	}

	//Checks if virtual page index is in TLB
	//if true, returns index for page in TLB
	//if false, returns -1
	public int checkTLB(int virtualPageIndex) {
		for (int i = 0; i < TLB.length; ++i) {
			if (TLB[i].getVirtualPageNum == virtualPageIndex && TLB[i].isValid())
				return i;
		}

		return -1;
	}

	//Probably a better name for this function
	//Checks if virtual page index is loaded in physical memory
	//if true, return page frame number
	//if false, throw error for hard miss
	public int checkVirtualPageTable(int virtualPageIndex) {
		if (pageTable[virtualPageIndex].isValid())
			return pageTable[virtualPageIndex].getPageFrameNumber;
		else
			//TODO: throw error and OS loads page into physical memory
			return -1;
	}
	
	public int[][] getPhysicalMem() {
		return physicalMem;
	}

	public int getPhysicalMem(int pageFrame, int pageOffset) {
		return physicalMem[pageFrame][pageOffset];
	}
	
	public int getTLBPageReplacementCounter() {
		return TLBPageReplacementCounter;
	}
	
	//idk if we need any setters yet, we can add them later if we need them
	public void setPhysicalMem(int pageFrame, int pageOffset, int data) {
		physicalMem[pageFrame][pageOffset] = data;
	}

	//called by OS every 10 instructions
	//resets all r bits in TLB and in page table
	public void resetRbits() {
		for (int i = 0; i < TLB.length; ++i)
			TLB[i].setRbit(false);
		for (int i = 0; i < pageTable.length; ++i)
			pageTable[i].setRbit(false);
	}

	public void unsetDbit(int virtualPageIndex) {
		int TLBIndex = checkTLB(virtualPageIndex);

		if (TLBIndex != -1)
			TLB[TLBIndex].setDbit(false);
		
		pageTable[virtualPageIndex].setDbit(false);
	}

	// public TlbEntries[] replaceTLBEntries(TlbEntries[] TLB, TlbEntries replacementEntry) {
	// 	for(int i = 0; i < TLB.length; i++) {
	// 		if(i == replacementEntry.getVirtualPageNum()){
	// 			TLB[i] = replacementEntry;
	// 			pageReplacementCounter++;
	// 		}
	// 	}
	// 	return TLB;
	// }
	//adds new tableEntry
	public void addTLBEntry(TlbEntries entry) {
		if (TLB[TLBPageReplacementCounter].isDirty()){
			//TODO: write to disk
		}

		TLB[TLBPageReplacementCounter] = entry;
		TLBPageReplacementCounter = (TLBPageReplacementCounter + 1) % SIZE_OF_TLB;
	}
}
