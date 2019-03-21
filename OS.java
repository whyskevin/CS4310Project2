
public class OS {
	public static void main(String[] args) {
		
		CircularlyLinkedList<Integer> clockReplAlg = new CircularlyLinkedList();
		
		MMU mmu = new MMU();
		
		for(int i = 0; i < mmu.getPhysicalMem().length; i++) {
			clockReplAlg.add(mmu.getPhysicalMem()[0][i]);
		}
		
		VirtualPageTableEntries[] pageTable = mmu.getPageTable();
		
	}
	public void pageReplacement(CircularlyLinkedList clockReplAlg, VirtualPageTableEntries[] pageTable, int replacementEntry) {
		for(VirtualPageTableEntries[] page: pageTable) {
			for(VirtualPageTableEntries pageEntry: page) {
				if((int)clockReplAlg.getData() == pageEntry.getPageFrameNum()) {
					if(pageEntry.isReferenced() == true) {
						pageEntry.setRbit(false);
						clockReplAlg.advance();
					} else {
						clockReplAlg.setData(replacementEntry);
					}
				}
			}
		}
	}
	
}
