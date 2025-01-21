package com.ll.cafeservice.domain.product.service;

import com.ll.cafeservice.domain.product.NewProduct;
import com.ll.cafeservice.domain.product.Product;
import com.ll.cafeservice.domain.product.dto.request.ProductCreateRequest;
import com.ll.cafeservice.domain.product.dto.request.ProductUpdateRequest;
import com.ll.cafeservice.domain.product.dto.response.ProductCreateResponse;
import com.ll.cafeservice.domain.product.dto.response.ProductInfoResponse;
import com.ll.cafeservice.domain.product.implement.ProductImageManager;
import com.ll.cafeservice.domain.product.implement.ProductManager;
import com.ll.cafeservice.domain.product.implement.ProductReader;
import com.ll.cafeservice.domain.product.mapper.ProductMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.mock.web.MockMultipartFile;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
class ProductServiceTest {
    private ProductService productService;
    @Mock
    private ProductManager productManager;
    @Mock
    private ProductReader productReader;
    @Mock
    private ProductImageManager productImageManager;
    @Mock
    private ProductMapper productMapper;
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this); //Mock 객체 초기화.
        productService = new ProductService(productManager, productReader, productImageManager, productMapper);
    }
    MockMultipartFile mockFile = new MockMultipartFile(
            "file",
            "coffee.jpg",
            "image",
            "Test".getBytes()
    );
    @Test
    @DisplayName("addProduct 데이터 추가, 저장")
    void addProductTest() {
        //MockMultipartFile 생성 및 요청 데이터 정의
        ProductCreateRequest request = new ProductCreateRequest(
                "커피",
                4500,
                "좋은커피",
                100,
                mockFile
        );
        when(productManager.addProduct(any(NewProduct.class))).thenReturn(1L);
        ProductCreateResponse response = productService.addProduct(request);
        ProductCreateResponse expectedResponse = new ProductCreateResponse(1L, "제품이 생성되었습니다.");
        assertThat(response.equals(expectedResponse)).isTrue();
    }
    @Test
    @DisplayName("getList 품목 리스트 반환")
    void getListTest() {
        Product product1 = new Product(
                1L,
                "커피",
                "좋은커피",
                4500,
                100,
                "img.jpg"
        );
        Product product2 = new Product(
                2L,
                "커피2",
                "좋은커피2",
                5000,
                50,
                "img1.jpg"
        );
        //productReader.findAll()이 위의 데이터 반환
        when(productReader.findAllActivateProduct()).thenReturn(List.of(product1, product2));
        List<ProductInfoResponse> response = productService.getList();
        //expectedList 생성
        List<ProductInfoResponse> expectedList = List.of(
                new ProductInfoResponse(1L, "커피", 100, 4500, "좋은커피", "img.jpg"),
                new ProductInfoResponse(2L, "커피2", 50, 5000, "좋은커피2", "img1.jpg")
        );
        //리스트의 각 항목 비교
        for (int i = 0; i < response.size(); i++) {
            assertThat(response.get(i).equals(expectedList.get(i))).isTrue();
        }
    }
    @Test
    @DisplayName("updatedProduct 제품 수정")
    void updatedProductTest() {
        //기존 데이터
        Long productId = 1L;
        Product existingProduct = new Product(
                productId,
                "커피",
                "좋은커피",
                4500,
                100,
                "img.jpg"
        );
        //수정 요청 데이터
        ProductUpdateRequest updateRequest = new ProductUpdateRequest(
                "커피123",
                9999,
                "좋은커피123",
                99,
                mockFile
        );
        //productReader.findById가 기존 product 반환
        when(productReader.findById(productId)).thenReturn(existingProduct);
        Product updatedProduct = new Product(
                productId,
                updateRequest.name(),
                updateRequest.description(),
                updateRequest.price(),
                updateRequest.quantity(),
                existingProduct.getImgFilename() //기존의 것 사용
        );
        Product expectedProduct = new Product(
                productId,
                "커피123",
                "좋은커피123",
                9999,
                99,
                "img.jpg"
        );
        assertThat(updatedProduct.equals(expectedProduct)).isTrue();
    }
}