package com.example.jpajoinfetchjoincompare.domain

import javax.persistence.*

@Entity
class Member(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    val name: String,

    val age: Int,

    @ManyToOne(fetch = FetchType.LAZY, cascade = [CascadeType.PERSIST])
    val team: Team? = null
) {


}