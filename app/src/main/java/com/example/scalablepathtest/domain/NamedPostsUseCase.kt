package com.example.scalablepathtest.domain

import com.example.scalablepathtest.data.PostsService
import com.example.scalablepathtest.data.UsersService
import com.example.scalablepathtest.data.models.Post
import com.example.scalablepathtest.data.models.User
import com.example.scalablepathtest.domain.models.NamedPost
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers

class NamedPostsUseCase(
    private val usersRepository: UsersService,
    private val postsService: PostsService
) {

    fun getNamedPosts(): Observable<List<NamedPost>> {
        val usersStream = usersRepository.getUsers().share()
        return postsService.getPosts()
            .flatMapSingle { posts ->
                Observable.fromIterable(posts)
                    .flatMapMaybe { post: Post ->
                        usersStream
                            .flatMapIterable { it }
                            .filter { user: User -> user.id == post.userId }
                            .map { user: User -> NamedPost(user, post) }
                            .firstElement()

                    }
                    .toList()
            }
            .subscribeOn(Schedulers.io())
    }
}
