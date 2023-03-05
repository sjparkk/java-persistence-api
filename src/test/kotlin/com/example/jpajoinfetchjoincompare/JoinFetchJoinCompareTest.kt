package com.example.jpajoinfetchjoincompare

import com.example.jpajoinfetchjoincompare.domain.Member
import com.example.jpajoinfetchjoincompare.domain.Team
import com.example.jpajoinfetchjoincompare.repository.TeamRepository
import com.example.jpajoinfetchjoincompare.service.TeamService
import org.hibernate.LazyInitializationException
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.TestConstructor
import org.springframework.transaction.annotation.Transactional


@SpringBootTest
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
class JoinFetchJoinCompareTest(
    val teamService: TeamService,
    val teamRepository: TeamRepository
) {

    @BeforeEach
    @Transactional
    fun setUp() {
        val team1 = Team(
            name = "team1",
            members = mutableListOf()
        )
        val member1 = Member(
            name = "team1-member1",
            age = 20,
            team = team1
        )
        val member2 = Member(
            name = "team1-member2",
            age = 21,
            team = team1
        )
        val member3 = Member(
            name = "team1-member3",
            age = 22,
            team = team1
        )
        team1.members.add(member1)
        team1.members.add(member2)
        team1.members.add(member3)

        val team2 = Team(
            name = "team2",
        )
        val member4 = Member(
            name = "team2-member4",
            age = 23,
            team = team2
        )
        val member5 = Member(
            name = "team2-member5",
            age = 24,
            team = team2
        )
        team2.members.add(member4)
        team2.members.add(member5)

        teamRepository.save(team1)
        teamRepository.save(team2)

    }

    @Test
    fun joinTest() {

        /**
         * 실제 발생 쿼리
         * 1. Member가 조인되어 쿼리는 실행되었지만 Select 절에는 Team 관련 컬럼들만 존재
         *      -> 일반 조인은 실제 쿼리에 join을 걸어주기는 하지만 join 대상의 영속성까지는 관여하지 않아 Member 관련 컬럼은 존재 x
        select
            team0_.id as id1_1_,
            team0_.name as name2_1_
        from
            team team0_
        inner join
            member members1_
                on team0_.id=members1_.team_id
         */
        val findJoin: List<Team> = teamService.findAllJoinMember()

        println(findJoin)

        //일반 조인인 경우이므로 Member Entity는 초기화되지 않아 Member 관련 필드 접근 시 LazyInitializationException 발생
        assertThrows(LazyInitializationException::class.java) {
            findJoin[0].members[0].age
        }

    }

    @Test
    fun fetchJoinTest() {

        /**
         * 실제 발생 쿼리
         * 1. 일반 조인과 차이점으로는 대상 Entity(Team)와 Fetch Join이 걸린 Entity(Member) 모두 영속화
        select
            distinct team0_.id as id1_1_0_,
            members1_.id as id1_0_1_,
            team0_.name as name2_1_0_,
            members1_.age as age2_0_1_,
            members1_.name as name3_0_1_,
            members1_.team_id as team_id4_0_1_,
            members1_.team_id as team_id4_0_0__,
            members1_.id as id1_0_0__
        from
            team team0_
        inner join
            member members1_
                on team0_.id=members1_.team_id
         */
        val findFetchJoin = teamService.findAllFetchJoinMember()
        println(findFetchJoin)
    }

    /**
     * 일반 조인 쓰는 경우
     *
     * 1. 무조건 Fetch Join 사용은 x (성능 고려)
     * 2. 필요한 Entity만 영속성 컨텍스트에 올려서 사용할 때
     *
     * 사용 예시
     * - team1-member2라는 이름을 가진 member가 속해 있는 Team을 찾는 경우
     * -> 이런 경우 쿼리 검색 조건에는 연관관계가 있는 Member의 조건이 필요하지만 결과 값에는 Member의 관련된 데이터가 필요하지 않다.
     * -> 이럴 때 굳이 Fetch Join을 사용하여 모두 영속화 시키는 것보다 일반 Join을 사용하는 것이 효율적
     */
    @Test
    fun joinConditionTest() {

        /**
         * 실제 발생 쿼리
         *
        select
           distinct team0_.id as id1_1_,
            team0_.name as name2_1_
        from
            team team0_
        inner join
            member members1_
                on team0_.id=members1_.team_id
        where
            members1_.name=?
         */
        val findJoinByName = teamService.findMemberJoinByMemberName("team2-member4")
        println(findJoinByName)

        //조회는 잘 되지만 영속화 되지 않아 초기화가 되지 않은 member 필드에 접근 시 LazyInitializationException 나는 것은 마찬가지
        assertThrows(LazyInitializationException::class.java) {
            findJoinByName[0].members[0].age
        }
    }

}