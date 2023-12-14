package kz.yermek.testproject.services;

import kz.yermek.testproject.models.Images;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;


public interface ImageService {
    List<Images> getImages();
    Images getImage(int id);
    Images uploadImage(MultipartFile imageFile) throws IOException;
    byte[] downloadImage(int id);
    int saveImage(MultipartFile imageFile) throws IOException;


}
