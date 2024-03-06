package ch.zhaw.ads;

import java.util.ArrayList;
import java.util.List;

public class ListStack implements Stack {
    private List<Object> stack = new ArrayList<>();
    @Override
    public void push(Object x) throws StackOverflowError {
        stack.add(x);
    }

    @Override
    public Object pop() {
        if(isEmpty()) {
            return null;
        }
        return stack.remove(stack.size() - 1);
    }

    @Override
    public boolean isEmpty() {
        return stack.isEmpty();
    }

    @Override
    public Object peek() {
        return stack.get(stack.size() - 1);
    }

    @Override
    public void removeAll() {
        stack.clear();
    }

    @Override
    public boolean isFull() {
        return false;
    }
}
