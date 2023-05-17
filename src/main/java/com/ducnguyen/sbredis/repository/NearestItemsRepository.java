package com.ducnguyen.sbredis.repository;

import com.ducnguyen.sbredis.entity.CacheNearestItems;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NearestItemsRepository extends CrudRepository<CacheNearestItems, String> {
}
