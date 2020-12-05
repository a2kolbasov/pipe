/*
 * Copyright © 2020 Александр Колбасов
 */

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import pipe.Command;
import pipe.Ram;

public class TestRamBuilder {
    private Ram.Builder builder;

    @Before
    public void init() {
        builder = Ram.builder();
    }

    @Test
    public void add() {
        Assert.assertEquals(0, builder.getPointer());

        builder.add(5).add(3);
        Assert.assertEquals(2, builder.getPointer());

        builder.setPointer(8).add(7);
        Assert.assertEquals(9, builder.getPointer());

        Ram ram = builder.build();

        Ram expectedRam = new Ram();
        expectedRam.set(0, 5);
        expectedRam.set(1, 3);
        expectedRam.set(8, 7);

        Assert.assertEquals(expectedRam, ram);
    }

    @Test
    public void addCommand() {
        Ram ram = builder.add(Command.ADD).build();
        Assert.assertEquals(Command.ADD.ordinal(), ram.get(0));
    }
}
