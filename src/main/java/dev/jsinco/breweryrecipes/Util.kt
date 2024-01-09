package dev.jsinco.breweryrecipes

import org.bukkit.ChatColor

object Util {

    lateinit var prefix: String

    fun loadUtils() {
        prefix = colorcode(BreweryRecipes.getInstance().config.getString("prefix")!!)
    }

    private const val WITH_DELIMITER = "((?<=%1\$s)|(?=%1\$s))"

    /**
     * @param text The string of text to apply color/effects to
     * @return Returns a string of text with color/effects applied
     */
    @JvmStatic
    fun colorcode(text: String): String {
        val texts = text.split(String.format(WITH_DELIMITER, "&").toRegex()).dropLastWhile { it.isEmpty() }
            .toTypedArray()
        val finalText = StringBuilder()
        var i = 0
        while (i < texts.size) {
            if (texts[i].equals("&", ignoreCase = true)) {
                //get the next string
                i++
                if (texts[i][0] == '#') {
                    finalText.append(net.md_5.bungee.api.ChatColor.of(texts[i].substring(0, 7)).toString() + texts[i].substring(7))
                } else {
                    finalText.append(ChatColor.translateAlternateColorCodes('&', "&" + texts[i]))
                }
            } else {
                finalText.append(texts[i])
            }
            i++
        }
        return finalText.toString()
    }


    @JvmStatic
    fun colorArrayList(list: List<String>): List<String>? {
        val coloredList: MutableList<String> = ArrayList()
        for (string in list) {
            coloredList.add(colorcode(string))
        }
        return coloredList
    }

    @JvmStatic
    fun colorArray(array: Array<String>): Array<String>? {
        for (i in array.indices) {
            array[i] = colorcode(array[i])
        }
        return array
    }
}