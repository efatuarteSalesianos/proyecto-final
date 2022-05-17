package com.salesianostriana.dam.finalapi.awss3;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.salesianostriana.dam.finalapi.errors.exceptions.StorageException;
import com.salesianostriana.dam.finalapi.utils.ImageCompressor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class AWSS3Service {
    private final AmazonS3 s3client;

    private final String bucketName = "mysalon";

    private String storeByteArray(byte[] image, String filename, String contentType, long size) throws IOException {
        try (InputStream inputStream = new ByteArrayInputStream(image)) {
            ObjectMetadata metadata = new ObjectMetadata();
            metadata.setContentLength(size);
            metadata.setContentType(contentType);
            PutObjectRequest request = new PutObjectRequest(bucketName, filename, inputStream, metadata);
            s3client.putObject(request);
            String fileUrl = "https://mysalonapi.s3.eu-west-3.amazonaws.com/";
            return fileUrl + filename;
        }
    }

    public String store(MultipartFile file) {
        String filename = StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename()));
        String newFilename = "";
        try {
            if (file.isEmpty())
                throw new StorageException("Empty file");

            newFilename = filename;
            while (s3client.doesObjectExist(bucketName, newFilename)) {
                String extension = StringUtils.getFilenameExtension(newFilename);
                String name = newFilename.replace("." + extension, "");
                String suffix = Long.toString(System.currentTimeMillis());
                suffix = suffix.substring(suffix.length() - 6);
                newFilename = name + "_" + suffix + "." + extension;
            }
            return storeByteArray(file.getBytes(), newFilename, file.getContentType(), file.getSize());
        } catch (IOException e) {
            throw new StorageException("Failed to store file " + file.getOriginalFilename(), e);
        }
    }

    public String storeCompressed(MultipartFile file) {
        byte[] scaled = ImageCompressor.compressAvatar(file);
        String filename = StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename()));
        String newFilename = "";
        try {
            if (file.isEmpty())
                throw new StorageException("Empty file");
            newFilename = filename;
            while (s3client.doesObjectExist(bucketName, newFilename)) {
                String extension = StringUtils.getFilenameExtension(newFilename);
                String name = newFilename.replace("." + extension, "");
                String suffix = Long.toString(System.currentTimeMillis());
                suffix = suffix.substring(suffix.length() - 6);
                newFilename = name + "_" + suffix + "." + extension;
            }
            return storeByteArray(scaled, newFilename, file.getContentType(), scaled.length);
        } catch (IOException e) {
            throw new StorageException("Failed to store file " + file.getOriginalFilename(), e);
        }
    }

    public String storeCompressedSite(MultipartFile file) {
        byte[] scaled = ImageCompressor.compressSiteImg(file);
        String filename = StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename()));
        String newFilename = "";
        try {
            if (file.isEmpty())
                throw new StorageException("Empty file");
            newFilename = filename;
            while (s3client.doesObjectExist(bucketName, newFilename)) {
                String extension = StringUtils.getFilenameExtension(newFilename);
                String name = newFilename.replace("." + extension, "");
                String suffix = Long.toString(System.currentTimeMillis());
                suffix = suffix.substring(suffix.length() - 6);
                newFilename = name + "_" + suffix + "." + extension;
            }
            return storeByteArray(scaled, newFilename, file.getContentType(), scaled.length);
        } catch (IOException e) {
            throw new StorageException("Failed to store file " + file.getOriginalFilename(), e);
        }
    }

    public String storeCompressedSiteVideo(MultipartFile file) {
        byte[] scaled = ImageCompressor.compressSiteVideo(file);
        String filename = StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename()));
        String newFilename = "";
        try {
            if (file.isEmpty())
                throw new StorageException("Empty file");
            newFilename = filename;
            while (s3client.doesObjectExist(bucketName, newFilename)) {
                String extension = StringUtils.getFilenameExtension(newFilename);
                String name = newFilename.replace("." + extension, "");
                String suffix = Long.toString(System.currentTimeMillis());
                suffix = suffix.substring(suffix.length() - 6);
                newFilename = name + "_" + suffix + "." + extension;
            }
            return storeByteArray(scaled, newFilename, file.getContentType(), scaled.length);
        } catch (IOException e) {
            throw new StorageException("Failed to store file " + file.getOriginalFilename(), e);
        }
    }

    public void deleteObject(String key) {
        //You can get the object's key from the object's URL.
        try {
            s3client.deleteObject(bucketName, key);
        } catch (AmazonServiceException e) {
            throw new StorageException("Failed to delete file " + key, e);
        }
    }
}