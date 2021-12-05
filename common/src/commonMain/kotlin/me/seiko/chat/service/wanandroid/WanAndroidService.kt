package me.seiko.chat.service.wanandroid

import io.ktor.client.HttpClient
import io.ktor.client.request.get
import me.seiko.chat.service.wanandroid.model.Article

class WanAndroidService(
  private val httpClient: HttpClient
) {

  companion object {
    const val BASE_URL = "https://www.wanandroid.com/"
  }

  suspend fun getIndexList(page: Int, pageSize: Int): List<Article> {
    return httpClient.getPageData("${BASE_URL}article/list/$page/json?page_size=$pageSize")
  }
}

private suspend inline fun <reified T> HttpClient.getPageData(url: String): List<T> {
  return getResponse<WanPageData<T>>(url).data
}

private suspend inline fun <reified T> HttpClient.getResponse(url: String): T {
  val response = get<WanResponse<T>>(url)
  if (response.errorCode == 0) {
    return response.data!!
  }
  throw RuntimeException(response.errorMsg)
}
