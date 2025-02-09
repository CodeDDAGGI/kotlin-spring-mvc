package com.study.spring_mvc.exception

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus
import java.lang.RuntimeException

// excetion 발생시
@ResponseStatus(HttpStatus.NOT_FOUND)
class NoArticleFound(message: String): RuntimeException(message) // 앞의 메세지를 런타임이셉션이 메세지를 받음