/*
 * Copyright © 2020 Александр Колбасов
 */

package pipe;

import java.util.Arrays;

public class Stack {
    private final int SIZE = 32;
    private final short[] stack = new short[SIZE];
    private int pointer = 0;

    public int getSize() {
        return this.stack.length;
    }

    public void push(int data) {
        if (pointer >= getSize()) throw new ProcessorException("Стек переполнен");
        stack[pointer++] = (short) data;
    }

    public int pop() {
        if (pointer == 0) throw new ProcessorException("Стек пустой");
        return Short.toUnsignedInt(stack[--pointer]);
    }

    public boolean isEmpty() {
        return pointer == 0;
    }

    public boolean isFull() {
        return pointer == getSize();
    }

    public int[] getData() {
        final int[] data = new int[pointer];
        for (int i = 0; i < data.length; i++) {
            data[i] = Short.toUnsignedInt(stack[i]);
        }
        return data;
    }

    @Override
    public String toString() {
        return Arrays.toString(getData());
    }
}
