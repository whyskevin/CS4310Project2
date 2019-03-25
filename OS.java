import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

public class OS {

	private static CircularlyLinkedList<VirtualPageTableEntries> clock = new CircularlyLinkedList<VirtualPageTableEntries>();

	public static int pageReplacement(VirtualPageTableEntries replacementEntry, int virtualPageNum) {
		//Logic before the clock is filled
		if(clock.length() < Driver.NUM_PHYSICAL_PAGES) {
			int pageNum = clock.length();
			clock.add(replacementEntry);
			replacementEntry.setPageFrameNum(pageNum);
			return pageNum;
		};
		
		//Logic for filled clock 
		for(int i = 0; i < clock.length(); i++) {
			if(!clock.getData().isReferenced())
				break;
			else {
				clock.getData().setRbit(false);
				clock.advance();
			}
		}
		if(clock.getData().isDirty()) {
			//write to hard disk?
			Driver.dirtyEvicted(true);
			clock.getData().setDbit(false);
			diskWrite(clock.getData(), virtualPageNum);
		}
		
		int evictedPage = clock.getData().getPageFrameNum();
		clock.getData().setVbit(false);
		clock.remove();
		clock.add(replacementEntry);
		replacementEntry.setPageFrameNum(evictedPage);
		Driver.setEvicted(evictedPage);
		
		return evictedPage;
	}
	
	//Passed in a File from Driver class and begins Scanning File contents
	public static void runProcess(File fileToRead) throws FileNotFoundException {
		boolean read = false;
		int data = 0;
		Scanner in = new Scanner(fileToRead);

		while(in.hasNext()) {
			for(int i = 0; i < 10; i++) {	//Used by CPU to unset R bits
				if (!in.hasNext())
					break;
				int instruction = in.nextInt();

				//grabbing virtual address and splitting
				String virtualAddress = in.next();
				int virtualPage = Integer.valueOf(virtualAddress.substring(0,2), 16);
				int pageOffset = Integer.valueOf(virtualAddress.substring(2,4), 16);
				boolean write = instruction == 1;

				Driver.setAddress(virtualAddress);
				Driver.setWrite(write);

				data = 0;
				if (write)
					data = in.nextInt();
				try {
					Driver.setEvicted(-1);
					Driver.dirtyEvicted(false);
					Driver.cpu.readInstruction(virtualPage, pageOffset, write, data);
					Driver.outputToCSV();
				}catch(Exception e){	//Hard miss
//					System.out.println(e.getMessage());					
					int pageNum = pageReplacement(Driver.mmu.getPageTable()[virtualPage], virtualPage);
					
					diskLoad(pageNum, virtualPage);

					Driver.cpu.readInstruction(virtualPage, pageOffset, write, data);
					
					Driver.softMiss(false);
					Driver.hardMiss(true);
					
					try {
						Driver.outputToCSV();
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			}
			resetRbit();
		}
	}

    public static void diskLoad(VirtualPageTableEntries entry, int virtualPageNum) {
    	diskLoad(entry.getPageFrameNum(), virtualPageNum);
    }
    
    public static void diskLoad( int pageFrameNumber, int virtualPageNum) {
        try {
            
            File file = new File(Driver.changedPageFileDir + "/" + Driver.zeroPad(Integer.toString(virtualPageNum, 16).toUpperCase()) + ".pg");
            Scanner sc = new Scanner(file);
            
            for (int i = 0; i < Driver.mmu.getPhysicalMem()[0].length; ++i)
                Driver.mmu.getPhysicalMem()[pageFrameNumber][i] = sc.nextInt();
            sc.close();
           Driver.mmu.getPageTable()[virtualPageNum].setVbit(true);
           Driver.mmu.getPageTable()[virtualPageNum].setRbit(true);
           Driver.mmu.getPageTable()[virtualPageNum].setDbit(false);
           Driver.mmu.getPageTable()[virtualPageNum].setPageFrameNum(pageFrameNumber);
//           System.out.println(Driver.mmu.getPageTable()[virtualPageNum]);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
    
    public static void diskWrite(VirtualPageTableEntries entry, int virtualPageNum) {
    	diskWrite(entry.getPageFrameNum(), virtualPageNum);
    }
    
    public static void diskWrite(int pageFrameNumber, int virtualPageNum) {
        try {
            File file = new File(Driver.changedPageFileDir + "/" + Driver.zeroPad(Integer.toString(virtualPageNum, 16).toUpperCase()) + ".pg");
            PrintWriter writer = new PrintWriter(file);
            
            for (int i = 0; i < Driver.mmu.getPhysicalMem()[0].length; ++i)
                    writer.println(Driver.mmu.getPhysicalMem()[pageFrameNumber][i]);
                    
            writer.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    } 

	public static void resetRbit() {
		Driver.mmu.resetRbits();
	}

	public static void unsetDbit(MMU mmu, int virtualPageIndex) {
		mmu.unsetDbit(virtualPageIndex);
	}
}
