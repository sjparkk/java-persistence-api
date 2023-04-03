package com.example.jpa.join_fetchjoin_compare.service

import com.example.jpa.join_fetchjoin_compare.domain.Team
import com.example.jpa.join_fetchjoin_compare.repository.TeamRepository
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