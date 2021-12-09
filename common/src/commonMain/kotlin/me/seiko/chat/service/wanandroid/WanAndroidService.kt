package me.seiko.chat.service.wanandroid

import io.ktor.client.HttpClient
import io.ktor.client.request.get
import me.seiko.chat.service.wanandroid.model.Article
import me.seiko.chat.service.wanandroid.model.Banner
import me.seiko.chat.service.wanandroid.model.Course
import me.seiko.chat.service.wanandroid.model.NaviItem

class WanAndroidService(
  private val httpClient: HttpClient
) {

  companion object {
    const val BASE_URL = "https://www.wanandroid.com/"
  }

  /**
   * 首页文章列表
   */
  suspend fun getIndexList(page: Int, pageSize: Int): List<Article> {
    return httpClient.getPageData("${BASE_URL}article/list/$page/json?page_size=$pageSize")
  }

  /**
   * 首页Banner
   */
  suspend fun getHomeBanner(): List<Banner> {
    return httpClient.getResponse("${BASE_URL}banner/json")
  }

  /**
   * 体系数据
   */
  suspend fun getCourseList(): List<Course> {
    return httpClient.getResponse("${BASE_URL}tree/json")
  }

  /**
   * 导航数据
   */
  suspend fun getNaviList(): List<NaviItem> {
    return httpClient.getResponse("${BASE_URL}navi/json")
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
