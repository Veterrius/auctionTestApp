package by.itstep.auction.service.exceptions;

public class NotEnoughMoneyException extends RuntimeException{
    public NotEnoughMoneyException(String message) {
        super(message);
    }
}