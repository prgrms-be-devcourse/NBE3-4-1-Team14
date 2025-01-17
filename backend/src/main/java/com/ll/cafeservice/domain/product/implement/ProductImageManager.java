package com.ll.cafeservice.domain.product.implement;

import com.ll.cafeservice.global.exception.image.ImageStoreException;
import com.ll.cafeservice.global.exception.image.ImageResourceNotFoundException;
import com.ll.cafeservice.global.exception.image.InvalidImageRequestException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
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
     * filename 으로 null 이나 빈 문자열, 잘못된 확장자가 들어오는 경우는 Resource 탐색로직 전에 예외를 반환합니다.
     * -> 불필요한 IO 바운드를 줄이기 위해 수정
     *
     * @param filename File name
     * @return Product Image Resource
     */
    public Resource getProductImageByFilename(String filename) {
        if(filename == null || filename.isEmpty() || checkInvalidExt(filename)) {
            throw new InvalidImageRequestException("Invalid Type Filename");
        }

        try{
            return new UrlResource("file:" + getFullPath(filename));
        } catch (Exception e) {
            throw new ImageResourceNotFoundException("요청한 이미지가 존재하지 않습니다.");
        }
    }

    public void deleteProductImageByFilename(String filename) {
        if(filename == null || filename.isEmpty() || checkInvalidExt(filename)){
            return;
        }

        String fullPath = getFullPath(filename);
        File file = new File(fullPath);
        if(!file.delete()){
            log.error("해당 파일이 존재하지 않거나, 실패했습니다. Filename : {}", filename);
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
                throw new ImageResourceNotFoundException("파일이 비어있거나 유효하지 않습니다.");
            }

            String originalFilename = multipartFile.getOriginalFilename();
            String storeFileName = createStoreFileName(originalFilename);
            if(checkInvalidExt(originalFilename)){
                throw new InvalidImageRequestException("File ext is invalid");
            }

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

    private boolean checkInvalidExt(String originalFilename) {
        String ext = extractExt(originalFilename);
        return !ext.equals("png") && !ext.equals("jpg") && !ext.equals("jpeg");
    }
}
