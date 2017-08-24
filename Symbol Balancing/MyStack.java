import java.util.LinkedList;

public class MyStack<T> {
	private LinkedList<T> myStack;
	
	public MyStack() {
		myStack = new LinkedList<>();
	}
	
	public void push (T item) {
		myStack.addFirst(item);
	}
	
	public T pop() {
		if (myStack.isEmpty()) {
			return null;
		}
		
		T data = myStack.getFirst();
		myStack.removeFirst();
		return data;
	}
	
	public int size() {
		return myStack.size();
	}

	public T peek() {
		if (myStack.isEmpty()) {
			System.out.println("Stack is empty");
		}

		T data = myStack.getFirst();
		return data;
	}

	public boolean isEmpty() {

		return myStack.isEmpty();
	}


}
