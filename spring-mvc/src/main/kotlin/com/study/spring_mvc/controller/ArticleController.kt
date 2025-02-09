package com.study.spring_mvc.controller

import com.study.spring_mvc.model.Article
import com.study.spring_mvc.service.ArticleService
import com.study.spring_mvc.service.ReqCreate
import com.study.spring_mvc.service.ReqUpdate
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/article")
class ArticleController (
    private val service : ArticleService
){

    @GetMapping("/{id}")
    fun get(@PathVariable id : Long): Article = service.get(id)

    @GetMapping("/all")
    fun getAll(@RequestParam title: String?):List<Article> =
        service.getAll(title)

    @PostMapping
    fun create(@RequestBody request: ReqCreate): Article =
        service.create(request)

    @PutMapping("/{id}")
    fun update(@PathVariable id:Long , @RequestBody request: ReqUpdate)=
        service.update(id, request)

    @DeleteMapping("/{id}")
    fun delete(@PathVariable id :Long) = service.delete(id)
}