public class TwoDimensionalArray {

    public static char[][] getTwoDimensionalArray(int size) {
        char[][] twoDimArray = new char[size][size];
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (i + j == size - 1 || i == j) {
                    twoDimArray[i][j] = 'X';
                } else {
                    twoDimArray[i][j] = ' ';
                }
            }
        }
        return twoDimArray;
    }
}
//Написать метод, который создаст двумерный массив char заданного размера.
// массив должен содержать символ symbol по диагоналям, пример для size = 3
// [X,  , X]
// [ , X,  ]
// [X,  , X]


