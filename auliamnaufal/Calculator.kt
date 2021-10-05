fun main() {
	val calculator = Calculator()
}

class Calculator() {
	fun summation(a: Int, b: Int): Int {
		return a + b
	}

	fun subtraction(a: Int, b: Int): Int {
		return a - b
	}

	fun multiplication(a: Int, b: Int): Int {
		return a * b
	}

	fun division(a: Int, b: Int): Int {
		return a / b
	}

	fun bmi(weight: Double, height: Double): Double {
		return weight / (height * height) * 10000.0
	}

	fun isEven(num: Int): Boolean {
		return num % 2 == 0
	}

	fun isOdd(num: Int): Boolean {
		return num % 2 != 0
	}

	fun getRandomNumberWithRange(start: Int, end: Int): Int {
		return (start..end).random()
	}

	fun getRandomNumberWithoutRange(): Int {
		return (0..10000000).random()
	}

	fun highestNumberInArray(arr: Array<Int>): Int {
		return arr.maxOrNull() ?: 0
	}
}