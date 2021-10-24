package by.itstep.auction.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Map;

@Controller
public class StartController {
    @GetMapping("/")
    public String start(Map<String, Object> model) {
        return "start";
    }
}
