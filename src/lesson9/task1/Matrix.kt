@file:Suppress("UNUSED_PARAMETER", "unused")
package lesson9.task1

import java.util.*

/**
 * Ячейка матрицы: row = ряд, column = колонка
 */
data class Cell(val row: Int, val column: Int)

/**
 * Интерфейс, описывающий возможности матрицы. E = тип элемента матрицы
 */
interface Matrix<E> {
    /** Высота */
    val height: Int

    /** Ширина */
    val width: Int

    /**
     * Доступ к ячейке.
     * Методы могут бросить исключение, если ячейка не существует или пуста
     */
    operator fun get(row: Int, column: Int): E

    operator fun get(cell: Cell): E

    /**
     * Запись в ячейку.
     * Методы могут бросить исключение, если ячейка не существует
     */
    operator fun set(row: Int, column: Int, value: E)

    operator fun set(cell: Cell, value: E)
}

/**
 * Простая
 *
 * Метод для создания матрицы, должен вернуть РЕАЛИЗАЦИЮ Matrix<E>.
 * height = высота, width = ширина, e = чем заполнить элементы.
 * Бросить исключение IllegalArgumentException, если height или width <= 0.
 */
fun <E> createMatrix(height: Int, width: Int, e: E): Matrix<E> =
        if (height <= 0 || width <= 0) throw IllegalArgumentException()
        else MatrixImpl(height, width, e)

/**
 * Средняя сложность
 *
 * Реализация интерфейса "матрица"
 */
class MatrixImpl<E>(override val height: Int, override val width: Int, e: E) : Matrix<E> {

    private val array = Array(height) { Array<Any?>(width) { e } }

    init {
        for (i in 0 until height)
            for (j in 0 until width)
                array[i][j] = e

    }

    override fun get(row: Int, column: Int): E =
            if (row in 0 until height && column in 0 until width && array[row][column] != null) array[row][column] as E
            else throw IllegalArgumentException()

    override fun get(cell: Cell): E = get(cell.row, cell.column)

    override fun set(row: Int, column: Int, value: E) {
        if (row in 0..height && column in 0..width) array[row][column] = value
        else throw IllegalArgumentException()
    }

    override fun set(cell: Cell, value: E) {
        set(cell.row, cell.column, value)
    }

    private fun equalsArray(other: MatrixImpl<*>): Boolean {
        this.array.forEachIndexed { iRow, row ->
            row.forEachIndexed { jColumn, value ->
                if (value != other[iRow, jColumn])
                    return false
            }
        }
        return true
    }

    override fun equals(other: Any?) =
            other is MatrixImpl<*> &&
                    height == other.height &&
                    width == other.width &&
                    equalsArray(other)

    override fun hashCode(): Int {
        var result = height
        result = 31 * result + width
        result = 31 * result + array.contentHashCode()
        return result
    }

    override fun toString(): String {
        val str = mutableListOf<String>()
        array.forEach {
            str += it.joinToString(", ", prefix = "[", postfix = "]")
        }
        return str.joinToString(", ", prefix = "[", postfix = "]")
    }

}

