
public class VirtualPageTableEntries {
	private boolean Vbit;
	private boolean Rbit;
	private boolean Dbit;
	private int pageFrameNum;
	
	public VirtualPageTableEntries() {
		Vbit = false;
		Rbit = false;
		Dbit = false;
		pageFrameNum = -1;
	}
	
	//Loaded pages will be present and referenced (true).
	public VirtualPageTableEntries(boolean V, boolean R, boolean D, int pfNum) {
		Vbit = V;
		Rbit = R;
		Dbit = D;
		pageFrameNum = pfNum;
	}
	
	//Getters
	public boolean isValid() {
		return Vbit;
	}

	public boolean isReferenced() {
		return Rbit;
	}
	
	public boolean isDirty() {
		return Dbit;
	}

	public int getPageFrameNum() {
		return pageFrameNum;
	}
	
	//Setters
	public void setVbit(boolean Vbit) {
		this.Vbit = Vbit;
	}
	public void setRbit(boolean Rbit) {
		this.Rbit = Rbit;
	}
	public void setDbit(boolean Dbit) {
		this.Dbit = Dbit;
	}
	public void setPageFrameNum(int pageFrameNum) {
		this.pageFrameNum = pageFrameNum;
	}
	
	//for debugging/checking purposes
	public String toString() {
		return "V bit: " + Vbit + " R bit: " + Rbit + " D bit: "
				+ Dbit + " Page frame Number: " + pageFrameNum;
	}
	
}
