package by.itstep.auction.service.exceptions;

public class LotAlreadyExistsException extends RuntimeException {
    public LotAlreadyExistsException(String message) {
        super(message);
    }
}
