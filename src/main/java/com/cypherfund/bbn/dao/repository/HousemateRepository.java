package com.cypherfund.bbn.dao.repository;

import com.cypherfund.bbn.dao.entity.Housemate;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface HousemateRepository extends JpaRepository<Housemate, Integer> {
    List<Housemate> findHousematesByIdIn(List<Integer> housemateIds);
}
