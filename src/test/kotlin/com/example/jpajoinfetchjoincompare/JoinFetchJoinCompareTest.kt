package com.example.jpajoinfetchjoincompare

import com.example.jpajoinfetchjoincompare.domain.Member
import com.example.jpajoinfetchjoincompare.domain.Team
import com.example.jpajoinfetchjoincompare.repository.TeamRepository
import org.junit.jupiter.api.BeforeEach
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.TestConstructor
import org.springframework.transaction.annotation.Transactional


@SpringBootTest
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
class JoinFetchJoinCompareTest(
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

}