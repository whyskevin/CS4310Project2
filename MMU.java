
public class MMU {
	
	private TlbEntries[] TLB;
	private VirtualPageTableEntries[] pageTable;
	private int[][] physicalMem;
	private int pageReplacementCounter = 0;	//is "pageToReplaceInTLB" a counter?
	
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
	
	public int[][] getPhysicalMem() {
		return physicalMem;
	}
	
	public int getPageReplacementCounter() {
		return pageReplacementCounter;
	}
	
	//idk if we need any setters yet, we can add them later if we need them
	
	public TlbEntries[] replaceTLBEntries(TlbEntries[] TLB, TlbEntries replacementEntry) {
		for(int i = 0; i < TLB.length; i++) {
			if(i == replacementEntry.getVirtualPageNum()){
				TLB[i] = replacementEntry;
				pageReplacementCounter++;
			}
		}
		return TLB;
	}
}
