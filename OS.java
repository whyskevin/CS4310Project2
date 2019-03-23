import java.io.FileNotFoundException;

public class OS {
	
	private static CircularlyLinkedList<TlbEntries> clock = new CircularlyLinkedList();
	
	public static CircularlyLinkedList<TlbEntries> loadClock(MMU mmu){
		TlbEntries[] rtnTLB = new TlbEntries[mmu.getPhysicalMem().length];
		for(TlbEntries i:rtnTLB) {
			clock.add(i);
		}
		return clock;
	}
	
	public static TlbEntries pageReplacement(TlbEntries replacementEntry) {
		if(clock.getData().isReferenced()) {
			TlbEntries rtn = clock.getData();
			clock.setData(replacementEntry);
			return rtn;
		} else {
			clock.getData().setRbit(false);
			clock.advance();
		}
		return clock.getData();
	}
	
	public static void clockEvict(TlbEntries replacementEntry) throws FileNotFoundException {
		TlbEntries t = pageReplacement(replacementEntry);
		if(t.isDirty()) {
			//write to hard disk?
			diskWrite(t);
		}
	}
	
	public static void diskWrite(TlbEntries t) throws FileNotFoundException {
		
	}
	
	public static void diskLoad(TlbEntries t) throws FileNotFoundException {
		
	}
	
	public static void resetRbit(MMU mmu) {
		mmu.resetRbits();
		
		for (int i = 0; i < clock.length(); ++i) {
			clock.getData().setRbit(false);
			clock.advance();
		}
	}
	
	public static void unsetDbit(MMU mmu, int virtualPageIndex) {
		mmu.unsetDbit(virtualPageIndex);
	}
	
	public static void readProcesses(CPU cpu) throws FileNotFoundException {
		cpu.readProcesses();
	}
	
}
