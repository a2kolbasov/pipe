/*
 * Copyright © 2020 Александр Колбасов
 */

import lombok.val;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import pipe.Processor;

public class TestProgram {
    private Processor processor;

    @Test
    public void somethink() {
        val program = "PUSH 5 PUSH 4 ADD PUSH 20 WRITE HLT";
        val processor = new Processor(program).run();

        Assert.assertEquals(9, processor.getRam().get(20));
    }
}
