package com.social.media.module.post.services;

import com.social.media.module.post.dto.ListPostResponseDTO;
import com.social.media.module.post.dto.PostDTO;
import com.social.media.module.user.dto.ListUserResponseDTO;

public interface PostInterface {
    public void create(PostDTO post);
    public PostDTO read(Long id);
    public void update(Long id, PostDTO user);
    public void delete(Long id);
    public ListPostResponseDTO list(
            Integer pageNumber,
            Integer pageSize,
            String sortBy,
            String sortOrder,
            String filterUser,
            String filterMessage
    );
}
