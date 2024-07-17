package com.paxaris.usermanagement.tenant.service;

import com.paxaris.usermanagement.tenant.entity.Product;
import com.paxaris.usermanagement.tenant.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Rohit Mehra
 */
@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    ProductRepository productRepository;

    @Override
    public List<Product> getAllProduct() {
        return productRepository.findAll();
    }
}
