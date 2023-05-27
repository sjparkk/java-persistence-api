package com.example.jpa.save_saveall_compare.repository

import com.example.jpa.save_saveall_compare.domain.Member1
import org.springframework.data.jpa.repository.JpaRepository

interface MemberRepository: JpaRepository<Member1, Long> {
}