package util

class RegexUtil {
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
    }
}