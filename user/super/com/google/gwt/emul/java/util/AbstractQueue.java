/*
 * Copyright 2007 Google Inc.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package java.util;

import static com.google.gwt.core.shared.impl.GwtPreconditions.checkArgument;
import static com.google.gwt.core.shared.impl.GwtPreconditions.checkElement;
import static com.google.gwt.core.shared.impl.GwtPreconditions.checkNotNull;
import static com.google.gwt.core.shared.impl.GwtPreconditions.checkState;

/**
 * Skeletal implementation of the Queue interface. <a
 * href="http://java.sun.com/j2se/1.5.0/docs/api/java/util/AbstractQueue.html">[Sun
 * docs]</a>
 * 
 * @param <E> element type.
 */
public abstract class AbstractQueue<E> extends AbstractCollection<E> implements
    Queue<E> {

  // Should not be instantiated directly.
  protected AbstractQueue() {
  }

  @Override
  public boolean add(E o) {
    checkState(offer(o), "Unable to add element to queue");
    return true;
  }

  @Override
  public boolean addAll(Collection<? extends E> c) {
    checkNotNull(c);
    checkArgument(c != this, "Can't add a queue to itself");

    boolean modified = false;
    for (E val : c) {
      modified |= add(val);
    }
    return modified;
  }

  @Override
  public void clear() {
    while (poll() != null) {
      // empty loop
    }
  }

  public E element() {
    E e = peek();
    checkElement(e != null, "Queue is empty");
    return e;
  }

  public abstract boolean offer(E o);

  public abstract E peek();

  public abstract E poll();

  public E remove() {
    E e = poll();
    checkElement(e != null, "Queue is empty");
    return e;
  }

}
