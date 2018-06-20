package com.vciobanu.filteringmatches.model;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import java.util.LinkedList;
import java.util.List;

import javax.persistence.criteria.Path;
import javax.persistence.criteria.Root;

@Component
public class MatchSpecificationsBuilder {

    public Specification<Match> build(MatchSearchFields matchSearchFields) {
        if (matchSearchFields == null) {
            return null;
        }

        LinkedList<Specification<Match>> specs = new LinkedList<>();

        buildHasPhotoSpec(matchSearchFields, specs);
        buildIsFavouriteSpec(matchSearchFields, specs);
        buildInContactSpec(matchSearchFields, specs);
        buildRangeSpec(SearchField.AGE, matchSearchFields.getMinAge(), matchSearchFields.getMaxAge(), specs);
        buildRangeSpec(SearchField.HEIGHT_IN_CM,
                matchSearchFields.getMinHeightInCm(), matchSearchFields.getMaxHeightInCm(), specs);
        buildCompatibilityScoreSpec(matchSearchFields, specs);

        if (specs.size() == 0) {
            return null;
        }

        Specification<Match> specificationCriteria = Specification.where(specs.pop());

        for (Specification<Match> spec : specs) {
            specificationCriteria = specificationCriteria.and(spec);
        }

        return specificationCriteria;
    }

    private void buildHasPhotoSpec(MatchSearchFields matchSearchFields, List<Specification<Match>> specs) {
        if (matchSearchFields.getHasPhoto() == null) {
            return;
        }

        Specification<Match> spec = matchSearchFields.getHasPhoto()
                ? (root, query, criteriaBuilder) ->
                criteriaBuilder.isNotNull(getFieldPath(root, SearchField.MAIN_PHOTO))
                : (root, query, criteriaBuilder) ->
                criteriaBuilder.isNull(getFieldPath(root, SearchField.MAIN_PHOTO));

        specs.add(spec);
    }

    private void buildIsFavouriteSpec(MatchSearchFields matchSearchFields, List<Specification<Match>> specs) {
        if (matchSearchFields.getIsFavourite() == null) {
            return;
        }

        Specification<Match> spec = matchSearchFields.getIsFavourite()
                ? (root, query, criteriaBuilder) -> criteriaBuilder.isTrue(getFieldPath(root, SearchField.FAVOURITE))
                : (root, query, criteriaBuilder) -> criteriaBuilder.isFalse(getFieldPath(root, SearchField.FAVOURITE));

        specs.add(spec);
    }

    private void buildInContactSpec(MatchSearchFields matchSearchFields, List<Specification<Match>> specs) {
        if (matchSearchFields.getInContact() == null) {
            return;
        }

        Specification<Match> spec = matchSearchFields.getInContact()
                ? (root, query, criteriaBuilder) ->
                criteriaBuilder.gt(getFieldPath(root, SearchField.CONTACTS_EXCHANGED), 0)
                : (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(getFieldPath(root, SearchField.CONTACTS_EXCHANGED), 0);

        specs.add(spec);
    }

    private void buildRangeSpec(
            SearchField searchField, Integer minLimit, Integer maxLimit, List<Specification<Match>> specs) {
        if (minLimit == null && maxLimit == null) {
            return;
        }

        Specification<Match> spec;

        if (minLimit != null && maxLimit != null) {
            spec = (root, query, criteriaBuilder)
                    -> criteriaBuilder.between(getFieldPath(root, searchField), minLimit, maxLimit);
        } else {
            spec = buildMinOrMaxSpec(searchField, minLimit, maxLimit);
        }

        specs.add(spec);
    }

    private void buildCompatibilityScoreSpec(MatchSearchFields matchSearchFields, List<Specification<Match>> specs) {
        Double minLimit = matchSearchFields.getMinCompatibilityScoreInPercentage() == null
                ? null : (double) matchSearchFields.getMinCompatibilityScoreInPercentage() / 100;
        Double maxLimit = matchSearchFields.getMaxCompatibilityScoreInPercentage() == null
                ? null : (double) matchSearchFields.getMaxCompatibilityScoreInPercentage() / 100;

        if (minLimit == null && maxLimit == null) {
            return;
        }

        Specification<Match> spec;

        if (minLimit != null && maxLimit != null) {
            spec = (root, query, criteriaBuilder)
                    -> criteriaBuilder.between(getFieldPath(root, SearchField.COMPATIBILITY_SCORE), minLimit, maxLimit);
        } else {
            spec = buildMinOrMaxSpec(SearchField.COMPATIBILITY_SCORE, minLimit, maxLimit);
        }

        specs.add(spec);
    }

    private Specification<Match> buildMinOrMaxSpec(SearchField searchField, Number minLimit, Number maxLimit) {
        if (minLimit != null) {
            return (root, query, criteriaBuilder)
                    -> criteriaBuilder.ge(getFieldPath(root, searchField), minLimit);
        }

        return (root, query, criteriaBuilder)
                -> criteriaBuilder.le(getFieldPath(root, searchField), maxLimit);
    }

    private <Y> Path<Y> getFieldPath(Root<Match> root, SearchField field) {
        return root.get(field.getName());
    }


    private enum SearchField {
        MAIN_PHOTO("mainPhoto"),
        FAVOURITE("favourite"),
        CONTACTS_EXCHANGED("contactsExchanged"),
        AGE("age"),
        HEIGHT_IN_CM("heightInCm"),
        COMPATIBILITY_SCORE("compatibilityScore");

        private String name;

        SearchField(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }
    }
}
