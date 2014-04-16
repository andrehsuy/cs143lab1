package simpledb;

import java.io.Serializable;
import java.util.*;

/**
 * TupleDesc describes the schema of a tuple.
 */
public class TupleDesc implements Serializable {

    /**
     * A help class to facilitate organizing the information of each field
     * */
    public static class TDItem implements Serializable {

        private static final long serialVersionUID = 1L;

        /**
         * The type of the field
         * */
        public final Type fieldType;
        
        /**
         * The name of the field
         * */
        public final String fieldName;

        public TDItem(Type t, String n) {
            this.fieldName = n;
            this.fieldType = t;
        }

        public String toString() {
            return fieldName + "(" + fieldType + ")";
        }
    }

    /**
     * @return
     *        An iterator which iterates over all the field TDItems
     *        that are included in this TupleDesc
     * */
    public Iterator<TDItem> iterator() {
        
        return l.iterator();
    }

    private static final long serialVersionUID = 1L;

    /**
     * Create a new TupleDesc with typeAr.length fields with fields of the
     * specified types, with associated named fields.
     * 
     * @param typeAr
     *            array specifying the number of and types of fields in this
     *            TupleDesc. It must contain at least one entry.
     * @param fieldAr
     *            array specifying the names of the fields. Note that names may
     *            be null.
     */
    public TupleDesc(Type[] typeAr, String[] fieldAr) {
    	size = typeAr.length;
    	l= new ArrayList<TDItem>();
    	for(int i=0;i< size;i++)
    	{
    		l.add(new TDItem(typeAr[i], fieldAr[i]));
    	}
    }

    /**
     * Constructor. Create a new tuple desc with typeAr.length fields with
     * fields of the specified types, with anonymous (unnamed) fields.
     * 
     * @param typeAr
     *            array specifying the number of and types of fields in this
     *            TupleDesc. It must contain at least one entry.
     */
    public TupleDesc(Type[] typeAr) {
    	size = typeAr.length;
    	l= new ArrayList<TDItem>();
    	for(int i=0;i< size;i++)
    	{
    		l.add(new TDItem(typeAr[i], ""));
    	}
    }

    /**
     * @return the number of fields in this TupleDesc
     */
    public int numFields() {
        return size;
    }

    /**
     * Gets the (possibly null) field name of the ith field of this TupleDesc.
     * 
     * @param i
     *            index of the field name to return. It must be a valid index.
     * @return the name of the ith field
     * @throws NoSuchElementException
     *             if i is not a valid field reference.
     */
    public String getFieldName(int i) throws NoSuchElementException {
        if(i>= size || i < 0)
        	throw new NoSuchElementException();
        else
        	return l.get(i).fieldName;
        	
    }

    /**
     * Gets the type of the ith field of this TupleDesc.
     * 
     * @param i
     *            The index of the field to get the type of. It must be a valid
     *            index.
     * @return the type of the ith field
     * @throws NoSuchElementException
     *             if i is not a valid field reference.
     */
    public Type getFieldType(int i) throws NoSuchElementException {
    	if(i>=size || i < 0)
        	throw new NoSuchElementException();
        else
        	return l.get(i).fieldType;
    }

    /**
     * Find the index of the field with a given name.
     * 
     * @param name
     *            name of the field.
     * @return the index of the field that is first to have the given name.
     * @throws NoSuchElementException
     *             if no field with a matching name is found.
     */
    public int fieldNameToIndex(String name) throws NoSuchElementException {
        for (int i = 0; i < size; i++)
        	if (l.get(i).fieldName == name)
        			return i;
        throw new NoSuchElementException();
    }

    /**
     * @return The size (in bytes) of tuples corresponding to this TupleDesc.
     *         Note that tuples from a given TupleDesc are of a fixed size.
     */
    public int getSize() {
    	int sizeOfType = 0;
    	int sizeOfString = 0;
    	int totalSize = 0;
    	for (int i = 0; i < size; i++) {
    		sizeOfType += l.get(i).fieldType.getLen();
    		sizeOfString += l.get(i).fieldName.length()*4;
    	}
    	totalSize = sizeOfType + sizeOfString;
    	return totalSize;
    }

    /**
     * Merge two TupleDescs into one, with td1.numFields + td2.numFields fields,
     * with the first td1.numFields coming from td1 and the remaining from td2.
     * 
     * @param td1
     *            The TupleDesc with the first fields of the new TupleDesc
     * @param td2
     *            The TupleDesc with the last fields of the TupleDesc
     * @return the new TupleDesc
     */
    public static TupleDesc merge(TupleDesc td1, TupleDesc td2) {
    	int td3Size = td1.numFields() + td2.numFields();
    	Type [] typeArray = new Type[td3Size];
    	String [] stringArray = new String[td3Size];
    	int i;
    	for (i = 0; i < td1.numFields(); i++) {
    		typeArray[i] = td1.getFieldType(i);
    		stringArray[i] = td1.getFieldName(i);
    	}
    	
    	for (; i < td3Size; i++) {
    		typeArray[i] = td2.getFieldType(i);
    		stringArray[i] = td2.getFieldName(i);
    	}
        
    	TupleDesc td3 = new TupleDesc(typeArray, stringArray);
    	
        return td3;
    }

    /**
     * Compares the specified object with this TupleDesc for equality. Two
     * TupleDescs are considered equal if they are the same size and if the n-th
     * type in this TupleDesc is equal to the n-th type in td.
     * 
     * @param o
     *            the Object to be compared for equality with this TupleDesc.
     * @return true if the object is equal to this TupleDesc.
     */
    public boolean equals(Object o) {
    	if (o.getClass().getName() != "TupleDesc")
    		return false;
    	
    	TupleDesc foo = (TupleDesc) o;
    	if (foo.numFields() != this.numFields())
    		return false;
    	
    	for (int i = 0; i < this.numFields(); i++) {
    		if (foo.getFieldType(i) != this.getFieldType(i))
    			return false;
    	}
    	
    	return true;
    }

    public int hashCode() {
        // If you want to use TupleDesc as keys for HashMap, implement this so
        // that equal objects have equals hashCode() results
        throw new UnsupportedOperationException("unimplemented");
    }

    /**
     * Returns a String describing this descriptor. It should be of the form
     * "fieldType[0](fieldName[0]), ..., fieldType[M](fieldName[M])", although
     * the exact format does not matter.
     * 
     * @return String describing this descriptor.
     */
    public String toString() {
    	String stringDesc = new String();
        for (int i = 0; i < this.numFields(); i++){
        	if (this.getFieldType(i) == Type.INT_TYPE)
        		stringDesc.concat("integer(");
        	else
        		stringDesc.concat("string(");
        	
        	stringDesc.concat(this.getFieldName(i) + "), ");
        }
        return stringDesc;
    }
    public int size;
    List<TDItem> l;
}
