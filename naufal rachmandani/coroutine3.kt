import kotlinx.coroutines.*
import kotlin.system.measureTimeMillis

//Secara default, sebenarnya sebuah suspend function tidaklah async, saat kita mengakses beberapa
//suspend function, semua akan dieksekusi secara sequential
//Jadi agar sebuah suspend function bisa berjalan secara concurrent, kita perlu menggunakan
//function launch ketika memanggil suspend function tersebut

fun main(){
    //testSuspend()
    //testSuspendWithCoroutines()
    testAsyncForGetValue()
}

fun testSuspend() {
    runBlocking {
        val total = measureTimeMillis {
            getFoo()
            getBar()
        }
        println(total)
    }
}

fun testSuspendWithCoroutines(){
    runBlocking {
        val total = measureTimeMillis {
            val job1 = GlobalScope.launch { getFoo() }
            val job2 = GlobalScope.launch { getBar() }
            //val total = joinAll(job1,job2).sum()
        }
        println(total)
    }
}

fun testAsyncForGetValue(){
    runBlocking {
        val total = measureTimeMillis {
            val foo = GlobalScope.async { getFoo() }
            val bar = GlobalScope.async { getBar() }
            //Deferred adalah turunan dari Job, yang membedakan adalah, Deferred membawa value hasil dari
            //async function
            //Deferred itu mirip konsep Promise atau Future, dimana datanya akan ada nanti
            val total = awaitAll(foo,bar).sum()
            println(total)
        }
        println(total)
    }
}

suspend fun getBar() : Int{
    delay(1000)
    return 10
}

suspend fun getFoo() : Int {
    delay(1000)
    return 10
}
