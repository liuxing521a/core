// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   ArrayIterator.java

package org.itas.common.collection;


// Referenced classes of package daff.util:
//            Iterator

class ArrayIterator
    implements Iterator
{

    public String toString()
    {
        StringBuffer stringbuffer = new StringBuffer(getClass().getName() + "[");
        for(int i = 0; i < length; i++)
            stringbuffer.append(data[i].toString() + ',');

        stringbuffer.setCharAt(stringbuffer.length() - 1, ']');
        return stringbuffer.toString();
    }

    ArrayIterator(Object aobj[], int i)
    {
        data = aobj;
        length = i;
    }

    public boolean equals(Object obj)
    {
        if(obj instanceof ArrayIterator)
        {
            ArrayIterator arrayiterator = (ArrayIterator)obj;
            boolean flag = arrayiterator.length == length;
            if(flag)
            {
                for(int i = 0; i < length; i++)
                    if(!data[i].equals(arrayiterator.data[i]))
                        return false;

            }
            return flag;
        } else
        {
            return false;
        }
    }

    public boolean hasNext()
    {
        return pointer < length;
    }

    public Object next()
    {
        return data[pointer++];
    }

    private Object data[];
    private int length;
    private int pointer;
}
