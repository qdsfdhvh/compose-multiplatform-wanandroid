package me.seiko.extension

import kotlinx.coroutines.CancellableContinuation
import kotlinx.coroutines.CompletionHandler
import kotlinx.coroutines.suspendCancellableCoroutine
import okhttp3.Call
import okhttp3.Callback
import okhttp3.Response
import okio.IOException
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException

suspend inline fun Call.await(): Response {
  return suspendCancellableCoroutine { continuation ->
    val callback = ContinuationCallback(this, continuation)
    enqueue(callback)
    continuation.invokeOnCancellation(callback)
  }
}

class ContinuationCallback constructor(
  private val call: Call,
  private val continuation: CancellableContinuation<Response>
) : Callback, CompletionHandler {

  override fun onResponse(call: Call, response: Response) {
    continuation.resume(response)
  }

  override fun onFailure(call: Call, e: IOException) {
    if (!call.isCanceled()) {
      continuation.resumeWithException(e)
    }
  }

  override fun invoke(cause: Throwable?) {
    try {
      call.cancel()
    } catch (_: Throwable) {
    }
  }
}