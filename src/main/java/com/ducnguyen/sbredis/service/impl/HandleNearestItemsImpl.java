package com.ducnguyen.sbredis.service.impl;

import com.ducnguyen.sbredis.dto.NearestItems;
import com.ducnguyen.sbredis.dto.request.NearestItemsRequest;
import com.ducnguyen.sbredis.entity.CacheNearestItems;
import com.ducnguyen.sbredis.repository.NearestItemsRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
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

    public void handleNearestItem(NearestItemsRequest request) throws JsonProcessingException {
        String cachePrefix = "cache-nearest-";
        String key = cachePrefix + request.getProfileId();
        CacheNearestItems nearestCacheFound = nearestItemsRepository.findById(key).orElse(null);

        ObjectMapper objectMapper = new ObjectMapper();
        if (ObjectUtils.isEmpty(nearestCacheFound)) {

            LinkedList<NearestItems> value = new LinkedList<>();
            NearestItems item = new NearestItems();
            item.setItemId(request.getItemId());
            item.setItemType(request.getItemType());
            value.addLast(item);

            String jsonValue = objectMapper.writeValueAsString(value);
            CacheNearestItems cacheNearest = new CacheNearestItems();
            cacheNearest.setKey(key);
            cacheNearest.setValue(jsonValue);
            nearestItemsRepository.save(cacheNearest);

        } else {
            String value = nearestCacheFound.getValue();
            LinkedList<NearestItems> valueQ = objectMapper.readValue(value, new TypeReference<>() {
            });
            if (valueQ.size() > 4) {
                valueQ.removeFirst();
            }

            NearestItems item = new NearestItems();
            item.setItemId(request.getItemId());
            item.setItemType(request.getItemType());
            valueQ.addLast(item);

            String jsonValue = objectMapper.writeValueAsString(valueQ);

            nearestCacheFound.setValue(jsonValue);
            nearestItemsRepository.save(nearestCacheFound);
        }
    }
}
