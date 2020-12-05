/*
 * Copyright © 2020 Александр Колбасов
 */

package pipe;

import lombok.Getter;
import lombok.extern.java.Log;

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
        log.info(command.toString());
        switch (command) {
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
                pushGreatestBits();
                pushLeastBits();
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
            case DUP:
            {
                int data = stack.pop();
                stack.push(data);
                stack.push(data);
            }
                break;
            case DROP:
                stack.pop();
                break;
            case LDC:
                counter = (short) stack.pop();
                break;
            case STC:
                stack.push(counter);
                break;
            case CMP:
                popToB();
                popToA();
                alu.SUB();
            case JMP:
                pc = (short) (stack.pop() - 1);
                break;
            case JZ:
                if (alu.getFlags().isZero()) pc = (short) (stack.pop() - 1);
                else pc++;
                break;
            case JC:
                if (alu.getFlags().isCarry()) pc = (short) (stack.pop() - 1);
                else pc++;
                break;
            default:
                throw new ProcessorException("Нереализованная команда");
        }
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
