package com.example.jpajoinfetchjoincompare.service

import com.example.jpajoinfetchjoincompare.domain.Team
import com.example.jpajoinfetchjoincompare.repository.TeamRepository
import org.springframework.stereotype.Service


@Service
class TeamService(
    val teamRepository: TeamRepository
) {

    fun findAllJoinMember(): List<Team> {
        return teamRepository.findAllJoinMember()
    }

    fun findAllFetchJoinMember(): List<Team> {
        return teamRepository.findAllFetchJoinMember()
    }

    fun findMemberJoinByMemberName(memberName: String): List<Team> {
        return teamRepository.findMemberJoinByMemberName(memberName)
    }
}