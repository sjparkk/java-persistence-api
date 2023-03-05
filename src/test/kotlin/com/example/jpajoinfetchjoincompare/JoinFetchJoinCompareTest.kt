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

}