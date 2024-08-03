package com.cypherfund.bbn.dao.specifications;

import com.cypherfund.bbn.dao.entity.Event;
import com.cypherfund.bbn.dao.specifications.filters.EventFilterCriteria;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class EventSearchSpecification {
    public static Specification<Event> getEventCriteria(EventFilterCriteria filterCriteria) {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (filterCriteria.getTournamentId() != 0) {
                predicates.add(cb.equal(root.get("tournament").get("id"), filterCriteria.getTournamentId()));
            }

            if (filterCriteria.getStatus() != null) {
                predicates.add(cb.equal(root.get("status"), filterCriteria.getStatus()));
            }

            predicates.add(cb.greaterThanOrEqualTo(root.get("eventDate"), java.time.Instant.now()));

            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }
}
