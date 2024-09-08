package com.social.media.module.post.services;

import com.social.media.model.Post;
import com.social.media.module.post.dto.ListPostResponseDTO;
import com.social.media.module.post.dto.PostDTO;
import com.social.media.module.post.repository.PostRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PostService implements PostInterface {
    @Autowired
    private PostRepository postRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public void create(PostDTO postDTO){
        Post post = modelMapper.map(postDTO, Post.class);
        postRepository.save(post);
        System.out.println("Post created");
    }
    @Override
    public void delete(Long id) {
        Optional<Post> qPost = postRepository.findById(id);
        Post post = qPost.orElseThrow(()->new RuntimeException("Post not found"));
        postRepository.delete(post);
    }

    @Override
    public ListPostResponseDTO list(
            Integer pageNumber,
            Integer pageSize,
            String sortBy,
            String sortOrder,
            String filterUser,
            String filterMessage
    ) {
        Sort sortByAndOrder = sortOrder.equalsIgnoreCase("asc") ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();

        Pageable pageable = PageRequest.of(pageNumber, pageSize).withSort(sortByAndOrder);

        Page<Post> postPage = postRepository.findAll(pageable);
        List<Post> posts = postPage.getContent();

        List<PostDTO> postList = posts.stream().map(post -> modelMapper.map(post, PostDTO.class)).toList();
        ListPostResponseDTO postResponseDTO = new ListPostResponseDTO();

        postResponseDTO.setContents(postList);
        postResponseDTO.setPageNumber(postPage.getNumber());
        postResponseDTO.setPageSize(postPage.getSize());
        postResponseDTO.setTotalElements(postPage.getTotalElements());
        postResponseDTO.setTotalPages(postPage.getTotalPages());
        postResponseDTO.setLastPage(postPage.isLast());

        return postResponseDTO;
    }

    @Override
    public PostDTO read(Long id) {
        Optional<Post> qPost = postRepository.findById(id);
        Post post = qPost.orElseThrow(()->new RuntimeException("Post not found"));
        return modelMapper.map(post, PostDTO.class);
    }

    @Override
    public void update(Long id, PostDTO postDTO) {
        Optional<Post> row = postRepository.findById(id);

        Post rowData = row.orElseThrow(()->new RuntimeException("Post not found"));


        Post post = modelMapper.map(postDTO, Post.class);

        if(post.getMessage() != null) rowData.setMessage(post.getMessage());
        if(post.getUser() != null) rowData.setUser(post.getUser());
        postRepository.save(rowData);
    }
}
