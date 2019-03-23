import java.io.FileNotFoundException;

public class OS {
	
	private static CircularlyLinkedList<VirtualPageTableEntries> clock = new CircularlyLinkedList();
	
	public static CircularlyLinkedList<VirtualPageTableEntries> loadClock(MMU mmu){
		VirtualPageTableEntries[] rtnPT = mmu.getPageTable();
		for(VirtualPageTableEntries i:rtnPT) {
			clock.add(i);
		}
		return clock;
		
		
	}
	
	public static VirtualPageTableEntries pageReplacement(VirtualPageTableEntries replacementEntry) {
		for(int i = 0; i < clock.size; i++) {
			if(clock.getData().isReferenced()) {
				VirtualPageTableEntries rtn = clock.getData();
				clock.setData(replacementEntry);
				return rtn;
			} else {
				clock.getData().setRbit(false);
				//changePageTableEntry(clock.getData(), mmu);
				clock.advance();
			}
		}
		return clock.getData();
	}
	
	/*public static void chagePageTableEntry(TlbEntries entry, MMU mmu) {
		int virtualPageIndex = entry.getVirtualPageNum();
		mmu.getPageTable()[virtualPageIndex].setRbit(false);
	}*/
	
	public static void clockEvict(VirtualPageTableEntries replacementEntry) throws FileNotFoundException {
		VirtualPageTableEntries entry = pageReplacement(replacementEntry);
		if(entry.isDirty()) {
			//write to hard disk?
			diskWrite(entry);
		}

	}
	
	public static void diskWrite(VirtualPageTableEntries t) throws FileNotFoundException {
		
	}
	
	public static void diskLoad(VirtualPageTableEntries t) throws FileNotFoundException {
		
	}
	
	public static void resetRbit(MMU mmu) {
		mmu.resetRbits();
		
	}
	
	public static void unsetDbit(MMU mmu, int virtualPageIndex) {
		mmu.unsetDbit(virtualPageIndex);
	}
	
	public static void readProcesses(CPU cpu) throws FileNotFoundException {
		cpu.readProcesses();
	}
	
}
