package main;

import main.model.DoingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import main.model.Doing;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
public class DoingController {

    @Autowired
    private DoingRepository doingRepository;

    @GetMapping("/doings")
    public synchronized String list(Model model) {
        Iterable<Doing> iterableDoings = doingRepository.findAll();
        ArrayList<Doing> doings = new ArrayList<>();
        for (Doing doing : iterableDoings) {
            doings.add(doing);
        }
        model.addAttribute("doings", doings);

        return "doings";
    }
    @GetMapping("doings/new")
    public String newDoing(Model model) {
        model.addAttribute("doing", new Doing());
        return "new";

    }
    @GetMapping("/doings/{doingId}")
    public ResponseEntity getDoing(@PathVariable int doingId) {
        Optional<Doing> optionalDoing = doingRepository.findById(doingId);
        if (optionalDoing.isPresent()) {
            Doing doing = optionalDoing.get();
            synchronized (doing) {
                return new ResponseEntity(doing, HttpStatus.OK);
            }
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(optionalDoing);
    }

    @PostMapping("/doings")
    public String addDoing(@ModelAttribute("doing") Doing doing) {
        synchronized (doing) {
            Doing d = doingRepository.save(doing);
            return "redirect:/doings";
        }
    }

    @PostMapping("/doings/{doingId}")
    public ResponseEntity addDoing(@PathVariable int doingId) {
        return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED).body(null);

    }

    @PutMapping("/doings/{doingId}/{newDescription}")
    public ResponseEntity changeDoingDescription(@PathVariable int doingId, @PathVariable String newDescription) {

        Optional<Doing> optionalDoing = doingRepository.findById(doingId);
        if (optionalDoing.isPresent()) {
            Doing doing = optionalDoing.get();
            synchronized (doing) {
                doing.setDescription(newDescription);
                doingRepository.save(doing);
                return new ResponseEntity(doing, HttpStatus.OK);
            }
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(optionalDoing);
    }

    @DeleteMapping("/doings")
    public ResponseEntity deleteAllDoing() {
        doingRepository.deleteAll();
        return new ResponseEntity(HttpStatus.OK);
    }

    @DeleteMapping("/doings/{doingId}")
    public ResponseEntity delete(@PathVariable int doingId) {

        Optional<Doing> optionalDoing = doingRepository.findById(doingId);

        if (optionalDoing.isPresent()) {
            Doing doing = optionalDoing.get();
            doingRepository.delete(doing);
            return new ResponseEntity(doing, HttpStatus.OK);
        }
        return ResponseEntity.status(HttpStatus.NOT_MODIFIED).body(optionalDoing);
    }
}

