import kotlinx.coroutines.*
import java.util.*
import java.util.concurrent.Executors

//Coroutine Scope

//Function launch dan async yang selama ini kita gunakan, sebenarnya adalah extention function dari
//coroutine scope

fun main() {
    //testCoroutineScope()
    //testParentChildScope()
    //testCoroutineFunction()
    //testParentChild()
}

fun testCoroutineScope() {
    val scope = CoroutineScope(Dispatchers.IO)
    //Gak bakal jalan
    scope.launch {
        delay(2000)
        println("1 : ${Date()}")
    }
    scope.launch {
        delay(1000)
        println("2 : ${Date()}")
    }
    runBlocking {
        delay(1000)
        scope.cancel()
        delay(3000)
    }
}

fun testParentChildScope() {
    val parentDispatchers = Executors.newFixedThreadPool(2).asCoroutineDispatcher()
    val parentScope = CoroutineScope(parentDispatchers)
    val job = parentScope.launch(CoroutineName("Parent")) {
        //child
        coroutineScope {
            launch(CoroutineName("Child")) {
                delay(1000)
                println("Anaknya ${Thread.currentThread().name}")
            }
        }
        println("ini parent ${Thread.currentThread().name}")
    }
    runBlocking { job.join() }
}

fun testParentChild() {
    val job = GlobalScope.launch {
        launch {
            delay(3000)
            println("1 : ${Date()}")
        }
        launch {
            delay(2000)
            println("2 ${Date()}")
        }
        delay(1000)
        println("Selesai")
    }
    runBlocking {
        //Thread.sleep(2000)
        //job.cancelChildren()
        job.join()
    }
}

fun testCoroutineFunction() {
    val scope = CoroutineScope(Dispatchers.IO)
    val job = scope.launch {
        val result = getSum()
        println(result)
    }
    runBlocking { job.join() }
}

suspend fun getSum(): Int = coroutineScope {
    val foo = async { getFoo2() }
    val bar = async { getBar2() }
    awaitAll(foo, bar).sum()
}

suspend fun getFoo2(): Int {
    delay(1000)
    //throw CancellationException()
    return 100
}

suspend fun getBar2(): Int {
    delay(1000)
    return 50
}



