package com.salesianostriana.dam.finalapi.services;

import com.salesianostriana.dam.finalapi.awss3.AWSS3Service;
import com.salesianostriana.dam.finalapi.dtos.user.*;
import com.salesianostriana.dam.finalapi.errors.exceptions.EntityNotFoundException;
import com.salesianostriana.dam.finalapi.errors.exceptions.PasswordMissMatchException;
import com.salesianostriana.dam.finalapi.errors.exceptions.StorageException;
import com.salesianostriana.dam.finalapi.errors.exceptions.UnauthorizeException;
import com.salesianostriana.dam.finalapi.models.Rol;
import com.salesianostriana.dam.finalapi.models.UserEntity;
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
import java.util.stream.Collectors;

@Service("userDetailsService")
@RequiredArgsConstructor
public class UserService extends BaseService<UserEntity, UUID, UserRepository> implements UserDetailsService {

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

    public UserEntity saveUser(CreateUserDto newUser, MultipartFile file) {
        String fileUrl = awsS3Service.storeCompressed(file);

        if (newUser.getPassword().contentEquals(newUser.getVerifyPassword())) {
            UserEntity userEntity = UserEntity.builder()
                    .fullName(newUser.getFullName())
                    .username(newUser.getUsername())
                    .email(newUser.getEmail())
                    .avatar(fileUrl)
                    .birthDate(newUser.getBirthDate())
                    .phone(newUser.getPhone())
                    .rol(Rol.CLIENTE)
                    .password(passwordEncoder.encode(newUser.getPassword()))
                    .build();
            return save(userEntity);
        } else {
            throw new PasswordMissMatchException();
        }
    }

    public UserEntity saveAdmin(CreateUserDto newUser, MultipartFile file) {
        String fileUrl = awsS3Service.storeCompressed(file);

        if (newUser.getPassword().contentEquals(newUser.getVerifyPassword())) {
            UserEntity userEntity = UserEntity.builder()
                    .fullName(newUser.getFullName())
                    .username(newUser.getUsername())
                    .email(newUser.getEmail())
                    .avatar(fileUrl)
                    .rol(Rol.ADMIN)
                    .birthDate(newUser.getBirthDate())
                    .phone(newUser.getPhone())
                    .password(passwordEncoder.encode(newUser.getPassword()))
                    .build();
            return save(userEntity);
        } else {
            throw new PasswordMissMatchException();
        }
    }

    public UserEntity savePropietario(CreateUserDto newUser, MultipartFile file) {
        String fileUrl = awsS3Service.storeCompressed(file);

        if (newUser.getPassword().contentEquals(newUser.getVerifyPassword())) {
            UserEntity userEntity = UserEntity.builder()
                    .fullName(newUser.getFullName())
                    .username(newUser.getUsername())
                    .email(newUser.getEmail())
                    .avatar(fileUrl)
                    .rol(Rol.PROPIETARIO)
                    .birthDate(newUser.getBirthDate())
                    .phone(newUser.getPhone())
                    .password(passwordEncoder.encode(newUser.getPassword()))
                    .build();
            return save(userEntity);
        } else {
            throw new PasswordMissMatchException();
        }
    }

    // convertToAdmin
    public GetUserDto convertToAdmin(UserEntity userEntity, String username) {
        Optional <UserEntity> userOptional = userRepository.findFirstByUsername(username);
        if (userOptional.isEmpty()) {
            throw new EntityNotFoundException("UserEntity not found");
        }
        if (userEntity.getRol() == Rol.ADMIN) {
            UserEntity newAdmin = userOptional.get();

            newAdmin.setRol(Rol.ADMIN);
            save(newAdmin);
            return userDtoConverter.toGetUserDto(newAdmin);
        } else {
            throw new UnauthorizeException("You must be an admin to convert to admin");
        }
    }

    public List<GetUserDto> getAllUsers(UserEntity user) {
        List<UserEntity> users = userRepository.findAll();
        if (user.getRol() == Rol.ADMIN) {
            if (users.isEmpty()) {
                throw new EntityNotFoundException("Users not found");
            } else {
                return users.stream().map(userDtoConverter::toGetUserDto).collect(Collectors.toList());
            }
        } else {
            throw new UnauthorizeException("You must be an admin to see all users");
        }
    }

    public List<UserEntity> findByRol(Rol rol) {
        return userRepository.findUserEntityByRol(rol);
    }

    public GetUserDto getAuthenticatedUser(UserEntity userEntity) {
        if (userEntity == null) {
            throw new EntityNotFoundException("UserEntity not found");
        } else
            return userDtoConverter.toGetUserDto(userEntity);
    }

    public GetUserDto getUserProfileByUsername(String username) {
        Optional<UserEntity> user = userRepository.findFirstByUsername(username);
        if(user.isEmpty()){
            throw new EntityNotFoundException("No userEntity matches the provided username");
        }else{
            return userDtoConverter.toGetUserDto(user.get());
        }
    }

    public GetPropietarioDto getPropietarioProfileByUsername(String username) {
        Optional<UserEntity> user = userRepository.findFirstByUsername(username);
        if(user.isEmpty()){
            throw new EntityNotFoundException("No userEntity matches the provided username");
        }else{
            return userDtoConverter.toGetPropietarioDto(user.get());
        }
    }

    public UserNameAvailabilityDto checkUsernameAvailability(String username){
        Optional<UserEntity> user = userRepository.findFirstByUsername(username);
        if(user.isEmpty()){
            return new UserNameAvailabilityDto(username,true);
        }else{
            return new UserNameAvailabilityDto(username,false);
        }
    }

    public GetUserDto editMyProfile (CreateUserDto newUser, MultipartFile file, UserEntity userEntity){
        Optional<UserEntity> userOptional = findById(userEntity.getId());

        if(userOptional.isEmpty()){
            throw new EntityNotFoundException("No userEntity matches the actual userEntity");
        }else if (file == null || !imagesTypes.contains(file.getContentType())){
            throw new StorageException("The provided file does not match any of the allowed file types, please ensure image type is jpg, jpeg or png.");
        }else {
            UserEntity userEntityPresent = userOptional.get();
            awsS3Service.deleteObject(userEntityPresent.getAvatar().substring(userEntityPresent.getAvatar().lastIndexOf("/")+1));
            String fileUrl = awsS3Service.storeCompressed(file);

            userEntity.setUsername(newUser.getUsername());
            userEntity.setAvatar(fileUrl);
            userEntity.setBirthDate(newUser.getBirthDate());
            userEntity.setEmail(newUser.getEmail());
            userEntity.setPhone(newUser.getPhone());
            userEntity.setPassword(passwordEncoder.encode(newUser.getPassword()));

            return userDtoConverter.toGetUserDto(save(userEntity));
        }
    }
}

