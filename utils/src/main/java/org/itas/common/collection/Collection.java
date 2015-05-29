/*
 * Copyright 2014 xcnet
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.itas.common.collection;



public interface Collection<E> extends Iterable<E> {

    boolean removeAll(Collection<E> c);

    Object[] toArray();

    <T> T[] toArray(T[] a);

    int hashCode();

    boolean add(E obj);

    boolean addAll(Collection<? extends E> c);

    boolean contains(E obj);

    int size();

    boolean equals(Object o);

    Iterator<E> iterator();

    boolean containsAll(Collection<? extends E> c);

    boolean retainAll(Collection<? extends E> c);

    void clear();

    boolean isEmpty();

    boolean remove(Object obj);
}
