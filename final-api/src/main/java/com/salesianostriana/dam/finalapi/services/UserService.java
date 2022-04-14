package com.salesianostriana.dam.finalapi.services;

import com.salesianostriana.dam.finalapi.awss3.AWSS3Service;
import com.salesianostriana.dam.finalapi.dtos.user.CreateUserDto;
import com.salesianostriana.dam.finalapi.dtos.user.GetUserDto;
import com.salesianostriana.dam.finalapi.dtos.user.UserDtoConverter;
import com.salesianostriana.dam.finalapi.dtos.user.UserNameAvailabilityDto;
import com.salesianostriana.dam.finalapi.errors.exceptions.EntityNotFoundException;
import com.salesianostriana.dam.finalapi.errors.exceptions.PasswordMissMatchException;
import com.salesianostriana.dam.finalapi.errors.exceptions.StorageException;
import com.salesianostriana.dam.finalapi.errors.exceptions.UnauthorizeException;
import com.salesianostriana.dam.finalapi.models.Rol;
import com.salesianostriana.dam.finalapi.models.User;
import com.salesianostriana.dam.finalapi.repositories.UserRepository;
import com.salesianostriana.dam.finalapi.services.base.BaseService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;

@Service("userDetailsService")
@RequiredArgsConstructor
public class UserService extends BaseService<User, UUID, UserRepository> implements UserDetailsService {

    private final PasswordEncoder passwordEncoder;
    private final UserDtoConverter userDtoConverter;
    private final AWSS3Service awsS3Service;
    private final UserRepository userRepository;
    private final List<String> imagesTypes = new ArrayList<>(Arrays.asList("image/jpeg", "image/png", "image/jpg"));

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return this.repository.findFirstByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(username + " no encontrado"));
    }

    public User saveUser(CreateUserDto newUser, MultipartFile file) {
        String fileUrl = awsS3Service.storeCompressed(file);

        if (newUser.getPassword().contentEquals(newUser.getPassword2())) {
            User user = User.builder()
                    .username(newUser.getUsername())
                    .email(newUser.getEmail())
                    .avatar(fileUrl)
                    .birthDate(newUser.getBirthDate())
                    .rol(Rol.USER)
                    .password(passwordEncoder.encode(newUser.getPassword()))
                    .build();
            return save(user);
        } else {
            throw new PasswordMissMatchException();
        }
    }

    public User saveAdmin(CreateUserDto newUser, MultipartFile file) {
        String fileUrl = awsS3Service.storeCompressed(file);

        if (newUser.getPassword().contentEquals(newUser.getPassword2())) {
            User user = User.builder()
                    .username(newUser.getUsername())
                    .email(newUser.getEmail())
                    .avatar(fileUrl)
                    .rol(Rol.ADMIN)
                    .birthDate(newUser.getBirthDate())
                    .phone(newUser.getPhone())
                    .password(passwordEncoder.encode(newUser.getPassword()))
                    .build();
            return save(user);
        } else {
            throw new PasswordMissMatchException();
        }
    }

    // convertToAdmin
    public User convertToAdmin(User user, String username) {
        Optional <User> userOptional = userRepository.findFirstByUsername(username);
        if (userOptional.isEmpty()) {
            throw new EntityNotFoundException("User not found");
        }
        if (user.getRol() == Rol.ADMIN) {
            User newAdmin = userOptional.get();

            newAdmin.setRol(Rol.ADMIN);
            return save(newAdmin);
        } else {
            throw new UnauthorizeException("You must be an admin to convert to admin");
        }
    }

    public List<User> getAllUsers(User user){
        List<User> userOptional = userRepository.findAll();
        if(userOptional.isEmpty()){ throw new EntityNotFoundException("There are no users to show"); }
        if(user.getRol().equals(Rol.ADMIN)){
            return userRepository.findAll();
        }else{
            throw new UnauthorizeException("Only admins can see all users");
        }
    }

    public GetUserDto getAuthenticatedUser(User user) {
        if (user == null) {
            throw new EntityNotFoundException("User not found");
        } else
            return userDtoConverter.toGetUserDto(user);
    }

    public GetUserDto getUserProfileByUsername(String username, User userPrincipal) {
        Optional<User> user = userRepository.findFirstByUsername(username);
        if(user.isEmpty()){
            throw new EntityNotFoundException("No user matches the provided username");
        }else{
            if(user.get().getId().equals(userPrincipal.getId())){
                return userDtoConverter.toGetUserDto(user.get());
            }
            return userDtoConverter.toGetUserDto(user.get());
        }
    }

    public UserNameAvailabilityDto checkUsernameAvailability(String username){
        Optional<User> user = userRepository.findFirstByUsername(username);
        if(user.isEmpty()){
            return new UserNameAvailabilityDto(username,true);
        }else{
            return new UserNameAvailabilityDto(username,false);
        }
    }

    public GetUserDto editMyProfile (CreateUserDto newUser, MultipartFile file, User user){
        Optional<User> userOptional = findById(user.getId());

        if(userOptional.isEmpty()){
            throw new EntityNotFoundException("No user matches the actual user");
        }else if (file == null || !imagesTypes.contains(file.getContentType())){
            throw new StorageException("The provided file does not match any of the allowed file types, please ensure image type is jpg, jpeg or png.");
        }else {
            User userPresent = userOptional.get();
            awsS3Service.deleteObjetct(userPresent.getAvatar().substring(userPresent.getAvatar().lastIndexOf("/")+1));
            String fileUrl = awsS3Service.storeCompressed(file);

            user.setUsername(newUser.getUsername());
            user.setAvatar(fileUrl);
            user.setBirthDate(newUser.getBirthDate());
            user.setEmail(newUser.getEmail());
            user.setPhone(newUser.getPhone());
            user.setPassword(passwordEncoder.encode(newUser.getPassword()));

            return userDtoConverter.toGetUserDto(save(user));
        }
    }
}

