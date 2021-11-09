package by.itstep.auction.controller;

import by.itstep.auction.service.ItemService;
import by.itstep.auction.service.LotService;
import by.itstep.auction.service.UserService;
import by.itstep.auction.service.exceptions.InvalidItemException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.security.Principal;

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

//    @ExceptionHandler(InvalidItemException.class)
//    public final ModelAndView handleInvalidItemException(InvalidItemException ex, Principal principal) {
//        ModelAndView modelAndView = new ModelAndView();
//        modelAndView.setViewName("lots");
//        modelAndView.addObject("message", ex.getMessage());
//        modelAndView.addObject("lots", lotService.findAllLots());
//        modelAndView.addObject("items", itemService.findItemsOfUser(userService.findUserByName(principal.getName())));
//        return modelAndView;
//    }

    @ExceptionHandler(InvalidItemException.class)
    public String handleInvalidItemException(InvalidItemException ex, Principal principal) {
        Long currentId = userService.findUserByName(principal.getName()).getId();
        return "redirect:/lots/"+currentId;
    }
}
