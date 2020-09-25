package com.canerozkaymak.githubrepotest.model

import java.io.Serializable

data class Repo(
    val id: Int,
    val name: String,
    val open_issues_count: Int,
    val owner: Owner,
    val stargazers_count: Int
) : Serializable