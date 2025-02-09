package com.study.spring_mvc.service

import com.study.spring_mvc.exception.NoArticleFound
import com.study.spring_mvc.model.Article
import com.study.spring_mvc.repository.AticleRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class ArticleService(
    private val repository: AticleRepository,
) {

    fun get(id:Long):Article =
        repository.findByIdOrNull(id)?: throw NoArticleFound("No Article found (id:$id)")

    fun getAll(title:String? = null): List<Article> =
        if(title.isNullOrEmpty()) repository.findAll() // 타이틀이 존재하지않을경우 전체 조회
    // if else도 값이 될 수 있음 모든 함수는 값이 될수 있다 "="
    else repository.findAllByTitleContains(title)

    @Transactional
    fun create(request: ReqCreate):Article =
        repository.save(
            Article(
                title = request.title,
                body = request.body,
                authorId = request.authorId,
            ))

    @Transactional
    fun update(id:Long , reqUpdate: ReqUpdate): Article =
        // null이 아닐때 조건 처리
        // nullable은 let처리가능
        repository.findByIdOrNull(id)?.let { article ->
            reqUpdate.title?.let { article.title = it }
            reqUpdate.body?.let { article.body = it }
            reqUpdate.authorId.let { article.authorId = it }
            article
        }?: throw NoArticleFound("No article Found(id : $id)")

    @Transactional
    fun delete(id: Long) =
        repository.deleteById(id)
}

// 간단하게 처리
data class ReqCreate(
    val title:String,
    val body: String?,
    val authorId:Long?
)

// 업데이트라 null이 될 수 있음
data class ReqUpdate(
    val title: String? = null,
    val body : String? = null,
    val authorId: Long? = null
)