package com.ducnguyen.sbredis.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class NearestItemsRequest {

    private String profileId;
    private String itemId;
    private String itemType;
}
