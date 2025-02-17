package hezix.org.shaudifydemo1.controller;

import hezix.org.shaudifydemo1.entity.song.Song;
import hezix.org.shaudifydemo1.entity.song.dto.CreateSongDTO;
import hezix.org.shaudifydemo1.service.SongService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
        model.addAttribute("song", songService.findById(id));
        return "song/view";
    }
    @GetMapping("/create")
    public String create(Model model) {
        model.addAttribute("song", new CreateSongDTO());
        return "song/create";
    }
    @PostMapping("/save")
    public String createPage(@ModelAttribute @Valid CreateSongDTO createSongDTO, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            List<String> errors = new ArrayList<>();
            for (FieldError error : bindingResult.getFieldErrors()) {
                errors.add(error.getDefaultMessage());
            }
            model.addAttribute("errors", errors);
            return "song/create";
        }
        songService.save(createSongDTO);
        return "redirect:/song/";
    }
}
