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
    }
}