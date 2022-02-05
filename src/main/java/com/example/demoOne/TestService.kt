package com.example.demoOne

import kotlinx.coroutines.*
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.client.RestTemplate
import java.time.Duration
import java.time.Instant

@RestController
class TestService {

    private fun <T> CoroutineScope.myRestTemplateAsync(
            start: CoroutineStart = CoroutineStart.DEFAULT,
            block: suspend CoroutineScope.() -> T
    ): Deferred<T> {

        return async(Dispatchers.IO, start) {
            block()
        }
    }

    @GetMapping("/check2")
    fun test(): ResponseEntity<String> {
        val start = Instant.now()
        val restTemplate = RestTemplate()
        runBlocking {
            for (i in 0..100000) {
                val something = myRestTemplateAsync{
                    try {
                        val res = callSomeRestApi(restTemplate)
                        println(i)
                        println( res.toString())
                    }
                    catch (e:Exception)
                    {
                        println(i)
                        println(e.localizedMessage)
                    }

                }
            }
        }
        val finish = Instant.now()

        val timeElapsed = Duration.between(start, finish).toMillis()

        println("Total time: $timeElapsed ms")

        return ResponseEntity("Hello", HttpStatus.OK)
    }

    private suspend fun callSomeRestApi(restTemplate: RestTemplate): Any {
        val msgServiceUrl = "https://dummy.restapiexample.com/api/v1/employee/1"
        return restTemplate.getForObject(msgServiceUrl, Any::class.java)!!
    }

}