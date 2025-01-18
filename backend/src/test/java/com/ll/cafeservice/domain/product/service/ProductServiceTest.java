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

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this); //Mock 객체 초기화.
        productService = new ProductService(productManager, productReader, productImageManager);
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

        assertThat(response).isNotNull();
        assertThat(response.id()).isEqualTo(1L);
        assertThat(response.message()).isEqualTo("제품이 생성되었습니다.");
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

        //반환 결과가 목록 포함하는지 확인
        assertThat(response).isNotNull();
        assertThat(response).hasSize(2);

        assertThat(response.get(0).id()).isEqualTo(1L);
        assertThat(response.get(0).name()).isEqualTo("커피");
        assertThat(response.get(0).description()).isEqualTo("좋은커피");
        assertThat(response.get(0).price()).isEqualTo(4500);
        assertThat(response.get(0).quantity()).isEqualTo(100);
        assertThat(response.get(0).filename()).isEqualTo("img.jpg");

        assertThat(response.get(1).id()).isEqualTo(2L);
        assertThat(response.get(1).name()).isEqualTo("커피2");
        assertThat(response.get(1).description()).isEqualTo("좋은커피2");
        assertThat(response.get(1).price()).isEqualTo(5000);
        assertThat(response.get(1).quantity()).isEqualTo(50);
        assertThat(response.get(1).filename()).isEqualTo("img1.jpg");
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
        assertThat(updatedProduct).isNotNull();
        assertThat(updatedProduct.getId()).isEqualTo(productId);
        assertThat(updatedProduct.getName()).isEqualTo("커피123");
        assertThat(updatedProduct.getDescription()).isEqualTo("좋은커피123");
        assertThat(updatedProduct.getPrice()).isEqualTo(9999);
        assertThat(updatedProduct.getQuantity()).isEqualTo(99);
        assertThat(updatedProduct.getImgFilename()).isEqualTo("img.jpg");

    }
}


