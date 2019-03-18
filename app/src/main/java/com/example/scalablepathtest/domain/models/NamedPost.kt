package com.example.scalablepathtest.domain.models

import com.example.scalablepathtest.data.models.Post
import com.example.scalablepathtest.data.models.User

data class NamedPost(val user: User, val post: Post)
