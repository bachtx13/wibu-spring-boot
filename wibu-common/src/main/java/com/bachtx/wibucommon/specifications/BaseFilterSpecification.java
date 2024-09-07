package com.bachtx.wibucommon.specifications;

import com.bachtx.wibucommon.criteria.FilterCriteria;
import com.bachtx.wibucommon.enums.EFilterOperation;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.lang.NonNull;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class BaseFilterSpecification<Entity> implements Specification<Entity> {
    private final List<FilterCriteria> filterCriteriaList = new ArrayList<>();

    public void add(FilterCriteria filterCriteria) {
        this.filterCriteriaList.add(filterCriteria);
    }

    @Override
    public Predicate toPredicate(@NonNull Root<Entity> root, @NonNull CriteriaQuery<?> query, @NonNull CriteriaBuilder builder) {
        List<Predicate> predicates = new ArrayList<>();

        //add criteria to predicate
        for (FilterCriteria criteria : filterCriteriaList) {
            if (criteria.getOperation().equals(EFilterOperation.GREATER_THAN)) {
                predicates.add(builder.greaterThan(
                        root.get(criteria.getTarget()), criteria.getValue().toString()));
            } else if (criteria.getOperation().equals(EFilterOperation.LESS_THAN)) {
                predicates.add(builder.lessThan(
                        root.get(criteria.getTarget()), criteria.getValue().toString()));
            } else if (criteria.getOperation().equals(EFilterOperation.GREATER_THAN_EQUAL)) {
                predicates.add(builder.greaterThanOrEqualTo(
                        root.get(criteria.getTarget()), criteria.getValue().toString()));
            } else if (criteria.getOperation().equals(EFilterOperation.LESS_THAN_EQUAL)) {
                predicates.add(builder.lessThanOrEqualTo(
                        root.get(criteria.getTarget()), criteria.getValue().toString()));
            } else if (criteria.getOperation().equals(EFilterOperation.NOT_EQUAL)) {
                predicates.add(builder.notEqual(
                        root.get(criteria.getTarget()), criteria.getValue()));
            } else if (criteria.getOperation().equals(EFilterOperation.EQUAL)) {
                predicates.add(builder.equal(
                        root.get(criteria.getTarget()), criteria.getValue()));
            } else if (criteria.getOperation().equals(EFilterOperation.MATCH)) {
                predicates.add(builder.like(
                        builder.lower(root.get(criteria.getTarget())),
                        "%" + criteria.getValue().toString().toLowerCase() + "%"));
            } else if (criteria.getOperation().equals(EFilterOperation.MATCH_END)) {
                predicates.add(builder.like(
                        builder.lower(root.get(criteria.getTarget())),
                        criteria.getValue().toString().toLowerCase() + "%"));
            } else if (criteria.getOperation().equals(EFilterOperation.MATCH_START)) {
                predicates.add(builder.like(
                        builder.lower(root.get(criteria.getTarget())),
                        "%" + criteria.getValue().toString().toLowerCase()));
            } else if (criteria.getOperation().equals(EFilterOperation.IN)) {
                predicates.add(builder.in(root.get(criteria.getTarget())).value(criteria.getValue()));
            } else if (criteria.getOperation().equals(EFilterOperation.NOT_IN)) {
                predicates.add(builder.not(root.get(criteria.getTarget())).in(criteria.getValue()));
            }
        }

        return builder.and(predicates.toArray(new Predicate[0]));
    }
}
