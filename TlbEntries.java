
public class TlbEntries extends VirtualPageTableEntries {
	private int virtualPageNumber;
	
	public TlbEntries() {
		super();
		virtualPageNumber = 0;
	}
	
	public TlbEntries(int vPageNum, boolean V, boolean R, boolean D, int pageFrameNum) {
		super(V, R, D, pageFrameNum);
		this.virtualPageNumber = vPageNum;
	}
	
	public void setVirtualPageNum(int virtualPageNumber) {
		this.virtualPageNumber = virtualPageNumber;
	}
	
	public int getVirtualPageNum() {
		return virtualPageNumber;
	}
	
	//for debugging/checking purposes
	public String toString() {
		return super.toString() + " Virtual Page Num: " + virtualPageNumber;
	}
}

