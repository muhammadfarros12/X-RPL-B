import kotlinx.coroutines.*

fun main() {
    //noWaitCoroutines()
    //createJob()
    //jobAwait()
    //jobCancel()
    //jobAwaitAll()
    //testIsActive()
    //testTimeOut()
    testTimeOutReturnNull()
}

fun noWaitCoroutines() {
    runBlocking {
        GlobalScope.launch {
            delay(2000)
            println("Selesai Coroutines")
        }
    }
    println("Selesai")
}

fun createJob() {
    val job = GlobalScope.launch {
        delay(2_000)
        println("Hello job")
    }
    job.start()
    // Thread.sleep(2_500)
}

fun jobAwait() {
    runBlocking {
        val job = GlobalScope.launch {
            delay(2_000)
            println("Hello job")
        }
        //job.join harus dipanggil di suspend function atau didalam coroutines
        job.join()
        println("DONE")
    }
}

fun jobAwaitAll() {
    runBlocking {
        val job1 = GlobalScope.launch {
            delay(2_000)
            println("Hello job 1")
        }
        val job2 = GlobalScope.launch {
            delay(3_000)
            println("Hello job 2")
        }
        joinAll(job1, job2)
        println("DONE")
    }
}

fun jobCancel() {
    runBlocking {
        val job = GlobalScope.launch {
            delay(4_000)
            println("Hello job")
        }
        //job.join harus dipanggil di suspend function atau didalam coroutines
        job.start()
        //Thread.sleep(5_000)
        Thread.sleep(3_000)
        job.cancel()
    }
}

fun testIsActive() {
    runBlocking {
        val job = GlobalScope.launch {
            try {
                if (!isActive) throw CancellationException()
                println("Masih berjalan-1")
                ensureActive()
                Thread.sleep(2000)
                ensureActive()
                println("Masih berjalan-2")
            } catch (e: CancellationException) {
                println("dibatalkan")
            }
        }
        job.cancel()
        job.join()
    }
}

fun testTimeOut() {
    runBlocking {
        val job = GlobalScope.launch {
            try {
                withTimeout(3_000) {
                    repeat(10) {
                        delay(1000)
                        println("Now in $it")
                    }
                }
            } catch (e: TimeoutCancellationException) {
                println("Batalin Kesuen")
            }
        }
        job.join()
    }
}

fun testTimeOutReturnNull() {
    runBlocking {
        val job : Job = GlobalScope.launch {
            println("Mulai")
            val value = withTimeoutOrNull(3_000) {
                repeat(10) {
                    delay(1000)
                    println("Now in $it")
                }
            }
            println(value)
            println("Selesai")
        }
        job.join()
    }
}