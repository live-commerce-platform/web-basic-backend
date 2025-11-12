package com.sparta.wishlist.service;

import com.sparta.wishlist.dto.ItemRequestDto;
import com.sparta.wishlist.dto.ItemResponseDto;

import java.util.List;
import java.util.concurrent.ExecutionException;

public interface ItemService {

    /**
     * 요청 DTO를 기반으로 새 상품을 저장합니다.
     *
     * @param itemRequestDto 새 상품 데이터가 담긴 DTO
     * @return 저장된 상품의 Response DTO
     */
    ItemResponseDto saveItem(ItemRequestDto itemRequestDto) throws ExecutionException, InterruptedException;

    /**
     * 모든 상품 목록을 조회합니다.
     *
     * @return 모든 상품의 Response DTO 리스트
     */
    List<ItemResponseDto> getAllItems() throws ExecutionException, InterruptedException;

    /**
     * ID로 특정 상품을 조회합니다.
     * (상품이 없을 경우 CustomException 발생)
     *
     * @param id 조회할 상품의 ID
     * @return 조회된 상품의 Response DTO
     */
    ItemResponseDto getItemById(Long id) throws ExecutionException, InterruptedException;

    /**
     * 기존 상품의 정보를 수정합니다.
     * (상품이 없을 경우 CustomException 발생)
     *
     * @param id             수정할 상품의 ID
     * @param itemRequestDto 수정할 데이터가 담긴 DTO
     * @return 수정된 상품의 Response DTO
     */
    ItemResponseDto updateItem(Long id, ItemRequestDto itemRequestDto) throws ExecutionException, InterruptedException;

    /**
     * ID로 특정 상품을 삭제합니다.
     * (상품이 없을 경우 CustomException 발생)
     *
     * @param id 삭제할 상품의 ID
     */
    void deleteItem(Long id) throws ExecutionException, InterruptedException;
}