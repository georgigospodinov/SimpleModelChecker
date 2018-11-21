package model;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;

/**
 * Represents a a sequence of {@link TransitionTo}s that can be made within a model.
 * This class stores instances of {@link TransitionTo} in both a {@link LinkedList} and a {@link HashSet}.
 * This allow constant time 'contains' operation, as well as Stack-like functionality through 'push' and 'pop'.
 * The first {@link TransitionTo} is at the bottom of the Stack and the last {@link TransitionTo} is at the top.
 *
 * @version 1.2
 */
public class Path implements Iterable<TransitionTo> {
    public static final boolean DEBUGGING = false;

    /**
     * This list stores the sequence of {@link TransitionTo}s.
     */
    private LinkedList<TransitionTo> list = new LinkedList<>();

    /** This set stores the {@link TransitionTo}s made. */
    private HashSet<TransitionTo> set = new HashSet<>();

    /**
     * The number of {@link TransitionTo}s stored.
     * This method checks the internal {@link LinkedList}.
     *
     * @return the size of the internal {@link LinkedList}
     */
    public int size() {
        return list.size();
    }

    /**
     * Whether or not any {@link TransitionTo}s are stored.
     * This method checks the internal {@link HashSet}.
     *
     * @return whether the internal {@link HashSet} is empty
     */
    public boolean isEmpty() {
        return set.isEmpty();
    }

    /**
     * Check whether this {@link Path} contains the given {@link TransitionTo}.
     * This method checks the internal {@link HashSet} making this a constant time operation.
     *
     * @param t the {@link TransitionTo} to hash and look for
     * @return true iff the internal {@link HashSet} contains the given {@link TransitionTo}
     */
    public boolean contains(TransitionTo t) {
        return set.contains(t);
    }

    /**
     * Adds the given {@link TransitionTo} to the end of this {@link Path}, as if it is the newest one.
     *
     * @param t the {@link TransitionTo} to add
     */
    public void push(TransitionTo t) {
        if (DEBUGGING)
            System.out.println("Pushing " + t);
        list.addLast(t);
        set.add(t);
    }

    /**
     * Removes the last {@link TransitionTo} from the top of the stack, as if undoing the last movement.
     *
     * @return the {@link TransitionTo} that was removed.
     */
    public TransitionTo pop() {
        TransitionTo t = list.removeLast();
        if (DEBUGGING)
            System.out.println("Popping  " + t);
        set.remove(t);
        return t;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Iterator<TransitionTo> iterator() {
        return list.iterator();
    }

    /**
     * @return the string representation of the internal list
     * @see LinkedList#toString()
     */
    @Override
    public String toString() {
        return list.toString();
    }

    /**
     * Creates and returns a {@link LinkedList} of {@link String}s that represent {@link State}s and {@link TransitionTo} between.
     * This method uses {@link State#getName()} and {@link TransitionTo#getActions()} to obtain {@link String}s for the sequence.
     *
     * @return a {@link LinkedList} of {@link State#name}s and {@link TransitionTo#actions}.
     */
    public LinkedList<String> getSequence() {
        LinkedList<String> seq = new LinkedList<>();
        for (TransitionTo t : list) {
            seq.add(Arrays.toString(t.getActions()));
            seq.add(t.getTrg().getName());
        }
        seq.pollFirst();
        return seq;
    }
}
