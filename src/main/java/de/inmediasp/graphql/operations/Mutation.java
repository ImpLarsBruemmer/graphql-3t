package de.inmediasp.graphql.operations;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.coxautodev.graphql.tools.GraphQLMutationResolver;

import de.inmediasp.graphql.persistence.Author;
import de.inmediasp.graphql.persistence.AuthorRepository;
import de.inmediasp.graphql.persistence.Post;
import de.inmediasp.graphql.persistence.PostRepository;

@Component
public class Mutation implements GraphQLMutationResolver {
    private final PostRepository postRepository;
    private final AuthorRepository authorRepository;

    @Autowired
    public Mutation(final PostRepository postRepository, final AuthorRepository authorRepository) {
        this.postRepository = postRepository;
        this.authorRepository = authorRepository;
    }

    @Transactional
    public Post writePost(final String title, final String text, final String category, final String author) {
        final Post post = new Post();

        post.setTitle(title);
        post.setText(text);
        post.setCategory(category);
        post.setAuthor(authorRepository.findAuthorByName(author).orElse(new Author(author)));

        postRepository.save(post);

        return post;
    }
}
