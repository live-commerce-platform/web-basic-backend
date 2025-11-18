package com.sparta.wishlist.controller;

import com.sparta.wishlist.dto.ItemRequestDto;
import com.sparta.wishlist.dto.ItemResponseDto;
import com.sparta.wishlist.service.ItemService;
import jakarta.validation.Valid; // (⭐️ @Valid 임포트)
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.ExecutionException;

@RestController
@RequiredArgsConstructor
public class ItemController {

    private final ItemService itemService;

    /**
     * API 엔드포인트 ( /items) : 새 찜 상품 생성
     * 요청 Body(@RequestBody)의 유효성(@Valid)을 검사합니다.
     *
     * @param dto 상품 상세 정보가 담긴 Request DTO
     * @return 201 Created 상태 코드와 함께 생성된 상품 정보를 반환합니다.
     */
    @______("/items")
    public ResponseEntity<ItemResponseDto> saveItem(@Valid @RequestBody ItemRequestDto dto) throws ExecutionException, InterruptedException {
        ItemResponseDto responseDto = itemService.saveItem(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseDto);
    }

    /**
     * API 엔드포인트 ( /items) : 전체 찜 상품 목록 조회
     *
     * @return 200 OK 상태 코드와 함께 전체 상품 목록 리스트를 반환합니다.
     */
    @______("/items")
    public ResponseEntity<List<ItemResponseDto>> getAllItems() throws ExecutionException, InterruptedException {
        List<ItemResponseDto> dtoList = itemService.getAllItems();
        return ResponseEntity.ok(dtoList);
    }

    /**
     * API 엔드포인트 ( /items/{id}) : 기존 찜 상품 수정
     * 요청 Body(@RequestBody)의 유효성(@Valid)을 검사합니다.
     *
     * @param id  수정할 상품의 ID (경로 변수)
     * @param dto 수정할 상품 상세 정보가 담긴 Request DTO
     * @return 200 OK 상태 코드와 함께 수정된 상품 정보를 반환합니다.
     */
    @______("/items/{id}")
    public ResponseEntity<ItemResponseDto> updateItem(
            @PathVariable Long id,
            @Valid @RequestBody ItemRequestDto dto) throws ExecutionException, InterruptedException {

        ItemResponseDto responseDto = itemService.updateItem(id, dto);
        return ResponseEntity.ok(responseDto);
    }

    /**
     * API 엔드포인트 ( /items/{id}) : 찜 상품 삭제
     *
     * @param id 삭제할 상품의 ID (경로 변수)
     * @return 204 No Content 상태 코드를 반환합니다.
     */
    @______("/items/{id}")
    public ResponseEntity<Void> deleteItem(@PathVariable Long id) throws ExecutionException, InterruptedException {
        itemService.deleteItem(id);
        return ResponseEntity.noContent().build();
    }



    /**
     * API 엔드포인트 ( /items/{id}) : 찜 상품 단건 조회
     *
     * @param id 조회할 상품의 ID (경로 변수)
     * @return 200 OK 상태 코드와 함께 해당 상품 정보를 반환합니다.
     */
    @GetMapping("/items/{id}")
    public ResponseEntity<ItemResponseDto> getItemById(@PathVariable Long id) throws ExecutionException, InterruptedException {
        // Service가 예외를 던지므로, Controller는 성공(200 OK)만 처리
        ItemResponseDto responseDto = itemService.getItemById(id);
        return ResponseEntity.ok(responseDto);
    }
}