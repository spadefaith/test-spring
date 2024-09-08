package com.social.media.module.user.services;

import com.social.media.module.user.dto.ListUserResponseDTO;
import com.social.media.module.user.dto.UserDTO;
import com.social.media.model.User;
import com.social.media.module.user.repository.UserRepository;
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
public class UserService implements UserInterface{

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public void create(UserDTO userDTO){
        User user = modelMapper.map(userDTO, User.class);
        userRepository.save(user);
        System.out.println("User created");
    }
    @Override
    public void delete(Long id) {
        Optional<User> qUser = userRepository.findById(id);
        User user = qUser.orElseThrow(()->new RuntimeException("User not found"));
        userRepository.delete(user);
    }

    @Override
    public ListUserResponseDTO list(
            Integer pageNumber,
            Integer pageSize,
            String sortBy,
            String sortOrder,
            String filterFirstName,
            String filterLastName,
            String filterId
    ) {
        Sort sortByAndOrder = sortOrder.equalsIgnoreCase("asc") ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();

        Pageable pageable = PageRequest.of(pageNumber, pageSize).withSort(sortByAndOrder);

        Page<User> userPage = userRepository.findAll(pageable);
        List<User> users = userPage.getContent();

        List<UserDTO> userList = users.stream().map(user -> modelMapper.map(user, UserDTO.class)).toList();
        ListUserResponseDTO userResponseDTO = new ListUserResponseDTO();

        userResponseDTO.setContents(userList);
        userResponseDTO.setPageNumber(userPage.getNumber());
        userResponseDTO.setPageSize(userPage.getSize());
        userResponseDTO.setTotalElements(userPage.getTotalElements());
        userResponseDTO.setTotalPages(userPage.getTotalPages());
        userResponseDTO.setLastPage(userPage.isLast());

        return userResponseDTO;
    }

    @Override
    public UserDTO read(Long id) {
        Optional<User> qUser = userRepository.findById(id);
        User user = qUser.orElseThrow(()->new RuntimeException("User not found"));
        return modelMapper.map(user, UserDTO.class);
    }

    @Override
    public void update(Long id, UserDTO userDTO) {
        Optional<User> row = userRepository.findById(id);

        User rowData = row.orElseThrow(()->new RuntimeException("User not found"));


        User user = modelMapper.map(userDTO, User.class);

        if(user.getFirstName() != null) rowData.setFirstName(user.getFirstName());
        if(user.getLastName() != null) rowData.setLastName(user.getLastName());
        userRepository.save(rowData);
    }
}
