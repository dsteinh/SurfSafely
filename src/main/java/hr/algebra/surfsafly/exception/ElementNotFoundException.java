package hr.algebra.surfsafly.exception;

import lombok.Getter;

@Getter
public class ElementNotFoundException extends Exception{
    String element;
    public ElementNotFoundException(String message, String element) {
        super(message);
        this.element = element;
    }
}
