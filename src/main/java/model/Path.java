package model;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;

public class Path implements Iterable<TransitionTo> {
    public static final boolean DEBUGGING = false;

    private LinkedList<TransitionTo> list = new LinkedList<>();
    private HashSet<TransitionTo> set = new HashSet<>();

    public int size() {
        return list.size();
    }

    public boolean isEmpty() {
        return set.isEmpty();
    }

    public boolean contains(TransitionTo t) {
        return set.contains(t);
    }

    public void push(TransitionTo t) {
        if (DEBUGGING)
            System.out.println("Pushing " + t);
        list.addLast(t);
        set.add(t);
    }

    public TransitionTo pop() {
        TransitionTo t = list.removeLast();
        if (DEBUGGING)
            System.out.println("Popping  " + t);
        set.remove(t);
        return t;
    }

    @Override
    public Iterator<TransitionTo> iterator() {
        return list.iterator();
    }

    @Override
    public String toString() {
        return list.toString();
    }

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
