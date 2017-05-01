package com.sys1yagi.mastodon4j.api.method

import com.sys1yagi.mastodon4j.MastodonClient
import com.sys1yagi.mastodon4j.Parameter
import com.sys1yagi.mastodon4j.api.entity.Instance
import com.sys1yagi.mastodon4j.api.entity.Results
import com.sys1yagi.mastodon4j.api.exception.Mastodon4jRequestException

class Public(private val client: MastodonClient) {
    /**
     * GET /api/v1/instance
     * @see https://github.com/tootsuite/documentation/blob/master/Using-the-API/API.md#instances
     */
    @Throws(Mastodon4jRequestException::class)
    fun getInstance(): Instance {
        val response = client.get("instance")
        if (response.isSuccessful) {
            val body = response.body().string()
            return client.getSerializer().fromJson(
                    body,
                    Instance::class.java
            )
        } else {
            throw Mastodon4jRequestException(response)
        }
    }

    /**
     * GET /api/v1/search
     * q: The search query
     * resolve: Whether to resolve non-local accounts
     * @see https://github.com/tootsuite/documentation/blob/master/Using-the-API/API.md#search
     */
    @JvmOverloads
    @Throws(Mastodon4jRequestException::class)
    fun getSearch(query: String, resolve: Boolean = false): Results {
        val response = client.get(
                "search",
                Parameter().apply {
                    append("q", query)
                    if (resolve) {
                        append("resolve", resolve)
                    }
                }
        )

        if (response.isSuccessful) {
            val body = response.body().string()
            return client.getSerializer().fromJson<Results>(
                    body,
                    Results::class.java
            )
        } else {
            throw Mastodon4jRequestException(response)
        }
    }
}