package de.inmediasp.graphql.operations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.coxautodev.graphql.tools.GraphQLResolver;

import de.inmediasp.graphql.persistence.Author;
import de.inmediasp.graphql.persistence.AuthorRepository;
import de.inmediasp.graphql.persistence.Post;

@Component
public class PostResolver implements GraphQLResolver<Post> {
    private final AuthorRepository authorRepository;

    @Autowired
    public PostResolver(final AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    public Author getAuthor(final Post post) {
        return authorRepository.findById(post.getAuthor().getId()).orElseThrow(RuntimeException::new);
    }
}
