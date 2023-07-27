package com.example.rental.repository.base;

import com.example.rental.model.base.BaseEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import java.io.Serializable;

@NoRepositoryBean
public interface ShopRepository<T extends BaseEntity<ID>, ID extends Serializable>
        extends JpaRepository<T, ID> {

}
