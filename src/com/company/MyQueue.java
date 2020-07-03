package com.company;

import java.util.Iterator;
import java.util.function.Consumer;

public class MyQueue<T> implements Iterable<T>{ // Очередь

    private class ListNode { //Элемент очереди

        T value;       //значение элемента очереди
        ListNode next; //ссылка на следующий элемент очереди

        ListNode(T value, ListNode next) {
            this.value = value;
            this.next = next;
        }

        ListNode(T value) {
            this.value = value;
            this.next = null;
        }

        ListNode() {
            this(null, null);
        }
    }

    private ListNode head = null;
    private ListNode tail = null;
    private int count = 0;

    public void add(T value) {
        if (head == null) {
            head = tail = new ListNode(value, null);
        } else {
            tail.next = new ListNode(value, null);
            tail = tail.next;
        }
        count++;
    }

    /**
     * Возвращает значение первого элемента очереди и удаляет его.(если очередь пуста возбуждает исключение).
     * @return value
     * @throws Exception ex
     */
    public T get() throws Exception {
        if (head == null) {
            throw new Exception("Очередь пуста!");
        }
        T result = head.value;
        head = head.next;
        if (head == null) {
            tail = null;
        }
        count--;
        return result;
    }

    /**
     * Возвращает значение первого элемента очереди и удаляет его.(если очередь пуста возвращает null).
     * @return value
     */
    public T poll() {
        T result;
        if (head == null) {
            result = null;
        } else {
            result = head.value;
            head = head.next;
            count--;
        }
        if (head == null) {
            tail = null;
        }
        return result;
    }

    /**
     * Возвращает значение первого элемента очереди без его удаления.
     * @return value
     * @throws Exception ex
     */
    public T peek() throws Exception {
        if (head == null) {
            throw new Exception("Очередь пуста!");
        }
        return head.value;
    }

    /**
     * Возвращает размер очереди.
     * @return size
     */
    public int getSize() {
        return count;
    }

    /**
     * Проверка пустоты очереди.
     * @return isEmpty?
     */
    public boolean isEmpty() {
        return count <= 0;
    }

    /**
     * Итератор
     * @return elements
     */
    @Override
    public Iterator<T> iterator() {
        return new Iterator<T>() {
            private ListNode curr = head;

            @Override
            public boolean hasNext() {
                return curr != null;
            }

            @Override
            public T next() {
                T res = curr.value;
                curr = curr.next;
                return res;
            }
        };
    }

    /**
     * Дублирует элементы очереди
     * @throws Exception ex
     */
    public void duplicateElements() throws Exception {
        if (head == null) {
            throw new Exception("Очередь пуста!");
        }
        int countItems = count;
        ListNode curr = head;
        for (int i = 0; i < countItems; i++) {
            ListNode newNode = new ListNode(curr.value, curr.next);
            count++;
            curr.next = newNode;
            curr = curr.next;
            if (i < countItems - 1) {
                curr = curr.next;
            }
        }

    }

}
