// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   HashMap.java

package org.itas.common.collection;


// Referenced classes of package daff.util:
//            Map, Set, Iterator, ArrayList, 
//            ArraySet, Collection

public final class HashMap
    implements Map
{
    private class MapEntry
    {

        Object key;
        Object value;
        MapEntry next;

        MapEntry(Object obj, Object obj1)
        {
            key = obj;
            value = obj1;
        }
    }


    private int index2(Object obj)
    {
        return -1;
    }

    private void ensureCapacity()
    {
        int i = (count * 100) / loadFactor;
        if(i >= entries.length)
        {
            MapEntry amapentry[] = entries;
            init((i * 100) / loadFactor);
            for(int j = 0; j < amapentry.length; j++)
            {
                for(MapEntry mapentry = amapentry[j]; mapentry != null; mapentry = mapentry.next)
                    put(mapentry.key, mapentry.value);

            }

        }
    }

    public Object put(Object obj, Object obj1)
    {
        if(obj == null || obj1 == null)
            return null;
        ensureCapacity();
        int i = Math.abs(obj.hashCode()) % entries.length;
        MapEntry mapentry = entries[i];
        if(mapentry == null)
        {
            mapentry = new MapEntry(obj, obj1);
            mapentry.key = obj;
            mapentry.value = obj1;
            entries[i] = mapentry;
            count++;
            return obj1;
        }
        if(mapentry.key.equals(obj))
        {
            mapentry.value = obj1;
            return obj1;
        }
        for(; mapentry != null; mapentry = mapentry.next)
        {
            if(mapentry.key.equals(obj))
            {
                mapentry.value = obj1;
                return obj1;
            }
            if(mapentry.next == null)
            {
                mapentry.next = new MapEntry(obj, obj1);
                count++;
                return obj1;
            }
        }

        return null;
    }

    public Object get(Object obj)
    {
        if(obj == null)
            return null;
        int i = Math.abs(obj.hashCode()) % entries.length;
        for(MapEntry mapentry = entries[i]; mapentry != null; mapentry = mapentry.next)
            if(mapentry.key.equals(obj))
                return mapentry.value;

        return null;
    }

    public void put(int i, Object obj)
    {
        put(new Integer(i), obj);
    }

    public Object get(int i)
    {
        return get(new Integer(i));
    }

    public String toString()
    {
        StringBuffer stringbuffer = new StringBuffer();
        stringbuffer.append(getClass().getName() + "{");
        for(int i = 0; i < entries.length; i++)
        {
            MapEntry mapentry = entries[i];
            if(mapentry != null)
            {
                stringbuffer.append(mapentry.key + "=" + mapentry.value + ";");
                for(mapentry = mapentry.next; mapentry != null; mapentry = mapentry.next)
                    stringbuffer.append(mapentry.key + "=" + mapentry.value + ";");

            }
        }

        stringbuffer.setCharAt(stringbuffer.length() - 1, '}');
        return stringbuffer.toString();
    }

    public HashMap()
    {
        this(100);
    }

    public HashMap(int i)
    {
        this(i, 75);
    }

    public HashMap(int i, int j)
    {
        if(j < 0 || j >= 100)
        {
            throw new IllegalArgumentException();
        } else
        {
            loadFactor = j;
            init(i);
            return;
        }
    }

    private int index1(Object obj)
    {
        return -1;
    }

    public int size()
    {
        return count;
    }

    public boolean containsKey(Object obj)
    {
        int i = Math.abs(obj.hashCode()) % entries.length;
        if(entries[i] == null)
            return false;
        MapEntry mapentry = entries[i];
        if(mapentry.key.equals(obj))
            return true;
        for(mapentry = mapentry.next; mapentry != null; mapentry = mapentry.next)
            if(mapentry.key.equals(obj))
                return true;

        return false;
    }

    private int locate(Object obj, int i)
    {
        return -1;
    }

    public boolean equals(Object obj)
    {
        return false;
    }

    public void putAll(Map map)
    {
        Object obj;
        for(Iterator iterator = map.keySet().iterator(); iterator.hasNext(); put(obj, map.get(obj)))
            obj = iterator.next();

    }

    public Collection values()
    {
        ArrayList arraylist = new ArrayList(count);
        for(int i = 0; i < entries.length; i++)
        {
            MapEntry mapentry = entries[i];
            if(mapentry != null)
            {
                arraylist.add(mapentry.value);
                for(mapentry = mapentry.next; mapentry != null; mapentry = mapentry.next)
                    arraylist.add(mapentry.value);

            }
        }

        return arraylist;
    }

    public void clear()
    {
        for(int i = 0; i < entries.length; i++)
            entries[i] = null;

        count = 0;
    }

    public boolean isEmpty()
    {
        return count == 0;
    }

    public boolean containsValue(Object obj)
    {
        for(int i = 0; i < entries.length; i++)
        {
            MapEntry mapentry = entries[i];
            if(mapentry != null)
            {
                if(mapentry.value.equals(obj))
                    return true;
                for(mapentry = mapentry.next; mapentry != null; mapentry = mapentry.next)
                    if(mapentry.value.equals(obj))
                        return true;

            }
        }

        return false;
    }

    public Set keySet()
    {
        ArraySet arrayset = new ArraySet(count);
        for(int i = 0; i < entries.length; i++)
        {
            MapEntry mapentry = entries[i];
            if(mapentry != null)
                for(; mapentry != null; mapentry = mapentry.next)
                    arrayset.add(mapentry.key);

        }

        return arrayset;
    }

    private void init(int i)
    {
        entries = new MapEntry[i];
        count = 0;
    }

    public Object remove(Object obj)
    {
        if(obj == null)
            return null;
        int i = Math.abs(obj.hashCode()) % entries.length;
        MapEntry mapentry = entries[i];
        if(mapentry == null)
            return null;
        count--;
        if(mapentry.key.equals(obj))
        {
            entries[i] = mapentry.next;
            return mapentry.value;
        }
        for(; mapentry.next != null; mapentry = mapentry.next)
            if(mapentry.next.key.equals(obj))
            {
                Object obj1 = mapentry.next.value;
                mapentry.next = mapentry.next.next;
                return obj1;
            }

        count++;
        return null;
    }

    private int loadFactor;
    private int count;
    private MapEntry entries[];
}
