package com.sparta.wishlist.service;

import com.sparta.wishlist.dto.ItemRequestDto;
import com.sparta.wishlist.dto.ItemResponseDto;
import com.sparta.wishlist.entity.Item;
import com.sparta.wishlist.exception.CustomException;
import com.sparta.wishlist.exception.ErrorCode;
import com.sparta.wishlist.respository.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ItemServiceImpl implements ItemService {

    private final ItemRepository itemRepository;

    /**
     * 새 찜 상품을 생성합니다.
     * DTO를 Entity로 변환하여 Repository에 저장한 후, Response DTO로 변환하여 반환합니다.
     */
    @Override
    public ItemResponseDto saveItem(ItemRequestDto itemRequestDto) throws ExecutionException, InterruptedException {
        // 1. DTO -> 객체(Entity)로 변환
        Item newItem = itemRequestDto.toEntity();

        // 2. Repository에 객체 저장
        Item savedItem = itemRepository.save(newItem);

        // 3. 객체 -> Response DTO로 변환하여 반환
        return new ItemResponseDto(savedItem);
    }

    /**
     * 모든 찜 상품을 조회합니다.
     * 조회된 Entity 리스트를 Response DTO 리스트로 변환하여 반환합니다.
     */
    @Override
    public List<ItemResponseDto> getAllItems() throws ExecutionException, InterruptedException {
        return itemRepository.findAll().stream()
                .map(ItemResponseDto::new) // (item -> new ItemResponseDto(item))
                .collect(Collectors.toList());
    }

    /**
     * ID로 찜 상품 1건을 조회합니다.
     * 상품이 없을 경우 ITEM_NOT_FOUND 예외를 발생시킵니다.
     */
    @Override
    public ItemResponseDto getItemById(Long id) throws ExecutionException, InterruptedException {
        Item item = findItemById(id); // (공통 메서드 사용)
        return new ItemResponseDto(item);
    }

    /**
     * 찜 상품을 수정합니다.
     * 1. 상품이 존재하는지 확인 (비즈니스 로직)
     * 2. 존재하면 DTO를 Entity로 변환 후 ID를 설정하여 저장합니다.
     */
    @Override
    public ItemResponseDto updateItem(Long id, ItemRequestDto itemRequestDto) throws ExecutionException, InterruptedException {
        // 1. (비즈니스 로직) 수정할 대상이 존재하는지 확인 (없으면 예외 발생)
        Item itemToUpdate = findItemById(id);

        // 2. DTO -> 객체(Entity)로 변환
        Item updatedItem = itemRequestDto.toEntity();
        // 3. (중요) ID는 URL의 것을 사용 (덮어쓰기)
        updatedItem.setId(id);

        Item savedItem = itemRepository.save(updatedItem);
        return new ItemResponseDto(savedItem);
    }

    /**
     * 찜 상품을 삭제합니다.
     * 1. 상품이 존재하는지 먼저 확인 (비즈니스 로직)
     * 2. 존재하면 Repository에서 삭제합니다.
     */
    @Override
    public void deleteItem(Long id) throws ExecutionException, InterruptedException {
        // 1. (⭐️ 비즈니스 로직) 삭제할 대상이 존재하는지 확인 (없으면 예외 발생)
        findItemById(id);

        // 2. Repository에서 삭제 시도
        if (!itemRepository.deleteById(id)) {
            // (이중 확인) deleteById가 false를 반환하면 (InMemory의 경우 등)
            throw new CustomException(ErrorCode.ITEM_NOT_FOUND);
        }
    }

    /**
     * [공통 메서드] ID로 아이템을 찾습니다.
     * Repository에서 ID로 조회 후, 결과가 없으면(Optional.empty()) 표준 예외(CustomException)를 발생시킵니다.
     *
     * @param id 찾을 상품의 ID
     * @return 조회된 Item Entity
     * @throws CustomException (ErrorCode.ITEM_NOT_FOUND) 상품이 없을 경우
     */
    private Item findItemById(Long id) throws ExecutionException, InterruptedException {
        return itemRepository.findById(id)
                .orElseThrow(() -> new CustomException(ErrorCode.ITEM_NOT_FOUND));
    }
}