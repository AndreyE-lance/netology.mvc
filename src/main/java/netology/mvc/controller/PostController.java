package netology.mvc.controller;

import netology.mvc.exception.NotFoundException;
import netology.mvc.model.Post;
import netology.mvc.service.PostService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/posts")
public class PostController {
    private final PostService service;

    public PostController(PostService service) {
        this.service = service;
    }

    @GetMapping
    public List<Post> all() {
        return service.all();
    }

    @GetMapping("/show")
    public ResponseEntity<Post> getById(@RequestParam(value="id") long id) {
        Post p = null;
        try {
           p = service.getById(id);
        } catch (NotFoundException e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(p, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Post> save(@RequestBody Post post) {
        Post p = null;
        try {
            p = service.save(post);
        } catch (NotFoundException e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(p, HttpStatus.OK);
    }

    @DeleteMapping("/del")
    public void removeById(@RequestParam(value="id") long id) {
        service.removeById(id);
    }
}
