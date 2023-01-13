# 게시판 프로젝트

### 기술스택

- Language
    - Java 17
- Library
    - bootstrap
    - summernote
    - hibernate jpa
    - lombok
    - validation
- Framework
    - Spring Boot
- Database
    - Mysql, H2

### 프로젝트 개요

Spring를 공부하면서 처음 만들게 된 프로젝트입니다. 제가 프로젝트를 시작하게 된 계기는 Spring-web-mvc에 익숙해 지기 위해 진행하게 되었습니다. 그래서 프로젝트를 직접 진행하면서 이론공부만 했을 때 보다 좀 더 프레임워크에 대해 익숙해지게 되었고 대략적으로 어떤 방식으로 설계해야하는지에 대한 감을 잡게 되었습니다. 여러가지 프로젝트를 진행할 수 있었지만 그 중에서 게시판을 선택하게 된 이유는 기능이 명확하기 때문에 따로 기획할 필요도 없기도 하고 어떤 기능을 확장시키기가 좋기 때문에 게시판을 선택하게 되었습니다.  

### 프로젝트 회고

프로젝트를 진행하면서 가장 힘들었던 점은 혼자 진행하다보니 코드리뷰를 받지 못해서 이게 맞는 구조인지 좋은 코드를 작성하고 있는지에 대한 의문이 많이 들었던 것 같습니다. 하지만 얻어간 부분도 많다고 생각합니다. 혼자 프로젝트를 진행하게 되니 코드에 대한 의문이 많이 생겨서 작성한 코드에 대해 계속 고민하게되고 지속적으로 리팩토링하게되고 결과적으로 제가 작성한 코드에 대해 많이 고민하게 되서 이런 부분이 많이 좋았던 것 같습니다. 처음 설계할 때는 데이터베이스를 적용하지 않았기 때문에 따로 테이블 설계를 하지않고 기능을 만들면서 정의했어서 나중에 데이터베이스를 도입하게 되면 많이 수정해야하나 라는 우려가 있었는데 다행이도 테이블이 복잡하지않고 테스트코드도 작성했기 때문에 댓글쪽 빼고는 무난하게 적용할 수 있었습니다. 이런 경험을 통해 가장 먼저 해야할 작업이 테이블 설계랑 엔티티 설계라는 것을 알게 되었고 테스트 코드에 대한 중요성을 많이 느꼈습니다. 제가 통과할만한 테스트케이스만 작성해서 엣지 케이스를 잡지못해 버그를 잡는데 오래걸린적인 부분도 있고 기능을 깜빡하고 구현하지 못한 경우도 있어서 추후에 작업할때는 TDD방식을 고려해봐야겠다는 생각을 하게 되었습니다. 

### 기능

- 글 목록
    
    글 목록은 일반적인 페이지네이션으로 구현하였고 제목으로 필터링 할 수 있는 기능도 추가하였습니다. 이 기능을 구현하면서 좀 까다로웠던 부분은 아무래도 페이지네이션에서 하단 인디케이터를 계산하는 부분이었던 것 같습니다. 아래 코드는 인디케이터를 계산하는 로직입니다.
    
    ```java
    @Override
        public List<Long> getPageIndicator(PostSearchRequest request, int pagePostsSize){
            // 인디케이터 리스트
            List<Long> list = new ArrayList<>();
            // 현재 검색조건에 해당하는 모든 게시글의 수를 조회
            Long totalPostsSize = postRepository.getTotalPostsSize(request);
            // 최대 페이지 넘버를 계산
            long maxPageNumber = calculatePageNumber(totalPostsSize/request.getPageSize(),totalPostsSize%request.getPageSize());
            // 현재 페이지의 글 갯수가 요청한 페이지의 갯수보다 작다면
            if(pagePostsSize < request.getPageSize()){
                // 최대 페이지 넘버까지 인디케이터 생성
                addPageIndicator(list,maxPageNumber);
            } else {
                // 현재 페이지 넘버
                int pageNum = request.getPageNum();
                // 남은 글 갯수
                long remainPostCnt = totalPostsSize - (long) pageNum * request.getPageSize();
                // 현재 페이지 기준으로 남은 페이지 갯수
                long remainPageCnt = calculatePageNumber(remainPostCnt/request.getPageSize(),remainPostCnt%request.getPageSize());
                // 인디케이터 중앙 size
                int center = indicatorSize / 2;
                //만약 현재페이지랑 남은 페이지 갯수의 합이 indicator 사이즈보다 크다면
                if(pageNum + remainPageCnt > indicatorSize){
                    // 현재 페이지를 중앙으로 두었을 때를 기준으로 제일 오른쪽 페이지 넘버랑 최대 페이지넘버랑 비교해서 가장 작은 값으로 비교  
                    addPageIndicator(list,Math.max(Math.min(pageNum+center,maxPageNumber),indicatorSize));
                } else{
                    addPageIndicator(list,pageNum+remainPageCnt);
                }
            }
            return list;
        }
    
        private long calculatePageNumber(long pageNumber, long remainPosts){
            // 남은 글이 있을 경우
            if(remainPosts > 0)
                // 페이지 넘버 1 증가
                return pageNumber+1;
            return pageNumber;
        }
    
        private void addPageIndicator(List<Long> list, long pageNum){
            int pageCnt = 0;
            // pageNum이 0보다 크면서 인디케이터 수 만큼 반복
            while(pageNum>0 && pageCnt < indicatorSize){
                list.add(0,pageNum);
                pageNum--;
                pageCnt++;
            }
        }
    ```
    

일단 Indicator 리스트를 선언하고 현재 검색조건에 해당되는 모든 글의 갯수를 조회합니다. 그리고 모든 글의 갯수랑 한페이지에 보여줘야할 글의 갯수를 나눠 최대로 표시할 수 있는 페이지 넘버를 계산하게 됩니다. 이 때 나머지가 생기게 되면 페이지 넘버가 한개가 더 추가되어야 하기 때문에 나머지 여부를 체크해서 한개를 더해주게 됩니다. 그리고 현재 페이지의 글 갯수가 보여줘야 하는 갯수보다 작은 경우 마지막 페이지이기 때문에 최대로 표시할 수 있는 페이지 넘버를 기준으로 인디케이터를 생성해주게 됩니다. 만약 그렇지 않은 경우 현재 페이지 넘버와 남은 페이지 수를 더한게 인디케이터 사이즈 보다 크다면 현재 페이지와 인디케이터 중앙 size를 더해 현재 페이지 넘버를 중앙에 위치할 수 있는지 최대 페이지 넘버랑 비교해서 가장 작은 값을 사용합니다. 그리고 현재 페이지와 인디케이터 중앙 size를 더한 값이 인디케이터보다 작을 수 있기 때문에 인디케이터 사이즈랑 비교해서 가장 큰값을 기준으로 인디케이터를 생성해줍니다.

- 회원가입
    
    회원가입은 딱히 validation 말고는 처리해야할 부분이 크게 없었습니다.
    
- 로그인
    
    interceptor를 통해 로그인이 필요한 페이지에서는 로그인 여부를 확인해서 로그인 하지 않았다면 로그인 화면으로 redirect 시켜주도록 구현하였고 로그인 방식중에 세션 방식을 채택해서 동시 로그인 제한하는 기능도 구현하였습니다. Controller에서 유저정보를 계속 가져와야하는 반복 로직이 있어 HandlerArgumentResolver를 통해 메서드의 인자로 유저정보를 가져올 수 있는 기능을 추가하였습니다.
    
- 글 작성, 수정 , 삭제
- 댓글(대댓글) 작성, 삭제
- 파일 업로드
    
    파일 업로드 기능은 게시글 작성에서 이미지를 넣는 부분이 있는데 이미지를 서버에 저장하기 위해 작업하게 되었고 Restful Api로 구현하였습니다. 그래서 폴더를 따로 지정해서 그 쪽에 저장하도록 로직을 작성하게 되었고 해당 이미지를 접근할 때 static 폴더에 저장을 해 사용자가 브라우저에서 바로 접근할 수 있도록 할 수 있을 것 같았지만 생각보다 많이 복잡할 것 같아 Controller를 통해 이미지를 가져오도록 구현하였습니다.
    
- HttpResponse 처리
    
    일단 해당 관련 처리는 RestApi처리를 위해 작업하게 되었고 기본적인 구조는 아래와 같습니다.
    
    ```java
    @Getter
    @AllArgsConstructor
    @EqualsAndHashCode
    public class HttpResponse<T> {
        private Integer code;
        private String message;
        private T data;
    }
    ```
    

code, message, data 이렇게 구성이 되어있는데 code는 http code이고 message는 서버에서 설정한 메세지이고 data는 보내고자 하는 데이터 입니다. 물론 http response를 클라이언트가 직접 code를 가져올 수 있지만 http code에 따른 메세지를 custom으로 보내고 싶었고 항상 일관적인 형식으로 보내기 때문에 클라이언트에서 처리하기 좋다고 판단되어 이런 구조를 가지게 되었습니다. 일단 메세지 같은경우는 서버에서 직접 정의한 메세지로 컨버팅 하기 위해 아래와 같이 작성하게 되었습니다.

```java
@RequiredArgsConstructor
@Component
public class HttpResponseMessageResolver {
    private final MessageSource messageSource;

    public String resolve(HttpStatus httpStatus,String...detailCodes){
        String code = httpStatus.name().toLowerCase();
        StringBuilder sb = new StringBuilder();
        sb.append("http").append(".").append(code);
        Arrays.stream(detailCodes).forEach(detailCode -> sb.append(".").append(detailCode));
        return messageSource.getMessage(sb.toString(), null, "Http Error", Locale.getDefault());
    }
}
```

httpStatus와 detailCodes(좀 더 디테일한 메세지를 설정하기 위해)를 인자로 받아 아래와 같이 properties 설정한 메세지 코드랑 매핑을 시켜주도록 하였습니다.

```java
http.ok=요청이 성공했습니다.
http.ok.post=글에 대한 요청이 성공했습니다.
http.not_modified=수정 되지 않았습니다.
http.bad_request=잘못된 요청 입니다.
http.unauthorized=인증 되지 않았습니다.
http.not_found=요청 URL을 찾을 수 업습니다.
http.internal_server_error=서버 내부에서 오류가 발생했습니다.
```

결과적으로 아래와 같은 매퍼를 통해 HttpResponse를 반환하게 됩니다.

```java
@RequiredArgsConstructor
@Component
public class HttpResponseMapper {
    private final HttpResponseMessageResolver httpResponseMessageResolver;

    public <T> HttpResponse<T> toHttpResponse(HttpStatus httpStatus, T data){
        int code = httpStatus.value();
        String message = httpResponseMessageResolver.resolve(httpStatus);
        return new HttpResponse<>(code,message,data);
    }

}
```

### ERD
![%E1%84%89%E1%85%B3%E1%84%8F%E1%85%B3%E1%84%85%E1%85%B5%E1%86%AB%E1%84%89%E1%85%A3%E1%86%BA_2023-01-12_%E1%84%8B%E1%85%A9%E1%84%92%E1%85%AE_11 34 55](https://user-images.githubusercontent.com/45057493/212216727-c5caf9ca-37e4-492f-ab1d-1dc0e8a2d1b8.png)
https://www.erdcloud.com/d/mpAgyJvAkA5vX5rGN
