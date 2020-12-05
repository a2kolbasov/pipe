/*
 * Copyright © 2020 Александр Колбасов
 */

package pipe;

public enum Command {
    /** Остановить выполнение (выйти) */ HLT,
    /** Сложение */ ADD,
    /** Вычитание */ SUB,
    /** Логическое И */ AND,
    /** Логическое ИЛИ */ OR,
    /** Логическое поразрядное дополнение */ XOR,
    /** Инверсия бит */ NOT,
    /** Сдвиг вправо */ SHR,
    /** Сдвиг влево */ SHL,
    /** Умножение (младшие биты на вершине стека) */ MUL,
    /** Сложение с переносом */ ADC,
    /** Положить следующую команду в стек */ PUSH,
    /** Прочитать число из RAM и положить его в стек */ READ,
    /** Записать число из стека в RAM */ WRITE,
    /** Продублировать вершину стека */ DUP,
    /** Выбросить вершину стека */ DROP,
    /** Загрузить в RG счётчика число из стека */ LDC,
    /** Выгрузить из RG счётчика число в стек */ STC,
    /** Сравнить 2 числа */ CMP,
    /** Прыжок */ JMP,
    /** Прыжок по флагу нуля */ JZ,
    /** Ппыжок по флагу переполнения */ JC,
//    /***/ SWAP,
//    /***/ ROR,
//    /***/ ROL,
//    /***/ MOV,
//    /***/ LOOP,
}
