package day09

import util.StringUtil
import java.io.File

class PartA {

    private fun handleData(data: List<Int>): List<Int> {
//        println("data: ${ data }")

        val newList = mutableListOf<Int>()

        for (i in 0..<(data.size - 1)) {
            newList.add(data[i + 1] - data[i])
        }

        if (newList.all { it == 0}) {
            newList.add(0)
        } else {
            val result = handleData(newList)
            newList.add(result.last() + newList.last())
        }

        return newList
    }

    fun run() {
//        val lines =  File("./src/${ this.javaClass.packageName }/example.txt").readLines()
        val lines =  File("./src/${ this.javaClass.packageName }/input.txt").readLines()

        val data = lines.map { StringUtil.parseInt(it, " ")}

        val results = data.map {
            val newList = handleData(it)
            (newList.last() + it.last())
        }

        val total = results.sum()
        println("total: ${ total }")
    }
}

class PartB {

    private fun handleData(data: List<Int>): List<Int> {
        val newList = mutableListOf<Int>()

        for (i in 0..<(data.size - 1)) {
            newList.add(data[i + 1] - data[i])
        }

        if (newList.all { it == 0}) {
            newList.add(0, 0)
        } else {
            val result = handleData(newList)
            newList.add(0, newList.first() - result.first())
        }

        return newList
    }

    fun run() {
//        val lines =  File("./src/${ this.javaClass.packageName }/example.txt").readLines()
        val lines =  File("./src/${ this.javaClass.packageName }/input.txt").readLines()

        val data = lines.map { StringUtil.parseInt(it, " ")}

        val results = data.map {
            val newList = handleData(it)
            val calc = it.first() - newList.first()

            calc
        }

        val total = results.sum()
        println("total: ${ total }")
    }
}

fun main() {
//    PartA().run()
    PartB().run()
}