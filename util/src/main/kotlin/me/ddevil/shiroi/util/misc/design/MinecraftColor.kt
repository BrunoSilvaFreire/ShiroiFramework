package me.ddevil.shiroi.util.misc.design

import com.google.common.collect.Maps
import java.awt.Color
import java.util.regex.Pattern

/**
 * Created by bruno on 11/10/2016.
 */
enum class MinecraftColor
/**
 * Checks if this code is a format code as opposed to a design code.

 * @return whether this MinecraftColor is a format code
 */(code: Char, val color: Color) {
    /**
     * Represents black
     */
    BLACK('0', Color(0, 0, 0)),
    /**
     * Represents dark blue
     */
    DARK_BLUE('1', Color(0, 0, 128)),

    /**
     * Represents dark green
     */
    DARK_GREEN('2', Color(0, 128, 0)),

    /**
     * Represents dark blue (aqua)
     */
    DARK_AQUA('3', Color(0, 128, 128)),

    /**
     * Represents dark red
     */
    DARK_RED('4', Color(128, 0, 0)),

    /**
     * Represents dark purple
     */
    DARK_PURPLE('5', Color(128, 0, 128)),

    /**
     * Represents gold
     */
    GOLD('6', Color(255, 165, 0)),

    /**
     * Represents gray
     */
    GRAY('7', Color(80, 80, 80)),

    /**
     * Represents dark gray
     */
    DARK_GRAY('8', Color(35, 35, 35)),

    /**
     * Represents blue
     */
    BLUE('9', Color(0, 0, 255)),

    /**
     * Represents green
     */
    GREEN('a', Color(0, 255, 0)),

    /**
     * Represents aqua
     */
    AQUA('b', Color(0, 255, 255)),

    /**
     * Represents red
     */
    RED('c', Color(255, 0, 0)),

    /**
     * Represents light purple
     */
    LIGHT_PURPLE('d', Color(255, 0, 255)),

    /**
     * Represents yellow
     */
    YELLOW('e', Color(255, 255, 0)),

    /**
     * Represents white
     */
    WHITE('f', Color(255, 255, 255));

    /**
     * Gets the char value associated with this design

     * @return A char value of this design code
     */
    val char: Char = code

    /**
     * @return The color's alternative color, for example, [.LIGHT_PURPLE] alternative color is [.DARK_PURPLE]
     */
    val alternativeColor: MinecraftColor?
        get() {
            when (this) {
                AQUA -> return DARK_AQUA
                BLACK -> return WHITE
                BLUE -> return DARK_BLUE
                DARK_AQUA -> return AQUA
                DARK_BLUE -> return BLUE
                DARK_GRAY -> return GRAY
                DARK_GREEN -> return GREEN
                DARK_PURPLE -> return LIGHT_PURPLE
                GOLD -> return YELLOW
                DARK_RED -> return RED
                GRAY -> return DARK_GRAY
                GREEN -> return DARK_GREEN
                LIGHT_PURPLE -> return DARK_PURPLE
                RED -> return DARK_RED
                YELLOW -> return GOLD
                WHITE -> return BLACK
                else -> return null
            }
        }

    override fun toString() = "$COLOR_CHAR$char"

    companion object {
        val ALPHA = Color(0, 0, 0, 0)
        /**
         * The special character which prefixes all chat colour codes. Use this if
         * you need to dynamically convert colour codes from your custom format.
         */
        val COLOR_CHAR = '\u00A7'
        private val STRIP_COLOR_PATTERN = Pattern.compile("[&§][0-9a-fA-FR]")
        private val STRIP_FORMAT_PATTERN = Pattern.compile("[&§][k-oK-OrR]")
        private val BY_CHAR = loadByChar()

        private fun loadByChar(): Map<Char, MinecraftColor> {
            val map: MutableMap<Char, MinecraftColor> = Maps.newHashMap()
            for (minecraftColor in MinecraftColor.values()) {
                map.put(minecraftColor.char, minecraftColor)
            }
            return map
        }

        /**
         * Gets the design represented by the specified design code

         * @param code Code to check
         * *
         * @return Associative [MinecraftColor] with the given code,
         * * or null if it doesn't exist
         */
        fun getByString(code: Char): MinecraftColor {
            return BY_CHAR[code] ?: throw IllegalArgumentException("Unknown code '$code'!")
        }

        /**
         * Gets the design represented by the specified design code

         * @param code Code to check
         * *
         * @return Associative [MinecraftColor] with the given code,
         * * or null if it doesn't exist
         */
        fun getByString(code: String): MinecraftColor = if (code.length > 1) MinecraftColor.valueOf(code) else MinecraftColor.getByString(
                code[0])

        /**
         * Strips the given message of all design codes

         * @param input String to strip of design
         * *
         * @return A copy of the input string, without any coloring
         */
        fun stripAll(input: String) = stripColor(stripFormat(input))

        fun stripColor(input: String) = STRIP_COLOR_PATTERN.matcher(input).replaceAll("")!!

        @JvmOverloads
        fun getColors(input: String, altColorChar: Char = '&'): List<MinecraftColor> {
            val list = ArrayList<MinecraftColor>()
            val b = input.toCharArray()
            for (i in 0 .. b.size - 1 - 1) {
                val next = b[i + 1]
                if (b[i] == altColorChar && "0123456789AaBbCcDdEeFfKkLlMmNnOoRr".indexOf(next) > -1) {
                    list += MinecraftColor.getByString(next)
                }
            }
            return list
        }

        fun stripFormat(input: String) = STRIP_FORMAT_PATTERN.matcher(input).replaceAll("")!!

        /**
         * Translates a string using an alternate design code character into a
         * string that uses the internal MinecraftColor.COLOR_CODE design code
         * character. The alternate design code character will only be replaced if
         * it is immediately followed by 0-9, A-F, a-f, K-O, k-o, R or r.

         * @param altColorChar    The alternate design code character to replace. Ex: &amp;
         * *
         * @param textToTranslate Text containing the alternate design code character.
         * *
         * @return Text containing the MinecraftColor.COLOR_CODE design code character.
         */
        @JvmOverloads
        fun translateAlternateColorCodes(textToTranslate: String, altColorChar: Char = '&'): String {
            val b = textToTranslate.toCharArray()
            for (i in 0..b.size - 1 - 1) {
                if (b[i] == altColorChar && "0123456789AaBbCcDdEeFfKkLlMmNnOoRr".indexOf(b[i + 1]) > -1) {
                    b[i] = MinecraftColor.COLOR_CHAR
                    b[i + 1] = Character.toLowerCase(b[i + 1])
                }
            }
            return String(b)
        }

        /**
         * Gets the ChatColors used at the end of the given input string.

         * @param input Input string to retrieve the colors from.
         * *
         * @return Any remaining ChatColors to pass onto the next line.
         */
        fun getLastColors(input: String): String {
            var result = ""
            val length = input.length

            // Search backwards from the end as it is faster
            for (index in length - 1 downTo -1 + 1) {
                val section = input[index]
                if (section == COLOR_CHAR && index < length - 1) {
                    val c = input[index + 1]
                    val color = getByString(c)
                    result = color.toString() + result
                }
            }
            return result
        }
    }
}
