package coroutine

import kotlinx.coroutines.*
fun main() {
    testAwaitCancellation()
}

//Selalu jalan walaupun udah selesai sampai dicancle
fun testAwaitCancellation() {
    runBlocking {
        val job = launch {
            try {
                println("Lagi ngerjain sesuatu...")
                //awaitCancellation()
            } finally {
                println("Done")
            }
        }
        delay(2000)
        //job.cancelAndJoin()
        job.join()
    }
}