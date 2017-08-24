
public class TwoStackQueue<AnyType> implements MyQueue<AnyType>{
	private MyStack<AnyType> S1 ;
	private MyStack<AnyType> S2 ;
	private int size;

	public TwoStackQueue() {
		S1 = new MyStack<AnyType>();
		S2 = new MyStack<AnyType>();
		size = 0;
	}
	@Override
	public void enqueue(AnyType x) {
		S1.push(x);
		size++;
	}

	@Override
	public AnyType dequeue() {
		if(S2.isEmpty()) {
			while(!S1.isEmpty()) {
				S2.push(S1.pop());
			}	
		}
		if(!S2.isEmpty()) {
			size--;
		}
		return S2.pop();
	}

	@Override
	public boolean isEmpty() {
		return size == 0;
	}

	@Override
	public int size() {
		return size;
	}

	

}
