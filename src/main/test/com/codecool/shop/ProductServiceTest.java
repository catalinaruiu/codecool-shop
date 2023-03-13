package com.codecool.shop;
import com.codecool.shop.dao.implementation.ProductCategoryDaoMem;
import com.codecool.shop.dao.implementation.ProductDaoMem;
import com.codecool.shop.dao.implementation.SupplierDaoMem;
import com.codecool.shop.model.Product;
import com.codecool.shop.model.ProductCategory;
import com.codecool.shop.model.Supplier;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


public class ProductServiceTest {
    private int index = 0;
    private int offIndex = 100;
    public ProductService productService;
    public ProductCategory mockCategory;
    public Product mockProduct;
    public Supplier mockSupplier;
    public ProductCategoryDaoMem categoryDaoMem;
    public ProductDaoMem productDaoMem;
    public SupplierDaoMem supplierDaoMem;
    @BeforeEach
    void build() {
        //Mocking classes:
        mockCategory = Mockito.mock(ProductCategory.class);
        mockProduct = Mockito.mock(Product.class);
        mockSupplier = Mockito.mock(Supplier.class);
        categoryDaoMem = ProductCategoryDaoMem.getInstance();
        productDaoMem = ProductDaoMem.getInstance();
        supplierDaoMem = SupplierDaoMem.getInstance();
        productService =ProductService.createInstance(productDaoMem, categoryDaoMem, supplierDaoMem);
    }
    @AfterEach
    void tearDown() {
        categoryDaoMem.getAll().clear();
        productDaoMem.getAll().clear();
        supplierDaoMem.getAll().clear();
    }
    @Test
    void getProductCategory_returnsCategoryWithExistingId() {
        categoryDaoMem.add(mockCategory);
        assertEquals(mockCategory, productService.getProductCategory(index));
    }

    @Test
    void getProductsForCategory_returnsRightProductsWithExistingCategory() {
        categoryDaoMem.add(mockCategory);
        Mockito.when(mockProduct.getProductCategory()).thenReturn(mockCategory);
        productDaoMem.add(mockProduct);
        List<Product> expected = new LinkedList<>();
        expected.add(mockProduct);

        assertEquals(expected, productService.getProductsForCategory(index));
    }


    @Test
    void getAllProducts() {
        productDaoMem.add(mockProduct);
        Product mockProduct2 = Mockito.mock(Product.class);
        productDaoMem.add(mockProduct2);
        Product mockProduct3 = Mockito.mock(Product.class);
        productDaoMem.add(mockProduct3);
        List<Product> expected = new LinkedList<>();
        expected.add(mockProduct);
        expected.add(mockProduct2);
        expected.add(mockProduct3);
        assertEquals(expected, productService.getAllProducts());
    }
    @Test
    void getProductById(){
        productDaoMem.add(mockProduct);
        Mockito.when(mockProduct.getId()).thenReturn(1);
        assertEquals(1,productDaoMem.find(mockProduct.getId()).getId());
    }

}
