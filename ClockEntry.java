import java.util.*;

public class ClockEntry {
	private VirtualPageTableEntries entry;
	private int virtualPageNumber;

	public ClockEntry(VirtualPageTableEntries e, int pageNum) {
		entry = e;
		virtualPageNumber = pageNum;
	}

	public int getPageFrameNum() {
		return entry.getPageFrameNum();
	}
	 
	public int getVirtualPageNum() {
		return virtualPageNumber;
	}
	
	public boolean isReferenced() {
		return entry.isReferenced();
	}
	
	public boolean isDirty() {
		return entry.isDirty();
	}
	
	public boolean isValid() {
		return entry.isValid();
	}
	
	public void setPageFrameNum(int pageFrameNum) {
		entry.setPageFrameNum(pageFrameNum);
	}
	
	public void setVirtualPageNum(int pageNum) {
		virtualPageNumber = pageNum;
	}
	
	public void setDbit(boolean b) {
		entry.setDbit(b);
	}
	
	public void setRbit(boolean b) {
		entry.setRbit(b);
	}
	
	public void setVbit(boolean b) {
		entry.setVbit(b);
	}
}
