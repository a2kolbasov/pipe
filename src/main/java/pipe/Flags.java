/*
 * Copyright © 2020 Александр Колбасов
 */

package pipe;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

@Accessors(chain = true)
@Getter
@Setter(value = AccessLevel.PROTECTED)
@ToString
public class Flags {

    /** Флаг переноса */
    private boolean carry = false;

    /** Флаг нуля */
    private boolean zero = false;

    /** Обнуляет флаги */
    protected Flags clean() {
        this.carry = false;
        this.zero = false;
        return this;
    }

    private static boolean parseCarryFlag(int resultOfOperation) {
        final int carryBit = ((resultOfOperation >> Short.SIZE) & 1);
        return carryBit == 1;
    }

    /** Устанавливает флаги по результату операции */
    protected Flags parseResult(int resultOfOperation) {
        this.carry = parseCarryFlag(resultOfOperation);
        this.zero = resultOfOperation == 0;
        return this;
    }
}
