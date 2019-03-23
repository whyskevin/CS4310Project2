import java.lang.Math;
import java.io.File;
import java.util.Scanner;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

public class Driver {
    public static final int CPU_ADDRESS_WIDTH = 16;
    public static final int PHYSICAL_ADDRESS_WIDTH = 4;
    public static final int PAGE_OFFSET = 2;
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
    
    public static FileWriter outputFile;
    
    public static void setAddress(String addr) {
        address = addr;
    }
    
    public static void setWrite(boolean wt) {
        write = Boolean.toString(wt);
    }
    
    public static void setValue(int val) {
        value = Integer.toString(val);
    }
    
    public static void softMiss(boolean miss) {
        soft = Boolean.toString(miss);
    }
    
    public static void hardMiss(boolean miss) {
        hard = Boolean.toString(miss);
    }
    
    public static void hit(boolean _hit) {
        hit = Boolean.toString(_hit);
    }
    
    public static void setEvicted(int pgNum) {
        evictedPageNum = Integer.toString(pgNum);
    }
    
    public static void dirtyEvicted(boolean dirty) {
        dirtyEvictedPage = Boolean.toString(dirty);
    }
    
    public static void csvHeader() {
        outputFile.write("Address, r/w, value, soft, hard, hit, evicted_pg#, dirty_evicted_page,\n");
    }
    
    public static void outputToCSV() {
        outputFile.write(address + ", " + write + ", " + value + ", " +
                        soft + ", " + hard + ", " + hit + ", " +
                        evictedPageNum + ", " + dirtyEvictedPage + ",\n");
    }
    
    public static void diskLoad(VirtualPageTableEntries entry, int virtualPageNum, String pageFileDir) {
        try {
        
            File file = new File(pageFileDir + "/" + Integer.toString(virtualPageNum, 16) + ".pg");
            Scanner sc = new Scanner(file);
            
            for (int i = 0; i < Driver.mmu.getPhysicalMem()[0].length; ++i)
                Driver.mmu.getPhysicalMem()[entry.getPageFrameNum()][i] = sc.nextInt();
            
            sc.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
    
    public static void diskWrite(VirtualPageTableEntries entry, int virtualPageNum, String pageFileDir) {
        try {
            File file = new File(pageFileDir + "/" + Integer.toString(virtualPageNum, 16) + ".pg");
            PrintWriter writer = new PrintWriter(file);
            
            for (int i = 0; i < Driver.mmu.getPhysicalMem()[0].length; ++i)
                    writer.println(Driver.mmu.getPhysicalMem()[entry.getPageFrameNum()][i]);
                    
            writer.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    } 
    
    public static void main(String[] args) {
        String arg = testDataDir + "test_1.txt";
        outputFile = new FileWriter("test" + arg.substring(arg.indexOf("_"), 2) + ".csv");
    	readDirectories(fname);
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