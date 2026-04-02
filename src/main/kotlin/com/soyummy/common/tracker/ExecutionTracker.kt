package com.soyummy.common.tracker

import org.springframework.context.annotation.Scope
import org.springframework.stereotype.Component
import java.util.UUID

@Component
@Scope("prototype")
class ExecutionTracker {
    private val startTime = System.currentTimeMillis()
    private val transactionId = UUID.randomUUID().toString().take(8)

    fun logStep(message: String) {
        val elapsed = System.currentTimeMillis() - startTime
        println("[TRX-$transactionId] +${elapsed}ms: $message")
    }

    fun finish(operationName: String) {
        val totalTime = System.currentTimeMillis() - startTime
        println("[TRX-$transactionId] COMPLETED: $operationName in ${totalTime}ms")
    }
}