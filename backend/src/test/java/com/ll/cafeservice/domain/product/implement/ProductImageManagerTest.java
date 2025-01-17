package com.ll.cafeservice.domain.product.implement;

import com.ll.cafeservice.global.exception.image.ImageStoreException;
import com.ll.cafeservice.global.exception.image.ImageResourceNotFoundException;
import com.ll.cafeservice.global.exception.image.InvalidImageRequestException;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class ProductImageManagerTest {

    private ProductImageManager productImageManager;

    @Mock
    private MultipartFile multipartFile;

    @BeforeEach
    void setUp() {
        productImageManager = new ProductImageManager("src/main/resources/static/image/product/");
    }

    @Nested
    public class ImageStoreTest{

        @Test
        @DisplayName("정상적인 저장")
        void t1() throws IOException {

            String originFilename = "test.png";
            when(multipartFile.isEmpty()).thenReturn(false);
            when(multipartFile.getOriginalFilename()).thenReturn(originFilename);

            String filename = productImageManager.storeProductImage(multipartFile);

            assertNotNull(filename);
            verify(multipartFile).transferTo(any(File.class));
        }

        @Test
        @DisplayName("null을 넘겨주며 저장을 요청하면 예외를 던진다")
        void t2() {
            assertThrows(ImageResourceNotFoundException.class, ()
                    -> productImageManager.storeProductImage(null));
        }

        @Test
        @DisplayName("서버에서 지정한 확장자가 아닌 경우, 예외가 발생한다.")
        void t3() {
            String originFilename = "test.abc";
            when(multipartFile.isEmpty()).thenReturn(false);
            when(multipartFile.getOriginalFilename()).thenReturn(originFilename);

            assertThrows(InvalidImageRequestException.class,
                    () -> productImageManager.storeProductImage(multipartFile));
        }

        @Test
        @DisplayName("파일 저장에 실패해도 예외를 던진다.")
        void t4() throws IOException {

            String originFilename = "test.png";
            when(multipartFile.isEmpty()).thenReturn(false);
            when(multipartFile.getOriginalFilename()).thenReturn(originFilename);
            doThrow(new IOException()).when(multipartFile).transferTo(any(File.class));

            assertThrows(ImageStoreException.class, () -> productImageManager.storeProductImage(multipartFile));
        }
    }

    @Nested
    public class ImageDeleteTest{

        @Mock
        private File mockFile;

        private String path = "src/main/resources/static/image/product/test.png";

        @BeforeEach
        void setUp() throws IOException {
            File testFile = new File(path);
            testFile.createNewFile();

            assertNotNull(testFile);
        }

        @AfterEach
        void tearDown() {
            File testFile = new File(path);
            if (testFile.exists()) {
                testFile.delete();
            }
        }

        @Test
        @DisplayName("정상적인 삭제 요청")
        void t1() throws IOException {
            // given
            String filename = "test.png";

            // when
            productImageManager.deleteProductImageByFilename(filename);
        }

        @Test
        @DisplayName("null이나 빈 문자열을 넣어도 delete를 시도하지 않아 예외가 던져지지 않는다.")
        void t2() {
            productImageManager.deleteProductImageByFilename(null);
            productImageManager.deleteProductImageByFilename("");
        }
    }


    @Nested
    public class ImageLoadTest{

        @Test
        @DisplayName("이미지를 요청 시에 해당 이미지가 존재하는 경우엔 Resource를, 없는 경우엔 예외를 발생시킨다.")
        void t1() throws IOException {
            String filename = "test.png";
            Resource result = productImageManager.getProductImageByFilename(filename);

            // Resource 를 반환하는 방식이기 때문에 실제 해당 Resource 에 파일이 존재하는지는 여기서 테스트할 수 없음.
            // 다만, Resource 가 잘 반환되는지 여부까지만 여기서 확인한다.
            assertAll(
                    () -> assertNotNull(result),
                    () -> assertInstanceOf(UrlResource.class, result)
            );
        }

        @Test
        @DisplayName("null, 빈문자열, 잘못된 확장자로 구성된 파일명을 조회시 예외 발생")
        void t2() {
            assertThrows(InvalidImageRequestException.class,
                    () -> productImageManager.getProductImageByFilename(null));

            assertThrows(InvalidImageRequestException.class,
                    () -> productImageManager.getProductImageByFilename(""));

            assertThrows(InvalidImageRequestException.class,
                    () -> productImageManager.getProductImageByFilename("test.abcd"));
        }
    }

}