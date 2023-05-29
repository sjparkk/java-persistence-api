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

    /**
     1. saveAll() 이라고 해서 한번에 저장하는 것이 아닌 save()를 호출한다.

    @Transactional
    public <S extends T> S save(S entity) {
        Assert.notNull(entity, "Entity must not be null.");
        if (this.entityInformation.isNew(entity)) {
            this.em.persist(entity);
            return entity;
        } else {
            return this.em.merge(entity);
        }
    }

    @Transactional
    public <S extends T> List<S> saveAll(Iterable<S> entities) {
        Assert.notNull(entities, "Entities must not be null!");
        List<S> result = new ArrayList();
        Iterator var3 = entities.iterator();

        while(var3.hasNext()) {
            S entity = var3.next();
            result.add(this.save(entity));
        }

        return result;
    }

     2. saveAll()도 내부적으로 save()를 호출하는데 성능 차이가 나는 이유는? (@Transactional)

     save()로 저장 시 1건의 데이터마다 save()를 호출하게 됨
     saveAll()은 1건 마다 인스턴스 내부의 save() 함수를 호출하게 됨

     save() 호출 시 상위에 Transaction이 존재하는 경우 해당 Transaction에 참여 없다면 새로 Transaction을 생성하고 save 후 커밋
     기존 Transaction 에 참여하더라도 외부 Bean(Repository) 객체의 save() 를 호출하므로 해당 과정에서 비용이 발생 됨

     반면, saveAll()에 경우는 Bean 객체 내부함수를 호출하기 때문에 save() 호출마다 트랜잭션을 새로 생성하거나 참여되는 과정이 발생하지 않아 성능 차이가 나는 것.

     */

}