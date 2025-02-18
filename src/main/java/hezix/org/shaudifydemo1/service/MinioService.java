package hezix.org.shaudifydemo1.service;

import hezix.org.shaudifydemo1.entity.song.SongFile;
import hezix.org.shaudifydemo1.exception.UploadFileException;
import hezix.org.shaudifydemo1.props.MinioProperties;
import io.minio.*;
import io.minio.http.Method;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class MinioService {

    private final MinioClient minioClient;
    private final MinioProperties minioProperties;

    public String uploadImage(SongFile file) {
        try {
            createBucket(minioProperties.getImageBucket());
        } catch (Exception e) {
            throw new UploadFileException("Image upload failed, error: " + e.getMessage());
        }
        MultipartFile multipartFile = file.getImage();
//        if (file.isEmpty() || file.getOriginalFilename() == null) {
//            throw new UploadFileException("Image upload failed, image must have name.");
//        }
        String fileName = generateFileName(multipartFile);
        InputStream inputStream;
        try {
            inputStream = multipartFile.getInputStream();
        } catch (Exception e) {
            throw new UploadFileException("Image upload failed, error: " + e.getMessage());
        }
        saveImage(inputStream, fileName);
        return fileName;
    }
    public String uploadSong(SongFile file) {
        try {
            createBucket(minioProperties.getSongBucket());
        } catch (Exception e) {
            throw new UploadFileException("Image upload failed, error: " + e.getMessage());
        }
        MultipartFile multipartFile = file.getSong();
//        if (file.isEmpty() || file.getOriginalFilename() == null) {
//            throw new UploadFileException("Image upload failed, image must have name.");
//        }
        String fileName = generateFileName(multipartFile);
        InputStream inputStream;
        try {
            inputStream = multipartFile.getInputStream();
        } catch (Exception e) {
            throw new UploadFileException("Image upload failed, error: " + e.getMessage());
        }
        saveSong(inputStream, fileName);
        return fileName;
    }

    @SneakyThrows
    private void saveImage(InputStream inputStream, String fileName) {
        minioClient.putObject(PutObjectArgs.builder()
                .stream(inputStream, inputStream.available(), -1)
                .bucket(minioProperties.getImageBucket())
                .object(fileName)
                .build());
    }
    @SneakyThrows
    private void saveSong(InputStream inputStream, String fileName) {
        minioClient.putObject(PutObjectArgs.builder()
                .stream(inputStream, inputStream.available(), -1)
                .bucket(minioProperties.getSongBucket())
                .object(fileName)
                .build());
    }

    private String generateFileName(MultipartFile file) {
        String extension = getExtension(file);
        return UUID.randomUUID().toString() + "." + extension;
    }

    private String getExtension(MultipartFile file) {
        return file.getOriginalFilename()
                .substring(file.getOriginalFilename().lastIndexOf(".") + 1);
    }

    @SneakyThrows
    private void createBucket(String bucketName) {
        boolean found = minioClient.bucketExists(BucketExistsArgs.builder()
                .bucket(bucketName)
                .build());
        if (!found) {
            minioClient.makeBucket(MakeBucketArgs.builder()
                    .bucket(bucketName)
                    .build());
        }
    }
    @SneakyThrows
    public String getPresignedUrl(String bucket, String filename){
        return minioClient.getPresignedObjectUrl(
                GetPresignedObjectUrlArgs.builder()
                        .method(Method.GET)
                        .bucket(bucket)
                        .object(filename)
                        .expiry(7, TimeUnit.DAYS)
                        .build()
        );
    }
}
