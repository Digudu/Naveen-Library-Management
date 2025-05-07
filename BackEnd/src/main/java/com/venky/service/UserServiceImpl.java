package com.venky.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.venky.dto.UserDto;
import com.venky.entity.Address;
import com.venky.entity.Book;
import com.venky.entity.User;
import com.venky.exception.UserNotFoundException;
import com.venky.repository.AddressRepository;
import com.venky.repository.BookRepository;
import com.venky.repository.UserRepository;

@Service
public class UserServiceImpl implements UserService {
    
    @Autowired
    UserRepository userRepository;

    @Autowired
    AddressRepository addressRepository;

    @Autowired
    BookRepository bookRepository;

    @Override
    public List<UserDto> getAllUsers() {
        List<User> users = userRepository.findByDeletedFalse();
        List<UserDto> userDtos = users.stream().map(this::convertToDto).collect(Collectors.toList());
        return userDtos;
    }

    @Override
    public UserDto addUser(UserDto userDto) {
        User user = new User();
        user.setId(userDto.getId());
        user.setName(userDto.getName());
        user.setGender(userDto.getGender());
        user.setDob(userDto.getDob());
        user.setEmail(userDto.getEmail());
        user.setMobile(userDto.getMobile());

        Address address = addressRepository.findById(userDto.getAddress().getId())
            .orElseThrow(() -> new RuntimeException("Address not found"));
        user.setAddress(address);

        List<Book> books = userDto.getBooks() != null ? userDto.getBooks().stream()
            .map(bookDto -> bookRepository.findById(bookDto.getId())
                .orElseThrow(() -> new RuntimeException("Book not found")))
            .collect(Collectors.toList()) : new ArrayList<>();
        user.setBooks(books);
        user.setDeleted(false);
        User savedUser = userRepository.save(user);
        System.out.println(savedUser);
        return convertToDto(savedUser);
    }


    @Override
    public UserDto getUserById(int id) {
        User user = userRepository.findById(id)
            .orElseThrow(() -> new UserNotFoundException("User with id " + id + " not found"));
        System.out.println(user+"venkatesh");
        return convertToDto(user);
    }

    @Override
    public UserDto updateUserById(int id, UserDto userDto) {
        User originalUser = userRepository.findById(id)
            .orElseThrow(() -> new UserNotFoundException("User with id " + id + " not found"));

        if (userDto.getAddress() != null) {
            Address address = addressRepository.findById(userDto.getAddress().getId())
                .orElseThrow(() -> new RuntimeException("Address not found"));
            address.setStreet(userDto.getAddress().getStreet());
            address.setCity(userDto.getAddress().getCity());
            address.setState(userDto.getAddress().getState());
            address.setCountry(userDto.getAddress().getCountry());
            address.setPincode(userDto.getAddress().getPincode());
            originalUser.setAddress(addressRepository.save(address));
        }

        if (userDto.getBooks() != null) {
            List<Book> books = userDto.getBooks().stream()
                .map(bookDto -> bookRepository.findById(bookDto.getId())
                    .orElseThrow(() -> new RuntimeException("Book not found")))
                .collect(Collectors.toList());
            originalUser.setBooks(books);
        }

        if (userDto.getDob() != null) {
            originalUser.setDob(userDto.getDob());
        }

        if (userDto.getEmail() != null) {
            originalUser.setEmail(userDto.getEmail());
        }

        if (userDto.getGender() != null) {
            originalUser.setGender(userDto.getGender());
        }

        if (userDto.getMobile() != null) {
            originalUser.setMobile(userDto.getMobile());
        }

        if (userDto.getName() != null) {
            originalUser.setName(userDto.getName());
        }

        User updatedUser = userRepository.save(originalUser);
        return convertToDto(updatedUser);
    }

    @Override
    public String deleteUserById(int id) {
        User user = userRepository.findById(id)
            .orElseThrow(() -> new UserNotFoundException("User with id " + id + " not found"));
       user.setDeleted(true);
        userRepository.save(user);
        return "User with id " + id + " Deleted Successfully";
    }

    private UserDto convertToDto(User user) {
        UserDto userDto = new UserDto();
        userDto.setId(user.getId());
        userDto.setName(user.getName());
        userDto.setGender(user.getGender());
        userDto.setDob(user.getDob());
        userDto.setEmail(user.getEmail());
        userDto.setMobile(user.getMobile());
        userDto.setAddress(user.getAddress());
        userDto.setBooks(user.getBooks());
        System.out.println(userDto+"dto");
        return userDto;
    }
}
