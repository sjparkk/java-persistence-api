package com.example.jpajoinfetchjoincompare.domain

import javax.persistence.*

@Entity
class Team(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    val name: String,

    @OneToMany(mappedBy = "team", fetch = FetchType.LAZY, cascade = [CascadeType.PERSIST])
    val members: MutableList<Member> = mutableListOf()

) {
}