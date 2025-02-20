package hezix.org.shaudifydemo1.controller;

import hezix.org.shaudifydemo1.entity.song.dto.CreateSongDTO;
import hezix.org.shaudifydemo1.entity.song.dto.SongFileDTO;
import hezix.org.shaudifydemo1.entity.song.dto.SongFormDTO;
import hezix.org.shaudifydemo1.entity.user.User;
import hezix.org.shaudifydemo1.entity.user.dto.CreateUserDTO;
import hezix.org.shaudifydemo1.entity.user.dto.ReadUserDTO;
import hezix.org.shaudifydemo1.entity.user.dto.UserFileDTO;
import hezix.org.shaudifydemo1.entity.user.dto.UserFormDTO;
import hezix.org.shaudifydemo1.mapper.ReadUserMapper;
import hezix.org.shaudifydemo1.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {

    private final UserService userService;
    private final ReadUserMapper readUserMapper;

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

    @GetMapping("/create")
    public String create(Model model) {
        UserFormDTO userFormDTO = new UserFormDTO();
        userFormDTO.setCreateUserDTO(new CreateUserDTO());
        userFormDTO.setUserFileDTO(new UserFileDTO());
        model.addAttribute("user", userFormDTO);
        return "user/create";
    }

    @PostMapping("/save")
    public String createPage(@ModelAttribute @Valid UserFormDTO userFormDTO, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            List<String> errors = new ArrayList<>();
            for (FieldError error : bindingResult.getFieldErrors()) {
                errors.add(error.getDefaultMessage());
            }
            model.addAttribute("errors", errors);
            model.addAttribute("user", userFormDTO);
            return "user/create";
        }
        User user = readUserMapper.toEntity(userService.save(userFormDTO.getCreateUserDTO()));

        if (userFormDTO.getUserFileDTO().getImage() != null && !userFormDTO.getUserFileDTO().getImage().isEmpty()) {
            userService.uploadImage(user.getId(), userFormDTO.getUserFileDTO());
        }

        return "redirect:/user/";
    }
}
