package com.salesianostriana.dam.finalapi.services;

import com.salesianostriana.dam.finalapi.awss3.AWSS3Service;
import com.salesianostriana.dam.finalapi.dtos.appointment.AppointmentDtoConverter;
import com.salesianostriana.dam.finalapi.dtos.appointment.CreateAppointmentDto;
import com.salesianostriana.dam.finalapi.dtos.appointment.GetAppointmentDto;
import com.salesianostriana.dam.finalapi.dtos.comment.CommentDtoConverter;
import com.salesianostriana.dam.finalapi.dtos.comment.CreateCommentDto;
import com.salesianostriana.dam.finalapi.dtos.comment.GetCommentDto;
import com.salesianostriana.dam.finalapi.dtos.site.*;
import com.salesianostriana.dam.finalapi.errores.excepciones.*;
import com.salesianostriana.dam.finalapi.models.*;
import com.salesianostriana.dam.finalapi.repositories.*;
import com.salesianostriana.dam.finalapi.services.base.BaseService;
import lombok.RequiredArgsConstructor;
import org.apache.catalina.User;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SiteService extends BaseService<Site, Long, SiteRepository> {
    private final List<String> imagesTypes = new ArrayList<>(Arrays.asList("image/jpeg", "image/png", "image/jpg"));
    private final List<String> videoTypes = new ArrayList<>(Arrays.asList("video/mp4", "video/avi", "video/mkv"));
    private final AWSS3Service awsS3Service;
    private final SiteDtoConverter siteDtoConverter;

    private final SiteRepository siteRepository;

    private final UserRepository userRepository;

    private final LikeRepository likeRepository;

    private final CommentRepository commentRepository;

    private final CommentDtoConverter commentDtoConverter;

    private final AppointmentRepository appointmentRepository;

    private final AppointmentDtoConverter appointmentDtoConverter;

    public Site createSite(CreateSiteDto createSiteDto, MultipartFile file) {
        String originalFileUrl;
        String scaledFileUrl;

        Optional<UserEntity> propietario = userRepository.findById(createSiteDto.getPropietarioId());
        if(propietario.isEmpty())
            throw new SingleEntityNotFoundException(createSiteDto.getPropietarioId().toString(), UserEntity.class);

        //Check if file is image or video and save it
        if (imagesTypes.contains(file.getContentType())) {
            //Save scaled and original images
            originalFileUrl = awsS3Service.store(file);
            scaledFileUrl = awsS3Service.storeCompressedSite(file);
        } else if (videoTypes.contains(file.getContentType())) {
            //Save scaled and original video
            originalFileUrl = awsS3Service.store(file);
            scaledFileUrl = awsS3Service.storeCompressedSiteVideo(file);
        } else {
            throw new FileNotSupportedException("File type not supported");
        }
        Site site = Site.builder()
                .name(createSiteDto.getName())
                .description(createSiteDto.getDescription())
                .address(createSiteDto.getAddress())
                .city(createSiteDto.getCity())
                .postalCode(createSiteDto.getPostalCode())
                .email(createSiteDto.getEmail())
                .phone(createSiteDto.getPhone())
                .web(createSiteDto.getWeb())
                .openingHour(createSiteDto.getOpeningHour())
                .closingHour(createSiteDto.getClosingHour())
                .originalFile(originalFileUrl)
                .scaledFile(scaledFileUrl)
                .propietario(propietario.get())
                .type(createSiteDto.getType())
                .build();

        return save(site);
    }

    public Site editSite(Long siteId, EditSiteDto editSiteDto, MultipartFile file) {
        Optional<Site> siteOptional = findById(siteId);
        try {

            Site site = siteOptional.get();
            //Delete old files
            awsS3Service.deleteObject(site.getOriginalFile().substring(site.getOriginalFile().lastIndexOf("/") + 1));
            awsS3Service.deleteObject(site.getScaledFile().substring(site.getScaledFile().lastIndexOf("/") + 1));

            site.setName(editSiteDto.getName());
            site.setDescription(editSiteDto.getDescription());
            site.setAddress(editSiteDto.getAddress());
            site.setCity(editSiteDto.getCity());
            site.setPhone(editSiteDto.getPhone());
            site.setPostalCode(editSiteDto.getPostalCode());
            site.setEmail(editSiteDto.getEmail());
            site.setPhone(editSiteDto.getPhone());
            site.setWeb(editSiteDto.getWeb());
            site.setOpeningHour(editSiteDto.getOpeningHour());
            site.setClosingHour(editSiteDto.getClosingHour());

            //Check if file is image or video and save it
            if (file != null) {
                //Check if file is image or video and save it
                if (imagesTypes.contains(file.getContentType())) {
                    //Save scaled and original images
                    site.setOriginalFile(awsS3Service.store(file));
                    site.setScaledFile(awsS3Service.storeCompressedSite(file));
                } else if (videoTypes.contains(file.getContentType())) {
                    //Save scaled and original video
                    site.setOriginalFile(awsS3Service.store(file));
                    site.setScaledFile(awsS3Service.storeCompressedSiteVideo(file));
                } else {
                    throw new FileNotSupportedException("File type not supported");
                }
            }
            return save(site);

        } catch (Exception e) {
            throw new FileNotFoundException("File not found");
        }
    }

    public List<GetListSiteDto> getAllSites() {
        List<Site> sites = findAll().stream().toList();

        if (sites.isEmpty()) {
            throw new ListEntityNotFoundException(Site.class);
        } else {
            return sites.stream().map(siteDtoConverter::toGetListSiteDto).collect(Collectors.toList());
        }
    }

    public List<GetListSiteDto> getSitesByName(String name) {
        List<Site> sitesByName = siteRepository.findByNameContaining(name);
        if (sitesByName.isEmpty()) {
            throw new ListEntityNotFoundException(Site.class);
        } else {
            return sitesByName.stream().map(siteDtoConverter::toGetListSiteDto).collect(Collectors.toList());
        }
    }

    public List<GetListSiteDto> getAllSitesByCity(String city) {
        List<Site> sitesByCity = siteRepository.findByCity(city);

        if (sitesByCity.isEmpty()) {
            throw new ListEntityNotFoundException(Site.class);
        } else {
            return sitesByCity.stream().map(siteDtoConverter::toGetListSiteDto).collect(Collectors.toList());
        }
    }

    public List<GetListSiteDto> getAllSitesByPostalCode(String postalCode) {
        List<Site> sitesByPostalCode = siteRepository.findByPostalCode(postalCode);

        if (sitesByPostalCode.isEmpty()) {
            throw new ListEntityNotFoundException(Site.class);
        } else {
            return sitesByPostalCode.stream().map(siteDtoConverter::toGetListSiteDto).collect(Collectors.toList());
        }
    }

    public List<GetListSiteDto> getAllSitesByPropietario(String username) {
        List<GetListSiteDto> sitesByUserEntity = siteRepository.findByPropietarioUsername(username);

        if (sitesByUserEntity.isEmpty()) {
            throw new ListEntityNotFoundException(Site.class);
        } else {
            return sitesByUserEntity;
        }
    }

    public Site getSingleSite(Long id) {
        Optional<Site> site = findById(id);

        if (site.isEmpty())
            throw new SingleEntityNotFoundException(id.toString(), Site.class);
        else
            return site.get();
    }

    public boolean deleteSite(Long siteId, UserEntity userEntity) {
        Optional<Site> siteOptional = findById(siteId);
        try {
            Site site = siteOptional.get();
            //Delete old files
            awsS3Service.deleteObject(site.getOriginalFile().substring(site.getOriginalFile().lastIndexOf("/") + 1));
            awsS3Service.deleteObject(site.getScaledFile().substring(site.getScaledFile().lastIndexOf("/") + 1));
            delete(site);
            return true;
        } catch (Exception e) {
            throw new SingleEntityNotFoundException(siteId.toString(), Site.class);
        }
    }

    public GetSiteDto addLike(Long siteId, UserEntity cliente) {
        Optional<Site> site = findById(siteId);
        if (site.isEmpty()) {
            throw new SingleEntityNotFoundException(siteId.toString(), Site.class);
        } else {
            Optional<Like> likeOptional = likeRepository.findBySiteIdAndClienteId(siteId, cliente.getId());
            if(likeOptional.isPresent()){
                throw new UnauthorizeException("You can't like the same site twice");
            }
            Like like = Like.builder()
                    .site(site.get())
                    .cliente(cliente)
                    .build();
            site.get().setLiked(true);
            like.addLikeToCliente(cliente);
            like.addLikeToSite(site.get());
            likeRepository.save(like);
            userRepository.save(cliente);
            siteRepository.save(site.get());
            save(site.get());
            return siteDtoConverter.toGetSiteDto(site.get());
        }
    }

    public void deleteLike(Long siteId, UserEntity userEntity) {
        Optional<Site> site = findById(siteId);
        if (site.isEmpty()) {
            throw new SingleEntityNotFoundException(siteId.toString(), Site.class);
        } else {
            site.get().getLikes().removeIf(like -> like.getCliente().getId().equals(userEntity.getId()));
            site.get().setLiked(false);
            likeRepository.deleteLike(siteId, userEntity.getId());
            save(site.get());
        }
    }

    public List<GetListSiteDto> getLikedSites(UserEntity userEntity){
        List<Like> likes = likeRepository.findAllByClienteId(userEntity.getId());
        List<Site> sites = new ArrayList<>();
        for(Like like : likes){
            sites.add(like.getSite());
        }
        return sites.stream().map(siteDtoConverter::toGetListSiteDto).collect(Collectors.toList());
    }

    public GetCommentDto addComment(Long siteId, CreateCommentDto newComment, UserEntity cliente, MultipartFile file) {
        Optional<Site> site = findById(siteId);
        String originalFileUrl;
        String scaledFileUrl;
        //Check if file is image or video and save it
        if (imagesTypes.contains(file.getContentType())) {
            //Save scaled and original images
            originalFileUrl = awsS3Service.store(file);
            scaledFileUrl = awsS3Service.storeCompressedSite(file);
        } else if (videoTypes.contains(file.getContentType())) {
            //Save scaled and original video
            originalFileUrl = awsS3Service.store(file);
            scaledFileUrl = awsS3Service.storeCompressedSiteVideo(file);
        } else {
            throw new FileNotSupportedException("File type not supported");
        }
        if (site.isPresent()) {
            Optional<Appointment> appointmentOptional = appointmentRepository.findBySiteIdAndClienteId(siteId, cliente.getId());
            if(appointmentOptional.isPresent()) {
                Comment comment = Comment.builder()
                        .site(site.get())
                        .cliente(cliente)
                        .title(newComment.getTitle())
                        .description(newComment.getDescription())
                        .createdDate(LocalDateTime.now())
                        .rate(newComment.getRate())
                        .originalFile(originalFileUrl)
                        .scaledFile(scaledFileUrl)
                        .build();
                comment.addCommentToCliente(cliente);
                comment.addCommentToSite(site.get());
                commentRepository.save(comment);
                userRepository.save(cliente);
                siteRepository.save(site.get());
                site.get().getComments().add(comment);
                save(site.get());
                return commentDtoConverter.toGetCommentDto(comment);
            } else {
                throw new EntityNotFoundException("No appointment found for this site");
            }
        } else {
            throw new SingleEntityNotFoundException(siteId.toString(), Site.class);
        }
    }

    public List<GetCommentDto> getAllComments(Long siteId) {
        Optional<Site> site = findById(siteId);
        if (site.isEmpty()) {
            throw new SingleEntityNotFoundException(siteId.toString(), Site.class);
        }
        return site.get().getComments().stream().map(commentDtoConverter::toGetCommentDto).collect(Collectors.toList());
    }

    public GetCommentDto getComment(Long siteId, CommentPK commentId) {
        Optional<Site> site = findById(siteId);
        if (site.isEmpty()) {
            throw new SingleEntityNotFoundException(siteId.toString(), Site.class);
        }
        Optional<Comment> comment = site.get().getComments().stream().filter(c -> c.getId().equals(commentId)).findAny();
        if (comment.isEmpty()) {
            throw new SingleEntityNotFoundException(commentId.toString(), Comment.class);
        }
        return GetCommentDto.builder()
                .cliente(comment.get().getCliente().getFullName())
                .site(comment.get().getSite().getName())
                .title(comment.get().getTitle())
                .description(comment.get().getDescription())
                .rate(comment.get().getRate())
                .image(comment.get().getScaledFile())
                .build();
    }

    public GetCommentDto editComment(Long siteId, CommentPK commentId, UserEntity cliente, MultipartFile file, CreateCommentDto comment) {
        Optional<Site> site = findById(siteId);
        Optional<Comment> commentOptional = commentRepository.existsById(commentId);
        String originalFileUrl;
        String scaledFileUrl;
        //Check if file is image or video and save it
        if (imagesTypes.contains(file.getContentType())) {
            //Save scaled and original images
            originalFileUrl = awsS3Service.store(file);
            scaledFileUrl = awsS3Service.storeCompressedSite(file);
        }
        else if (videoTypes.contains(file.getContentType())) {
            //Save scaled and original video
            originalFileUrl = awsS3Service.store(file);
            scaledFileUrl = awsS3Service.storeCompressedSiteVideo(file);
        } else {
            throw new FileNotSupportedException("File type not supported");
        }
        if (site.isEmpty()) {
            throw new SingleEntityNotFoundException(siteId.toString(), Site.class);
        }
        else if (commentOptional.isEmpty()) {
            throw new SingleEntityNotFoundException(commentId.toString(), Comment.class);
        }
        else {
            Comment commentEntity = commentOptional.get();
            commentEntity.setCliente(cliente);
            commentEntity.setTitle(comment.getTitle());
            commentEntity.setDescription(comment.getDescription());
            commentEntity.setCreatedDate(LocalDateTime.now());
            commentEntity.setOriginalFile(originalFileUrl);
            commentEntity.setScaledFile(scaledFileUrl);

            userRepository.save(cliente);
            commentRepository.save(commentEntity);
            save(site.get());
            return commentDtoConverter.toGetCommentDto(commentEntity);
        }
    }

    public void deleteComment(Long siteId, UUID clienteId, CommentPK commentId) throws BadRequestException {
        Optional<Site> site = findById(siteId);
        Optional<Comment> comment = commentRepository.existsById(commentId);
        Optional<UserEntity> cliente = userRepository.findById(clienteId);
        if (site.isEmpty()) {
            throw new SingleEntityNotFoundException(siteId.toString(), Site.class);
        }
        if (comment.isEmpty()) {
            throw new SingleEntityNotFoundException(commentId.toString(), Comment.class);
        }
        if (cliente.isEmpty()) {
            throw new SingleEntityNotFoundException(clienteId.toString(), UserEntity.class);
        }
        if (!comment.get().getCliente().getId().equals(cliente.get().getId())) {
            throw new BadRequestException("You can't delete a comment that is not yours");
        }
        //Delete old files
        awsS3Service.deleteObject(comment.get().getOriginalFile().substring(comment.get().getOriginalFile().lastIndexOf("/") + 1));
        awsS3Service.deleteObject(comment.get().getScaledFile().substring(comment.get().getScaledFile().lastIndexOf("/") + 1));
        site.get().getComments().remove(comment.get());
        commentRepository.deleteById(commentId);
        userRepository.save(cliente.get());
        save(site.get());
    }

    public GetAppointmentDto addAppointment(Long siteId, UserEntity cliente, CreateAppointmentDto createAppointmentDto) {
        Optional<Site> site = findById(siteId);
        if (site.isEmpty()) {
            throw new SingleEntityNotFoundException(siteId.toString(), Site.class);
        }
//        if (!site.get().getDaysOpen().contains(createAppointmentDto.getDate().getDayOfWeek())) {
//            throw new AppointmentNotAvailableException("The appointment day is not available");
//        }
//        if (site.get().getOpeningHour().isAfter(createAppointmentDto.getDate().toLocalTime()) || site.get().getClosingHour().isBefore(createAppointmentDto.getDate().toLocalTime())) {
//            throw new AppointmentNotAvailableException("The appointment hour is not available");
//        }
        Appointment appointment = Appointment.builder()
                .cliente(cliente)
                .site(site.get())
                .date(createAppointmentDto.getDate())
                .hour(createAppointmentDto.getHour())
                .description(createAppointmentDto.getDescription())
                .status(StatusType.ESPERA)
                .build();
        appointment.addAppointmentToCliente(cliente);
        appointment.addAppointmentToSite(site.get());
        appointmentRepository.save(appointment);
        userRepository.save(cliente);
        save(site.get());
        return appointmentDtoConverter.toGetAppointmentDto(appointment);

    }

    public List<GetAppointmentDto> getAllAppointments(Long siteId) {
        Optional<Site> site = findById(siteId);
        if (site.isEmpty()) {
            throw new SingleEntityNotFoundException(siteId.toString(), Site.class);
        }
        return site.get().getAppointments().stream().map(appointmentDtoConverter::toGetAppointmentDto).collect(Collectors.toList());
    }

    public GetAppointmentDto getAppointment(Long siteId, AppointmentPK appointmentId) {
        Optional<Site> site = findById(siteId);
        Optional<Appointment> appointment = appointmentRepository.findById(appointmentId);
        if (site.isEmpty()) {
            throw new SingleEntityNotFoundException(siteId.toString(), Site.class);
        }
        if (appointment.isEmpty()) {
            throw new SingleEntityNotFoundException(appointmentId.toString(), Appointment.class);
        }
        if (!appointment.get().getSite().getId().equals(siteId)) {
            throw new SingleEntityNotFoundException(siteId.toString(), Site.class);
        }
        return appointmentDtoConverter.toGetAppointmentDto(appointment.get());
    }

    public GetAppointmentDto editAppointment(Long siteId, AppointmentPK appointmentId, UserEntity userEntity, CreateAppointmentDto appointment) {
        Optional<Site> site = findById(siteId);
        Optional<Appointment> appointmentEntity = appointmentRepository.findById(appointmentId);
        if (site.isEmpty()) {
            throw new SingleEntityNotFoundException(siteId.toString(), Site.class);
        }
        if (appointmentEntity.isEmpty()) {
            throw new SingleEntityNotFoundException(appointmentId.toString(), Appointment.class);
        }
        if (!appointmentEntity.get().getSite().getId().equals(siteId)) {
            throw new SingleEntityNotFoundException(siteId.toString(), Site.class);
        }
        if (!appointmentEntity.get().getCliente().getId().equals(userEntity.getId())) {
            throw new SingleEntityNotFoundException(userEntity.getId().toString(), UserEntity.class);
        }
        appointmentEntity.get().setDate(appointment.getDate());
        appointmentEntity.get().setDescription(appointment.getDescription());
        appointmentRepository.save(appointmentEntity.get());
        return appointmentDtoConverter.toGetAppointmentDto(appointmentEntity.get());
    }

    public void deleteAppointment(Long siteId, UserEntity userEntity, AppointmentPK appointmentId) {
        Optional<Site> site = findById(siteId);
        Optional<Appointment> appointment = appointmentRepository.findById(appointmentId);
        if (site.isEmpty()) {
            throw new SingleEntityNotFoundException(siteId.toString(), Site.class);
        }
        if (appointment.isEmpty()) {
            throw new SingleEntityNotFoundException(appointmentId.toString(), Appointment.class);
        }
        if (!appointment.get().getCliente().getId().equals(userEntity.getId())) {
            if (!userEntity.getRol().equals(Rol.ADMIN)) {
                throw new UnauthorizeException("You can't delete an appointment that is not yours");
            }
        }
        site.get().getAppointments().remove(appointment.get());
        appointmentRepository.deleteById(appointmentId);
        save(site.get());
    }

    public boolean isAppointmentTimeAvailable(Long siteId, String appointmentDate) {
        Optional<Site> site = findById(siteId);
        if (site.isEmpty()) {
            throw new SingleEntityNotFoundException(siteId.toString(), Site.class);
        }
        List<Appointment> appointments = site.get().getAppointments();
        for (Appointment a : appointments) {
            if (a.getDate().equals(appointmentDate)) {
                return false;
            }
        }
        return true;
    }

}
