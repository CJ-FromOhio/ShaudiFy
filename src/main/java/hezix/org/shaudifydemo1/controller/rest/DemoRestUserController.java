package hezix.org.shaudifydemo1.controller.rest;

import hezix.org.shaudifydemo1.entity.user.dto.CreateUserDTO;
import hezix.org.shaudifydemo1.entity.user.dto.ReadUserDTO;
import hezix.org.shaudifydemo1.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/user")
@RequiredArgsConstructor
public class DemoRestUserController {

    private final UserService userService;

    @PostMapping("/create")
    public ResponseEntity<?> create(@Valid @RequestBody CreateUserDTO createUserDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body(bindingResult.getFieldError().getDefaultMessage());
        }
        return ResponseEntity.ok().body(userService.save(createUserDTO));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ReadUserDTO> get(@PathVariable Long id) {
        return ResponseEntity.ok().body(userService.findUserById(id));
    }

    @GetMapping()
    public ResponseEntity<List<ReadUserDTO>> getAll() {
        return ResponseEntity.ok().body(userService.findAllUsers());
    }

    @DeleteMapping("/{id}/delete")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        userService.delete(id);
        return ResponseEntity.ok().body("User deleted by id: " + id);
    }


}
