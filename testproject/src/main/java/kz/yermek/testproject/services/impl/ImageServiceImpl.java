package kz.yermek.testproject.services.impl;

import kz.yermek.testproject.models.Images;
import kz.yermek.testproject.repositories.ImageRepository;
import kz.yermek.testproject.services.ImageService;
import kz.yermek.testproject.util.ImageUtils;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.exception.ContextedRuntimeException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.zip.DataFormatException;

@Service
@RequiredArgsConstructor
public class ImageServiceImpl implements ImageService {

    private final ImageRepository imageRepository;

    @Override
    public List<Images> getImages() {
        return imageRepository.findAll();
    }

    @Override
    public Images getImage(int id) {
        return imageRepository.findById(id).orElseThrow();
    }

    @Override
    public Images uploadImage(MultipartFile imageFile) throws IOException {
        var imageToSave = Images.builder()
                .name(imageFile.getOriginalFilename())
                .type(imageFile.getContentType())
                .images(ImageUtils.compressImage(imageFile.getBytes()))
                .build();
        return imageRepository.save(imageToSave);
    }

    @Override
    public byte[] downloadImage(int id) {
        Optional<Images> dbImage = imageRepository.findById(id);
        return dbImage.map(image -> {
            try {
                return ImageUtils.decompressImage(image.getImages());
            } catch (DataFormatException | IOException exception) {
                throw new ContextedRuntimeException("Error downloading an image", exception)
                        .addContextValue("Image ID", image.getId());
            }
        }).orElse(null);
    }

    public int saveImage(MultipartFile imageFile) throws IOException {
        Images images = new Images();
        images.setName(imageFile.getOriginalFilename());
        images.setType(imageFile.getContentType());
        images.setImages(imageFile.getBytes());

        // Сохраняем объект Images в базе данных
        Images savedImage = imageRepository.save(images);

        // Возвращаем уникальный идентификатор сохраненного объекта Images
        return savedImage.getId();
    }


}
