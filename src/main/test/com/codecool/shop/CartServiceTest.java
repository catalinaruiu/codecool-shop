package com.codecool.shop;

import com.codecool.shop.dao.implementation.ProductCategoryDaoMem;
import com.codecool.shop.dao.implementation.ProductDaoMem;
import com.codecool.shop.dao.implementation.SupplierDaoMem;
import com.codecool.shop.model.Product;
import com.codecool.shop.model.ProductCategory;
import com.codecool.shop.model.Supplier;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.Mockito;

public class CartServiceTest {
    public ProductCategory mockCategory;
    public Product mockProduct;
    public Supplier mockSupplier;
    // Declare Dao classes:
    public ProductCategoryDaoMem categoryDaoMem;
    public ProductDaoMem productDaoMem;
    public CartDao cartDao;
    public SupplierDaoMem supplierDaoMem;

    public CartService cartService;
    @BeforeEach
    void build() {
        //Mocking classes:
        mockCategory = Mockito.mock(ProductCategory.class);
        mockProduct = Mockito.mock(Product.class);
        mockSupplier = Mockito.mock(Supplier.class);
        // Get instance of containers:
        categoryDaoMem = ProductCategoryDaoMem.getInstance();
        productDaoMem = ProductDaoMem.getInstance();
        supplierDaoMem = SupplierDaoMem.getInstance();
        // Instantiate ProductService:
        cartService= CartService.createInstance(cartDao);
    }

    @AfterEach
    void tearDown() {
        // Removing category from its DaoMem:
        categoryDaoMem.getAll().clear();
        // Remove product(s) from their from DaoMem:
        productDaoMem.getAll().clear();
        // Removing supplier from its DaoMem:
        supplierDaoMem.getAll().clear();
    }

}
