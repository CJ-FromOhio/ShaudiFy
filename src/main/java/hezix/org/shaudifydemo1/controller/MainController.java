package hezix.org.shaudifydemo1.controller;

import hezix.org.shaudifydemo1.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class MainController {

    private final UserService userService;

    @GetMapping("/")
    public String main(@AuthenticationPrincipal UserDetails userDetails, Model model) {
        model.addAttribute("user", userService.findUserByUsername(userDetails.getUsername()));
        return "main";
    }
}
