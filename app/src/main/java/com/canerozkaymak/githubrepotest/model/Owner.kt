package com.canerozkaymak.githubrepotest.model

import java.io.Serializable

data class Owner(
    val avatar_url: String,
    val id: Int,
    val login: String
) : Serializable