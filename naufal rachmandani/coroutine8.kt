import kotlinx.coroutines.*

//Supervisor Job membuat jika child failure coroutines tidak berhenti

fun main(){
    //testNoSupervisor()
    //testSupervisor()
    //testSupervisorScope()
}

fun testNoSupervisor(){
    val scope = CoroutineScope(Job() + Dispatchers.IO)
    val job1 = scope.launch {
        delay(2000)
        println("Job 1 selesai")
    }

    val job2 = scope.launch {
        delay(1000)
        throw IllegalArgumentException("Job 2 gagal")
    }

    runBlocking {
        joinAll(job1,job2)
    }
}

fun testSupervisor(){
    val scope = CoroutineScope(SupervisorJob() + Dispatchers.IO)
    val job1 = scope.launch {
        delay(2000)
        println("Job 1 selesai")
    }

    val job2 = scope.launch {
        delay(1000)
        throw IllegalArgumentException("Job 2 gagal")
    }

    runBlocking {
        joinAll(job1,job2)
    }
}

fun testSupervisorScope(){
    val scope = CoroutineScope(Job() + Dispatchers.IO)
    runBlocking {
        scope.launch {
            supervisorScope {
                launch {
                    delay(2000)
                    println("Job 1 selesai")
                }
                launch {
                    delay(1000)
                    throw IllegalArgumentException("Job 2 gagal")
                }
            }
        }
        delay(3000)
    }
}