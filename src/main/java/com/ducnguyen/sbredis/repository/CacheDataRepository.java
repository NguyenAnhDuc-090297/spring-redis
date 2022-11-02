package com.ducnguyen.sbredis.repository;

import com.ducnguyen.sbredis.entity.CacheData;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CacheDataRepository extends CrudRepository<CacheData, String> {
}
