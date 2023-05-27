package com.example.jpa.save_saveall_compare.domain

import javax.persistence.*

@Entity
class Member1(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    val name: String,

    val age: Int,

)