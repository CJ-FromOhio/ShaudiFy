package hezix.org.shaudifydemo1.controller;

import hezix.org.shaudifydemo1.entity.user.User;
import hezix.org.shaudifydemo1.entity.user.dto.CreateUserDTO;
import hezix.org.shaudifydemo1.entity.user.dto.UserFileDTO;
import hezix.org.shaudifydemo1.entity.user.dto.UserFormDTO;
import hezix.org.shaudifydemo1.mapper.ReadUserMapper;
import hezix.org.shaudifydemo1.service.UserService;
import io.micrometer.core.annotation.Timed;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;
    private final ReadUserMapper readUserMapper;
    @Timed("authC_login")
    @GetMapping("/login")
    public String login() {
        return "auth/login";
    }
    @Timed("authC_registrationPage")
    @GetMapping("/register")
    public String create(Model model) {
        UserFormDTO userFormDTO = new UserFormDTO();
        userFormDTO.setCreateUserDTO(new CreateUserDTO());
        userFormDTO.setUserFileDTO(new UserFileDTO());
        model.addAttribute("user", userFormDTO);
        return "auth/registry";
    }
    @Timed("authC_registration")
    @PostMapping("/registration")
    public String registration(@ModelAttribute @Valid UserFormDTO userFormDTO, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            List<String> errors = new ArrayList<>();
            for (FieldError error : bindingResult.getFieldErrors()) {
                errors.add(error.getDefaultMessage());
            }
            model.addAttribute("errors", errors);
            model.addAttribute("user", userFormDTO);
            return "auth/registry";
        }
        User user = readUserMapper.toEntity(userService.save(userFormDTO.getCreateUserDTO()));

        if (userFormDTO.getUserFileDTO().getImage() != null && !userFormDTO.getUserFileDTO().getImage().isEmpty()) {
            userService.uploadImage(user.getId(), userFormDTO.getUserFileDTO());
        }

        return "redirect:/login";
    }

}
