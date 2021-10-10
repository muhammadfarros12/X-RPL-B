import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*

//Flow mirip dengan sequence di Kotlin Collection, yang membedakan adalah flow berjalan sebagai
//coroutine dan kita bisa menggunakan suspend function di flow.
//Flow adalah collection cold atau lazy, artinya jika tidak diminta datanya, flow tidak akan berjalan
//(kode nya tidak akan dieksekusi)
//untuk dapet exception bisa pakai catch() Dan untuk finally, flow juga sudah menyediakan operatornya,
// nama function nya adalah onCompletion()

fun main(){
    //testFlow()
    //testFlowSuspend()
    //testException()
    //testCancelFlow()
}

fun testFlow(){
    val flow = flow{
        println("Flow Start")
        repeat(10) {
            delay(1000)
            emit(it)
        }
    }
    runBlocking {
        flow.collect { println(it) }
    }
}

fun testFlowSuspend(){
    val flow = numberFlow()
    runBlocking {
        flow.filter { it %2 != 0 }
                .map { changeToString(it) }
                .collect { println(it) }
    }
}

fun testException(){
    val flow = numberFlow()
    runBlocking {
        flow.map { check(it< 10); it }
                .onEach { println(it) }
                .catch { println("Error : ${it.message}") }
                .onCompletion { println("Finish") }
                .collect()
    }
}

fun testCancelFlow(){
    val flow = numberFlow()
    val scope = CoroutineScope(Dispatchers.IO)
    runBlocking {
        scope.launch {
            flow.onEach {
                if(it > 5) cancel()
                else println("Number : $it, Thread : ${Thread.currentThread().name}")
            }.collect()
        }
        delay(2000)
    }
}

fun numberFlow() : Flow<Int> = flow {
    repeat(100){
        emit(it)
    }
}.flowOn(Dispatchers.IO)

suspend fun changeToString(value : Int) : String{
    delay(100)
    return value.toString()
}