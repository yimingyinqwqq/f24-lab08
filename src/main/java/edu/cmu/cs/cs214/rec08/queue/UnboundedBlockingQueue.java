package edu.cmu.cs.cs214.rec08.queue;

import java.util.ArrayDeque;
import java.util.Deque;

import net.jcip.annotations.ThreadSafe;
import net.jcip.annotations.GuardedBy;

/**
 * Modify this class to be thread-safe and be an UnboundedBlockingQueue.
 */
@ThreadSafe
public class UnboundedBlockingQueue<E> implements SimpleQueue<E> {
    @GuardedBy("this")
    private Deque<E> queue = new ArrayDeque<>();

    public UnboundedBlockingQueue() { }

    public boolean isEmpty() { return queue.isEmpty(); }

    public int size() { return queue.size(); }

    public E peek() { return queue.peek(); }

    public void enqueue(E element) {
        synchronized(this){
            queue.add(element);
            this.notify();
        }
    }

    /**
     * TODO:  Change this method to block (waiting for an enqueue) rather
     * than throw an exception.  Completing this task may require
     * modifying other methods.
     */
    public E dequeue() throws InterruptedException {
        synchronized(this){
            while (queue.isEmpty()){
                this.wait();
            }

            return queue.remove();
        }
    }

    public String toString() { return queue.toString(); }
}
