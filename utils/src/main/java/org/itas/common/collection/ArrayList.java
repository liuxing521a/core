// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   ArrayList.java

package org.itas.common.collection;


// Referenced classes of package daff.util:
//            Collection, Iterator, ArrayIterator, List

public class ArrayList
    implements List
{

    public boolean removeAll(Collection collection)
    {
        boolean flag = true;
        for(Iterator iterator1 = collection.iterator(); iterator1.hasNext();)
            flag &= remove(iterator1.next());

        return flag;
    }

    public List subList(int i, int j)
    {
        ArrayList arraylist = new ArrayList(j - i);
        for(int k = i; k < j; k++)
            arraylist.add(get(k));

        return arraylist;
    }

    public Object[] toArray()
    {
        Object aobj[] = new Object[size()];
        System.arraycopy(((Object) (data)), 0, ((Object) (aobj)), 0, aobj.length);
        return aobj;
    }

    public Object[] toArray(Object aobj[])
    {
        System.arraycopy(((Object) (data)), 0, ((Object) (aobj)), 0, aobj.length);
        return aobj;
    }

    public int capacity()
    {
        return data.length;
    }

    private void ensureCapacity(int i)
    {
        if(data.length < i)
        {
            Object aobj[] = new Object[(i * 3) / 2];
            System.arraycopy(((Object) (data)), 0, ((Object) (aobj)), 0, size);
            data = aobj;
        }
    }

    public Object get(int i)
    {
        if(i < 0 || i >= size)
            return null;
        else
            return data[i];
    }

    public Object set(int i, Object obj)
    {
        Object obj1 = data[i];
        data[i] = obj;
        return obj1;
    }

    public String toString()
    {
        StringBuffer stringbuffer = new StringBuffer(getClass().getName() + "[");
        for(int i = 0; i < size; i++)
            stringbuffer.append(data[i].toString() + ',');

        stringbuffer.setCharAt(stringbuffer.length() - 1, ']');
        return stringbuffer.toString();
    }

    public ArrayList()
    {
        this(2);
    }

    public boolean add(Object obj)
    {
        if(obj == null)
        {
            throw new NullPointerException("item is null");
        } else
        {
            ensureCapacity(size + 1);
            data[size++] = obj;
            return true;
        }
    }

    public void add(int i, Object obj)
    {
        if(obj == null)
        {
            throw new NullPointerException("item is null");
        } else
        {
            ensureCapacity(size + 1);
            data[size++] = obj;
            return;
        }
    }

    public ArrayList(int i)
    {
        data = new Object[i];
        size = 0;
    }

    public boolean addAll(Collection collection)
    {
        for(Iterator iterator1 = collection.iterator(); iterator1.hasNext(); add(iterator1.next()));
        return true;
    }

    public boolean addAll(int i, Collection collection)
    {
        for(Iterator iterator1 = collection.iterator(); iterator1.hasNext(); add(i++, iterator1.next()));
        return true;
    }

    public boolean contains(Object obj)
    {
        for(int i = 0; i < size; i++)
            if(data[i] == obj)
                return true;

        return false;
    }

    public int size()
    {
        return size;
    }

    public int indexOf(Object obj)
    {
        for(int i = 0; i < size(); i++)
            if(data[i] == obj)
                return i;

        return -1;
    }

    public boolean equals(Object obj)
    {
        if(obj instanceof ArrayList)
        {
            ArrayList arraylist = (ArrayList)obj;
            boolean flag = arraylist.size == size;
            if(flag)
            {
                for(int i = 0; i < size; i++)
                    if(!data[i].equals(arraylist.data[i]))
                        return false;

            }
            return flag;
        } else
        {
            return false;
        }
    }

    public Iterator iterator()
    {
        return new ArrayIterator(data, size());
    }

    public boolean containsAll(Collection collection)
    {
        boolean flag = true;
        for(Iterator iterator1 = collection.iterator(); iterator1.hasNext();)
            flag &= contains(iterator1.next());

        return flag;
    }

    public boolean retainAll(Collection collection)
    {
        clear();
        addAll(collection);
        return true;
    }

    public void clear()
    {
        for(int i = 0; i < size; i++)
            data[i] = null;

        size = 0;
    }

    public int lastIndexOf(Object obj)
    {
        for(int i = size() - 1; i > 0; i--)
            if(data[i] == obj)
                return i;

        return -1;
    }

    public boolean isEmpty()
    {
        return size == 0;
    }

    public boolean remove(Object obj)
    {
        Object obj1 = remove(indexOf(obj));
        return obj1 != null;
    }

    public Object remove(int i)
    {
        if(i < 0 || i >= size)
        {
            return null;
        } else
        {
            Object obj = data[i];
            data[i] = data[size - 1];
            data[size - 1] = null;
            size--;
            return obj;
        }
    }

    private Object data[];
    private int size;
    
}
