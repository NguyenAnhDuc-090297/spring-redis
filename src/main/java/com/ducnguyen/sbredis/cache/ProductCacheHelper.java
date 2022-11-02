package com.ducnguyen.sbredis.cache;

import com.ducnguyen.sbredis.dto.request.ProductSearchRequest;

public class ProductCacheHelper {

    public static String buildCacheKey(String keyPrefix, ProductSearchRequest productSearchRequest) {
        StringBuilder builder = new StringBuilder(keyPrefix);

        builder.append("-").append(productSearchRequest.getName().toLowerCase());

        if (productSearchRequest.getCategory() != null) {
            builder.append("-").append(productSearchRequest.getCategory().toLowerCase());
        }
        return builder.toString();
    }

}
