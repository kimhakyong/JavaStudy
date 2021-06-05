

### Java Assert 사용 조건
- 사전 조건(pre-conditions) : 메소드를 호출할때 지켜야 하는 요구사항을 체크합니다.(private 메소드에만 사용합니다.)
- 사후 조건(post-conditions) : 코드를 수행할 결과 확신하는 값을 체크합니다.
- 클래스 불변성(class invariants) : 객체가 반드시 특정 상태에 있다고 확신하는 것을 검증
- public 메소드의 인자에 대해서는 Assertion을 사용하지 않습니다.(누가 어떤 값을 넣을지 확신하지 못함.)