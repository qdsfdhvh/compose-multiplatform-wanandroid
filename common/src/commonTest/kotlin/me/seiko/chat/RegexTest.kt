import me.seiko.chat.HTTP_REGEX
import kotlin.test.Test
import kotlin.test.assertFalse
import kotlin.test.assertTrue

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
  @Test
  fun http_regex() {
    assertTrue { HTTP_REGEX.matches("https://juejin.im/user/5a3ba9375188252bca050ade") }
    assertTrue { HTTP_REGEX.matches("https://developer.android.google.cn/") }
    assertTrue { HTTP_REGEX.matches("https://developer.android.google.cn/reference/kotlin/android/") }
    assertTrue { HTTP_REGEX.matches("https://developer.android.google.cn/reference/kotlin/android/widget/TextView?hl=en") }
    assertFalse { HTTP_REGEX.matches("www.aaa###.com") }
    assertFalse { HTTP_REGEX.matches("127.0.0.1") }
  }
}
