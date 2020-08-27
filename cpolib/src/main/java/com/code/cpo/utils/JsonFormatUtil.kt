package com.code.cpo.utils

import android.text.TextUtils

/**
 *  author : balance
 *  date : 2020/8/24 6:38 PM
 *  description :
 */
class JsonFormatUtil {
    companion object {
        fun formatString(text: String): String {
            if (text.isNullOrEmpty()) {
                return ""
            }
            val json = StringBuilder()
            var indentString = ""
            for (i in 0 until text.length) {
                val letter = text[i]
                when (letter) {
                    '{', '[' -> {
                        json.append(
                            """
                                
                                $indentString$letter
                                
                                """.trimIndent()
                        )
                        indentString = indentString + "\t"
                        json.append(indentString)
                    }
                    '}', ']' -> {
                        indentString = indentString.replaceFirst("\t".toRegex(), "")
                        json.append(
                            """
                                
                                $indentString$letter
                                """.trimIndent()
                        )
                    }
                    ',' -> json.append(
                        """
                            $letter
                            $indentString
                            """.trimIndent()
                    )
                    else -> json.append(letter)
                }
            }
            return json.toString()
        }
    }


}