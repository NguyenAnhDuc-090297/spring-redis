package com.ducnguyen.sbredis.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.index.Indexed;

@Getter
@Setter
@RedisHash("nearestItems")
@AllArgsConstructor
@NoArgsConstructor
public class CacheNearestItems {

    @Id
    private String key;

    @Indexed
    private String value;
}
