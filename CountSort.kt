package com.zyflool.kotlin

/**
 * nums: 要排序的数组
 * k: 数组内的数据是0～k的整数
 */
fun countSort(nums: IntArray, k: Int) {
    val count = IntArray(k+1){0}
    for ( e in nums )
        count[e]++
    for ( i in 1..k )
        count[i] += count[i-1]
    val backup = IntArray(nums.size+1){0}
    for ( j in nums.size-1 downTo 0) {
        backup[count[nums[j]]] = nums[j]
        count[nums[j]]--
    }
    for ( i in 1 until backup.size )
        nums[i-1] = backup[i]
}