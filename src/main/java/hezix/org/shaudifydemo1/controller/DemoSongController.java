package hezix.org.shaudifydemo1.controller;

import hezix.org.shaudifydemo1.entity.song.Song;
import hezix.org.shaudifydemo1.entity.song.dto.CreateSongDTO;
import hezix.org.shaudifydemo1.entity.song.dto.ReadSongDTO;
import hezix.org.shaudifydemo1.entity.user.dto.ReadUserDTO;
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
    public ResponseEntity<ReadSongDTO> get(@PathVariable Long id) {
        return ResponseEntity.ok().body(songService.findById(id));
    }
    @GetMapping("/{userId}/song/{songId}")
    public ResponseEntity<ReadUserDTO> assignSong(@PathVariable("userId") Long userId, @PathVariable("songId") Long songId) {
        return ResponseEntity.ok().body(songService.assignSong(songId, userId));
    }
    @GetMapping()
    public ResponseEntity<List<ReadSongDTO>> getAll() {
        return ResponseEntity.ok().body(songService.findAll());
    }
}
