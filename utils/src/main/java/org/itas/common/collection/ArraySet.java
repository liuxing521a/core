// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   ArraySet.java

package org.itas.common.collection;


// Referenced classes of package daff.util:
//            ArrayList, Set

public class ArraySet extends ArrayList
    implements Set
{

    public ArraySet(int i)
    {
        super(i);
    }

    public boolean add(Object obj)
    {
        if(contains(obj))
            return false;
        else
            return super.add(obj);
    }

    public void add(int i, Object obj)
    {
        if(contains(obj))
        {
            return;
        } else
        {
            super.add(i, obj);
            return;
        }
    }
}
