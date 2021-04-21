package netology.mvc.repository;

import netology.mvc.exception.NotFoundException;
import netology.mvc.model.Post;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicInteger;

@Repository
public class PostRepository {
    private List<Post> postList = new CopyOnWriteArrayList<>();
    private AtomicInteger postID = new AtomicInteger(0);

    public List<Post> all() {
        List<Post> posts = new CopyOnWriteArrayList<>();
        posts.forEach(p -> {
            if (!p.isDeleted()) posts.add(p);
        });
        return posts;
    }

    public Optional<Post> getById(long id) throws NotFoundException{
        Post post = null;
        for (Post p : postList) {
            if (p.getId() == id) {
                if (!p.isDeleted())
                    post = p;
                else throw new NotFoundException("Пост удален.");
            }
        }
        return Optional.ofNullable(post);
    }

    public Post save(Post post) throws NotFoundException{
        if (post.getId() == 0) {
            postID.getAndIncrement();
            post.setId(postID.get());
        } else {
            for (Post p : postList) {
                if (p.getId() == post.getId()) {
                    if (!p.isDeleted())
                        p.setContent(post.getContent());
                    else throw new NotFoundException("Нельзя обновить удаленный пост.");
                } else {
                    postID.getAndIncrement();
                    post.setId(postID.get());
                }
            }
        }
        postList.add(post);
        return post;
    }

    public void removeById(long id) {
        //postList.removeIf(p -> p.getId() == id);
        for (Post p : postList) {
            if (p.getId() == id) {
                p.setDeleted(true);
            }
        }
    }
}