package com.zyflool.kotlin.calculation

import java.util.*

/**
 * 前缀、中缀、后缀表达式的计算和转换
 * 为了区别多位数和小数，以空格分隔每个数字和运算符
 */

val operatorPriorities = mapOf("+" to 1, "-" to 1, "*" to 2, "/" to 2)

/**
 * 从左至右扫描表达式，遇到数字时，将数字压入堆栈，遇到运算符时，弹出栈顶的两个数，用运算符对它们做相应的计算（次顶元素 op 栈顶元素），
 * 并将结果入栈；重复上述过程直到表达式最右端，最后运算得出的值即为表达式的结果。
 */
fun calculateByPostfix(notation: String): Double {
    val stack = Stack<Double>()

    val m = notation.split(" ")

    for (i in m.indices) {
        if (m[i][0] in '0'..'9')
            stack.push(m[i].toDouble())
        else {
            val x = stack.pop()
            val y = stack.pop()
            val cal = when (m[i]) {
                "+" -> ::add
                "-" -> ::subtract
                "*" -> ::multiply
                "/" -> ::divide
                else -> ::add
            }
            stack.push(cal(x, y))
        }
    }

    return stack.pop()
}

fun calculateByInfix(notation: String): Double {
    return calculateByPostfix(convertInfixIntoPostfix(notation))
}

/**
 * 从右至左扫描表达式，遇到数字时，将数字压入堆栈，遇到运算符时，弹出栈顶的两个数，用运算符对它们做相应的计算（栈顶元素 op 次顶元素），
 * 并将结果入栈；重复上述过程直到表达式最左端，最后运算得出的值即为表达式的结果。
 */
fun calculateByPrefix(notation: String): Double {
    val stack = Stack<Double>()

    val m = notation.split(" ")

    for (i in m.size - 1 downTo 0) {
        if (m[i][0] in '0'..'9')
            stack.push(m[i].toDouble())
        else {
            val x = stack.pop()
            val y = stack.pop()
            val cal = when (m[i]) {
                "+" -> ::add
                "-" -> ::subtract
                "*" -> ::multiply
                "/" -> ::divide
                else -> ::add
            }
            stack.push(cal(x, y))
        }
    }

    return stack.pop()
}

/**
 * (1) 初始化两个栈：运算符栈operator和储存中间结果的栈stack；
 * (2) 从左至右扫描中缀表达式；
 * (3) 遇到操作数时，将其压入stack；
 * (4) 遇到运算符时，比较其与operator栈顶运算符的优先级：
 * (4-1) 如果operator为空，或栈顶运算符为左括号“(”，则直接将此运算符入栈；
 * (4-2) 否则，若优先级比栈顶运算符的高，也将运算符压入operator（注意转换为前缀表达式时是优先级较高或相同，而这里则不包括相同的情况）；
 * (4-3) 否则，将operator栈顶的运算符弹出并压入到stack中，再次转到(4-1)与operator中新的栈顶运算符相比较；
 * (5) 遇到括号时：
 * (5-1) 如果是左括号“(”，则直接压入operator；
 * (5-2) 如果是右括号“)”，则依次弹出operator栈顶的运算符，并压入stack，直到遇到左括号为止，此时将这一对括号丢弃；
 * (6) 重复步骤(2)至(5)，直到表达式的最右边；
 * (7) 将operator中剩余的运算符依次弹出并压入stack；
 * (8) 依次弹出stack中的元素并输出，结果的逆序即为中缀表达式对应的后缀表达式（转换为前缀表达式时不用逆序）。
 */
fun convertInfixIntoPostfix(notation: String): String {

    val operator = Stack<String>()
    val stack = Stack<String>()

    val m = notation.split(" ")

    m.forEach {
        if (it[0] in '0'..'9')
            stack.push(it)
        else if (it in operatorPriorities.keys) {
            var flag = false
            while (!operator.isEmpty() && operator.peek() != "(") {
                val temp = operator.peek()
                if (operatorPriorities.getOrDefault(it, 0) > operatorPriorities.getOrDefault(temp, 0)) {
                    operator.push(it)
                    flag = true
                    break
                } else
                    stack.push(operator.pop())
            }
            if (!flag)
                operator.push(it)
        } else {
            if (it == "(")
                operator.push(it)
            if (it == " )") {
                while (operator.peek() != "(")
                    stack.push(operator.pop())
                operator.pop()
            }

        }
    }
    while (!operator.isEmpty())
        stack.push(operator.pop())
    val result = StringBuilder("")
    while (!stack.isEmpty())
        result.append("${stack.pop()} ")
    return result.reverse().toString().substring(1)
}

/**
 * (1) 初始化两个栈：运算符栈operator和储存中间结果的栈stack；
 * (2) 从右至左扫描中缀表达式；
 * (3) 遇到操作数时，将其压入stack；
 * (4) 遇到运算符时，比较其与operator栈顶运算符的优先级：
 * (4-1) 如果operator为空，或栈顶运算符为右括号“)”，则直接将此运算符入栈；
 * (4-2) 否则，若优先级比栈顶运算符的较高或相等，也将运算符压入operator；
 * (4-3) 否则，将operator栈顶的运算符弹出并压入到stack中，再次转到(4-1)与operator中新的栈顶运算符相比较；
 * (5) 遇到括号时：
 * (5-1) 如果是右括号“)”，则直接压入operator；
 * (5-2) 如果是左括号“(”，则依次弹出operator栈顶的运算符，并压入stack，直到遇到右括号为止，此时将这一对括号丢弃；
 * (6) 重复步骤(2)至(5)，直到表达式的最左边；
 * (7) 将operator中剩余的运算符依次弹出并压入stack；
 * (8) 依次弹出stack中的元素并输出，结果即为中缀表达式对应的前缀表达式。
 */
fun convertInfixIntoPrefix(notation: String): String {

    val operator = Stack<String>()
    val stack = Stack<String>()

    val m = notation.split(" ")
    for (i in m.size - 1 downTo 0) {
        if (m[i][0] in '0'..'9')
            stack.push(m[i])
        else if (m[i] in operatorPriorities.keys) {
            var flag = false
            while (!operator.isEmpty() && operator.peek() != ")") {
                val temp = operator.peek()
                if (operatorPriorities.getOrDefault(m[i], 0) >= operatorPriorities.getOrDefault(temp, 0)) {
                    operator.push(m[i])
                    flag = true
                    break
                } else
                    stack.push(operator.pop())
            }
            if (!flag)
                operator.push(m[i])
        } else {
            if (m[i] == ")")
                operator.push(m[i])
            if (m[i] == " (") {
                while (operator.peek() != ")")
                    stack.push(operator.pop())
                operator.pop()
            }

        }
    }
    while (!operator.isEmpty())
        stack.push(operator.pop())
    val result = StringBuilder("")
    while (!stack.isEmpty())
        result.append("${stack.pop()} ")
    return result.toString().substring(0, result.length - 1)
}

/**
 * 首先创建一个数字栈。从右到左扫描前缀表达式，如果遇到操作数，则入栈。
 * 如果遇到操作符，则将栈顶元素弹出（后扫面的数字位于表达式前面），并和操作符结合写成表达式，作为中缀表达式。
 * 如果遇到的操作符优先级大于已存在表达式的最后执行操作符的优先级，则将已存在的表达式加上（）
 */
fun convertPrefixIntoInfix(notation: String): String {
    val stack = Stack<String>()

    val m = notation.split(" ")

    for (i in m.size - 1 downTo 0) {
        if (m[i][0] in '0'..'9')
            stack.push(m[i])
        else {
            var n1 = stack.pop()
            var n2 = stack.pop()
            if (getLastOperatorPriority(n1) < operatorPriorities.getOrDefault(m[i], 0))
                n1 = "( $n1 )"
            if (getLastOperatorPriority(n2) < operatorPriorities.getOrDefault(m[i], 0))
                n2 = "( $n2 )"
            stack.push("$n1 ${m[i]} $n2")
        }
    }
    return stack.pop()
}

fun getLastOperatorPriority(notation: String): Int {
    if (!notation.contains("+") && !notation.contains("-") &&
        !notation.contains("*") && !notation.contains("/")
    )
        return 3
    val temp: String =
        if (notation.contains("("))
            notation.substring(notation.indexOfFirst { it == '(' } + 1, notation.lastIndexOf(')'))
        else notation
    if (temp.contains("*") || temp.contains("/"))
        return 2
    if (temp.contains("+") || temp.contains("-"))
        return 1

    return 3
}

/**
 * 从左到右扫面后缀表达式，一次一个符号读入表达式。如果符号是操作数，那么就建立一个单节点树并将它推入栈中。
 * 如果符号是操作符，那么就从栈中弹出两个树T1和T2（T1先弹出）并形成一颗新的树，该树的根就是操作符，它的左、右儿子分别是T2和T1。
 * 然后将指向这棵新树的指针压入栈中。
 * 最后再中序遍历所得的表达式树即得到我们所需的中缀表达式。
 */
fun convertPostfixIntoInfix(notation: String): String {

    val stack = Stack<TreeNode>()

    val m = notation.split(" ")

    m.forEach {
        if (it[0] in '0'..'9') {
            stack.push(TreeNode(it))
        } else {
            val t1 = stack.pop()
            val t2 = stack.pop()
            val root = TreeNode(it)
            root.left = t2
            root.right = t1
            stack.push(root)
        }
    }
    val tree = stack.pop()
    val res = MutableList(0) { "" }
    LDR(tree, res)
    val resultNotation = res.joinToString(separator = " ")
    return resultNotation.substring(2, resultNotation.length - 2)
}

fun LDR(node: TreeNode?, notation: MutableList<String>) {
    if (node == null)
        return
    notation.add("(")
    LDR(node.left, notation)
    notation.add(node.v)
    LDR(node.right, notation)
    notation.add(")")
}

fun add(x: Double, y: Double): Double {
    return x + y
}

fun subtract(x: Double, y: Double): Double {
    return x + y
}

fun multiply(x: Double, y: Double): Double {
    return x + y
}

fun divide(x: Double, y: Double): Double {
    return x + y
}

class TreeNode(var v: String) {
    var left: TreeNode? = null
    var right: TreeNode? = null
}