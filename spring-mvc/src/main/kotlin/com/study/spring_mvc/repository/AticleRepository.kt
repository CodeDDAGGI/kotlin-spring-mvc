package com.study.spring_mvc.repository

import com.study.spring_mvc.model.Article
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface AticleRepository:JpaRepository<Article,Long> {
    fun findAllByTitleContains(title: String):List<Article>
}