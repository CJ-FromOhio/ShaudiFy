package hezix.org.shaudifydemo1.controller.rest;

import hezix.org.shaudifydemo1.entity.song.SongFile;
import hezix.org.shaudifydemo1.entity.song.dto.CreateSongDTO;
import hezix.org.shaudifydemo1.entity.song.dto.ReadSongDTO;
import hezix.org.shaudifydemo1.entity.song.dto.SongFileDTO;
import hezix.org.shaudifydemo1.entity.song.dto.SongFormDTO;
import hezix.org.shaudifydemo1.entity.user.dto.ReadUserDTO;
import hezix.org.shaudifydemo1.mapper.SongFileMapper;
import hezix.org.shaudifydemo1.service.SongService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/song")
@RequiredArgsConstructor
public class DemoRestSongController {

    private final SongService songService;

    @PostMapping("/create")
    public ResponseEntity<?> create(@Valid @RequestBody CreateSongDTO createSongDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body(bindingResult.getFieldError().getDefaultMessage());
        }
        return ResponseEntity.ok().body(songService.saveDto(createSongDTO));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ReadSongDTO> get(@PathVariable Long id) {
        return ResponseEntity.ok().body(songService.findById(id));
    }

    @GetMapping("/randomSong")
    public ReadSongDTO getRandomSong() {
        return songService.findRandomSong();
    }

    @GetMapping("/{userId}/song/{songId}")
    public ResponseEntity<ReadUserDTO> assignSong(@PathVariable("userId") Long userId, @PathVariable("songId") Long songId) {
        return ResponseEntity.ok().body(songService.assignSong(songId, userId));
    }

    @GetMapping()
    public ResponseEntity<List<ReadSongDTO>> getAll() {
        return ResponseEntity.ok().body(songService.findAll());
    }

    @PostMapping("/{id}/image")
    public ResponseEntity<ReadSongDTO> uploadImage(@PathVariable Long id, @ModelAttribute SongFormDTO file) {
        songService.uploadImage(id, file.getSongFileDTO());
        return ResponseEntity.ok().body(songService.findById(id));
    }
    @DeleteMapping("/{id}/delete")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        songService.delete(id);
        return ResponseEntity.ok().body("Deleted Song by id: " + id);
    }
    @GetMapping("/{id}/image")
    public ResponseEntity<String> readImage(@PathVariable Long id) {
        return ResponseEntity.ok().body(songService.findImageById(id));
    }
}
