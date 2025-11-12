package com.sparta.wishlist.respository;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.QueryDocumentSnapshot;
import com.google.cloud.firestore.QuerySnapshot;
import com.google.firebase.cloud.FirestoreClient;
import com.sparta.wishlist.entity.Item;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutionException;

@Repository
@Profile("database") // "database" 프로필일 때만 이 클래스를 사용
public class DatabaseItemRepository implements ItemRepository {

    private static final String COLLECTION_NAME = "wishlist";

    /**
     * Firestore 인스턴스를 가져오는 헬퍼 메서드
     */
    private Firestore getDb() {
        return FirestoreClient.getFirestore();
    }

    /**
     * 상품을 Firestore에 저장(생성/수정)합니다.
     * (주의: Firestore의 자동 ID는 String이지만, 이 프로젝트는 Long ID를 사용하고 있습니다.)
     * (Item의 ID가 Long이므로, Firestore 문서 ID와 Item의 ID를 매핑하는 방식에 주의가 필요합니다.)
     */
    @Override
    public Item save(Item item) throws ExecutionException, InterruptedException {
        DocumentReference docRef;
        if (item.getId() == null) {
            // [방식 1: Firestore가 ID를 생성하게 함]
            // (이 경우, Firestore의 String ID를 Item의 Long ID 필드에 저장할 방법을 찾아야 함)
            docRef = getDb().collection(COLLECTION_NAME).document();
            // (임시) Firestore의 String ID 해시코드를 Long ID로 사용 (권장 X)
            item.setId(Math.abs((long)docRef.getId().hashCode()));
            docRef.set(item).get();
        } else {
            // [방식 2: Item의 Long ID를 Firestore 문서 ID(String)로 사용]
            docRef = getDb().collection(COLLECTION_NAME).document(String.valueOf(item.getId()));
            docRef.set(item).get();
        }
        return item;
    }

    /**
     * Firestore 'wishlist' 컬렉션의 모든 문서를 조회합니다.
     */
    @Override
    public List<Item> findAll() throws ExecutionException, InterruptedException {
        List<Item> wishlist = new ArrayList<>();
        ApiFuture<QuerySnapshot> future = getDb().collection(COLLECTION_NAME).get();
        List<QueryDocumentSnapshot> documents = future.get().getDocuments();

        for (QueryDocumentSnapshot document : documents) {
            wishlist.add(document.toObject(Item.class));
        }
        return wishlist;
    }

    /**
     * Firestore에서 Long ID (문자열로 변환)를 기준으로 문서를 조회합니다.
     */
    @Override
    public Optional<Item> findById(Long id) throws ExecutionException, InterruptedException {
        DocumentReference docRef = getDb().collection(COLLECTION_NAME).document(String.valueOf(id));
        ApiFuture<DocumentSnapshot> future = docRef.get();
        DocumentSnapshot document = future.get();

        if (document.exists()) {
            return Optional.ofNullable(document.toObject(Item.class));
        } else {
            return Optional.empty();
        }
    }

    /**
     * Firestore에서 Long ID (문자열로 변환)를 기준으로 문서를 삭제합니다.
     * (수정: 삭제할 대상이 *없으면* false 반환)
     */
    @Override
    public boolean deleteById(Long id) throws ExecutionException, InterruptedException {
        // (수정) 삭제할 대상이 없으면 false를 반환 (Service의 404 처리를 위함)
        if (this.findById(id).isEmpty()) {
            return false;
        }
        // 대상이 있으면 삭제
        getDb().collection(COLLECTION_NAME).document(String.valueOf(id)).delete().get();
        return true;
    }
}