package com.example.jpa.save_saveall_compare

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

    @Test
    @Transactional
    fun `save() 시간 측정 테스트`() {
        val start = System.currentTimeMillis()

        var count = 300000

        while (count-- > 0) {
            val member1 = Member1(name = "test", age = 20)
            memberRepository.save(member1)
        }

        println("시간 측정 결과 : " + (System.currentTimeMillis() - start) + "ms")
        /**
        데이터 10만건 -> 7.8초
        데이터 30만건 -> 16.1초
         */
    }

    @Test
    @Transactional
    fun `saveAll() 시간 측정 테스트`() {
        val start = System.currentTimeMillis()

        var count = 300000

        val members = mutableListOf<Member1>()

        while (count-- > 0) {
            val member1 = Member1(name = "test", age = 20)
            members.add(member1)
        }

        memberRepository.saveAll(members)

        println("시간 측정 결과 : " + (System.currentTimeMillis() - start) + "ms")
        /**
         데이터 10만건 -> 6.0초
         데이터 30만건 -> 10.3초
         */
    }

}