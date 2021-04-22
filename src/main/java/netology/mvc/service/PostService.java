package netology.mvc.service;

import netology.mvc.exception.NotFoundException;
import netology.mvc.model.Post;
import netology.mvc.repository.PostRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PostService {
    private final PostRepository repository;

    public PostService(PostRepository repository) {
        this.repository = repository;
    }

    public List<Post> all() {
        return repository.all();
    }

    public Post getById(long id) throws NotFoundException {
        return repository.getById(id).orElseThrow(NotFoundException::new);
    }

    public Post save(Post post) throws NotFoundException {
        return repository.save(post);
    }

    public void removeById(long id) {
        repository.removeById(id);
    }
}