package com.vankorno.vankornohelpers.navig

sealed interface Screen { val name: String }

abstract class BaseScreen(override val name: String) : Screen

object ScrHome : BaseScreen("Home")
object ScrSett : BaseScreen("Sett")






