package com.paxaris.usermanagement.tenant.repository;

import com.paxaris.usermanagement.tenant.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author Rohit Mehra
 */
public interface ProductRepository extends JpaRepository<Product, Integer> {
}
