package com.stonetree.demowagen.features.manufacturer.resources.executor

import java.util.*
import java.util.concurrent.Executor

class ManufacturerExecutor : Executor {
    private val tasks = LinkedList<Runnable>()

    override fun execute(command: Runnable) {
        tasks.add(command)
    }

    fun flush(): Boolean {
        val consumed = !tasks.isEmpty()

        var task = tasks.poll()
        while (task != null) {
            task.run()
            task = tasks.poll()
        }
        return consumed
    }
}