package com.bachtx.mangaservice.specifications;

import com.bachtx.mangaservice.entities.Genre;
import com.bachtx.mangaservice.entities.Manga;
import jakarta.persistence.criteria.*;

import java.util.List;
import java.util.UUID;

public class MangaFilterSpecification extends BaseFilterSpecification<Manga> {
    @Override
    protected boolean relationShipPredicateHandle(FilterCriteria criteria, List<Predicate> predicates, Root<Manga> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
        if (criteria.getTarget().equals("genres")) {
            Join<Manga, Genre> genreJoin = root.join("genres");
            @SuppressWarnings("unchecked")
            List<UUID> ids = ((List<String>) criteria.getValue())
                    .stream()
                    .map(UUID::fromString)
                    .toList();
            _updatePredicates(predicates, builder, criteria.getOperation(), genreJoin.get("id"), ids);
            return true;
        }
        return false;
    }
}
