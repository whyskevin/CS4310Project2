import java.lang.Math;
import java.util.Scanner;
import java.io.*;

public class Driver {
    public static final int CPU_ADDRESS_WIDTH = 16;
    public static final int PHYSICAL_ADDRESS_WIDTH = 12;
    public static final int PAGE_OFFSET = 8;
    public static final int TLB_SIZE = 16;

    public static int NUM_VIRTUAL_PAGES = (int) Math.pow(2, CPU_ADDRESS_WIDTH - PAGE_OFFSET);
    public static int NUM_PHYSICAL_PAGES = (int) Math.pow(2, PHYSICAL_ADDRESS_WIDTH - PAGE_OFFSET);
    public static int PAGE_SIZE = (int) Math.pow(2, PAGE_OFFSET);
    
    public static OS os = new OS();
    public static CPU cpu = new CPU();
    public static MMU mmu = new MMU(TLB_SIZE, NUM_VIRTUAL_PAGES, NUM_PHYSICAL_PAGES, PAGE_SIZE);
    
    
    public static String pageFileDir = "Project2_test_and_page_files/page_files";
    public static String testDataDir = "Project2_test_and_page_files/test_files";
    
    public static String address, write, value, soft, hard, hit, evictedPageNum, dirtyEvictedPage;
    
    public static PrintWriter outputFile;
    
    public static void setAddress(String addr) {
        address = addr;
    }
    
    public static void setWrite(boolean wt) {
        write = wt ? "1" : "0";
    }
    
    public static void setValue(int val) {
        value = Integer.toString(val);
    }
    
    public static void softMiss(boolean miss) {
        soft = miss ? "1" : "0";
    }
    
    public static void hardMiss(boolean miss) {
        hard = miss ? "1" : "0";
    }
    
    public static void hit(boolean _hit) {
        hit = _hit ? "1" : "0";
    }
    
    public static void setEvicted(int pgNum) {
        evictedPageNum = Integer.toString(pgNum);
    }
    
    public static void dirtyEvicted(boolean dirty) {
        dirtyEvictedPage = dirty ? "1" : "0";
    }
    
    public static void csvHeader() throws IOException {
        outputFile.write("Address, r/w, value, soft, hard, hit, evicted_pg#, dirty_evicted_page,\n");
    }
    
    public static void outputToCSV() throws IOException {
        outputFile.write(address + ", " + write + ", " + value + ", " +
                        soft + ", " + hard + ", " + hit + ", " +
                        evictedPageNum + ", " + dirtyEvictedPage + ",\n");
    }
    
    public static String zeroPad(String s) {
    	if (s.length() < 2)
    		return "0" + s;
    	return s;
    }
    
    public static void main(String[] args) {
//        String arg = testDataDir + "/test_1.txt";
    	String arg = args[0];
        // OS.loadClock();
        
//        System.out.println(mmu.getPhysicalMem().length);
        try {
			outputFile = new PrintWriter(arg.substring(0, arg.indexOf('.')) + ".csv");
			csvHeader();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	readDirectories(arg);
    	outputFile.close();
    }
    
    public static void readDirectories(String fname) {
    	File file = new File(fname);
    	try {
			os.runProcess(file);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
}