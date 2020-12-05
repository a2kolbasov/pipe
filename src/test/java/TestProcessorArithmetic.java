/*
 * Copyright © 2020 Александр Колбасов
 */

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import pipe.Command;
import pipe.Processor;
import pipe.Ram;

public class TestProcessorArithmetic {
    private Processor processor;

    @Before
    public void init() {
    }

    @Test
    public void ADD() {
        processor = new Processor(Ram.builder()
                .add(Command.PUSH).add(3)
                .add(Command.PUSH).add(2)
                .add(Command.ADD)
                .build()
        );
        processor.step().step();
        Assert.assertArrayEquals(new int[]{2, 3}, processor.getStack().getData());
        Assert.assertArrayEquals(new int[]{5}, processor.step().getStack().getData());
    }

    @Test
    public void ADC() {
        processor = new Processor(Ram.builder()
                .add(Command.PUSH).add(0xffff)
                .add(Command.PUSH).add(2)
                .add(Command.ADC)
                .build()
        );
        processor.run();
        Assert.assertArrayEquals(new int[]{2}, processor.getStack().getData());
    }
}
