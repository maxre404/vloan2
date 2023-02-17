package com.tg.vloan.net

import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext

open class AndroidScope(private val scopeLife: CoroutineScope, private val dispatcher: CoroutineDispatcher = Dispatchers.Main):CoroutineScope {
    private val coroutineExceptionHandler = CoroutineExceptionHandler { _, throwable ->
        catch?.let { it ->
            it(throwable)
        }
    }
    private var catch: (AndroidScope.(Throwable) -> Unit)? = null
    private var finally: (AndroidScope.(Throwable?) -> Unit)? = null
    fun safeLaunch(block: suspend CoroutineScope.() -> Unit): AndroidScope {
        scopeLife.launch(coroutineContext) {
            block()
        }.invokeOnCompletion {
            finally?.let { it1 -> it1(it) }
        }
        return this
    }

    open fun launch(block: suspend CoroutineScope.() -> Unit): AndroidScope {
        launch(EmptyCoroutineContext) {
            block()
        }.invokeOnCompletion {
            finally(it)
        }
        return this
    }

    protected open fun catch(e: Throwable) {
        catch?.invoke(this@AndroidScope, e)
    }

    protected open fun finally(e: Throwable?) {
        finally?.invoke(this@AndroidScope, e)
    }

    open fun catch(block: AndroidScope.(Throwable) -> Unit = {}): AndroidScope {
        this.catch = block
        return this
    }

    open fun finally(block: AndroidScope.(Throwable?) -> Unit = {}): AndroidScope {
        this.finally = block
        return this
    }
    open fun cancel(cause: CancellationException? = null) {
        val job = coroutineContext[Job]
            ?: error("Scope cannot be cancelled because it does not have a job: $this")
        job.cancel(cause)
    }

    open fun cancel(
        message: String,
        cause: Throwable? = null
    ) = cancel(CancellationException(message, cause))

    override val coroutineContext: CoroutineContext = dispatcher + coroutineExceptionHandler + SupervisorJob()
}