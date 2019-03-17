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
 * A generic Node class to be used in the CircularlyLinkedList
 * @author Sonal Periwal
 */
public class Node<T> {
	protected T data;
	protected Node<T> next;
	
	/** Constructor to be used if second argument is not given. 
	 *  @param dataPortion  The data of the node.*/
	public Node(T dataPortion){
		this(dataPortion, null);
	}
	
	/** Constructor to be used setting data to dataPortion and next to nextNode. 
	 *  @param dataPortion, nextNode  The element and next node of this node*/
	public Node(T dataPortion, Node<T> nextNode){
		data = dataPortion;
		next = nextNode;
	}
	
	/** Gets the data of the cursor
	 * 	@return current cursor data */
	public T getData(){
		return data;
	}
	
	/** Sets or replaces data of cursor to newData.
	 *  @param newData  The data to be set */
	public void setData(T newData){
		data = newData;
	}
	
	/** Gets the next node of the cursor
	 * 	@return cursor's next node */
	public Node<T> getNext(){
		return next;
	}
	
	/** Sets or replaces next node of cursor to nextNode.
	 *  @param nextNode  The next node to be set */
	public void setNext(Node<T> nextNode){
		next = nextNode;
	}
	
	/** retrieve's cursor's data and next node
	 * 	@return cursor's data and next node */
	public String toString(){
		return "[" + data + " : " + next + "]";
	}
}