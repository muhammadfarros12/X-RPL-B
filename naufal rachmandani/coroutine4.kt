package coroutine

import kotlinx.coroutines.*
import java.util.concurrent.Executors

//CoroutineDispatcher digunakan untuk menentukan thread mana yang bertanggung jawab untuk
//mengeksekusi coroutine

//Dispatchers.Default, ini adalah default dispatcher, isinya minimal 2 thread, atau sebanyak jumlah
//cpu (mana yang lebih banyak). Dispatcher ini cocok untuk proses coroutine yang cpu-bound

//Dispatcher.IO, ini adalah dispatcher yang berisikan thread sesuai dengan kebutuhan, ketika butuh
//akan dibuat, ketika sudah tidak dibutuhkan, akan dihapus, mirip cache thread pool di executor
//service. Dispatcher ini akan sharing thread dengan Default dispatcher

//Dispatchers.Main, ini adalah dispatchers yang berisikan main thread UI, cocok ketika kita butuh
//running di thread main seperti di Java Swing, JavaFX atau Android. Untuk menggunakan ini, kita
//harus menambah library ui tambahan

fun main() {
    //testDispatcher()
    testDispatcherCostume()
}

fun testDispatcher() {
    println("Thread Utama : ${Thread.currentThread().name}")
    runBlocking {
        println("RunBlocking Thread : ${Thread.currentThread().name}")
        val job1 = GlobalScope.launch(Dispatchers.Default) {
            println("Default Thread : ${Thread.currentThread().name}")
        }
        val job2 = GlobalScope.launch(Dispatchers.IO) {
            println("IO Thread : ${Thread.currentThread().name}")
        }
        //Dispatchers.Unconfined, ini adalah dispatcher yang tidak menunjuk thread apapun, biasanya akan
        //melanjutkan thread di coroutine sebelumnya
        //Confined (tanpa parameter), ini adalah dispatcher yang akan melanjutkan thread dari coroutine sebelumnya
        //Unconfined dan Confined, pada Unconfined, thread bisa berubah di tengah jalan jika
        //memang terdapat code yang melakukan perubahan thread
        GlobalScope.launch(Dispatchers.Unconfined) {
            println("Unconfined Thread : ${Thread.currentThread().name}")
            delay(1000)
            println("Unconfined Thread After Delay : ${Thread.currentThread().name}")
        }

        GlobalScope.launch(Dispatchers.Unconfined) {
            println("No Params Dispatcher Thread : ${Thread.currentThread().name}")
            delay(1000)
            println("No Params Dispatcher Thread After Delay : ${Thread.currentThread().name}")
        }
        joinAll(job1,job2)
        delay(3000)
    }
}

fun testDispatcherCostume(){
    val disPatcherService = Executors.newFixedThreadPool(2).asCoroutineDispatcher()
    val disPatcherUI = Executors.newFixedThreadPool(2).asCoroutineDispatcher()
    val disPatcherOther = Executors.newFixedThreadPool(2).asCoroutineDispatcher()

    runBlocking {
        println("Thread RunBlocking Thread : ${Thread.currentThread().name}")
        val job1 =GlobalScope.launch(disPatcherService){
            println("Service Thread : ${Thread.currentThread().name}")
            //caranya jika kita ingin menjalankan code program kita dalam coroutine di thread yang berbeda
            withContext(disPatcherOther){
                println("Pindah Thread dari Thread job 1, Thread : ${Thread.currentThread().name}")
            }
        }
        val job2 = GlobalScope.launch(disPatcherUI){
            println("Service Thread : ${Thread.currentThread().name}")
        }
        joinAll(job1,job2)
    }
}