# *JPA (Java Persistence API)*

## Description
*** 
- 해당 프로젝트는 JPA 를 사용하면서 헷갈렸던 부분을 정확히 이해하고자 만들게 된 프로젝트입니다.
- 테스트를 하기 위한 구현 코드와 테스트 코드로 이루어져 있습니다.
- 해당 개념들의 대한 내용 정리는 아래 목차별로 정리되어 있습니다.

## Spec
*** 
- **SpringBoot 2.5.4**
- **Kotlin 1.6.21**
- **JAVA 11**
- **Gradle 7.5.1**
- **Build: Kotlin Gradle(kts)**

## 목차
***
1. 일반 Join 과 Fetch Join 차이점 (package - join_fetchjoin_compare)

***
### 1. 일반 Join 과 Fetch Join 차이점
- 일반 join 과 fetchJoin 둘의 정확한 차이점과 사용법을 정리합니다.
- JPA 사용 시 N+1 문제를 해결하기 위한 방안으로 Fetch Join을 사용하게 되는데 정확히 어떤 원리에 의해서 fetchJoin을 사용하는지 그렇다면 일반 Join은 언제 사용해야 되는지에 대해서 정리합니다.
#### Join
- 연관 entity에 Join을 걸어도 실제 쿼리에서 SELECT하는 Entity는 오직 JPQL에서 조회하는 주체가 되는 entity만 조회하여 영속화
- 조회의 주체가 되는 entity만 SELECT해서 영속화하기 때문에 데이터는 필요하지 않지만 연관 entity가 검색조건에는 필요한 경우에 주로 사용
#### Fetch Join
- 조회의 주체가 되는 entity 이외에 Fetch Join이 걸린 연관 Entity 도 함께 SELECT 하여 모두 영속화
- Fetch Join이 걸린 entity 모두 영속화하기 때문에 FetchType이 Lazy인 Entity를 참조하더라도 이미 영속성 컨텍스트에 들어있기 때문에 따로 쿼리가 실행되지 않은 채로 N+1 문제가 해결되는 것 
