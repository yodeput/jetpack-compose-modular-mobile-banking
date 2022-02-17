package yodeput.mobile.banking.common.util

import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.input.OffsetMapping
import androidx.compose.ui.text.input.TransformedText
import androidx.compose.ui.text.input.VisualTransformation

class CurrencyVisualTransformation: VisualTransformation {
    override fun filter(text: AnnotatedString): TransformedText {
        // Make the string XXX-XXX-XXX
        val trimmed = if (text.text.length >= 9) text.text.substring(0..8) else text.text
        var output = ""
        var aa = arrayListOf<String>()
        for (i in trimmed.indices) {
            aa.add(trimmed[i].toString())
            if (i == 3) aa.add(i, ",")
                else if (i == 6)  aa.add(i+1, ",")
        }
        aa.forEach {
            output += it
        }

        // XXX,XXX,XXX

        // XXX, = 3
        // XXX,XXX, = 6

        /**
         * The offset works such that the translator ignores hyphens. Conversions
         * from original to transformed text works like this
        - 3rd character in the original text is the 4th in the transformed text
        - The 6th character in the original becomes the 8th
        In reverse, the conversion is such that
        - 10th Character in transformed becomes the 8th in original
        - 4th in transformed becomes 3rd in original
         */

        val cameroonNumberTranslator = object : OffsetMapping {
            override fun originalToTransformed(offset: Int): Int {

                 //[offset [0 - 2] remain the same]
                if (offset <= 3) return offset
                 //[3 - 5] transformed to [4 - 6] respectively
                if (offset <= 7) return offset + 1
                 //[6 - 8] transformed to [8 - 10] respectively
                if (offset <= 8) return offset + 2
                return 11 // Total number of digits, plus two hyphens
            }

            override fun transformedToOriginal(offset: Int): Int {
                if (offset <= 2) return offset
                if (offset <= 6) return offset - 1
                if (offset <= 10) return offset - 2
                return 9 // Total number of digits, without two hyphens

            }

        }

        return TransformedText(
            AnnotatedString(output),
            cameroonNumberTranslator)

    }

}