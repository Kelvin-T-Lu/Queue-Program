/**
 * A line queue with a limited capacity where the first object in would be served first.
 * @author Kelvin Lu 
 * @param <T> The object that is inside the queue. 
*/
public class CircularLine<T> implements CircularLineInterface<T> {
	/** The storage object of this class. */
	private Object[] line;
	/** The starting index of the line. */
	private int start = 0;
	/** The ending index of the line. */
	private int end;
	/** Capacity of the line with the one extra space. */
	private int capacity;

	// Instance Variable Methods
	/**
	 * Return the starting index position.
	 * @return Starting index position.
	 */
	public int getStart() {
		return start;
	}
	/**
	 * Returns the ending index position.
	 * @return Ending index position.
	 */
	public int getEnd() {
		return end;
	}

	// Constructors
	/** 
	 * A circular line object with a limited capacity set by parameter.
	 * @param capacity The current capacity of a line. 
	 */
	public CircularLine(int capacity) {
		line = new Object[capacity];
		this.capacity = capacity;
		this.end = line.length - 1;
	}
	
	/** 
	 * A circular line object with the default capacity of 50 elements. 
	 */
	public CircularLine() {
		this(50);
	}

	/**
	 * Doubles the capacity of the line. Copy's the line's queue and place it into a
	 * new line with higher capacity. Start and end indexes will reset.
	 */
	public void doubleCapacity() {
		capacity *= 2;
		// Index of the new array
		int tempIndex = 0;
		Object[] newLine = new Object[capacity];
		// Copying old line to new line
		if (start > end) {
			for (int i = start; i < line.length; i++) {

				newLine[tempIndex++] = line[i];
			}
			for (int j = 0; j <= end; j++) {
				newLine[tempIndex++] = line[j];
			}
		}
		else {// (Start < end)
			for (int i = start; start <= end; i++) {
				newLine[tempIndex++] = line[i];
			}
		}
		this.line = newLine;
		end = tempIndex - 1;
		start = 0;

	}

	/**
	 * Returns the content of the array, similar to ArrayList toString method format. 
	 * @return Return the contents of the line.
	 */
	@Override public String toString() {
		if (isEmpty()) {
			return "[]";
		}
		String data = "[";
		if (start <= end) {
			for (int i = start; i <= end; i++) {
				data += line[i].toString();
				if ((i) < end) {
					data += ",";
				}
			}
		} else {
			for (int i = start; i < line.length; i++) {
				data += line[i].toString();
				if ((i) < line.length) {
					data += ",";
				}
			}
			for (int j = 0; j <= end; j++) {
				data += line[j].toString();
				if ((j + 1) <= end) {
					data += ",";
				}
			}
		}
		data += "]";
		return data;
	}

	@Override public void insert(T newData) {
		if (isFull()) {
			doubleCapacity();
		}
		if (end + 1 < line.length) {
			line[++end] = newData;
		} else {
			end = 0;
			line[end] = newData;
		}
	}

	@Override public T remove() {
		if (isEmpty()) {
			throw new NoElementException();
		}
		T temp = (T) line[start];
		if ((start + 1 == line.length)) {
			line[start] = null;
			start = 0;
		} else {
			line[start++] = null;
		}
		return temp;
	}

	@Override public void removeAll() {
		if (isEmpty()) {
			throw new NoElementException();
		}
		line = new Object[capacity];
		start = 0;
		end = line.length - 1;
	}

	@Override
	public T getFront() {
		return (T)line[start];
	}

	@Override
	public T getBack() {
		return (T)line[end];
	}

	@Override
	public int getCapacity() {
		return capacity - 1;
	}

	@Override
	public int size() {
		// Empty line
		if (line[start] == null) {
			return 0;
		}
		// One element in line
		else if (start == end) {
			return 1;
		}
		//If the start is at the 0 index
		else if (start < end && start == 0) {
			return (end+1);
		}
		// If end is not circled around
		else if (start < end) {
			return (end - start);
		}
		// If the end has circled around
		else {
			return ((line.length - start) + (end + 1));
		}
	}

	@Override
	public boolean isEmpty() {
		if (size() == 0) {
			return true;
		}

		return false;
	}

	@Override
	public boolean isFull() {
		if (size() == (getCapacity())) {
			return true;
		}
		return false;
	}

}
