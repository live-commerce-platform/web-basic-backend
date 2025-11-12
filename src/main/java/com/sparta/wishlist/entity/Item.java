package com.sparta.wishlist.entity;

import lombok.*;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Item {

    @Setter
    private Long id;
    private String name;
    private Long price;
    private String category;
    private String description;
    private String link;
    private String image;

}