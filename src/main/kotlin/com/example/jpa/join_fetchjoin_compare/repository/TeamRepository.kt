package com.example.jpa.join_fetchjoin_compare.repository

import com.example.jpa.join_fetchjoin_compare.domain.Team
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query


interface TeamRepository: JpaRepository<Team, Long> {

    @Query("select distinct t from Team t join t.members")
    fun findAllJoinMember(): List<Team>

    @Query("SELECT distinct t FROM Team t join fetch t.members")
    fun findAllFetchJoinMember(): List<Team>

    @Query("SELECT distinct t FROM Team t join t.members m where m.name = :memberName")
    fun findMemberJoinByMemberName(memberName: String): List<Team>

}

