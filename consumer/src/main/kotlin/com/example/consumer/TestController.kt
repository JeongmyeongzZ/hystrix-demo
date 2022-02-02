package com.example.consumer

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.client.RestTemplate
import org.springframework.web.client.getForEntity

@RestController
class TestController {

    @GetMapping
    @HystrixCommand(fallbackMethod = "fallback")
    fun test(): String? {
        val rt = RestTemplate();

        val entity = rt.getForEntity<String>("http://127.0.0.1:8098");

        if (entity.statusCode == HttpStatus.OK) {
            return entity.body
        }

        throw RuntimeException("supplier is not OK")
    }

    private fun fallback(): String {
        return "hello fallback";
    }
}
