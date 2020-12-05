/*
 * Copyright © 2020 Александр Колбасов
 */

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import pipe.ALU;

public class TestALU extends ALU {
    @Before
    public void initTest() {
        this.setA(0).setB(0);
    }

    @Test
    public void ADDTest() {
        setA(1);
        setB(2);
        ADD();
        Assert.assertEquals(3, getResultLeastBits());

        // Регистры очищаются сами
        setA(1);
        ADD();
        Assert.assertEquals(1, getResultLeastBits());

        // При переполнении
        setA(0xffff);
        setB(3);
        ADD();
        Assert.assertEquals(2, getResultLeastBits());
        Assert.assertEquals(1, getResultGreatestBits());
        Assert.assertTrue(getFlags().isCarry());
        Assert.assertFalse(getFlags().isZero());
    }

    @Test
    public void SUBTest() {
        setA(5);
        setB(3);
        SUB();
        Assert.assertEquals(2, getResultLeastBits());

        SUB();
        Assert.assertTrue(getFlags().isZero());
    }

    @Test
    public void NOTTest() {
        setA(0xff);
        NOT();
        Assert.assertEquals(Short.toUnsignedInt((short) 0xff00), getResultLeastBits());
    }

    @Test
    public void ADCTest() {
        setA(1);
        setB(2);
        ADC();
        Assert.assertEquals(3, getResultLeastBits());

        setA(0xffff);
        setB(3);
        ADC();
        Assert.assertEquals(3, getResultLeastBits());
        Assert.assertEquals(0, getResultGreatestBits());
        Assert.assertTrue(getFlags().isCarry());
    }
}
