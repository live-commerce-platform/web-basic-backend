package com.sparta.wishlist.dto;

import com.sparta.wishlist.entity.Item;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ItemRequestDto {

    @NotBlank(message = "상품명은 필수 입력 값입니다.") // 비어있을 수 없음
    private String name;

    @Min(value = 0, message = "가격은 0 이상이어야 합니다.") // 0 이상
    private Long price;
    
    @NotBlank(message = "카테고리는 필수 입력 값입니다.")
    private String category;
    
    private String description;
    private String link;
    private String image;

    public Item toEntity() {
        return Item.builder()
                .name(name)
                .price(price)
                .category(category)
                .description(description)
                .link(link)
                .image(image)
                .build();
    }
}