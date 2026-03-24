package service;

import dao.*;
import entities.Post;
import java.util.List;

public class PostService implements Service<Post>
{
    public DAO<Post> daoService = new PostDAO();

    @Override
    public void saveEntity(Post entity) {
        daoService.save(entity);
    }

    @Override
    public void updateEntity(Post entity) {
        daoService.update(entity);
    }

    @Override
    public void deleteEntity(Post entity) {
        daoService.delete(entity);
    }

    @Override
    public Post findEntity(int id) {
        return daoService.findById(id);
    }

    @Override
    public List<Post> findAllEntities() {
        return daoService.findAll();
    }
}
