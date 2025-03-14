package hezix.org.shaudifydemo1.controller;

import hezix.org.shaudifydemo1.entity.song.Song;
import hezix.org.shaudifydemo1.entity.song.dto.CreateSongDTO;
import hezix.org.shaudifydemo1.entity.song.dto.ReadSongDTO;
import hezix.org.shaudifydemo1.entity.song.dto.SongFileDTO;
import hezix.org.shaudifydemo1.entity.song.dto.SongFormDTO;
import hezix.org.shaudifydemo1.entity.user.User;
import hezix.org.shaudifydemo1.mapper.SongMapper;
import hezix.org.shaudifydemo1.service.SongService;
import hezix.org.shaudifydemo1.service.UserService;
import io.micrometer.core.annotation.Timed;
import io.micrometer.core.annotation.TimedSet;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
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
    private final UserService userService;
    private final SongMapper songMapper;

    @Timed("songC_getAll")
    @GetMapping("/")
    public String getAll(Model model) {
        model.addAttribute("songs", songService.findAll());
        return "song/all";
    }
    @Timed("songC_getById")
    @GetMapping("/{id}")
    public String getById(@PathVariable Long id, Model model) {
        ReadSongDTO readSongDTO = songService.findById(id);
        if(readSongDTO.getCreatedBy() != null) {
            model.addAttribute("user", userService.findUserByUsername(readSongDTO.getCreatedBy()));
        }
        model.addAttribute("song", readSongDTO);
        return "song/view";
    }
    @Timed("songC_randomSong")
    @GetMapping("/randomSong")
    public String getRandomSong(Model model) {
        model.addAttribute("song", songService.findRandomSong());
        return "song/random";
    }
    @Timed("songC_savePage")
    @GetMapping("/create")
    public String create(Model model) {
        SongFormDTO songFormDTO = new SongFormDTO();
        songFormDTO.setCreateSongDTO(new CreateSongDTO());
        songFormDTO.setSongFileDTO(new SongFileDTO());
        model.addAttribute("song", songFormDTO);
        return "song/create";
    }
    @Timed("songC_save")
    @PostMapping("/save")
    public String createPage(@ModelAttribute @Valid SongFormDTO songFormDTO, BindingResult bindingResult, Model model, @AuthenticationPrincipal UserDetails userDetails) {
        if (bindingResult.hasErrors()) {
            List<String> errors = new ArrayList<>();
            for (FieldError error : bindingResult.getFieldErrors()) {
                errors.add(error.getDefaultMessage());
            }
            model.addAttribute("errors", errors);
            model.addAttribute("song", songFormDTO);
            return "song/create";
        }
        Song song = songMapper.toEntity(songFormDTO.getCreateSongDTO());
        songService.saveEntity(song, userDetails);

        if (songFormDTO.getSongFileDTO().getImage() != null && !songFormDTO.getSongFileDTO().getImage().isEmpty()) {
            songService.uploadImage(song.getId(), songFormDTO.getSongFileDTO());
        }

        if (songFormDTO.getSongFileDTO().getSong() != null && !songFormDTO.getSongFileDTO().getSong().isEmpty()) {
            songService.uploadSong(song.getId(), songFormDTO.getSongFileDTO());
        }
        return "redirect:/song/";
    }

}
