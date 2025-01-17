package com.ll.cafeservice.domain.product.service;

import com.ll.cafeservice.domain.product.NewProduct;
import com.ll.cafeservice.domain.product.dto.request.ProductCreateRequest;
import com.ll.cafeservice.domain.product.dto.response.ProductCreateResponse;
import com.ll.cafeservice.domain.product.implement.ProductManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.mock.web.MockMultipartFile;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class ProductServiceTest {

    private ProductService productService;

    @Mock
    private ProductManager productManager;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this); //Mock 객체 초기화.
        productService = new ProductService(productManager, null);
    }

    @Test
    @DisplayName("addProduct 데이터 추가, 저장")
    void addProductTest(){
        //MockMultipartFile 생성 및 요청 데이터 정의
        MockMultipartFile mockFile = new MockMultipartFile(
                "f",
                "f",
                "f",
                "f".getBytes()
        );
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
}


//todo. getList 품목 리스트 반환

//todo. updatedProduct 제품 수정

//todo. deleteProduct 제품 삭제 - 나중에