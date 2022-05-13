package com.salesianostriana.dam.finalapi.services;

import com.salesianostriana.dam.finalapi.awss3.AWSS3Service;
import com.salesianostriana.dam.finalapi.dtos.appointment.AppointmentDtoConverter;
import com.salesianostriana.dam.finalapi.dtos.appointment.CreateAppointmentDto;
import com.salesianostriana.dam.finalapi.dtos.appointment.GetAppointmentDto;
import com.salesianostriana.dam.finalapi.dtos.comment.CommentDtoConverter;
import com.salesianostriana.dam.finalapi.dtos.comment.CreateCommentDto;
import com.salesianostriana.dam.finalapi.dtos.comment.GetCommentDto;
import com.salesianostriana.dam.finalapi.dtos.site.CreateSiteDto;
import com.salesianostriana.dam.finalapi.dtos.site.GetSiteDto;
import com.salesianostriana.dam.finalapi.dtos.site.SiteDtoConverter;
import com.salesianostriana.dam.finalapi.errors.exceptions.*;
import com.salesianostriana.dam.finalapi.models.*;
import com.salesianostriana.dam.finalapi.repositories.AppointmentRepository;
import com.salesianostriana.dam.finalapi.repositories.CommentRepository;
import com.salesianostriana.dam.finalapi.repositories.LikeRepository;
import com.salesianostriana.dam.finalapi.repositories.SiteRepository;
import com.salesianostriana.dam.finalapi.services.base.BaseService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SiteService extends BaseService<Site, Long, SiteRepository> {
    private final List<String> imagesTypes = new ArrayList<>(Arrays.asList("image/jpeg", "image/png", "image/jpg"));
    private final List<String> videoTypes = new ArrayList<>(Arrays.asList("video/mp4", "video/avi", "video/mkv"));
    private final AWSS3Service awsS3Service;
    private final SiteDtoConverter siteDtoConverter;

    private final SiteRepository siteRepository;

    private final LikeRepository likeRepository;

    private final CommentRepository commentRepository;

    private final CommentDtoConverter commentDtoConverter;

    private final AppointmentRepository appointmentRepository;

    private final AppointmentDtoConverter appointmentDtoConverter;

    public Site createSite(CreateSiteDto createSiteDto, MultipartFile file, UserEntity userEntity) {
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
                .build();

        return save(site);
    }

    public Site editSite(Long siteId, CreateSiteDto createSiteDto, MultipartFile file) {
        Optional<Site> siteOptional = findById(siteId);
        try {

            Site site = siteOptional.get();
            //Delete old files
            awsS3Service.deleteObject(site.getOriginalFile().substring(site.getOriginalFile().lastIndexOf("/") + 1));
            awsS3Service.deleteObject(site.getScaledFile().substring(site.getScaledFile().lastIndexOf("/") + 1));

            site.setName(createSiteDto.getName());
            site.setDescription(createSiteDto.getDescription());
            site.setAddress(createSiteDto.getAddress());
            site.setCity(createSiteDto.getCity());
            site.setPostalCode(createSiteDto.getPostalCode());
            site.setEmail(createSiteDto.getEmail());
            site.setPhone(createSiteDto.getPhone());
            site.setWeb(createSiteDto.getWeb());
            site.setOpeningHour(createSiteDto.getOpeningHour());
            site.setClosingHour(createSiteDto.getClosingHour());

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

    public List<GetSiteDto> getAllSites() {
        List<Site> sites = findAll().stream().toList();

        if (sites.isEmpty()) {
            throw new FileNotFoundException("There are not sites available");
        } else {
            return sites.stream().map(siteDtoConverter::toGetSiteDto).collect(Collectors.toList());
        }
    }

    public List<GetSiteDto> getSitesByName(String name) {
        List<Site> sitesByName = siteRepository.findByNameContaining(name);
        if (sitesByName.isEmpty()) {
            throw new FileNotFoundException("There are not sites available");
        } else {
            return sitesByName.stream().map(siteDtoConverter::toGetSiteDto).collect(Collectors.toList());
        }
    }

    public List<GetSiteDto> getAllSitesByCity(String city) {
        List<Site> sitesByCity = siteRepository.findByCity(city);

        if (sitesByCity.isEmpty()) {
            throw new FileNotFoundException("There are not sites in this city available");
        } else {
            return sitesByCity.stream().map(siteDtoConverter::toGetSiteDto).collect(Collectors.toList());
        }
    }

    public List<GetSiteDto> getAllSitesByType(String type) {
        List<Site> sitesByType = siteRepository.findByType(type);

        if (sitesByType.isEmpty()) {
            throw new FileNotFoundException("There are not sites of this type available");
        } else {
            return sitesByType.stream().map(siteDtoConverter::toGetSiteDto).collect(Collectors.toList());
        }
    }

    public List<GetSiteDto> getAllSitesByPostalCode(String postalCode) {
        List<Site> sitesByPostalCode = siteRepository.findByPostalCode(postalCode);

        if (sitesByPostalCode.isEmpty()) {
            throw new FileNotFoundException("There are not sites with this postal code available");
        } else {
            return sitesByPostalCode.stream().map(siteDtoConverter::toGetSiteDto).collect(Collectors.toList());
        }
    }

    public Site getSingleSite(Long id) {
        Optional<Site> site = findById(id);

        if (site.isEmpty())
            throw new EntityNotFoundException("No site matches the provided id");
        else
            return site.get();
    }

    public boolean deleteSite(Long siteId, UserEntity userEntity) {
        Optional<Site> siteOptional = findById(siteId);
        try {
            Site site = siteOptional.get();

            if (userEntity.getRol().equals(Rol.ADMIN)) {
                //Delete old files
                awsS3Service.deleteObject(site.getOriginalFile().substring(site.getOriginalFile().lastIndexOf("/") + 1));
                awsS3Service.deleteObject(site.getScaledFile().substring(site.getScaledFile().lastIndexOf("/") + 1));
                delete(site);
                return true;
            } else {
                throw new UnauthorizeException("You are not authorized to delete this site");
            }
        } catch (Exception e) {
            throw new FileNotFoundException("File not found");
        }
    }

    public Site addLike(Long siteId, UserEntity cliente) {
        Optional<Site> site = findById(siteId);
        if (site.isEmpty()) {
            throw new EntityNotFoundException("No site matches the provided id");
        } else {
            Optional<Like> likeOptional = likeRepository.findFirstBySiteIdAndClienteId(siteId, cliente.getId());
            if(likeOptional.isPresent()){
                throw new UnauthorizeException("You can't like the same site twice");
            }
            Like like = Like.builder()
                    .site(site.get())
                    .cliente(cliente)
                    .build();

            site.get().getLikes().add(like);
            return save(site.get());
        }
    }

    public Site deleteLike(Long siteId, UserEntity userEntity) {
        Optional<Site> site = findById(siteId);
        if (site.isEmpty()) {
            throw new EntityNotFoundException("No site matches the provided id");
        } else {
            site.get().getLikes().removeIf(like -> like.getCliente().getId().equals(userEntity.getId()));
            likeRepository.deleteLike(siteId, userEntity.getId());
            return save(site.get());
        }
    }

    //Add comment to a site by id only if the appointment hour that client had in that site has passed
    public GetCommentDto addComment(Long siteId, CreateCommentDto newComment, UserEntity userEntity, MultipartFile file) {
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
        if (site.isEmpty()) {
            throw new EntityNotFoundException("No site matches the provided id");
        }
        else {
            Optional<Appointment> appointmentOptional = appointmentRepository.findFirstBySiteIdAndClienteId(siteId, userEntity.getId());
            if(appointmentOptional.isEmpty()){
                throw new UnauthorizeException("You can't comment on a site without an appointment");
            }
            Appointment appointment = appointmentOptional.get();
            if(appointment.getDate().isAfter(LocalDateTime.now())){
                throw new UnauthorizeException("You can't comment on a site that has not already passed");
            }
            Comment comment = Comment.builder()
                    .site(site.get())
                    .cliente(userEntity)
                    .description(newComment.getDescription())
                    .createdDate(LocalDateTime.now())
                    .originalFile(originalFileUrl)
                    .scaledFile(scaledFileUrl)
                    .build();
            site.get().getComments().add(comment);
            save(site.get());
            return commentDtoConverter.toGetCommentDto(comment);
        }
    }

    public List<GetCommentDto> getAllComments(Long siteId) {
        Optional<Site> site = findById(siteId);
        if (site.isEmpty()) {
            throw new EntityNotFoundException("No site matches the provided id");
        }
        return site.get().getComments().stream().map(commentDtoConverter::toGetCommentDto).collect(Collectors.toList());
    }

    public GetCommentDto getComment(Long siteId, CommentPK commentId) {
        Optional<Site> site = findById(siteId);
        if (site.isEmpty()) {
            throw new EntityNotFoundException("No site matches the provided id");
        }
        Optional<Comment> comment = site.get().getComments().stream().filter(c -> c.getId().equals(commentId)).findAny();
        if (comment.isEmpty()) {
            throw new EntityNotFoundException("No comment matches the provided id");
        }
        return GetCommentDto.builder()
                .cliente(comment.get().getCliente().getFull_name())
                .site(comment.get().getSite().getName())
                .title(comment.get().getTitle())
                .description(comment.get().getDescription())
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
            throw new EntityNotFoundException("No site matches the provided id");
        }
        else if (commentOptional.isEmpty()) {
            throw new EntityNotFoundException("No comment matches the provided id");
        }
        else {
            Comment commentEntity = commentOptional.get();
            commentEntity.setCliente(cliente);
            commentEntity.setTitle(comment.getTitle());
            commentEntity.setDescription(comment.getDescription());
            commentEntity.setCreatedDate(LocalDateTime.now());
            commentEntity.setOriginalFile(originalFileUrl);
            commentEntity.setScaledFile(scaledFileUrl);

            save(site.get());
            return commentDtoConverter.toGetCommentDto(commentEntity);
        }
    }

    public void deleteComment(Long siteId, UserEntity userEntity, CommentPK commentId) {
        Optional<Site> site = findById(siteId);
        Optional<Comment> comment = commentRepository.existsById(commentId);
        if (site.isEmpty()) {
            throw new EntityNotFoundException("No site matches the provided id");
        }
        if (comment.isEmpty()) {
            throw new EntityNotFoundException("No comment matches the provided id");
        }
        if (!comment.get().getCliente().getId().equals(userEntity.getId())) {
            throw new UnauthorizeException("You can't delete a comment that is not yours");
        }
        //Delete old files
        awsS3Service.deleteObject(comment.get().getOriginalFile().substring(comment.get().getOriginalFile().lastIndexOf("/") + 1));
        awsS3Service.deleteObject(comment.get().getScaledFile().substring(comment.get().getScaledFile().lastIndexOf("/") + 1));
        site.get().getComments().remove(comment.get());
        commentRepository.deleteById(commentId);
        save(site.get());
    }

    public GetAppointmentDto addAppointment(Long siteId, UserEntity userEntity, CreateAppointmentDto createAppointmentDto) {
        Optional<Site> site = findById(siteId);
        if (site.isEmpty()) {
            throw new EntityNotFoundException("No site matches the provided id");
        }
        if (!site.get().getDaysOpen().contains(createAppointmentDto.getDate().getDayOfWeek())) {
            throw new AppointmentNotAvailableException("The appointment date is not available");
        }
        if (createAppointmentDto.getDate().getHour() < site.get().getOpeningHour() || createAppointmentDto.getDate().getHour() > site.get().getClosingHour()) {
            throw new AppointmentNotAvailableException("The appointment hour is not available");
        }
        Appointment appointment = Appointment.builder()
                .cliente(userEntity)
                .site(site.get())
                .date(createAppointmentDto.getDate())
                .description(createAppointmentDto.getDescription())
                .build();
        site.get().getAppointments().add(appointment);
        save(site.get());
        return appointmentDtoConverter.toGetAppointmentDto(appointment);
    }

    public List<GetAppointmentDto> getAllAppointments(Long siteId) {
        Optional<Site> site = findById(siteId);
        if (site.isEmpty()) {
            throw new EntityNotFoundException("No site matches the provided id");
        }
        return site.get().getAppointments().stream().map(appointmentDtoConverter::toGetAppointmentDto).collect(Collectors.toList());
    }

    public GetAppointmentDto getAppointment(Long siteId, AppointmentPK appointmentId) {
        Optional<Site> site = findById(siteId);
        Optional<Appointment> appointment = appointmentRepository.existById(appointmentId);
        if (site.isEmpty()) {
            throw new EntityNotFoundException("No site matches the provided id");
        }
        if (appointment.isEmpty()) {
            throw new EntityNotFoundException("No appointment matches the provided id");
        }
        if (!appointment.get().getSite().getId().equals(siteId)) {
            throw new UnauthorizeException("No appointment matches the provided id");
        }
        return appointmentDtoConverter.toGetAppointmentDto(appointment.get());
    }

    public GetAppointmentDto editAppointment(Long siteId, AppointmentPK appointmentId, UserEntity userEntity, CreateAppointmentDto appointment) {
        Optional<Site> site = findById(siteId);
        Optional<Appointment> appointmentEntity = appointmentRepository.existById(appointmentId);
        if (site.isEmpty()) {
            throw new EntityNotFoundException("No site matches the provided id");
        }
        if (appointmentEntity.isEmpty()) {
            throw new EntityNotFoundException("No appointment matches the provided id");
        }
        if (!appointmentEntity.get().getSite().getId().equals(siteId)) {
            throw new UnauthorizeException("No appointment matches the provided id");
        }
        if (!appointmentEntity.get().getCliente().getId().equals(userEntity.getId())) {
            throw new UnauthorizeException("No appointment matches the provided id");
        }
        appointmentEntity.get().setDate(appointment.getDate());
        appointmentEntity.get().setDescription(appointment.getDescription());
        appointmentRepository.save(appointmentEntity.get());
        return appointmentDtoConverter.toGetAppointmentDto(appointmentEntity.get());
    }

    public void deleteAppointment(Long siteId, UserEntity userEntity, AppointmentPK appointmentId) {
        Optional<Site> site = findById(siteId);
        Optional<Appointment> appointment = appointmentRepository.existById(appointmentId);
        if (site.isEmpty()) {
            throw new EntityNotFoundException("No site matches the provided id");
        }
        if (appointment.isEmpty()) {
            throw new EntityNotFoundException("No appointment matches the provided id");
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

    public boolean isAppointmentTimeAvailable(Long siteId, CreateAppointmentDto appointment) {
        Optional<Site> site = findById(siteId);
        if (site.isEmpty()) {
            throw new EntityNotFoundException("No site matches the provided id");
        }
        List<Appointment> appointments = site.get().getAppointments();
        for (Appointment a : appointments) {
            if (a.getDate().equals(appointment.getDate())) {
                return false;
            }
        }
        return true;
    }

}
