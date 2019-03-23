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

    
    public static void diskLoad(MMU mmu, int virtualPageIndex, int physicalPageIndex, String pageFileDir) {
        try {
        
            File file = new File(pageFileDir + "/" + Integer.toString(virtualPageIndex, 16) + ".pg");
            Scanner sc = new Scanner(file);
            
            for (int i = 0; i < mmu.getPhysicalMem()[physicalPageIndex].length; ++i)
                mmu.getPhysicalMem()[physicalPageIndex][i] = sc.nextInt();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        
    }
    
    public static void diskWrite(MMU mmu, int virtualPageIndex, int physicalPageIndex, String pageFileDir) {
        try {
            PrintWriter writer = new PrintWriter(pageFileDir + "/" + Integer.toString(virtualPageIndex, 16) + ".pg");
            
            for (int i = 0; i < mmu.getPhysicalMem().length; ++i) {
                for (int j = 0; j < mmu.getPhysicalMem()[i].length; ++j)
                    writer.println(mmu.getPhysicalMem()[i][j]);
            }
            writer.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    } 
    
    public static void main(String[] args) {
        int vp = 24;
        int pf = 1;
        diskWrite(mmu, 24, 1, pageFileDir);
        
        for (int i = 0; i < mmu.getPhysicalMem().length; ++i) {
            for (int j = 0; j < mmu.getPhysicalMem()[i].length; ++j)
                System.out.println(mmu.getPhysicalMem()[i][j] + " ");
            System.out.println();
        }
        
        return;
    }
}