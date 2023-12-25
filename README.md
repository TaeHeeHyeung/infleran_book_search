Practice2B- Retrofit 으로 카카오 책 검색 API 다루기(Retrofit, Moshi, OkHttp, Kapt -> KSP)
Practice2C- Android App Architecture 기반 구축하기 (Repository, ViewModel, Coroutin, LiveData)

- plugin 설치 kotlin to Json

- apiTester: https://resttesttest.com/

- Moshi: kotlin 코드를 Json으로 직렬화, 역 직렬화 해주는 API
- @generateAdapter: JsonObject 대응
- @field:Json(name =[]) JSON 필드명 지정해주기

- secret gradle plugin: APK 같은 코드 외부로 부터 숨기기

Practice2D-검색결과를 UI에 표시하기 (Coil, ListAdapter, SaveStateHandle)

- 안드로이드의 HTTP 통신
- RESTFul
- HttpUrlConnection -> Volley
- OkHttp-> Retrofit
- Ktor : 코틀린에서 클라이언트와 서버를 연결

- Singleton Pattern 기초
- object를 사용하면 기본적으로 singleTone 객체가 생성됨        
  대신 생성자를 넘기기 불가능하여 생성자가 필요할 경우 싱글톤을 다시 작성해줘야함    
  double Checked Locking 에 유의    
  성능이 느려질 수 있기 떄문에 Bill Pugh Solution 사용

- Coroutine 기초
- 메모리 구조의 차이    
  프로그램 코드 -> 프로세스 선점 ->    
  async: 스택 선점, 비동기 -> 병행성 O -> Context Swiching 필요 O   
  coroutine: 힙 선점 (함수형) -> 병행성 X -> Context Swiching 필요 X

- 코루틴 구조
    - Coroutine Scope 코루틴 동작하는 범위
    - Coroutine Context Dispatcher(Default, IO, Main, Unconfined)    
      Job&Deferred (코루틴의 상태, 중지, 실행 관리)

    - Coroutine Builder
      설정 값을 통해 Coroutine 생성
      launch Job 반환 async Deffered 객체 반환
      withContext
    - Dispatcher switch

- 코루틴 지연
  delay, join (launch), await (async)
- 코루틴 취소
    - cancel
    - cancelAndJoin
    - withTimeout
    - withTimeoutOrNull

- 예외처리
    - CoroutineExceptionHandler


- ListAdapter 기초
- AsyncListDiffer, DiffUtils
- ListAdapter, DiffUtils