/*
 * Copyright © 2020 Александр Колбасов
 */

package pipe;

public class ProcessorException extends RuntimeException {
    public ProcessorException() {
        super();
    }

    public ProcessorException(String message) {
        super(message);
    }
}

