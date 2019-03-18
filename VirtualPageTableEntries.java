
public class VirtualPageTableEntries {
	private boolean Vbit;
	private boolean Rbit;
	private boolean Dbit;
	private int pageFrameNum;
	
	//pls check the default bits
	public VirtualPageTableEntries() {
		Vbit = false;
		Rbit = false;
		Dbit = false;
		pageFrameNum = -1;
	}
	
	public VirtualPageTableEntries(boolean V, boolean R, boolean D, int pfNum) {
		Vbit = V;
		Rbit = R;
		Dbit = D;
		pageFrameNum = pfNum;
	}
	
	//pretty much getter for V, R, and D
	public bool isValid() {
		return Vbit;
	}

	public bool isReferenced() {
		return Rbit;

	}public bool isDirty() {
		return Dbit;
	}

	public int getPageFrameNum() {
		return pageFrameNum;
	}
	
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
