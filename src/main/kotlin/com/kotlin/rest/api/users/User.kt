package com.kotlin.rest.api.users

import jakarta.persistence.*
import lombok.Data

@Entity
@Table(name = "users")
data class User (

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val userId:Int?= null,
    val name: String,
    val email: String,
    val password: String
)