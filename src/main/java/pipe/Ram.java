/*
 * Copyright © 2020 Александр Колбасов
 */

package pipe;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

public class Ram {
    private final int SIZE = 512;
    private final short[] ram = new short[SIZE];

    public int getSize() {
        return this.ram.length;
    }

    public void set(int index, int data) {
        check(index);
        this.ram[index] = (short) data;
    }

    public void set(int index, Command command) {
        set(index, command.ordinal());
    }

    public int get(int index) {
        check(index);
        return Short.toUnsignedInt(this.ram[index]);
    }

    private void check(int index) {
        if (index < 0 || index >= getSize()) throw new ProcessorException("Такой ячейки нет");
    }

    @Accessors(chain = true)
    public static class Builder {

        private final Ram ram;

        private Builder() {
            this.ram = new Ram();
        }

        /** Указатель на ячейку, куда будут записанны данные при {@link #add(int)} */
        @Getter
        @Setter
        private int pointer = 0;

        /** Записывает данные по индексу {@link #getPointer()} и увеличивает его на 1 */
        public Builder add(int data) {
            ram.set(pointer++, data);
            return this;
        }

        public Builder add(Command command) {
            ram.set(pointer++, command);
            return this;
        }

        public Ram build() {
            return ram;
        }
    }

    public static Builder builder() {
        return new Builder();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (! (obj instanceof Ram)) return false;

        Ram objRam = (Ram) obj;
        if (objRam.getSize() != this.getSize()) return false;
        for (int i = 0; i < this.getSize(); i++) {
            if (this.get(i) != objRam.get(i)) return false;
        }
        return true;
    }
}
