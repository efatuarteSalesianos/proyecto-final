package com.salesianostriana.dam.finalapi.services;

import com.salesianostriana.dam.finalapi.awss3.AWSS3Service;
import com.salesianostriana.dam.finalapi.dtos.site.CreateSiteDto;
import com.salesianostriana.dam.finalapi.dtos.site.GetSiteDto;
import com.salesianostriana.dam.finalapi.dtos.site.SiteDtoConverter;
import com.salesianostriana.dam.finalapi.errors.exceptions.EntityNotFoundException;
import com.salesianostriana.dam.finalapi.errors.exceptions.FileNotFoundException;
import com.salesianostriana.dam.finalapi.errors.exceptions.FileNotSupportedException;
import com.salesianostriana.dam.finalapi.errors.exceptions.UnauthorizeException;
import com.salesianostriana.dam.finalapi.models.Like;
import com.salesianostriana.dam.finalapi.models.Rol;
import com.salesianostriana.dam.finalapi.models.Site;
import com.salesianostriana.dam.finalapi.models.UserEntity;
import com.salesianostriana.dam.finalapi.repositories.LikeRepository;
import com.salesianostriana.dam.finalapi.repositories.SiteRepository;
import com.salesianostriana.dam.finalapi.services.base.BaseService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

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
    private final LikeRepository likeRepository;

    public Site createSite(CreateSiteDto createSiteDto, MultipartFile file, UserEntity userEntity) {
        String originalFileUrl = "";
        String scaledFileUrl = "";
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
                .originalFile(originalFileUrl)
                .scaledFile(scaledFileUrl)
                .build();

        return save(site);
    }

    public Site editSite(Long siteId, CreateSiteDto createSiteDto, MultipartFile file) {
        Optional<Site> siteOptional = findById(siteId);
        try {

            //Site site = siteOptional.orElseThrow(() -> new FileNotFoundException("Site not found"));

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

    public List<GetSiteDto> getAllSites() {
        List<Site> sites = findAll().stream().toList();

        if (sites.isEmpty()) {
            throw new FileNotFoundException("There are not sites available");
        } else {
            return sites.stream().map(siteDtoConverter::toGetSiteDto).collect(Collectors.toList());
        }
    }

    public List<GetSiteDto> getAllSitesByType(String type) {
        List<Site> sitesByType = findAll().stream().parallel().filter(x -> x.getType().getValue().equals(type)).toList();

        if (sitesByType.isEmpty()) {
            throw new FileNotFoundException("There are not sites of this type available");
        } else {
            return sitesByType.stream().map(siteDtoConverter::toGetSiteDto).collect(Collectors.toList());
        }

    }

    public Site getSingleSite(Long id) {
        Optional<Site> site = findById(id);

        if (site.isEmpty())
            throw new EntityNotFoundException("No site matches the provided id");
        else
            return site.get();
        }

    //add like
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

    //delete like
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

}
