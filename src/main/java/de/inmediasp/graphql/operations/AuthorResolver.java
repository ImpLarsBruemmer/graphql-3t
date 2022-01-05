package de.inmediasp.graphql.operations;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.coxautodev.graphql.tools.GraphQLResolver;

import de.inmediasp.graphql.persistence.Author;
import de.inmediasp.graphql.persistence.Post;
import de.inmediasp.graphql.persistence.PostRepository;

@Component
public class AuthorResolver implements GraphQLResolver<Author> {
    private final PostRepository postRepository;

    @Autowired
    public AuthorResolver(final PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    public List<Post> getPosts(final Author author) {
        return postRepository.findAllById(Collections.singletonList(author.getId()));
    }
}
