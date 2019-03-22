
public class OS {
	
	CircularlyLinkedList<TlbEntries> clock = new CircularlyLinkedList();
	MMU mmu = new MMU();
	
	public CircularlyLinkedList<TlbEntries> loadClock(MMU mmu){
		TlbEntries[] rtnTLB = mmu.getTLB();
		for(TlbEntries i:rtnTLB) {
			clock.add(i);
		}
	}
	
	public TlbEntries pageReplacement(TlbEntries replacementEntry) {
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
	}
	
	public void resetRbit(MMU mmu) {
		mmu.resetRbits();
	}
	
	public void unsetDbit(MMU mmu, int virtualPageIndex) {
		mmu.unsetDbit(virtualPageIndex);
	}
	
}
