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

        if (newUser.getPassword().contentEquals(newUser.getPassword2())) {
            UserEntity userEntity = UserEntity.builder()
                    .username(newUser.getUsername())
                    .email(newUser.getEmail())
                    .avatar(fileUrl)
                    .birthDate(newUser.getBirthDate())
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

        if (newUser.getPassword().contentEquals(newUser.getPassword2())) {
            UserEntity userEntity = UserEntity.builder()
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

        if (newUser.getPassword().contentEquals(newUser.getPassword2())) {
            UserEntity userEntity = UserEntity.builder()
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
    public UserEntity convertToAdmin(UserEntity userEntity, String username) {
        Optional <UserEntity> userOptional = userRepository.findFirstByUsername(username);
        if (userOptional.isEmpty()) {
            throw new EntityNotFoundException("UserEntity not found");
        }
        if (userEntity.getRol() == Rol.ADMIN) {
            UserEntity newAdmin = userOptional.get();

            newAdmin.setRol(Rol.ADMIN);
            return save(newAdmin);
        } else {
            throw new UnauthorizeException("You must be an admin to convert to admin");
        }
    }

    public List<UserEntity> getAllUsers(UserEntity userEntity){
        List<UserEntity> userEntityOptional = userRepository.findAll();
        if(userEntityOptional.isEmpty()){ throw new EntityNotFoundException("There are no users to show"); }
        if(userEntity.getRol().equals(Rol.ADMIN)){
            return userRepository.findAll();
        }else{
            throw new UnauthorizeException("Only admins can see all users");
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

    public GetUserDto getUserProfileByUsername(String username, UserEntity userEntityPrincipal) {
        Optional<UserEntity> user = userRepository.findFirstByUsername(username);
        if(user.isEmpty()){
            throw new EntityNotFoundException("No userEntity matches the provided username");
        }else{
            if(user.get().getId().equals(userEntityPrincipal.getId())){
                return userDtoConverter.toGetUserDto(user.get());
            }
            return userDtoConverter.toGetUserDto(user.get());
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

