package hezix.org.shaudifydemo1.controller;

import hezix.org.shaudifydemo1.entity.song.Song;
import hezix.org.shaudifydemo1.entity.song.dto.CreateSongDTO;
import hezix.org.shaudifydemo1.entity.song.dto.ReadSongDTO;
import hezix.org.shaudifydemo1.entity.song.dto.SongFileDTO;
import hezix.org.shaudifydemo1.entity.song.dto.SongFormDTO;
import hezix.org.shaudifydemo1.service.SongService;
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
@RequestMapping("/song")
public class SongController {

    private final SongService songService;

    @GetMapping("/")
    public String getAll(Model model) {
        model.addAttribute("songs", songService.findAll());
        return "song/all";
    }
    @GetMapping("/{id}")
    public String getById(@PathVariable Long id, Model model) {
        ReadSongDTO readSongDTO = songService.findById(id);

        model.addAttribute("song", readSongDTO);
        return "song/view";
    }
    @GetMapping("/create")
    public String create(Model model) {
        SongFormDTO songFormDTO = new SongFormDTO();
        songFormDTO.setCreateSongDTO(new CreateSongDTO());
        songFormDTO.setSongFileDTO(new SongFileDTO());
        model.addAttribute("song", songFormDTO);
        return "song/create";
    }
    @PostMapping("/save")
    public String createPage(@ModelAttribute @Valid SongFormDTO songFormDTO, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            List<String> errors = new ArrayList<>();
            for (FieldError error : bindingResult.getFieldErrors()) {
                errors.add(error.getDefaultMessage());
            }
            model.addAttribute("errors", errors);
            model.addAttribute("song", songFormDTO);
            return "song/create";
        }
        Song song = songService.save(songFormDTO.getCreateSongDTO());
        songService.uploadImage(song.getId(), songFormDTO.getSongFileDTO());
        songService.uploadSong(song.getId(), songFormDTO.getSongFileDTO());
        return "redirect:/song/";
    }

}
