/**
 * A queue that is ordered by priority. 
 * @author Kelvin Lu
 * @param <T> A comparable object that is inside the line. 
*/
public class PriorityCircularLine<T extends Comparable<T>> implements CircularLineInterface<T> {
	/** The storage object of this class. */
	private Object[] line;
	/** The starting index of the line. */
	private int start = 0;
	/** The ending index of the line. */
	private int end;
	/** Capacity of the line with the one extra space. */
	private int capacity;

	// Instance Variable Methods. 
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
 
	//Constructors 
	/**
	 * A queue with a custom set capacity.
	 * @param capacity The current capacity of a line.
	 */
	public PriorityCircularLine(int capacity) {
		line = new Object[capacity];
		this.capacity = capacity;
		this.end = line.length - 1;
	}
	/**
	 * A line with a default capacity of 50. 
	 */
	public PriorityCircularLine() {
		this(50);
	}
 
	/** 
	 * If line is full, the line would double is capacity. This is done by forming a new line 
	 * and copying the contents into the new line. The start and end index will reset. 
	 */
	public void doubleCapacity() {
		capacity *= 2;
		//Index of the new array.
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
		//If start and end is in linear order in the line. 
		if (start <= end) {
			for (int i = start; i <= end; i++) {
				data += line[i].toString();
				if ((i) < end) {
					data += ",";
				}
			}
		} else { //If the end index is near the beginning of line. 
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
 
	@Override
	public void insert(T newData) {
		if (isFull()) {
			doubleCapacity();
		}
		//When line is empty
		if(line[start] == null){
			end= start; 
			line[start] = newData; 
			return; 
		}
		//If newData is greater than what's stored
		if(newData.compareTo((T)line[end])>0){
			end++; 
			checkEnd();
			line[end] = newData; 
			return; 
		} 
  
		//If newData is less than other datasets
		if(newData.compareTo((T)line[start])<0){
			shuffle(start,end);
			line[start] = newData;
			end++;
			checkEnd();
			return;
		}
  
		//Middle Indexes
		int i = findIndex(newData);
		System.out.printf("Index Data: %d\n", i);
		shuffle(i,end);
		line[i] = newData;
		end++; 
	}
 
	/**
	 * If end is going out of the line's bounds, then change end index to start. 
	 */
	private void checkEnd(){
		if(end == line.length){
			end = 0; 
		}
	}
 
	/** Find the index of where the object should be placed. 
	 * @param newData The data object the method is trying to find the index for to insert.
	 * @return Index of the placed object.
	*/
	private int findIndex(T newData) { 
		//One data in line. 
		if(start == 0 && end == 0){
			if(newData.compareTo((T)line[0])>0){
				return 1;
			}
			return 0; 
		}
		//Multiple data in line. 
		int i = end; 
		while(i !=(start-1)) {
			if(end==(-1)) { 
				end = line.length-1; 
				continue;
			}
			if(newData.compareTo(((T)line[i]))>0) {
				return ++i; 
			}
			i--;
		}
		return -1;
	}
 
	/**
	 * Shuffles a line down by one starting at the starting index. 
	 * @param start The starting index of a shuffle.
	 * @param end The ending index of a shuffle. 
	 */
	private void shuffle(int start, int end) {
		int i = end; 
		while(i!=(start-1)) {
			//If there's only one element, shift once.
			if(start == 0 && i == 0){
				line[i+1] = line[i];
				break;
			}
			//When i is at the 0 index. 
			if(i==0) {
				line[0] = line[line.length-1];
				i = line.length - 2;
				continue;
			}
			//When i is at the end of the array
			if(i==line.length - 1){
				line[0] = line[i];
				i--; 
				continue;
			}
			line[i+1] = line[i];
			i--;
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
