package de.inmediasp.graphql.operations;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.coxautodev.graphql.tools.GraphQLQueryResolver;

import de.inmediasp.graphql.persistence.Post;
import de.inmediasp.graphql.persistence.PostRepository;

@Component
@Transactional
public class Query implements GraphQLQueryResolver {
    private final PostRepository postRepository;

    @Autowired
    public Query(final PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    public List<Post> getRecentPosts(final int count, final int offset) {
        return postRepository.findAll().stream().skip(offset).limit(count).toList();
    }

    public Optional<Post> getPost(final int id) {
        return postRepository.findById((long) id);
    }
}
