package com.ducnguyen.sbredis.service.impl;

import com.ducnguyen.sbredis.dto.NearestItems;
import com.ducnguyen.sbredis.dto.request.NearestItemsRequest;
import com.ducnguyen.sbredis.entity.CacheNearestItems;
import com.ducnguyen.sbredis.repository.NearestItemsRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.LinkedList;

@Service
@Slf4j
public class HandleNearestItemsImpl {

    private final NearestItemsRepository nearestItemsRepository;

    public HandleNearestItemsImpl(NearestItemsRepository nearestItemsRepository) {
        this.nearestItemsRepository = nearestItemsRepository;
    }

    public void handleNearestItem(NearestItemsRequest request) {
        String cachePrefix = "cache-nearest-";
        String key = cachePrefix + request.getProfileId();
        CacheNearestItems nearestCacheFound = nearestItemsRepository.findById(key).orElse(null);
        if (ObjectUtils.isEmpty(nearestCacheFound)) {
            LinkedList<NearestItems> value = new LinkedList<>();
            NearestItems item = new NearestItems();
            item.setItemId(request.getItemId());
            item.setItemType(request.getItemType());
            value.addFirst(item);

            CacheNearestItems cacheNearest = new CacheNearestItems();
            cacheNearest.setKey(key);
            cacheNearest.setValue(value);
            nearestItemsRepository.save(cacheNearest);
        } else {
            LinkedList<NearestItems> value = nearestCacheFound.getValue();
            if (value.size() > 4) {
                value.removeLast();
            }
            NearestItems item = new NearestItems();
            item.setItemId(request.getItemId());
            item.setItemType(request.getItemType());
            value.addFirst(item);

            nearestCacheFound.setValue(value);
            nearestItemsRepository.save(nearestCacheFound);
        }
    }
}
