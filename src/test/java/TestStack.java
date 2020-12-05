/*
 * Copyright © 2020 Александр Колбасов
 */

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import pipe.ProcessorException;
import pipe.Stack;

public class TestStack {
    private Stack stack;

    @Before
    public void init() {
        stack = new Stack();
    }

    @Test(expected = ProcessorException.class)
    public void pushMoreThatSize() {
        for (int i = 0; i <= stack.getSize(); i++) {
            stack.push(i);
        }
    }

    @Test(expected = ProcessorException.class)
    public void popIfEmpty() {
        stack.pop();
    }

    @Test
    public void pushAndPop(){
        stack.push(5);
        stack.push(8);
        Assert.assertEquals(8, stack.pop());
        Assert.assertEquals(5, stack.pop());
    }

    @Test
    public void noNegativeData() {
        stack.push(-5);
        Assert.assertEquals(0xffff - 4, stack.pop());
    }

    @Test
    public void getData() {
        stack.push(5);
        stack.push(8);
        Assert.assertArrayEquals(new int[]{5, 8}, stack.getData());
    }
}
