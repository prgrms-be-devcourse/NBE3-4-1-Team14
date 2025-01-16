package com.ll.cafeservice.domain.product.implement;

import com.ll.cafeservice.global.exception.ImageStoreException;
import com.ll.cafeservice.global.exception.ResourceNotFoundException;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

/**
 * Product 엔티티의 이미지 정보를 관리하기 위해 사용하는 Implement 클래스
 */

@Slf4j
@Component
public class ProductImageManager {

    private final String uploadPath;

    public ProductImageManager(
            @Value("${image.file.path}") String path
    ) {
        this.uploadPath = new File(path)
                .getAbsolutePath() + File.separator;
        // 디렉토리 생성
        new File(uploadPath).mkdirs();
    }

    /**
     * URL 경로에 해당하는 파일을 반환합니다.
     *
     * @param filename File name
     * @return Product Image Resource
     */
    public Resource getProductImageByFilename(String filename) {
        try{
            return new UrlResource("file:" + getFullPath(filename));
        } catch (Exception e) {
            throw new ResourceNotFoundException("요청한 이미지가 존재하지 않습니다.");
        }
    }

    /**
     * Product Image 를 전달받아 디렉토리에 저장한다.
     *
     * @param multipartFile Image 파일
     * @return 저장한 파일 이름
     */
    public String storeProductImage(MultipartFile multipartFile) {
        try {
            if (multipartFile == null || multipartFile.isEmpty()) {
                throw new ResourceNotFoundException("파일이 비어있거나 유효하지 않습니다.");
            }

            String originalFilename = multipartFile.getOriginalFilename();
            String storeFileName = createStoreFileName(originalFilename);
            multipartFile.transferTo(new File(getFullPath(storeFileName)));

            return storeFileName;
        } catch (IOException e) {
            throw new ImageStoreException("파일 저장에 실패했습니다.");
        }
    }

    private String getFullPath(String filename) {
        return uploadPath + filename;
    }

    private String createStoreFileName(String originalFilename) {
        String ext = extractExt(originalFilename);
        String uuid = UUID.randomUUID().toString();
        return uuid + "." + ext;
    }

    private String extractExt(String originalFilename) {
        int pos = originalFilename.lastIndexOf(".");
        return originalFilename.substring(pos + 1);
    }
}
