package com.ducnguyen.sbredis.controller;

import com.ducnguyen.sbredis.dto.request.NearestItemsRequest;
import com.ducnguyen.sbredis.service.impl.HandleNearestItemsImpl;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class HomeController {

    private final HandleNearestItemsImpl handleNearestItems;

    public HomeController(HandleNearestItemsImpl handleNearestItems) {
        this.handleNearestItems = handleNearestItems;
    }

    @PostMapping("/cache-nearest")
    public void addCache(@RequestBody NearestItemsRequest request) {
        handleNearestItems.handleNearestItem(request);
    }
}
