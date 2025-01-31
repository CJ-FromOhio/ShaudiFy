package hezix.org.shaudifydemo1.controller;

import hezix.org.shaudifydemo1.entity.user.User;
import hezix.org.shaudifydemo1.entity.user.dto.CreateUserDTO;
import hezix.org.shaudifydemo1.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.http.HttpResponse;
import java.util.List;

@RestController
@RequestMapping("api/v1/user")
@RequiredArgsConstructor
public class DemoUserController {

    private final UserService userService;

    @PostMapping("/create")
    public ResponseEntity<User> create(@RequestBody CreateUserDTO createUserDTO) {
        return ResponseEntity.ok().body(userService.save(createUserDTO));
    }
    @GetMapping("/{id}")
    public ResponseEntity<User> get(@PathVariable Long id) {
        return ResponseEntity.ok().body(userService.findUserById(id));
    }
    @GetMapping()
    public ResponseEntity<List<User>> getAll() {
        return ResponseEntity.ok().body(userService.findAllUsers());
    }
    @DeleteMapping("/{id}/delete")
    public ResponseEntity delete(@PathVariable Long id) {
        userService.delete(id);
        return ResponseEntity.ok().body("User deleted by id: " + id);
    }
    @GetMapping("/{userId}/song/{songId}")
    public ResponseEntity<User> assignSong(@PathVariable("userId") Long userId, @PathVariable("songId") Long songId) {
        return ResponseEntity.ok().body(userService.assignSong(songId, userId));
    }
}
