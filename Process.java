public class Process {
	private boolean write;
	private int virtualPage;
	private int pageOffset;
	private int data;
	
	public Process() {
		write = false;
		virtualPage = 0;
		pageOffset = 0;
		data = 0;
	}
	
	public Process(boolean readWriteBit, int virtualPageIndex, int offset, int data) {
		this.write = readWriteBit;
		this.virtualPage = virtualPageIndex;
		this.pageOffset = offset;
		this.data = data;
	}
	
	public boolean getReadWriteBit() {
		return write;
	}
	
	public int getVirtualPageNum() {
		return virtualPage;
	}
	
	public int getOffset() {
		return pageOffset;
	}
	
	public int getData() {
		return data;
	}
	
	public String toString() {
		return "Read: " + write + " VirtualPgIndex: " + virtualPage + " Offset: " + pageOffset + " Data: "+ data;
		
	}

}
