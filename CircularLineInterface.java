/** 
 * A basic interface for a line queue. 
 * @author Kelvin Lu
 * @param <T> The type of object that will be in the queue. 
 */
public interface CircularLineInterface<T> {
	/** This method will add an object into the line. The line would double capacity if full
	 * @param newData The inserted object. 
	 */
	public void insert(T newData);
	
	/** Removes first object in line and returns it. 
	 * @return Returns the object that is being removed.
 	 * @exception NoElementException Thrown if the line is empty. 
	 */
	public T remove() throws NoElementException;
	
	/**
	 * Removes all elements inside a line.
	 * @exception NoElementException Thrown if the line is empty.
	 */
	public void removeAll();
	
	/**
	 * Returns the first element in a line.
	 * @return The first element object inside a line.
	 */
	public T getFront();
	
	/**
	 * Returns the last element in a line.
	 * @return The last element inside a line.
	 */
	public T getBack(); 
	
	/** 
	 * Returns the maximum storage capacity of a line.
	 * @return The storage capacity of a line.
	 */
	public int getCapacity(); 
	
	/** 
	 * Return the number of elements in a line.
	 * @return The number of elements currently in line.
	 */
	public int size();
	
	/**
	 * Returns if the line is empty.
	 * @return True if the line is empty, false otherwise.
	 */
	public boolean isEmpty();
	
	/** 
	 * Returns whether a line is at full capacity.
	 * @return True if line is full, false otherwise.
	 */
	public boolean isFull();
}
