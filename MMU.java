import java.util.*;
public class MMU {
	
	private TlbEntries[] TLB;
	private VirtualPageTableEntries[] pageTable;
	private int[][] physicalMem;
	private int TLBPageReplacementCounter = 0;	//is "pageToReplaceInTLB" a counter? It keeps a reference to which page to be replaced.
	
	public MMU(int tlbSize, int numVirtualPages, int numPhysicalPages, int pageSize) {
		TLB = new TlbEntries[tlbSize];
		pageTable = new VirtualPageTableEntries[numVirtualPages];
		physicalMem = new int[numPhysicalPages][pageSize];
	}

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

	//adds new tableEntry
	public void addTLBEntry(TlbEntries entry) {
		TLB[TLBPageReplacementCounter] = entry;
		TLBPageReplacementCounter = (TLBPageReplacementCounter + 1) % TLB.length;
	}
	
	public void processInstruction(int virtualPageIndex, int physicalOffset , boolean write, int data) {
		int TLB_Flag = checkTLB(virtualPageIndex);
		TlbEntries presentEntry;
		
		Driver.setEvicted(-1);
		Driver.dirtyEvictedPage(false);
		
		if(TLB_Flag >= 0) {	//The entry exists in the TLB
			presentEntry = TLB[TLB_Flag];
			Driver.softMiss(false);
			Driver.hardMiss(false);
			Driver.hit(true);
		} else {	//The entry doesn't exist in the TLB
			Driver.hit(false);
			int checkResult = checkVirtualPageTable(virtualPageIndex);	//Now check the page table
			if(checkResult >= 0) {	//Soft miss
				//Get the entry from the page table
				//Now replace the oldest entry in the TLB with the entry
				pageTable[checkResult].setRbit(true);
				VirtualPageTableEntries retrievedEntry = pageTable[checkResult];
				TlbEntries replacementEntry = new TlbEntries(virtualPageIndex,
						retrievedEntry.isValid(),
						retrievedEntry.isReferenced(),
						retrievedEntry.isDirty(),
						retrievedEntry.getPageFrameNum());
				addTLBEntry(replacementEntry);
				presentEntry = replacementEntry;
		
				Driver.softMiss(true);
				Driver.hardMiss(false);
			} else{	//Hard miss
				Driver.softMiss(false);
				Driver.hardMiss(true);
				throw new MissingResourceException("Cannot find " + virtualPageIndex + " in the page table.", "VirtualPageEntries", String.valueOf(virtualPageIndex));
				//Throws to CPU readInstruction()
			}
		}
		presentEntry.setRbit(true);
		if(write) {	//Writing
			setPhysicalMem(presentEntry.getPageFrameNum(),physicalOffset,data);
			presentEntry.setDbit(true);
		}else {	//Reading
			data = physicalMem[presentEntry.getPageFrameNum()][physicalOffset];
		}
		
		Driver.setValue(data);
	}


	/*	processInstruction()
	 * 1.Checks if it's present in the TLB
	 * 2.If it is present
	 * 		Read: R bit to true
	 * 		Write: Access (Pg frame number, offset)	in the RAM. Overwrite the value there to the given data
	 * 3.Else if it isn't present check virtualPageTable
	 * 		Soft Miss: if its present in virtualPageTable
	 * 			Retrieves the entry from the pageTable.
	 * 			Add to the TLB and change V and R bit to true
	 * 				Read: does nothing
	 * 				Write: Access (Pg frame number, offset)	in the RAM. Overwrite the value there to the given data
	 * 		Hard Miss:	//Goes to OS to find replacement entry
	 *			Use the clock replacement algorithm to decide which entry in Pg. table to evict
	 *			Loads .pg file from /.page_files directory using the virtualPageIndex.
	 *			Replaces the entry in the pageTable with the new entry at the evicted entry's location.
	 * 			Page frame num is kept
	 * */
	
}
