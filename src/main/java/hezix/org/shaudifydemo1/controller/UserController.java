package hezix.org.shaudifydemo1.controller;

import hezix.org.shaudifydemo1.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    @GetMapping("/")
    public String all(Model model) {
        model.addAttribute("users", userService.findAllUsers());
        return "user/all";
    }
    @GetMapping("/{id}")
    public String getById(@PathVariable Long id, Model model) {
        model.addAttribute("user", userService.findUserById(id));
        return "user/view";
    }
}
