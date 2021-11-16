package by.itstep.auction.controller;

import by.itstep.auction.service.ItemService;
import by.itstep.auction.service.LotService;
import by.itstep.auction.service.UserService;
import by.itstep.auction.service.exceptions.InvalidItemException;
import by.itstep.auction.service.exceptions.LotAlreadyExistsException;
import by.itstep.auction.service.exceptions.NotEnoughMoneyException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.security.Principal;

@ControllerAdvice
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(InvalidItemException.class)
    public ModelAndView handleInvalidItemException(InvalidItemException ex) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("lotsError");
        modelAndView.addObject("message", ex.getMessage());
        return modelAndView;
    }

    @ExceptionHandler(NotEnoughMoneyException.class)
    public ModelAndView handleNotEnoughMoneyException(NotEnoughMoneyException ex) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("lotsError");
        modelAndView.addObject("message", ex.getMessage());
        return modelAndView;
    }

    @ExceptionHandler(LotAlreadyExistsException.class)
    public ModelAndView handleInvalidItemException(LotAlreadyExistsException ex) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("lotsError");
        modelAndView.addObject("message", ex.getMessage());
        return modelAndView;
    }
}
