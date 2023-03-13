package com.codecool.shop.config;


import com.codecool.shop.dao.ProductCategoryDao;
import com.codecool.shop.dao.ProductDao;
import com.codecool.shop.dao.SupplierDao;
import com.codecool.shop.dao.implementation.ProductCategoryDaoMem;
import com.codecool.shop.dao.implementation.ProductDaoMem;
import com.codecool.shop.dao.implementation.SupplierDaoMem;
import com.codecool.shop.model.Product;
import com.codecool.shop.model.ProductCategory;
import com.codecool.shop.model.Supplier;


import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import java.math.BigDecimal;



@WebListener
public class Initializer implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        ProductDao productDataStore = ProductDaoMem.getInstance();
        ProductCategoryDao productCategoryDataStore = ProductCategoryDaoMem.getInstance();
        SupplierDao supplierDataStore = SupplierDaoMem.getInstance();

        //setting up a new supplier
        Supplier amazon = new Supplier("Amazon", "Digital content and services");
        supplierDataStore.add(amazon);
        Supplier lenovo = new Supplier("Lenovo", "Computers");
        supplierDataStore.add(lenovo);
        Supplier apple = new Supplier("Apple", "Computers");
        supplierDataStore.add(apple);

        //setting up a new product category
        ProductCategory tablet = new ProductCategory("Tablet", "Hardware", "A tablet computer, commonly shortened to tablet, is a thin, flat mobile computer with a touchscreen display.");
        productCategoryDataStore.add(tablet);
        ProductCategory phone = new ProductCategory("Phone", "Hardware", "A mobile phone is a portable device for connecting to a telecommunications network in order to transmit and receive voice, video, or other data.");
        productCategoryDataStore.add(phone);
        ProductCategory laptop = new ProductCategory("Laptop", "Hardware", "Laptops are computers designed for portability. They have all the same components and capabilities of traditional desktops, but are smaller and can fold up.");
        productCategoryDataStore.add(laptop);

        //setting up products and printing it
        productDataStore.add(new Product("Amazon Fire", new BigDecimal("49.9"), "EUR", "Fantastic price. Large content ecosystem. Good parental controls. Helpful technical support.", tablet, amazon));
        productDataStore.add(new Product("Lenovo IdeaPad Miix 700", new BigDecimal("479"), "EUR", "Keyboard cover is included. Fanless Core m5 processor. Full-size USB ports. Adjustable kickstand.", tablet, lenovo));
        productDataStore.add(new Product("Amazon Fire HD 8", new BigDecimal("89"), "EUR", "Amazon's latest Fire HD 8 tablet is a great value for media consumption.", tablet, amazon));

        productDataStore.add(new Product("iPhone X", new BigDecimal("1000"), "EUR", "The 11th generation of the iPhone. Super Retina Display, TrueDepth Camera System, Face ID and A11 Bionic Chip with Neural Engine.", phone, apple));
        productDataStore.add(new Product("iPhone 7", new BigDecimal("400"), "EUR", "The 10th generation of iPhone. Water and dust resistance, a new capacitive, static home button, revised antenna bands.", phone, apple));
        productDataStore.add(new Product("iPhone 11", new BigDecimal("700"), "EUR", "The 13th generation of iPhone. Addition of the more powerful Apple A13 Bionic chip as well as an ultra-wide dual-camera system.", phone, apple));

        productDataStore.add(new Product("Macbook Air 2020", new BigDecimal("2000"), "EUR", "A macOS laptop with a 13.30-inch display that has a resolution of 2560x1600 pixels. It is powered by a Core i3 processor and it comes with 8GB of RAM.", laptop, apple));
        productDataStore.add(new Product("Macbook Pro 2019", new BigDecimal("1400"), "EUR", "The 13-inch MacBook Pro is Apple's best ultraportable laptop, thanks to stylish looks, an excellent touchpad, and long battery life.", laptop, apple));
        productDataStore.add(new Product("Macbook 2015", new BigDecimal("600"), "EUR", "The 15-inch MacBook Pro has a brilliant Retina Display, powerful processor and new graphics card options, plus faster flash storage.", laptop, apple));
    }
    @Override
    public void contextDestroyed(ServletContextEvent sce) {
    }
}
