package com.social.media.module.user.services;

import com.social.media.module.user.dto.ListUserResponseDTO;
import com.social.media.module.user.dto.UserDTO;

public interface UserInterface {
    public void create(UserDTO user);
    public UserDTO read(Long id);
    public void update(Long id, UserDTO user);
    public void delete(Long id);
    public ListUserResponseDTO list(
            Integer pageNumber,
            Integer pageSize,
            String sortBy,
            String sortOrder,
            String filterFirstName,
            String filterLastName,
            String filterId
    );
}
