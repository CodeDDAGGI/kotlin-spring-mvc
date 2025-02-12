package com.study.spring_mvc.application.service

import com.study.spring_mvc.exception.NoArticleFound
import com.study.spring_mvc.model.Article
import com.study.spring_mvc.repository.ArticleRepository
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import com.study.spring_mvc.service.ArticleService
import com.study.spring_mvc.service.ReqCreate
import com.study.spring_mvc.service.ReqUpdate
import org.springframework.data.jpa.domain.AbstractPersistable_.id
import org.springframework.data.repository.findByIdOrNull
import org.springframework.test.annotation.Rollback
import org.springframework.transaction.annotation.Transactional
import kotlin.jvm.optionals.getOrNull
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertTrue

@SpringBootTest
class ArticleServiceTest (
    @Autowired
    private val service : ArticleService,
    @Autowired
    private val repository: ArticleRepository
){
    @Test
    @Transactional
    fun createAndGet() {
        val created = service.create(ReqCreate("title1", body = "body1" , authorId = 1))
        val read = service.get(created.id)
        println("생성 값 : $created")
        println("가져온값 : $read")
        assertEquals(created.id , read.id)
        assertEquals(created.title , read.title)
        assertEquals(created.body , read.body)
        assertEquals(created.authorId, read.authorId)
        assertNotNull(read.createdAt)
        assertNotNull(read.updatedAt)
    }

    @Test
    @Transactional
    fun get(){
        val created = service.create(ReqCreate("title2", "body2", 1))
        val found = repository.findByIdOrNull(created.id)?:throw NoArticleFound("id :$id")

        println("테스트 유저 정보 : $found")
    }

    @Test
    @Transactional
    fun getAll(){
        val testList = listOf(
            service.create(ReqCreate("title1","body1" , 1)),
            service.create(ReqCreate("title2","body2" , 2)),
            service.create(ReqCreate("title3","body3" , 3))
        )

        val list :List<Article> = service.getAll("2")
        println("전체 리스트 : $list")

        assertTrue(list.isNotEmpty())
    }

    @Test
    @Transactional
    fun update() {
        val created = service.create(ReqCreate("title3", "body3", 3))
        val request = ReqUpdate(title = "updatetitle" , "updatebody" , 3 )

        val updated = repository.findByIdOrNull(created.id)?.let {
            article ->
            request.title?.let { article.title = it }
            request.body?.let { article.body = it }
            request.authorId.let { article.authorId = it}
            article
        }?: throw NoArticleFound("id : $id")

        repository.save(updated)

        val savedArticle = repository.findByIdOrNull(created.id)
        assertNotNull(savedArticle)
        assertEquals("updatetitle", savedArticle.title)
        assertEquals("updatebody", savedArticle.body)
        assertEquals(3, savedArticle.authorId)

        println(savedArticle)
    }

    @Test
    @Transactional
    fun delete(id : Long) {
        val created = service.create(ReqCreate("titleDel" , "delBody" , 6))

        repository.deleteById(created.id)
    }
}