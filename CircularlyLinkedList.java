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
 * A generic CircularlyLinkedList (CLL) class that implements the methods:
 * getData(), length(), add(T data), remove(), advance(), print() 
 * @author Sonal Periwal
 */
public class CircularlyLinkedList<T> {
	protected Node<T> cursor;
	public int size;
	
	/** Initialization of default constructor */
	public CircularlyLinkedList(){
		cursor = null;
		size = 0;
	}
	
	public void setData(T newData) {
		cursor.getNext().data = newData;
	}
	
	/** Gets the data of the cursor
	 * 	@return current cursor data */
	public T getData(){
		return cursor.getNext().data;
	}
	
	/** Gets the number of elements of the cLL
	 * 	@return size  Number of elements */
	public int length(){
	    return size;
	}
	
	/** Creates a new node with data and sets it right after the cursor, increases size of list.
	 *  @param data  The data to add
	 * 	@return true or false  Depending on whether the addition is successful or not */
	public boolean add(T data){
		if(data == null){
			return false;
		}else {
			Node<T> newNode = new Node<T>(data);
			if(cursor == null){
				newNode.setNext(newNode);
				cursor = newNode;
			} else {
				newNode.setNext(cursor.getNext());
				cursor.setNext(newNode);
			}
			size++;
			advance();
			return true;
		}
	}
	
	/** Sets cursor to node after current cursor.
	 * 	@return true or false  Depending on whether the advancement is successful or not */
	public boolean advance(){
		if(cursor.next == null){
			return false;
		} else {
			cursor = cursor.getNext();
			return true;
		}
	}
	
	/** Removes the node immediately after the cursor and decreases size of list.
	 * 	@return true or false  Depending on whether the subtraction is successful or not */
	public boolean remove() {
		if(cursor.next == null){
			return false;
		} 
		Node<T> oldNode = cursor.next;
		if(oldNode == cursor){
			cursor = null;
		} else{
			cursor.next = oldNode.next;
			oldNode.next = null;
		}
		size--;
		return true;
	}
	
	/** Prints list while traversing through it. Stops printing when current node equals cursor again */
	public void print(){
		Node<T> current = cursor;
		while(current!=null){
			System.out.println(current.data);
			current = current.next;
			if(current.data.equals(cursor.data)){
				return;
			}
		}
	}
}
	
