package me.seiko.chat

val HTTP_REGEX = "((https|http)://(([a-z0-9]+[.])|(www.))?)\\w+[.|/]([a-z0-9]*)?[[.]([a-z0-9]{0,})]+((/[\\S&&[^,;\u4E00-\u9FA5]]+)+)?([.][a-z0-9]*+|/?)".toRegex()
