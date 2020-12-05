/*
 * Copyright © 2020 Александр Колбасов
 */

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import pipe.Command;
import pipe.ProcessorException;
import pipe.Ram;

public class TestRam {
    private Ram ram;

    @Before
    public void init() {
        ram = new Ram();
    }

    @Test
    public void setAndGet() {
        ram.set(1, 4);
        Assert.assertEquals(4, ram.get(1));

        ram.set(ram.getSize()-1, 2);
        Assert.assertEquals(2, ram.get(ram.getSize()-1));

        Assert.assertEquals(0, ram.get(5));
    }

    @Test
    public void noNegativeNumbers() {
        ram.set(0, -10);
        Assert.assertEquals(0xffff - 9, ram.get(0));
    }

    @Test
    public void outOfBounds() {
        try {
            ram.set(-1, 1);
            throw new AssertionError();
        } catch (ProcessorException e){}

        try {
            ram.get(ram.getSize());
            throw new AssertionError();
        } catch (ProcessorException e){}
    }

    @Test
    public void setCommand() {
        ram.set(5, Command.AND);
        Assert.assertEquals(Command.AND.ordinal(), ram.get(5));
    }
}
