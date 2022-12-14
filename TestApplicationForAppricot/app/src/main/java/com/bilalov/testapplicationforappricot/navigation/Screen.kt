package com.bilalov.testapplicationforappricot.navigation

sealed class Screen(val screenName: String) {

    object Main : Screen("main")
    object SecondView : Screen("second")
    object InfoView : Screen("infoView")



    fun withArgs(vararg args: String): String {
        return buildString {
            append(screenName)
            args.forEach { args ->
                append("/$args")
            }
        }
    }
}