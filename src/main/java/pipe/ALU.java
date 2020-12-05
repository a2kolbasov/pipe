/*
 * Copyright © 2020 Александр Колбасов
 */

package pipe;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * Арифметико-логическое устройство
 */
@Accessors(chain = true)
public class ALU {

    @Getter
    private final Flags flags = new Flags();

    /** RG A */
    @Setter
    private int a = 0;

    /** RG B */
    @Setter
    private int b = 0;

    private int resultRG = 0;

    private void setResultRG(int result) {
        this.a = 0;
        this.b = 0;
        this.resultRG = result;
        flags.parseResult(result);
    }

    public int getResultLeastBits(){
        return Short.toUnsignedInt((short) resultRG);
    }

    public int getResultGreatestBits(){
        return Short.toUnsignedInt((short) (resultRG >> Short.SIZE));
    }

    /** @see Command#ADD */
    protected void ADD() {
        setResultRG(a + b);
    }
    /** @see Command#SUB */
    protected void SUB() {
        setResultRG(a - b);
    }
    /** @see Command#AND */
    protected void AND() {
        setResultRG(a & b);
    }
    /** @see Command#OR */
    protected void OR() {
        setResultRG(a | b);
    }
    /** @see Command#XOR */
    protected void XOR() {
        setResultRG(a ^ b);
    }
    /** @see Command#NOT */
    protected void NOT() {
        setResultRG(~a);
    }
    /** @see Command#SHL */
    protected void SHL() {
        setResultRG(a << 1);
    }
    /** @see Command#SHR */
    protected void SHR() {
        setResultRG(a >> 1);
    }
    /** @see Command#MUL */
    protected void MUL() {
        setResultRG(a * b);
    }
    /** @see Command#ADC */
    protected void ADC() {
        setResultRG(a + b);
        if (getFlags().isCarry()) resultRG = getResultLeastBits() + 1;
    }
}
