
멤버 / 프로젝트 / 프로젝트 멤버

세 가지 모델이 있는 간단한 API를 만들어봅니다.


* GET /members/{id} API 응답을 아래 json 처럼 응답하도록 구현 합니다.
  ````
  {
    "id": "1",
    "name": "유재석",
    "age": "50",
    "class": "A",
    "locale": "ko"
  }
  ````
* GET /projects/{code} API 응답을 아래 json 처럼 응답하도록 구현 합니다.
  ````
  {
    "code": "black&white",
    "localDate": "20241011",
    "type": "public"
  }
  ````
* [ ] member,project GET API 조회시 없는 리소스면 에러를 응답하세요. (적절한 HTTP Code - RestControllerAdvice)  
* [ ] 멤버 / 프로젝트 생성
    * [ ] 멤버, 프로젝트는 각각 id, code는 중복을 허용하지 않습니다. (적절한 HTTP Code - RestControllerAdvice)
* [ ] 멤버 / 프로젝트 리스트를 응답하는 API를 만듭니다.
    * [ ] 각 컨트롤러에서는 `Pageable pageable` 을 받아 페이징 처리를 합니다. 해당 페이지에 기본값은 page=0, size = 5이고 max size는 10입니다.
* [ ] 멤버를 프로젝트에 추가 / 프로젝트 멤버 목록을 조회하는 API를 작성합니다. (프로젝트는 여러명에 프로젝트 멤버가 포함되고, 멤버도 여러 프로젝트에 가입이 가능합니다.)
    * [ ] API Path를 고민해보세요. 
* [ ]  멤버 목록 조회 API에서 Accept Header가 text/csv로 들어온 경우 아래 처럼 응답합니다.(기본은 json)
  id,name,class,locale
  1,신건영,A,ko
* [ ] 멤버 생성을 하는 API를 Content-Type: text/csv 를 받을수 있도록 추가 구현 합니다. 
  * 1,신건영,A,ko
* [ ] 멤버 생성시 메신저 알림 보내는 기능을 FeignClient을 이용해서 구현합니다.