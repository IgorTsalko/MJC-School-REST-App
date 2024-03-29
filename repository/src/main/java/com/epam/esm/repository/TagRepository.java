package com.epam.esm.repository;

import com.epam.esm.common.entity.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public interface TagRepository extends JpaRepository<Tag, Long> {

    Optional<Tag> findByTitle(String title);

    List<Tag> findAllByTitleIn(Iterable<String> titles);

    default List<Tag> saveIfNotExist(List<Tag> tags) {
        return tags.stream()
                .map(tag -> findByTitle(tag.getTitle()).orElseGet(() -> save(tag)))
                .collect(Collectors.toList());
    }

    @Query(nativeQuery = true,
            value = "select t.id, t.title " +
                    "from \"user\" u " +
                    "         join \"order\" o on u.id = o.user_id " +
                    "         join gift_certificate gc on gc.id = o.gift_certificate_id " +
                    "         left join gift_certificate_tag gct on gc.id = gct.gift_certificate_id " +
                    "         left join tag t on t.id = gct.tag_id " +
                    "where u.id = (select user_id from \"order\" group by user_id order by sum(price) desc limit 1) " +
                    "group by t.id " +
                    "order by count(*) desc " +
                    "limit 1")
    Tag findMostUsedTagForUserWithHighestCostOfAllOrders();
}
