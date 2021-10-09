import kotlinx.coroutines.*
import java.util.*

fun main(){
    testNonCancellable()
}

fun testNonCancellable(){
    runBlocking {
        val job = GlobalScope.launch {
            try{
                println("Curret-1 ${Date()}")
                delay(2000)
                println("Curret-2 ${Date()}")
            }finally {
                withContext(NonCancellable){
                    println(isActive)
                    delay(1000)
                    println("Done")
                }
            }
        }
        job.cancelAndJoin()
    }
}