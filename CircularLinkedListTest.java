//
// Name: Periwal, Sonal
// Homework: #1
// Due: 4/24/17
// Course: cs-240-02-sp17
//
// Description:
// Implement the generic class CircularlyLinkedList (CLL). This class will make use of the generic
// class Node. The CLL will provide the following methods: getData(), length(), add(T data), remove(), advance(), print() 
//

/** 
 * A test of the methods as defined in the CircularlyLinkedList class
 * @author Sonal Periwal
*/
public class CircularLinkedListTest {
	public static void main(String[] args) {
		
		//create empty list
		CircularlyLinkedList<String> list = new CircularlyLinkedList<String>();
		
		//Tests add method.]
		//add stuff in opposite order
		String[] words = {"test", "red", "house", "work", "sleep"};
		for (int i = 0; i < words.length; i++) {
			System.out.print("Adding " + words[i] + " returns: ");
			System.out.println(list.add(new String(words[i])));
		}
		System.out.println();
		//Tests length method.
		System.out.println("The list has " + list.length() + " elements." );
		System.out.println();
		//Tests getData method.
		System.out.println("The cursor is currently at: " + list.getData());
		//Tests advance method.
		System.out.print("Advancing to the next node " + "returns: ");
		System.out.println(list.advance());
		System.out.println("The cursor is now at: " + list.getData());
		System.out.println();
		//Tests print method.
		System.out.println("Displaying list:" );
		list.print();
		System.out.println();
		//Tests remove method.
		System.out.print("Removing an element " + "returns: ");
		System.out.println(list.remove());
		System.out.println();
		System.out.println("Displaying current list:" );
		list.print();
	}
}
