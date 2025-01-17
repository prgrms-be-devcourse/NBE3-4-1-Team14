package com.ll.cafeservice.domain.product.service;

import com.ll.cafeservice.domain.product.NewProduct;
import com.ll.cafeservice.domain.product.Product;
import com.ll.cafeservice.domain.product.dto.request.ProductCreateRequest;
import com.ll.cafeservice.domain.product.dto.response.ProductCreateResponse;
import com.ll.cafeservice.domain.product.dto.response.ProductInfoResponse;
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

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this); //Mock 객체 초기화.
        productService = new ProductService(productManager, productReader);
    }

    MockMultipartFile mockFile = new MockMultipartFile(
            "file",
            "coffee.jpg",
            "image",
            "Test".getBytes()
    );


    @Test
    @DisplayName("addProduct 데이터 추가, 저장")
    void addProductTest(){
        //MockMultipartFile 생성 및 요청 데이터 정의

        ProductCreateRequest request = new ProductCreateRequest(
                "커피",
                4500,
                "좋은커피",
                100,
                mockFile);
        when(productManager.addProduct(any(NewProduct.class))).thenReturn(1L);

        ProductCreateResponse response = productService.addProduct(request);

        assertThat(response).isNotNull();
        assertThat(response.id()).isEqualTo(1L);
        assertThat(response.message()).isEqualTo("제품이 생성되었습니다.");
    }

    @Test
    @DisplayName("getList 품목 리스트 반환")
    void getListTest(){
        Product product1 = new Product(
                1L,
                "커피",
                "좋은커피",
                4500,
                100,
        "img.jpg");
        Product product2 = new Product(
                2L,
                "커피2",
                "좋은커피2",
                5000,
                50,
                "img1.jpg");

        //productReader.findAll()이 위의 데이터 반환
        when(productReader.findAll()).thenReturn(List.of(product1, product2));

        List<ProductInfoResponse> response = productService.getList();

        //반환 결과가 목록 포함하는지 확인
        assertThat(response).isNotNull();
        assertThat(response).hasSize(2);

        assertThat(response.get(0).id()).isEqualTo(1L);
        assertThat(response.get(0).name()).isEqualTo("커피");
        assertThat(response.get(0).price()).isEqualTo(4500);

        assertThat(response.get(1).id()).isEqualTo(2L);
        assertThat(response.get(1).name()).isEqualTo("커피2");
        assertThat(response.get(1).price()).isEqualTo(5000);
    }
}


//todo. getList 품목 리스트 반환

//todo. updatedProduct 제품 수정

//todo. deleteProduct 제품 삭제 - 나중에