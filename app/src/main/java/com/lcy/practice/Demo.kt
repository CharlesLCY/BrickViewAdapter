package com.lcy.practice

import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import java.util.concurrent.Executors


/**
 *
 * <p> Created by CharlesLee on 2022/8/26
 * 15708478830@163.com
 */

fun printThread(prefix: String) {
    println("$prefix：${Thread.currentThread().name}")
}

fun simpleFlow() = flow {
    for (i in 1..5) {
        delay(100)
        emit(i)
    }
}.filter {
    it % 2 == 0
}.map {
    "String $it"
}

suspend fun execute() = withContext(Dispatchers.IO) {
    println("sleeping")
    delay(2000)
    "123"
}

fun main() {
//    runBlocking {
//        simpleFlow().collect {
//            println(it)
//        }
//        println("simpleFlow finished")
//
//        val startTime = System.currentTimeMillis()
//        flowOf(3, 5, 7).onEach {
//            delay(100)
//        }.collect {
//            println("${System.currentTimeMillis() - startTime}ms $it")
//        }
//        println("flowOf finished")
//
//        (3..6).reduce { accumulator, value ->
//            accumulator + value
//        }
//        println("asFlow finished")

//        flowOf(1, 2, 3, 4, 5)
//            .runningFold("a") { a, b ->
//                a + b
//            }.collect {
//                println(it)
//            }
//        println("fold finished")
//
//        flowOf(1, 2, 3, 4, 5)
//            .runningReduce { accumulator, value ->
//                accumulator + value
//            }.collect {
//                println(it)
//            }
//        println("reduce finished")

//        flow {
//            emit(1)
//            delay(590)
//            emit(2)
//            delay(590)
//            emit(3)
//            delay(1010)
//            emit(4)
//            delay(1010)
//        }.sample(
//            1000
//        ).collect {
//            println(it)
//        }

//        flowOf(1, 3)
//            .flatMapMerge {
//                flowOf("$it a", "$it b")
//            }.collect {
//                println(it)
//            }

//        flowOf(1, 2, 3, 4)
//            .onEach {
//                delay(300)
//                println("$it A")
//            }
//            .buffer(100)
//            .collect {
//                println("$it B")
//            }

//        flowOf(1, 1, 2, 2, 2, 2, 2, 3, 2, 3, 3)
//            .distinctUntilChanged { old, new ->
//                old - new == 1
//            }
//            .collect {
//                println(it)
//            }

//        (1..5).asFlow().onEach {
//            delay(300)
//        }.flowOn(Dispatchers.IO)
//
//        flowOf(1, 2, 3)
//            .onEach {
//                println(it)
//            }
//            .launchIn(this)


    val s1 = Executors.newFixedThreadPool(3).asCoroutineDispatcher()
    val s2 = Executors.newFixedThreadPool(3).asCoroutineDispatcher()
    val s3 = Executors.newFixedThreadPool(3).asCoroutineDispatcher()
    val s4 = Executors.newFixedThreadPool(3).asCoroutineDispatcher()
    val main = Executors.newFixedThreadPool(1).asCoroutineDispatcher()

//        runBlocking {
//            val job = CoroutineScope(main).launch {
//                if (isActive) {
//                    flowOf(1, 2, 3)
//                        .onEach { printThread("1") }
//                        .flowOn(s1)
//                        .onEach { printThread("2") }
//                        .flowOn(s2)
//                        .flatMapMerge {
//                            flowOf(1, 2, 3)
//                                .onEach { printThread("inner 1") }
//                                .flowOn(s3)
//                                .onEach { printThread("inner 2") }
//                        }
//                        .onEach { printThread("3") }
//                        .flowOn(s4)
//                        .collect {
//                            printThread("end")
//                        }
//                }
//
//                println("do something")
//            }
//            job.cancelAndJoin()
//        }

//    }

//    CoroutineScope(Dispatchers.Main).launch {
//
//    }
//
//    runBlocking {
//
//    }
//
//    GlobalScope.launch {
//
//    }

//    CoroutineScope(main).launch {
//        val time1 = System.currentTimeMillis()
//
//        val task1 = async(s1) {
//            delay(2000)
//            println("1.执行task1.... [当前线程为：${Thread.currentThread().name}]")
//            "one"  //返回结果赋值给task1
//        }
//
//        val task2 = async(s2) {
//            delay(1000)
//            println("2.执行task2.... [当前线程为：${Thread.currentThread().name}]")
//            "two"  //返回结果赋值给task2
//        }
//
//        task2.cancel()
//
//        println("task1 = ${task1.await()}, task2 = ${task2.await()}, 耗时 ${System.currentTimeMillis() - time1} ms  [当前线程为：${Thread.currentThread().name}]")
//
//        println("协程运行...")
//    }
//
//    println("正常运行...")

//    runBlocking {
//        val job = GlobalScope.launch { // root coroutine with launch
//            println("Throwing exception from launch")
//            throw IndexOutOfBoundsException() // 我们将在控制台打印 Thread.defaultUncaughtExceptionHandler
//        }
//        job.join()
//        println("Joined failed job")
//        val deferred = async { // root coroutine with async
//            println("Throwing exception from async")
//            throw ArithmeticException() // 没有打印任何东西，依赖用户去调用等待
//        }
//        try {
//            deferred.await()
//            println("Unreached")
//        } catch (e: ArithmeticException) {
//            println("Caught ArithmeticException")
//        }
//    }

//    CoroutineScope(main).launch(
//        CoroutineExceptionHandler { coroutineContext, throwable ->
//
//        }
//    ) {
//        val deferred = async(Dispatchers.IO, CoroutineStart.LAZY) { // root coroutine with async
////            println("Throwing exception from async")
////            throw ArithmeticException("出错了") // 没有打印任何东西，依赖用户去调用等待
//
//            delay(2000)
//            println("doing...")
//            "请求成功"
//        }
//        println(deferred.await())
////        deferred.start()
//    }
//
//    runBlocking {
//        coroutineScope {
//
//        }
//    }

//    runBlocking {
//        val job = CoroutineScope(main).launch {
//            try {
//                val startTime = System.currentTimeMillis()
//                var nextPrintTime = startTime
//                var i = 0
//                while (i < 10) { // 一个执行计算的循环，只是为了占用 CPU
//                    // 每秒打印消息两次
//                    if (System.currentTimeMillis() >= nextPrintTime) {
//                        println("I'm sleeping ${i++} ...")
//                        nextPrintTime += 200L
//                    }
//                }
//            } catch (e: Exception) {
//                println("异常：${e}")
//            }
//        }
//        delay(1000)
//        println("going to cancel")
//        job.cancel()
//        println("canceled")
//    }

//    runBlocking {
//        val job = launch {
//            try {
//                repeat(100) {
//                    delay(200)
//                    println("任务执行：$it")
//                }
//            } catch (e: Exception) {
//                println("异常：${e.message}")
//            } finally {
//                withContext(NonCancellable){
//                    println("finally")
//                    delay(1000)
//                    println("finally_delay")
//                }
//            }
//        }
//        delay(2000)
//        println("要取消了")
//        job.cancelAndJoin()
//        println("取消完成")
//    }

//    runBlocking {
//        withTimeout(2000L) {
//            try {
//                delay(3000)
//            } catch (e: Exception) {
//                println(e.message)
//            }
//        }
//    }

//    runBlocking {
//        println("launching")
//        try {
//            withTimeout(1000) {
//                execute()
//            }
//        } catch (e: TimeoutCancellationException) {
//            println(e)
//        }
//        val job = launch(Dispatchers.Main) {
//
//        }
//
////        job.getCancellationException()
//
//    }

//    CoroutineScope(main).launch {
////        println(loadAndCombine("lcy", "glq"))
//
//        val deferred1 = async {
//            delay(5000)
//            "lcy"
//        }
//        val deferred2 = async {
//            delay(2000)
//            "glq"
//        }
//        println("${deferred1.await()}, ${deferred2.await()}")
//
//
////        val deferred1 = async {
////            delay(1000)
////            "lcy"
////        }
////
////        println(deferred1.await())
//    }

//    CoroutineScope(main).launch {
//        val deferred2 = async {
//            delay(2000)
//            "glq"
//        }
//
//        println(deferred2.await())
//    }

    val flow = flow {
        delay(1000)
        emit(1)
        delay(1000)
        emit(2)
        delay(1000)
        emit("2sdf")
    }

    runBlocking {
        doBackground2(flowOf(1, 2, "lcy").onEach {
            delay(1000)
        })


    }

    runCatching {

    }.onFailure {

    }.onSuccess {

    }
}

// 常用的示意例子，这里用的是async，实际上用launch时，coroutineScope也会等待其结束后再返回。
suspend fun loadAndCombine(name1: String, name2: String): String = coroutineScope {
    val deferred1 = async {
        delay(5000)
        name1
    }
    val deferred2 = async {
        delay(2000)
        name2
    }
    "${deferred1.await()}, ${deferred2.await()}"
}

suspend fun doBackground1() = coroutineScope {
    "glq"
}

suspend fun <T> doBackground2(flow: Flow<T>) = withContext(Dispatchers.IO) {
    flow.collect {
        println(it)
    }


}
