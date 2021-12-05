package me.seiko.chat.service.wanandroid

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class WanResponse<T>(
  val data: T?,
  val errorCode: Int,
  val errorMsg: String
)

@Serializable
data class WanPageData<T>(
  val curPage: Int,
  val offset: Int,
  val over: Boolean,
  val pageCount: Int,
  val size: Int,
  val total: Int,
  @SerialName("datas") val data: List<T>
)
