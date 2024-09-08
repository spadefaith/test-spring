package com.social.media.module.post;


import com.social.media.config.AppConsts;
import com.social.media.module.post.dto.*;
import com.social.media.module.post.services.PostService;
import com.social.media.module.user.dto.*;
import com.social.media.module.user.services.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class PostController {


    @Autowired
    public PostService postService;

    @PostMapping("/api/v1/post")
    public ResponseEntity<CreatePostResponseDTO> create(@Valid @RequestBody PostDTO post) {
        postService.create(post);

        return ResponseEntity.ok(new CreatePostResponseDTO("Post created"));
    }

    @GetMapping("/api/v1/post/{id}")
    public ResponseEntity<GetPostResponseDTO> read(@PathVariable Long id) {
        PostDTO post = postService.read(id);
        return ResponseEntity.ok(new GetPostResponseDTO(post));
    }

    @PutMapping("/api/v1/post/{id}")
    public ResponseEntity<UpdatePostResponseDTO> update(@PathVariable Long id, @RequestBody PostDTO post) {
        postService.update(id, post);

        return ResponseEntity.ok(new UpdatePostResponseDTO("Post updated"));
    }

    @DeleteMapping("/api/v1/post/{id}")
    public ResponseEntity<DeletePostResponseDTO> delete(@PathVariable Long id) {
        postService.delete(id);
        return ResponseEntity.ok(new DeletePostResponseDTO("Post deleted"));
    }

    @GetMapping("/api/v1/posts")
    public ResponseEntity<ListPostResponseDTO> list(
            @RequestParam(name="pageNumber", defaultValue = AppConsts.PAGE_NUMBER) Integer pageNumber,
            @RequestParam(name="pageSize", defaultValue = AppConsts.PAGE_SIZE) Integer pageSize,
            @RequestParam(name="sortBy", defaultValue = "id") String sortBy,
            @RequestParam(name="sortOrder", defaultValue = "desc") String sortOrder,
            @RequestParam(name="filterUser", defaultValue = "") String filterUser,
            @RequestParam(name="filterMessage", defaultValue = "") String filterMessage
    ) {
        postService.list(pageNumber,pageSize,sortBy,sortOrder,filterUser,filterMessage);

        return ResponseEntity.ok(new ListPostResponseDTO());
    }
}
