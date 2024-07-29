package com.cypherfund.bbn.dao.specifications;

import com.cypherfund.bbn.dao.entity.Bet;
import com.cypherfund.bbn.dao.specifications.filters.BetFilterCriteria;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

/**
 * Author: E.Ngai
 * Date: 7/29/2024
 * Time: 2:11 PM
 **/
public class BetSearchSpecification {
    public static Specification<Bet> getBetCriteria(BetFilterCriteria filterCriteria) {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (filterCriteria.getUserId() != null && !filterCriteria.getUserId().isEmpty()) {
                predicates.add(cb.equal(root.get("ticket").get("userId"), filterCriteria.getUserId()));
            }

            if (filterCriteria.getStartDate() != null && !filterCriteria.getStartDate().isEmpty()) {
                predicates.add(cb.greaterThanOrEqualTo(root.get("ticket").get("createdAt"), filterCriteria.getStartDate()));
            }

            if (filterCriteria.getEndDate() != null && !filterCriteria.getEndDate().isEmpty() ) {
                predicates.add(cb.lessThanOrEqualTo(root.get("ticket").get("createdAt"), filterCriteria.getEndDate()));
            }

            if (filterCriteria.getJackpotId() != null ) {
                predicates.add(cb.equal(root.get("jackpot").get("id"), filterCriteria.getJackpotId()));
            }

            if (filterCriteria.getStatus() != null) {
                predicates.add(cb.equal(root.get("status"), filterCriteria.getStatus()));
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }
}
