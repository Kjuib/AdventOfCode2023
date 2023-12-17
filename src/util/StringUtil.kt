package util

class StringUtil {
    companion object {
        fun parseInt(regex: String, text: String, defaultValue: Int? = null): Int {
            return parseInt(Regex(regex), text, defaultValue)
        }

        fun parseInt(regex: Regex, text: String, defaultValue: Int? = null): Int {
            val value = regex.find(text)?.groups?.get(1)?.value
            if (value == null) {
                if (defaultValue == null) {
                    throw Exception("Regex Mismatch: `${ regex }` on `${ text }`")
                } else {
                    return defaultValue
                }
            } else {
                return Integer.valueOf(value)
            }
        }

        fun parseInt(text: String, delimiter: String = " "): List<Int> {
            return text.split(delimiter).mapNotNull {
                val num = it.trim()
                if (num.isEmpty()) {
                    null
                } else {
                    Integer.valueOf(num)
                }
            }
        }

        fun parseLong(text: String, delimiter: String = " "): List<Long> {
            return text.split(delimiter).mapNotNull {
                val num = it.trim()
                if (num.isEmpty()) {
                    null
                } else {
                    num.toLong()
                }
            }
        }

        fun parseGraph(lines: List<String>): MutableList<MutableList<String>> {
            return lines.map {line ->
                line
                    .split("")
                    .filter { it.isNotEmpty() }
                    .toMutableList()
            }.toMutableList()
        }

        fun parseGraphInt(lines: List<String>): MutableList<MutableList<Int>> {
            return lines.map {line ->
                line
                    .split("")
                    .filter { it.isNotEmpty() }
                    .map { it.toInt() }
                    .toMutableList()
            }.toMutableList()
        }

        fun printGraph(label: String, graph: List<List<Any>>) {
            val lines = graph.map {
                it.joinToString(" ")
            }
            println("${label}:\n${lines.joinToString("\n")}")
        }

        fun printList(label: String, list: List<Any>) {
            println("${label}:\n${list.joinToString("\n")}")
        }
    }
}