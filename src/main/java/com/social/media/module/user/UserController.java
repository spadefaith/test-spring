package com.social.media.module.user;

import com.social.media.config.AppConsts;
import com.social.media.module.user.dto.*;
import com.social.media.module.user.services.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
public class UserController {

    @Autowired
    public UserService userService;

    @PostMapping("/api/v1/user")
    public ResponseEntity<CreateUserResponseDTO> create(@Valid @RequestBody UserDTO user) {
        userService.create(user);

        return ResponseEntity.ok(new CreateUserResponseDTO("User created"));
    }

    @GetMapping("/api/v1/user/{id}")
    public ResponseEntity<GetUserResponseDTO> read(@PathVariable Long id) {
        UserDTO user = userService.read(id);
        return ResponseEntity.ok(new GetUserResponseDTO(user));
    }

    @PutMapping("/api/v1/user/{id}")
    public ResponseEntity<UpdateUserResponseDTO> update(@PathVariable Long id, @RequestBody UserDTO user) {
        userService.update(id, user);

        return ResponseEntity.ok(new UpdateUserResponseDTO("User updated"));
    }

    @DeleteMapping("/api/v1/user/{id}")
    public ResponseEntity<DeleteUserResponseDTO> delete(@PathVariable Long id) {
        userService.delete(id);
        return ResponseEntity.ok(new DeleteUserResponseDTO("User deleted"));
    }

    @GetMapping("/api/v1/users")
    public ResponseEntity<ListUserResponseDTO> list(
            @RequestParam(name="pageNumber", defaultValue = AppConsts.PAGE_NUMBER) Integer pageNumber,
            @RequestParam(name="pageSize", defaultValue = AppConsts.PAGE_SIZE) Integer pageSize,
            @RequestParam(name="sortBy", defaultValue = "id") String sortBy,
            @RequestParam(name="sortOrder", defaultValue = "desc") String sortOrder,
            @RequestParam(name="filterFirstName", defaultValue = "") String filterFirstName,
            @RequestParam(name="filterLastName", defaultValue = "") String filterLastName,
            @RequestParam(name="filterId", defaultValue = "") String filterId
    ) {
        userService.list(pageNumber,pageSize,sortBy,sortOrder,filterFirstName,filterLastName,filterId);

        return ResponseEntity.ok(new ListUserResponseDTO());
    }
}
