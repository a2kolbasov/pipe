/*
 * Copyright © 2020 Александр Колбасов
 */

import org.junit.Assert;
import org.junit.Test;
import pipe.Flags;

public class TestFlags extends Flags{
    @Test
    public void parseCarryFlag() {
        Assert.assertTrue(parseResult(0xffff + 1).isCarry());
        Assert.assertFalse(parseResult(0xffff).isCarry());
        Assert.assertFalse(parseResult(0).isCarry());
    }
}
