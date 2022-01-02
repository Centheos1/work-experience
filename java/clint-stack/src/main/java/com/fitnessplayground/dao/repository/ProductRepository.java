package com.fitnessplayground.dao.repository;

import com.fitnessplayground.dao.domain.MboProduct;
import org.springframework.data.repository.CrudRepository;

public interface ProductRepository extends CrudRepository<MboProduct, Long> {


}
