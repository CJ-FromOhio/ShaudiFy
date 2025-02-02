package hezix.org.shaudifydemo1.controller;

import hezix.org.shaudifydemo1.entity.song.Song;
import hezix.org.shaudifydemo1.entity.song.dto.CreateSongDTO;
import hezix.org.shaudifydemo1.entity.user.User;
import hezix.org.shaudifydemo1.entity.user.dto.CreateUserDTO;
import hezix.org.shaudifydemo1.service.SongService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/song")
@RequiredArgsConstructor
public class DemoSongController {

    private final SongService songService;

    @PostMapping("/create")
    public ResponseEntity<Song> create(@RequestBody CreateSongDTO createUserDTO) {
        return ResponseEntity.ok().body(songService.save(createUserDTO));
    }
    @GetMapping("/{id}")
    public ResponseEntity<Song> get(@PathVariable Long id) {
        return ResponseEntity.ok().body(songService.findById(id));
    }
    @GetMapping("/{songId}/assign/{songId}")
    public ResponseEntity<User> assignSong(@PathVariable("userId") Long userId, @PathVariable("songId") Long songId) {
        return ResponseEntity.ok().body(songService.assignSong(songId, userId));
    }
    @GetMapping()
    public ResponseEntity<List<Song>> getAll() {
        return ResponseEntity.ok().body(songService.findAll());
    }
}
