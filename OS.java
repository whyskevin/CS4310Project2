
public class OS {
	
	private static CircularlyLinkedList<TlbEntries> clock = new CircularlyLinkedList();
	MMU mmu = new MMU();
	
	public static CircularlyLinkedList<TlbEntries> loadClock(MMU mmu){
		TlbEntries[] rtnTLB = mmu.getTLB();
		for(TlbEntries i:rtnTLB) {
			clock.add(i);
		}
	}
	
	public static TlbEntries pageReplacement(TlbEntries replacementEntry) {
		for(int i = 0; i < clock.size; i++) {
			if(clock.getData().isReferenced()) {
				TlbEntries rtn = clock.getData();
				clock.setData(replacementEntry);
				return rtn;
			} else {
				clock.getData().setRbit(false);
				clock.advance();
			}
		}
		return clock.getData();
	}
	
	public static void clockEvict(TlbEntries replacementEntry) {
		TlbEntries t = pageReplacement(replacementEntry);
		if(t.isDirty()) {
			//write to hard disk?
			
		}
	}
	
	public static void resetRbit(MMU mmu) {
		mmu.resetRbits();
	}
	
	public static void unsetDbit(MMU mmu, int virtualPageIndex) {
		mmu.unsetDbit(virtualPageIndex);
	}
	
}
