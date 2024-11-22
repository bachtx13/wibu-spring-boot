package com.bachtx.mangaservice.specifications;

import com.bachtx.wibucommon.enums.EFilterOperation;
import jakarta.persistence.criteria.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.lang.NonNull;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public abstract class BaseFilterSpecification<Entity> implements Specification<Entity> {
    private final List<FilterCriteria> filterCriteriaList = new ArrayList<>();

    public void add(FilterCriteria filterCriteria) {
        this.filterCriteriaList.add(filterCriteria);
    }

    protected abstract boolean relationShipPredicateHandle(FilterCriteria criteria, List<Predicate> predicates, Root<Entity> root, CriteriaQuery<?> query, CriteriaBuilder builder);

    @Override
    public Predicate toPredicate(@NonNull Root<Entity> root, @NonNull CriteriaQuery<?> query, @NonNull CriteriaBuilder builder) {
        List<Predicate> predicates = new ArrayList<>();

        for (FilterCriteria criteria : filterCriteriaList) {
            if (relationShipPredicateHandle(criteria, predicates, root, query, builder)) {
                continue;
            } else {
                _updatePredicates(
                        predicates, builder, criteria.operation, root.get(criteria.getTarget()), criteria.getValue());
            }
        }

        return builder.and(predicates.toArray(new Predicate[0]));
    }

    protected <T> void _updatePredicates(List<Predicate> predicates, CriteriaBuilder builder, EFilterOperation operation, Path<T> expression, Object value) {
        switch (operation) {
            case GREATER_THAN, LESS_THAN, GREATER_THAN_EQUAL, LESS_THAN_EQUAL, MATCH, MATCH_START, MATCH_END: {
                @SuppressWarnings("unchecked")
                Path<String> stringedExpression = (Path<String>) expression;
                String stringedValue = value.toString();
                switch (operation) {
                    case GREATER_THAN:
                        predicates.add(builder.greaterThan(stringedExpression, stringedValue));
                        break;
                    case LESS_THAN:
                        predicates.add(builder.lessThan(stringedExpression, stringedValue));
                        break;
                    case GREATER_THAN_EQUAL:
                        predicates.add(builder.greaterThanOrEqualTo(stringedExpression, stringedValue));
                        break;
                    case LESS_THAN_EQUAL:
                        predicates.add(builder.lessThanOrEqualTo(stringedExpression, stringedValue));
                        break;
                    case MATCH:
                        predicates.add(builder.like(builder.lower(stringedExpression), "%" + stringedValue.toLowerCase() + "%"));
                        break;
                    case MATCH_END:
                        predicates.add(builder.like(builder.lower(stringedExpression), stringedValue.toLowerCase() + "%"));
                        break;
                    case MATCH_START:
                        predicates.add(builder.like(builder.lower(stringedExpression), "%" + stringedValue.toLowerCase()));
                        break;
                }
                break;
            }
            default: {
                @SuppressWarnings("unchecked")
                Path<Boolean> booleanExpression = (Path<Boolean>) expression;
                @SuppressWarnings("unchecked")
                T castedValue = (T) value;

                switch (operation) {
                    case NOT_EQUAL:
                        predicates.add(builder.notEqual(expression, value));
                        break;
                    case EQUAL:
                        predicates.add(builder.equal(expression, value));
                        break;
                    case IN:
                        predicates.add(builder.in(expression).value(castedValue));
                        break;
                    case NOT_IN:
                        predicates.add(builder.not(booleanExpression).in(castedValue));
                        break;
                }
            }
        }
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class FilterCriteria {
        private String target;
        private Object value;
        private EFilterOperation operation;
    }
}
