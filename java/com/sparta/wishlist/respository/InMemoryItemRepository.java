package com.sparta.wishlist.respository;

import com.sparta.wishlist.entity.Item;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Repository
@Profile("memory") // "memo" 프로필일 때만 이 클래스를 사용
@RequiredArgsConstructor
public class InMemoryItemRepository implements ItemRepository {

    // (DB 대신 사용하는 임시 메모리 저장소)
    private final Map<Long, Item> database = new ConcurrentHashMap<>();
    // (ID 자동 생성을 위한 카운터)
    private final AtomicLong idCounter = new AtomicLong();

    /**
     * 상품을 메모리 맵에 저장(생성/수정)합니다.
     * ID가 없으면 새로 생성하여 저장합니다.
     */
    @Override
    public Item save(Item item) {
        if (item.getId() == null) {
            item.setId(idCounter.incrementAndGet());
        }
        database.put(item.getId(), item);
        return item;
    }

    /**
     * 메모리 맵의 모든 상품을 리스트로 반환합니다.
     */
    @Override
    public List<Item> findAll() {
        return List.copyOf(database.values());
    }

    /**
     * 메모리 맵에서 ID로 상품을 찾아 Optional로 반환합니다.
     */
    @Override
    public Optional<Item> findById(Long id) {
        return Optional.ofNullable(database.get(id));
    }

    /**
     * 메모리 맵에서 ID로 상품을 삭제합니다.
     * 삭제 성공 시 true를 반환합니다.
     */
    @Override
    public boolean deleteById(Long id) {
        return database.remove(id) != null;
    }
}