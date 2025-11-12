package com.sparta.wishlist.respository;

import com.sparta.wishlist.entity.Item;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutionException;

public interface ItemRepository {

    /**
     * 상품을 데이터 저장소에 저장 (생성 또는 수정)합니다.
     *
     * @param item 저장할 Item 객체 (ID가 없으면 생성, 있으면 수정)
     * @return 저장된 Item 객체 (새 ID가 포함될 수 있음)
     */
    Item save(Item item) throws ExecutionException, InterruptedException;

    /**
     * 데이터 저장소의 모든 상품을 조회합니다.
     *
     * @return 모든 Item 객체 리스트
     */
    List<Item> findAll() throws ExecutionException, InterruptedException;

    /**
     * ID로 특정 상품을 조회합니다.
     *
     * @param id 조회할 상품의 ID
     * @return Optional<Item> (상품이 있으면 Optional.of(item), 없으면 Optional.empty())
     */
    Optional<Item> findById(Long id) throws ExecutionException, InterruptedException;

    /**
     * ID로 특정 상품을 삭제합니다.
     *
     * @param id 삭제할 상품의 ID
     * @return 삭제에 성공하면 true, 삭제할 대상이 없었으면 false
     */
    boolean deleteById(Long id) throws ExecutionException, InterruptedException;
}