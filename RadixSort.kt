package com.zyflool.kotlin

/**
 * nums: 要排序的数组
 * Base: 数组中数据的进制
 */
fun radixSort(nums: IntArray, Base: Int) {
    var max: Int = Int.MIN_VALUE
    var times = 1

    for ( i in nums )
        max = i.coerceAtLeast(max)

    while ( max / times > 0 ) {

        var bucket = IntArray(Base){0}

        for ( e in nums )
            bucket[ (e / times) % Base ]++

        for ( i in 1 until Base )
            bucket[i] += bucket[i-1]

        val temp = IntArray(nums.size){0}
        for ( i in nums.size-1 downTo 0)
            temp[--bucket[(nums[i] / times) % Base]] =  nums[i]

        for ( i in nums.indices )
            nums[i] = temp[i]

        times *= Base
    }
}
