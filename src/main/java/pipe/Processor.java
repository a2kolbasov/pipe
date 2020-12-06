/*
 * Copyright © 2020 Александр Колбасов
 */

package pipe;

import lombok.Getter;
import lombok.extern.java.Log;
import lombok.val;

@Log
public class Processor {
    @Getter
    private final Ram ram;
    @Getter
    private final Stack stack = new Stack();
    @Getter
    private final ALU alu = new ALU();

    /*
     * Счётчик команд
     */
    @Getter
    private short pc = 0;

    /*
     * Счётчик цикла
     */
    @Getter
    private short counter = 0;

    public Processor(Ram ram) {
        this.ram = ram;
    }

    public Processor(String program) {
        String[] commands = program.split("[ \n]+");
        Ram.Builder builder = Ram.builder();

        for (int i = 0; i < commands.length; i++) {
            final String command = commands[i];
            try {
                builder.add(Command.valueOf(command));
            } catch (IllegalArgumentException ie) {
                try {
                    builder.add(Integer.parseInt(command));
                } catch (NumberFormatException ne) {
                    throw new ProcessorException(String.format("Неверный аргумент \"%s\" (:%d)", command, i));
                }
            }
        }

        this.ram = builder.build();
    }

    public Processor step() {
        int command = ram.get(pc);
        exec(command);
        pc++;
        return this;
    }

    public Processor run() {
        int command;
        do {
            command = ram.get(pc);
            exec(command);
            pc++;
        } while (Command.values()[command] != Command.HLT);
        return this;
    }

    private void exec(int command) {
        Command[] commands = Command.values();
        if (command < 0 || command >= commands.length) throw new IllegalArgumentException("Неверный номер команды");
        exec(commands[command]);
    }

    private void exec(Command command) {
        switch (command) {
            case NON:
                break;
            case HLT:
                break;
            case ADD:
                popToB();
                popToA();
                alu.ADD();
                pushLeastBits();
                break;
            case SUB:
                popToB();
                popToA();
                alu.SUB();
                pushLeastBits();
                break;
            case AND:
                popToB();
                popToA();
                alu.AND();
                pushLeastBits();
                break;
            case OR:
                popToB();
                popToA();
                alu.OR();
                pushLeastBits();
                break;
            case XOR:
                popToB();
                popToA();
                alu.XOR();
                pushLeastBits();
                break;
            case NOT:
                popToA();
                alu.NOT();
                pushLeastBits();
                break;
            case SHR:
                popToA();
                alu.SHR();
                pushLeastBits();
                break;
            case SHL:
                popToA();
                alu.SHL();
                pushLeastBits();
                break;
            case MUL:
                popToB();
                popToA();
                alu.MUL();
                pushLeastBits();
                pushGreatestBits();
                break;
            case ADC:
                popToB();
                popToA();
                alu.ADC();
                pushLeastBits();
                break;
            case PUSH:
                stack.push(ram.get(++pc));
                break;
            case READ:
                stack.push(ram.get(stack.pop()));
                break;
            case WRITE:
                ram.set(stack.pop(), stack.pop());
                break;
            case DUP: {
                val data = stack.pop();
                stack.push(data);
                stack.push(data);
                break;
            }
            case DROP:
                stack.pop();
                break;
            case LDC:
                this.counter = (short) stack.pop();
                break;
            case STC:
                stack.push(this.counter);
                break;
            case CMP: {
                val b = stack.pop();
                val a = stack.pop();
                stack.push(a);
                stack.push(b);
                alu.setA(a).setB(b).SUB();
                break;
            }
            case JMP:
                this.pc = (short) (this.ram.get(++this.pc) - 1);
                break;
            case JZ:
                this.pc++;
                if (alu.getFlags().isZero()) this.pc = (short) (this.ram.get(this.pc) - 1);
                break;
            case JC:
                this.pc++;
                if (alu.getFlags().isCarry()) this.pc = (short) (this.ram.get(this.pc) - 1);
                break;
            case ROL: {
                val c = stack.pop();
                val b = stack.pop();
                val a = stack.pop();
                stack.push(b);
                stack.push(c);
                stack.push(a);
                break;
            }
            case ROR: {
                val c = stack.pop();
                val b = stack.pop();
                val a = stack.pop();
                stack.push(c);
                stack.push(a);
                stack.push(b);
                break;
            }
            case SWAP: {
                val b = stack.pop();
                val a = stack.pop();
                stack.push(b);
                stack.push(a);
                break;
            }
            case LOOP:
                this.pc++;
                if (this.counter == 0 || --this.counter == 0) {
                    this.pc = (short) (this.ram.get(this.pc) - 1);
                }
                break;
            case MAX: {
                val b = stack.pop();
                val a = stack.pop();
                this.alu.setA(a).setB(b).SUB();
                stack.push(
                        this.alu.getFlags().isCarry() ? b : a
                );
            }
                break;
            case MIN: {
                val b = stack.pop();
                val a = stack.pop();
                this.alu.setA(a).setB(b).SUB();
                stack.push(
                        this.alu.getFlags().isCarry() ? a : b
                );
            }
                break;
            default:
                throw new ProcessorException("Нереализованная команда");
        }
        log.info(String.format(
                "command: %s\npc: %s\nstack: %s\nflags: %s",
                command, getPc(), getStack(), getAlu().getFlags()
        ));
    }

    private void popToA() {
        this.alu.setA(this.stack.pop());
    }
    private void popToB() {
        this.alu.setB(this.stack.pop());
    }
    private void pushLeastBits() {
        this.stack.push(this.alu.getResultLeastBits());
    }
    private void pushGreatestBits() {
        this.stack.push(this.alu.getResultGreatestBits());
    }
}
