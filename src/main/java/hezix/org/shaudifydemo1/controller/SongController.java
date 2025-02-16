package hezix.org.shaudifydemo1.controller;

import hezix.org.shaudifydemo1.service.SongService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/song")
public class SongController {

    private final SongService songService;

    @GetMapping()
    public String song(Model model) {
        model.addAttribute("songs", songService.findAll());
        return "song/all";
    }
}
