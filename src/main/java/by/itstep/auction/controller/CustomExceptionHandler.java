package by.itstep.auction.controller;

import by.itstep.auction.service.ItemService;
import by.itstep.auction.service.LotService;
import by.itstep.auction.service.UserService;
import by.itstep.auction.service.exceptions.InvalidItemException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.security.Principal;
import java.util.Map;

@ControllerAdvice
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {

    private final LotService lotService;
    private final ItemService itemService;
    private final UserService userService;

    public CustomExceptionHandler(LotService lotService, ItemService itemService, UserService userService) {
        this.lotService = lotService;
        this.itemService = itemService;
        this.userService = userService;
    }

    @ExceptionHandler(InvalidItemException.class)
    public final ModelAndView handleInvalidItemException(InvalidItemException ex, Principal principal) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("lots");
        modelAndView.addObject("message", ex.getMessage());
        modelAndView.addObject("lots", lotService.findAllLots());
        modelAndView.addObject("items", itemService.findItemsOfUser(userService.findUserByName(principal.getName())));
        return modelAndView;
    }
}
