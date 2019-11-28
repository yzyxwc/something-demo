package com.example.redisdemo.selocal.linklist;

import java.util.Objects;

/**
 * @author wc
 * @date 10/12/2019
 * distribution:
 * handle create the linklist dependency of Node,is the base of LinkedList
 * main structure is previous Node  next Node and main data
 * Characteristic:
 * it seemed  easy remove and insert but is hart to find data.as for me
 * think that insert is also to find the position of the data so it also find the data.
 */
public class Node {
    /**
     * 前一个结点
     */
    private Node previous;
    /**
     * 业务数据
     */
    private Object obj;
    /**
     * 下一个节点
     */
    private Node next;

    public Node() {
    }

    public Node(Node previous, Object obj, Node next) {
        super();
        this.previous = previous;
        this.obj = obj;
        this.next = next;
    }

    public Node getPrevious() {
        return previous;
    }

    public void setPrivious(Node previous) {
        this.previous = previous;
    }

    public Object getObj() {
        return obj;
    }

    public void setObj(Object obj) {
        this.obj = obj;
    }

    public Node getNext() {
        return next;
    }

    public void setNext(Node next) {
        this.next = next;
    }

    @Override
    public String toString() {
        return obj.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Node)) {
            return false;
        }
        Node node = (Node) o;
        return getPrevious().equals(node.getPrevious()) &&
                getObj().equals(node.getObj()) &&
                getNext().equals(node.getNext());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getPrevious(), getObj(), getNext());
    }
}
