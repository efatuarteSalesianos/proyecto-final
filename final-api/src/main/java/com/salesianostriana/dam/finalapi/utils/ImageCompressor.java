package com.salesianostriana.dam.finalapi.utils;

import com.salesianostriana.dam.finalapi.errors.exceptions.StorageException;
import io.github.techgnious.IVCompressor;
import io.github.techgnious.dto.ResizeResolution;
import io.github.techgnious.dto.VideoFormats;
import io.github.techgnious.exception.VideoException;
import org.imgscalr.Scalr;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class ImageCompressor {
    static IVCompressor compressor = new IVCompressor();

    public static byte[] compressAvatar(MultipartFile file) {
        String filename = StringUtils.cleanPath(file.getOriginalFilename());
        String extension = StringUtils.getFilenameExtension(filename);
        BufferedImage original = null;
        try {
            original = ImageIO.read(
                    new ByteArrayInputStream(file.getBytes())
            );
            BufferedImage scaled = Scalr.resize(original, 128);
            ByteArrayOutputStream os = new ByteArrayOutputStream();
            ImageIO.write(scaled, extension, os);
            //InputStream is = new ByteArrayInputStream(os.toByteArray());
            return os.toByteArray();
        } catch (IOException e) {
            throw new StorageException("Failed to resize file" + file.getOriginalFilename(), e);
        }
    }

    public static byte[] compressSiteImg(MultipartFile file) {
        String filename = StringUtils.cleanPath(file.getOriginalFilename());
        String extension = StringUtils.getFilenameExtension(filename);
        BufferedImage original = null;
        try {
            original = ImageIO.read(
                    new ByteArrayInputStream(file.getBytes())
            );
            BufferedImage scaled = Scalr.resize(original, 1024);
            ByteArrayOutputStream os = new ByteArrayOutputStream();
            ImageIO.write(scaled, extension, os);
            //InputStream is = new ByteArrayInputStream(os.toByteArray());
            return os.toByteArray();
        } catch (IOException e) {
            throw new StorageException("Failed to resize file" + file.getOriginalFilename(), e);
        }
    }

    public static byte[] compressSiteVideo(MultipartFile file) {
        try {
            return compressor.reduceVideoSize(file.getBytes(), VideoFormats.MP4, ResizeResolution.R480P);
        } catch (IOException | VideoException e) {
            throw new StorageException("Failed to compress file" + file.getOriginalFilename(), e);
        }
    }
}
