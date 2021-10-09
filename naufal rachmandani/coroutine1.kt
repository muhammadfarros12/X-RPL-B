import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import java.util.*
import kotlin.concurrent.thread

fun main(){
    println(Thread.currentThread().name)
    testThreadVsCoroutines()
}

fun testThreadVsCoroutines(){
//    (1..100).forEach { _ ->
//        thread {
//            Thread.sleep(1000)
//            println("Hello Now in ${Thread.currentThread().name}")
//        }
//    }
//    Thread.sleep(5000)

//    (1..1000).forEach { _ ->
//        GlobalScope.launch {
//            delay(1000)
//            println("Coroutine ${Thread.currentThread().name}")
//        }
//    }
//    runBlocking { delay(5000) }
    println("Selesai")
}

fun normal(){
    mesenKopiBTS()
    println("Selesai")
}

fun withSuspend(){
    GlobalScope.launch {
        mesenKopi()
    }
    //Thread.sleep(4000)
    println("Selesai")
}

fun mesenKopiBTS(){
    println("Hello World ${Date()}")
    Thread.sleep(2000)
    println("Hello World ${Date()}")
}

suspend fun mesenKopi(){
    println("Mesen Kopi ${Date()}")
    delay(2000)
    println("Kopi tiba ${Date()}")
}