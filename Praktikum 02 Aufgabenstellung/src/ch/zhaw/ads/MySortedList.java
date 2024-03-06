package ch.zhaw.ads;

import java.util.AbstractList;

class MyList2 extends AbstractList {
    class ListNode implements Comparable{
        Object data;
        ListNode next, prev;

        ListNode(){

        }
        ListNode(Object o) {
            data = o;
        }

        @Override
        public int compareTo(Object o) {
            return data.toString().compareTo(o.toString());
        }
    }
    public ListNode head = new ListNode();
    int size = 0;

    @Override
    public boolean add(Object o) {
        if(head.prev == null) {
            ListNode node = new ListNode(o);
            head.prev = node;
            head.next = node;
            node.next = head;
            node.prev = head;
        } else {
            ListNode last = head.prev;
            ListNode newLast = new ListNode(o);
            last.next= newLast;
            newLast.prev = last;
            newLast.next = head;
            head.prev = newLast;
        }
        size++;
        return true;
    }

    @Override
    public Object get(int pos) {
        int count = 0;
        ListNode currentNode = head.next;
        while (count < pos) {
            currentNode = currentNode.next;
            count++;
        }
        return currentNode.data;
    }

    @Override
    public int size() {
        return size;
//        int count = 0;
//        if (head.next != null) {
//            ListNode current = head.next;
//            while (current != null) {
//                current = current.next;
//                count++;
//            }
//        }
//        return count;
    }

    @Override
    public void clear(){
        head = new ListNode();
    }

    public boolean remove(Object obj) {
        ListNode currentNode = head.next;
        //Only head - empty list
        if(currentNode != null) {
            //If only 1 element
            if (size == 1 && currentNode.data.equals(obj)) {
                head.next = null;
                head.prev = null;
            } else {
                while (currentNode.data != null && !currentNode.data.equals(obj)) {
                    currentNode = currentNode.next;
                }
                if (currentNode.data != null) {
                    currentNode.prev.next = currentNode.next;
                    currentNode.next.prev = currentNode.prev;
                } else {
                    return false;
                }
            }
            size--;
            return true;
        } else {
            return false;
        }
    }
}

public class MySortedList extends MyList2{

    @Override
    public boolean add(Object val) {
        if(head.prev == null) {
            ListNode node = new ListNode(val);
            head.prev = node;
            head.next = node;
            node.next = head;
            node.prev = head;
        } else {
            ListNode currentNode = head.next;

            while(currentNode.compareTo(val) < 0) {
                currentNode = currentNode.next;
                if(currentNode.data == null) {
                    super.add(val);
                    return true;
                }
            }

            ListNode previous = currentNode.prev;
            ListNode next = currentNode;
            ListNode newElement = new ListNode(val);
            previous.next= newElement;
            newElement.prev = previous;
            newElement.next = next;
            currentNode.prev = newElement;
        }
        size++;
        return true;
    }
}
