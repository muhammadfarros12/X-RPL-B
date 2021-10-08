import kotlinx.coroutines.*
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.Semaphore
import kotlinx.coroutines.sync.withLock
import kotlinx.coroutines.sync.withPermit

//Mutex

fun main(){
    testCounter()
    testCounterWithMutex()
    testSemaphore()
}

fun testCounter(){
    var counter = 0
    val scope = CoroutineScope(Dispatchers.IO)
    repeat(100){
        scope.launch { repeat(1000){counter++} }
    }
    runBlocking { delay(3000) }
    println("Nothing $counter")
}

//Dengan menggunakan mutex, kita bisa pastikan bahwa hanya ada 1 coroutine yang bisa mengakses
//kode tersebut, code coroutine yang lain akan menunggu sampai coroutine pertama selesai

fun testCounterWithMutex(){
    var counter = 0
    val myMutex = Mutex()
    val scope = CoroutineScope(Dispatchers.IO)
    repeat(100){
        scope.launch {
            repeat(1000){
                myMutex.withLock { counter++ }
            }
        }
    }
    runBlocking { delay(3000) }
    println("With Mutex $counter")
}

//Semaphore kita bisa menentukan berapa jumlah corotine yang boleh mengakses nya pada satu waktu

fun testSemaphore(){
    var counter = 0
    val mySemaphore = Semaphore(4)
    val scope = CoroutineScope(Dispatchers.IO)
    repeat(100){
        scope.launch {
            repeat(1000){
                mySemaphore.withPermit { counter++ }
            }
        }
    }
    runBlocking { delay(3000) }
    println("With Semaphore $counter")
}