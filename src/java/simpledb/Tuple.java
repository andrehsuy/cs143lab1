package simpledb;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Iterator;

/**
 * Tuple maintains information about the contents of a tuple. Tuples have a
 * specified schema specified by a TupleDesc object and contain Field objects
 * with the data for each field.
 */
public class Tuple implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * Create a new tuple with the specified schema (type).
     * 
     * @param td
     *            the schema of this tuple. It must be a valid TupleDesc
     *            instance with at least one field.
     */
    public Tuple(TupleDesc td) {
        Iterator<TDItem> itr= td.iterator();
        Type[] typeArray= new Type[td.size];
        String[] stringArray= new String[td.size];
        
        while(itr.hasNext()) {
            TDItem item = itr.next();
            typeArray.add(item.fieldType);
            stringArray.add(item.fieldName);
        }
        t= new TupleDesc(typeArray, stringArray);
        size= t.size;
        f= new ArrayList<Field>(size);
        id = null;
    }

    /**
     * @return The TupleDesc representing the schema of this tuple.
     */
    public TupleDesc getTupleDesc() {
        return t;
    }

    /**
     * @return The RecordId representing the location of this tuple on disk. May
     *         be null.
     */
    public RecordId getRecordId() {
        return id;
    }

    /**
     * Set the RecordId information for this tuple.
     * 
     * @param rid
     *            the new RecordId for this tuple.
     */
    public void setRecordId(RecordId rid) {
        
        id= new RecordId(rid.getPageId(), rid.tupleno());
    }

    /**
     * Change the value of the ith field of this tuple.
     * 
     * @param i
     *            index of the field to change. It must be a valid index.
     * @param f
     *            new value for the field.
     */
    public void setField(int i, Field f) {
        if(i>0 && i < size)
        {
            Field temp;
            if(f instanceof IntField)
            {
                IntField old= (IntField) f;
                temp= new IntField(old.getValue());
            }
            
            else if (f instanceof StringField)
            {
                IntField old= (StringField) f;
                temp= new StringField(old.getValue, old.getSize());
            }
            f.set(i, temp);
        }
    }

    /**
     * @return the value of the ith field, or null if it has not been set.
     * 
     * @param i
     *            field index to return. Must be a valid index.
     */
    public Field getField(int i) {
        // some code goes here
        if(i > 0 && i < size)
            return f[i];
        else
            return null;
    }

    /**
     * Returns the contents of this Tuple as a string. Note that to pass the
     * system tests, the format needs to be as follows:
     * 
     * column1\tcolumn2\tcolumn3\t...\tcolumnN\n
     * 
     * where \t is any whitespace, except newline, and \n is a newline
     */
    public String toString() {
        String returnString="";
        Iterator<Field> itr= f.iterator();
        while(itr.hasNext()) {
            Field item = itr.next();
            returnString+= itr.getValue();
        }
        returnString+="\n";
        
        return returnString;
    }
    
    /**
     * @return
     *        An iterator which iterates over all the fields of this tuple
     * */
    public Iterator<Field> fields()
    {
        return f.iterator();
    }
    
    /**
     * reset the TupleDesc of thi tuple
     * */
    public void resetTupleDesc(TupleDesc td)
    {
        // some code goes here
    }
    private TupleDesc t;
    private RecordId id;
    private List<Field> f;
    private int size;
}
