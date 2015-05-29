// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   Set.java

package org.itas.common.collection;


// Referenced classes of package daff.util:
//            Collection, Iterator

public interface Set
    extends Collection
{

    public abstract boolean removeAll(Collection collection);

    public abstract Object[] toArray();

    public abstract Object[] toArray(Object aobj[]);

    public abstract int hashCode();

    public abstract boolean add(Object obj);

    public abstract boolean addAll(Collection collection);

    public abstract boolean contains(Object obj);

    public abstract int size();

    public abstract boolean equals(Object obj);

    public abstract Iterator iterator();

    public abstract boolean containsAll(Collection collection);

    public abstract boolean retainAll(Collection collection);

    public abstract void clear();

    public abstract boolean isEmpty();

    public abstract boolean remove(Object obj);
}
