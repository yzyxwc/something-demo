package com.example.redisdemo.selocal.linklist;

/**
 * @author wc
 * @date 10/12/2019
 * distribution:
 * the collection type of linkedList handle implement
 */
public class LinkedListImpl {
    /**
     * 首个节点
     */
    private Node first;
    /**
     * 尾节点
     */
    private Node last;
    /**
     * 链表长度(大小)
     */
    private int size;

    /**
     * 新增数据
     */
    public LinkedListImpl add(Object object){
        Node temp = new Node();
        if(null == first){
            temp.setPrivious(null);
            temp.setObj(object);
            temp.setNext(null);
            first = temp;
            last = temp;
        }else{
            temp.setPrivious(last);
            temp.setObj(object);
            temp.setNext(null);
            last.setNext(temp);
            last = temp;
        }
        size++;
        return this;
    }
    /**
     * 查询数据
     */
    public Object get(int index){
        Node node = node(index);
        if(null != node){
            return node.getObj();
        }
        return null;
    }

    /**
     * 循环遍历
     * @param index 坐标
     * @return 节点
     */
    private Node node(int index) {
        Node temp = null;
        if(null != first){
            temp = first;
            for (int i = 0; i < index; i++) {
                temp = temp.getNext();
            }
        }
        return temp;
    }
    /**
     * 修改数据
     */
    public LinkedListImpl update(int index,Object obj){
        Node temp = node(index);
        if(null != temp){
            temp.setObj(obj);
        }
        return this;
    }
    /**
     * 指定位置插入数据
     */
    public LinkedListImpl insert(int index,Object obj){
        Node node = node(index);
        Node temp = new Node();
        temp.setObj(obj);
        if(null != node){
            Node previous = temp.getPrevious();
            previous.setNext(temp);
            temp.setPrivious(previous);
            temp.setNext(node);
            node.setPrivious(temp);
            size++;
        }
        return this;
    }
    /**
     * 删除节点数据
     */
    public LinkedListImpl delete(int index){
        Node node = node(index);
        if(null != node){
            Node previous = node.getPrevious();
            Node next = node.getNext();
            previous.setNext(next);
            next.setNext(previous);
            size --;
        }
        return this;
    }
    /**
     * 获取当前linkedList的大小
     */
    public Integer size(){
        return this.size;
    }

    @Override
    public String toString() {
        return "LinkedListImpl{" +
                "first=" + first +
                ", last=" + last +
                ", size=" + size +
                '}';
    }
}
