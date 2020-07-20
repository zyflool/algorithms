package com.zyflool.kotlin

import kotlin.collections.ArrayList

fun bucketSort(nums: IntArray) {
    var max = nums[0]
    var min = nums[0]
    for (n in nums) {
        max = n.coerceAtLeast(max)
        min = n.coerceAtMost(min)
    }

    val bucketSize = (max-min)/(nums.size-1)
    val bucketNum = (max-min)/bucketSize+1

    val buckList = ArrayList<ArrayList<Int>>()

    // create bucket
    for (i in 0..bucketNum) {
        buckList.add(ArrayList())
    }

    // push into the bucket
    for (i in nums.indices) {
        val index = (nums[i] - min) / bucketSize
        buckList[index].add(nums[i])
    }

    var bucket: ArrayList<Int>
    var index = 0
    for (i in 0 until bucketNum) {
        bucket = buckList[i]
        insertSort(bucket)
        for (k in bucket) {
            nums[index++] = k
        }
    }
}

// 把桶內元素插入排序
fun insertSort(bucket: MutableList<Int>?) {
    for (i in 1 until bucket!!.size) {
        val temp = bucket[i]
        var j = i - 1
        while (j >= 0 && bucket[j] > temp) {
            bucket[j + 1] = bucket[j]
            j--
        }
        bucket[j + 1] = temp
    }
}