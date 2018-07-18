package com.jags.arunkumar.androidtest.Network.NetworkUtil

object CacheConfig {
    /** cache file name */
    const val cacheFileName = "inkstoneHttpCache"

    /** Cache 50 MB */
    const val httpCacheSize: Long = 50 * 1024 * 1024 // 50 MB

    /** url cache the time 1 month */
    const val cacheTime = 2419200
    val timeout = 60L

    /** db content expire duration */
    // TODO: update the expire date in future
    val dbCacheDurationInSeconds = 60 * 60 * 24 * 7 * 2 // 2 weeks

    /** image cache size */
    val frescoMainCache: Long = 200 * 1024 * 1024 // 200 MB
    val frescoSmallCache: Long = 50 * 1024 * 1024

    /** pager fragment cache count */
    val viewPagerFragmentCacheCount = 2

    /** video loaded buffer cache */
    val videoCacheFileName = "inkstoneVideoCache"
    val maxVideoCacheSize: Long = 1000 * 1024 * 1024 // 1 GB
    val maxVideoFileSize: Long = 1500 * 1024 * 1024 // 1.5 GB
    val minVideoBufferDuration: Int = 3 * 60 * 1000 // 3 minutes
    val maxVideoBufferDuration: Int = 5 * 60 * 1000 // 5 minutes
}