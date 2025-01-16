package com.ll.cafeservice.domain.product.implement;

import com.ll.cafeservice.global.exception.ResourceNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

/**
 * Product 엔티티의 이미지 정보를 관리하기 위해 사용하는 Implement 클래스
 */

@Component
public class ProductImageManager {

    /**
     * Product Image를 전달받아 디렉토리에 저장한다.
     * @param file Image 파일
     * @return 저장한 디렉토리 url
     */
    public String storeProductImage(MultipartFile file) {

        return "";
    }

    /**
     * URL 경로에 해당하는 파일을 반환합니다.
     * @param url Product Image URL
     * @return Product Image
     */
    public MultipartFile getProductImageByUrl(String url) {

        // TODO : NULL 체크 + 유효한 파일이 있는지 체크
        if(url == null) {
            throw new ResourceNotFoundException("유효한 이미지 경로가 아닙니다.");
        }



        return null;
    }
}
