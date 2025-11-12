<div align="center">

# Web Basic - My Wishlist (Backend)
웹 개발 기초 입문자를 위한 학습용 Spring 백엔드 프로젝트입니다. 찜 목록 API 서버를 만들며 Spring Boot와 REST API의 기초를 익힙니다.

</div>

## 소개

- **목표**: Spring Boot 기반으로 '진짜' API 서버를 만들고, 프론트엔드와 통신하는 과정을 실습합니다.
- **구성**: `controller`(API 진입점), `service`(비즈니스 로직), `repository`(데이터 접근)로 역할을 명확히 분리합니다.

## 시작하기

사전 준비: [Java 17 (JDK)](https://www.oracle.com/java/technologies/downloads/#java17)와 [IntelliJ](https://www.jetbrains.com/idea/download/) (Community 버전도 가능)가 설치되어 있어야 합니다. 이 저장소는 Gradle을 기본으로 사용합니다.

```bash
# 1. GitHub에서 ZIP 파일 다운로드 및 압축 해제
# 2. IntelliJ에서 'Open'을 클릭하여 해당 폴더를 엽니다.
# 3. IntelliJ가 Gradle 의존성을 자동으로 설치합니다. (1-3분 소요)

# 개발 서버 실행
# src/main/java/.../WishlistApplication.java 파일에서 Run(▶️) 버튼 클릭

# 개발 서버 중지
# IntelliJ 하단 Run 탭에서 Stop(⏹️) 버튼 클릭
실습 문제 (빈칸 채우기 1문제)
아래 문제는 파일 위치와 목적을 확인하고, 빈칸을 채운 뒤 직접 실행해 정답을 검증합니다.

문제 1: Controller에서 API 엔드포인트 매핑하기
목표: API 명세서에 맞게 '주소'와 '행동(Method)'을 매핑하는 어노테이션을 채웁니다.

파일: src/main/java/com/sparta/wishlist/controller/ItemController.java

힌트: REST API에서 데이터를 생성(Create) / 조회(Read)하는 HTTP Method와 Spring 어노테이션은 무엇이었나요?

@RestController
@RequiredArgsConstructor
public class ItemController {

    private final ItemService itemService;

    // 1. 찜 상품 추가 (Create)
    // 힌트: POST /items 요청을 매핑
    @_____("/items")
    public ResponseEntity<ItemResponseDto> addWishlistItem(@Valid @RequestBody ItemRequestDto dto) {
        // ...
    }

    // 2. 전체 찜 상품 조회 (Read)
    // 힌트: GET /items 요청을 매핑
    @_____("/items")
    public ResponseEntity<List<ItemResponseDto>> getAllWishlistItems() {
        // ...
    }
    
    // ... (이하 PUT, DELETE, GET(id) 등) ...
}
```
## 테스트 방법
1. IntelliJ에서 WishlistApplication.java의 Run(▶️) 버튼을 눌러 개발 서버를 실행합니다.
2. Postman을 엽니다.
3. baseURL을 http://localhost:8080으로 설정합니다.
4. Postman으로 POST /items, GET /items, DELETE /items/{id} 요청을 보내 서버가 정상 동작하는지 테스트합니다.

## 개발자 도구 활용하기
- 브라우저의 F12(개발자 도구)가 프론트엔드의 console.log를 보여줬다면, IntelliJ의 Run 탭은 백엔드 서버의 로그를 보여줍니다.
- API 요청이 들어올 때마다 Spring 서버의 로그가 출력되는 것을 확인하세요.
- 만약 서버가 멈추거나 500 Internal Server Error가 발생하면, 이 Run 탭에 빨간색으로 표시되는 에러 로그를 보고 원인을 찾아야 합니다.

## 체크리스트
- [ ] IntelliJ에서 Spring 서버가 Tomcat started on port(s): 8080 로그와 함께 정상 실행된다.
- [ ] memo 프로필로 실행 후, Postman으로 POST -> GET -> DELETE -> GET 요청이 의도대로 동작한다.
- [ ] firebase 프로필로 전환 후, Postman으로 POST -> Stop(서버 중지) -> Run(서버 재시작) -> GET을 했을 때 데이터가 사라지지 않고 조회된다.
- [ ] 문제 1, 2를 완료하고 3-3 테스트까지 통과했다.

라이선스
이 프로젝트는 LICENSE 파일의 내용을 따릅니다.