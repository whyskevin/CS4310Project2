
public class MMU {
	
	private TlbEntries[] TLB;
	private VirtualPageTableEntries[] pageTable;
	private int[][] physicalMem;
	private int TLBPageReplacementCounter = 0;	//is "pageToReplaceInTLB" a counter? It keeps a reference to which page to be replaced.
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
			if (TLB[i].getVirtualPageNum() == virtualPageIndex && TLB[i].isValid())
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
			return pageTable[virtualPageIndex].getPageFrameNum();
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
	public void replaceTLBEntry(TlbEntries replacementEntry) {
		if (TLB[TLBPageReplacementCounter].isDirty()){
			//TODO: write to disk
		}

		TLB[TLBPageReplacementCounter] = replacementEntry;
		TLBPageReplacementCounter = (TLBPageReplacementCounter + 1) % SIZE_OF_TLB;
	}
	
	public void processInstruction(int virtualPageIndex, int physicalOffset , boolean read, int data) {
		if(read) {	//Reading
			int TLB_Flag = checkTLB(virtualPageIndex);
			if(TLB_Flag >= 0) {	//The entry exists in the TLB
				
			}else {	//The entry doesn't exist in the TLB
				int checkResult = checkVirtualPageTable(virtualPageIndex);	//Now check the page table
				if(checkResult >= 0) {
					//Get the entry from the page table
					//Now replace the oldest entry in the TLB with the entry
					VirtualPageTableEntries retrievedEntry = pageTable[checkResult];
					retrievedEntry.setVbit(true);
					retrievedEntry.setRbit(true);
					replaceTLBEntry((TlbEntries)retrievedEntry);
				}else{	//Hard miss
					
				}
			}
		}else {	//Writing
			VirtualPageTableEntries newEntry = new VirtualPageTableEntries();
			
		}
	}
	
}
