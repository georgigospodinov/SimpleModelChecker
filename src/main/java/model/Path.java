package model;

import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;

public class Path implements Iterable<TransitionTo> {
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

    public boolean push(TransitionTo t) {
        if (!set.add(t))
            return false;

        list.addLast(t);
        return true;
    }

    public TransitionTo pop() {
        TransitionTo t = list.removeLast();
        set.remove(t);
        return t;
    }

    @Override
    public Iterator<TransitionTo> iterator() {
        return list.iterator();
    }
}
