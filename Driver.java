import java.lang.Math;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.Scanner;
//import org.apache.commons.io.FileUtils;

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
    // public static String testDataDir = "Project2_test_and_page_files/test_files";
    public static String changedPageFileDir = "Project2_test_and_page_files/changed_page_files";
    
    public static File changedPageFiles;
    
    public static String address, write, value, soft, hard, hit, evictedPageNum, dirtyEvictedPage;
    
    public static PrintWriter outputFile;
    
    public Driver() {}
    
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
        evictedPageNum = pgNum < 0? "N/A" : zeroPad(Integer.toString(pgNum, 16)).toUpperCase();
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
    	String arg = args[0];
        try {
        	File originalPageFiles = new File(pageFileDir);
        	changedPageFiles = new File (changedPageFileDir);
			copyFolder(originalPageFiles, changedPageFiles);	//Copy page files into a new directory. Then operate loads/writes using that directory
			Driver.outputFile = new PrintWriter(arg.substring(0, arg.indexOf('.')) + ".csv");
			csvHeader();
		} catch (IOException e) {
			e.printStackTrace();
		}
    	readDirectories(arg);
    	Driver.outputFile.close();
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
    
    //This function copies all the files from sourceFolder to destinationFolder
    private static void copyFolder(File sourceFolder, File destinationFolder) throws IOException
    {
        //Verify if destinationFolder is already present; If not then create it
        if (!destinationFolder.exists())
            destinationFolder.mkdir();
         
        //Get all files from source directory
        String files[] = sourceFolder.list();
         
        //Iterate over all files and copy them to destinationFolder one by one
        for (String file : files)
        {
            File srcFile = new File(sourceFolder, file);
            File destFile = new File(destinationFolder, file);
            
            Files.copy(srcFile.toPath(), destFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
        }
    }

}