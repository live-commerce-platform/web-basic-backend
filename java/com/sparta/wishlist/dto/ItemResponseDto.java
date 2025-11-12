package com.sparta.wishlist.dto;

import com.sparta.wishlist.entity.Item;
import lombok.Getter;

@Getter
public class ItemResponseDto {
    private Long id;
    private String name;
    private Long price;
    private String category;
    private String description;
    private String link;
    private String image;

    public ItemResponseDto(Item item) {
        this.id = item.getId();
        this.name = item.getName();
        this.price = item.getPrice();
        this.category = item.getCategory();
        this.description = item.getDescription();
        this.link = item.getLink();
        this.image = item.getImage();
    }
}