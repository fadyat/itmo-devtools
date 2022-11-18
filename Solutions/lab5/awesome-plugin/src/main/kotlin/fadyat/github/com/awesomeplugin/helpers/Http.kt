package fadyat.github.com.awesomeplugin.helpers

import java.net.URI
import java.net.http.HttpClient
import java.net.http.HttpRequest
import java.net.http.HttpResponse


enum class HttpMethod {
    GET, POST, PUT, DELETE,
}

fun doRequest(
    method: HttpMethod,
    url: String,
): HttpResponse<String> {
    val client = HttpClient.newHttpClient()
    val requestBuilder = HttpRequest.newBuilder()
        .uri(URI.create(url))
        .method(method.name, HttpRequest.BodyPublishers.noBody())

    val request = requestBuilder.build()
    return client.send(request, HttpResponse.BodyHandlers.ofString())
}
