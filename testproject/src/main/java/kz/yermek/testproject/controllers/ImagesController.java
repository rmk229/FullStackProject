package kz.yermek.testproject.controllers;

import kz.yermek.testproject.models.Images;
import kz.yermek.testproject.services.ImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Map;

import static org.springframework.util.MimeTypeUtils.IMAGE_PNG_VALUE;


@RestController
@RequestMapping("/images")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")
public class ImagesController {

    private final ImageService imageService;

    @GetMapping
    public ResponseEntity<Images> getImagesForm() {
        imageService.getImages();
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping(consumes = "multipart/form-data")
    public ResponseEntity<?> uploadImage(@RequestPart("images") MultipartFile file) throws IOException, URISyntaxException {
        Images uploadImage = imageService.uploadImage(file);
//        return ResponseEntity.status(HttpStatus.OK).body(Map.of("id", uploadImage));
        return ResponseEntity.created(new URI("/images/" +
                uploadImage.getId())).body(uploadImage);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> downloadImage(@PathVariable int id) {
        byte[] imageData = imageService.downloadImage(id);
        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.valueOf(IMAGE_PNG_VALUE))
                .body(imageData);
    }
}
