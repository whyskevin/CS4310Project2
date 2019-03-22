public class Process {
	private int readOrWrite;
	private int virtualPage;
	private int pageOffset;
	private int data;
	
	public Process() {
		readOrWrite = 0;
		virtualPage = 0;
		pageOffset = 0;
		data = 0;
	}
	
	public Process(int readWriteBit, int virtualPageIndex, int offset, int data) {
		this.readOrWrite = readWriteBit;
		this.virtualPage = virtualPageIndex;
		this.pageOffset = offset;
		this.data = data;
	}
	
	public int getReadWriteBit() {
		return readOrWrite;
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
		return "Read: " + readOrWrite + " VirtualPgIndex: " + virtualPage + " Offset: " + pageOffset + " Data: "+ data;
		
	}

}
