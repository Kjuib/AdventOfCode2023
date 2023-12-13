package day13

import util.StringUtil
import java.io.File

class PartA {

    private fun checkMirror(pond: List<String>, index: Int): Boolean {
        return pond.all {
            val side1 = it.substring(0, index)
            val side2 = it.substring(index)

//            println("LINE: ${side1}-${side2}")
            if (side1.length > side2.length) {
                side1.endsWith(side2.reversed())
            } else {
                side2.reversed().endsWith(side1)
            }
        }
    }

    private fun getMirrorIndex(pond: List<String>): Int {
        for (i in 1..<pond[0].length) {
            val mirrored = checkMirror(pond, i)
            if (mirrored) {
                return i
            }
        }

        val transposedPond1 = MutableList(pond[0].length) { mutableListOf<Char>() }
        pond.forEach { line ->
            line.forEachIndexed { indexChar, char ->
                transposedPond1[indexChar] += char
            }
        }
        val transposedPond2 = transposedPond1.map {
            it.joinToString("")
        }

        for (i in 1..<transposedPond2[0].length) {
            val mirrored = checkMirror(transposedPond2, i)
            if (mirrored) {
                return i * 100
            }
        }

        throw Exception("Unable to find mirror: ${pond[0]}")
    }

    fun run() {
//        val lines =  File("./src/${ this.javaClass.packageName }/example.txt").readLines()
        val lines =  File("./src/${ this.javaClass.packageName }/input.txt").readLines()

        val ponds: MutableList<MutableList<String>> = mutableListOf()
        ponds.add(mutableListOf())
        lines.forEach {
            if (it.isEmpty()) {
                ponds.add(mutableListOf())
            } else {
                ponds.last().add(it)
            }
        }

        val results = ponds.map { pond ->
            getMirrorIndex(pond)
        }

        val total = results.sum()
        println("total: ${ total }")
    }
}

class PartB {

    private fun checkMirror(pond: List<String>, index: Int): Boolean {
        return pond.all {
            val side1 = it.substring(0, index)
            val side2 = it.substring(index)

//            println("LINE: ${side1}-${side2}")
            if (side1.length > side2.length) {
                side1.endsWith(side2.reversed())
            } else {
                side2.reversed().endsWith(side1)
            }
        }
    }

    private fun getMirrorIndex(pond: List<String>, ignoreResult: Int = -1): Int {
        for (i in 1..<pond[0].length) {
            val mirrored = checkMirror(pond, i)
            if (mirrored && i != ignoreResult) {
                return i
            }
        }

        val transposedPond1 = MutableList(pond[0].length) { mutableListOf<Char>() }
        pond.forEach { line ->
            line.forEachIndexed { indexChar, char ->
                transposedPond1[indexChar] += char
            }
        }
        val transposedPond2 = transposedPond1.map {
            it.joinToString("")
        }

        for (i in 1..<transposedPond2[0].length) {
            val mirrored = checkMirror(transposedPond2, i)
            if (mirrored && (i * 100 != ignoreResult)) {
                return i * 100
            }
        }

        return 0
    }

    private fun findSmudge(pond: List<String>): Int {
        val oldMirrorValue = getMirrorIndex(pond)
        for (lineIndex in pond.indices) {
            for (charIndex in 0..<pond[0].length) {
                val newPond = pond.toMutableList()
                val current = newPond[lineIndex][charIndex]
                if (current == '.') {
                    newPond[lineIndex] = newPond[lineIndex].substring(0, charIndex) + '#' + newPond[lineIndex].substring(charIndex + 1)
                } else {
                    newPond[lineIndex] = newPond[lineIndex].substring(0, charIndex) + '.' + newPond[lineIndex].substring(charIndex + 1)
                }

                val result = getMirrorIndex(newPond, oldMirrorValue)
                if (result > 0) {
                    return result
                }
            }
        }

        throw Exception("Unable to find smudge: ${pond[0]}")
    }

    fun run() {
//        val lines =  File("./src/${ this.javaClass.packageName }/example.txt").readLines()
        val lines =  File("./src/${ this.javaClass.packageName }/input.txt").readLines()

        val ponds: MutableList<MutableList<String>> = mutableListOf()
        ponds.add(mutableListOf())
        lines.forEach {
            if (it.isEmpty()) {
                ponds.add(mutableListOf())
            } else {
                ponds.last().add(it)
            }
        }

        val results = ponds.map { pond ->
            findSmudge(pond)
        }

        val total = results.sum()
        println("total: ${ total }")
    }

}

fun main() {
//    PartA().run()
    PartB().run()
}