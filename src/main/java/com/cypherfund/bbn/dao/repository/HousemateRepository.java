package com.cypherfund.bbn.dao.repository;

import com.cypherfund.bbn.dao.entity.Housemate;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HousemateRepository extends JpaRepository<Housemate, Integer> {
}