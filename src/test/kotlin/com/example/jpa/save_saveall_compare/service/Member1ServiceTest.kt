package com.example.jpa.save_saveall_compare.service

import com.example.jpa.save_saveall_compare.domain.Member1
import com.example.jpa.save_saveall_compare.repository.MemberRepository
import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.TestConstructor
import org.springframework.transaction.annotation.Transactional

@SpringBootTest
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
class Member1ServiceTest(
    val memberRepository: MemberRepository
) {


}