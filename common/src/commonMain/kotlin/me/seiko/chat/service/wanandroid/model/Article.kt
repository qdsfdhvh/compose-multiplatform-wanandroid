package me.seiko.chat.service.wanandroid.model

import kotlinx.serialization.Serializable

@Serializable
data class Article(
  var apkLink: String = "",
  var audit: Int = -1,
  var author: String = "",
  var canEdit: Boolean = false,
  var chapterId: Int = -1,
  var chapterName: String = "",
  var collect: Boolean = false,
  var courseId: Int = -1,
  var desc: String = "",
  var descMd: String = "",
  var envelopePic: String = "",
  var fresh: Boolean = false,
  var host: String = "",
  var id: Int = -1,
  var link: String = "",
  var niceDate: String = "",
  var niceShareDate: String = "",
  var origin: String = "",
  var prefix: String = "",
  var projectLink: String = "",
  var publishTime: Long = 0L,
  var realSuperChapterId: Int = -1,
  var selfVisible: Int = -1,
  var shareDate: Long = 0L,
  var shareUser: String = "",
  var superChapterId: Int = -1,
  var superChapterName: String = "",
  var tags: List<ArticleTag> = emptyList(),
  var title: String = "",
  var type: Int = -1,
  var userId: Int = -1,
  var visible: Int = -1,
  var zan: Int = -1
) {

  @Serializable
  data class ArticleTag(
    var name: String,
    var url: String
  )
}
