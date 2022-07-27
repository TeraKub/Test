import java.util.*;

public class Main {
    private static int romanNumsCount = 0; // Счётчик введённых римских чисел

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Введите выражение:");
        String input = scanner.nextLine(); // Ввод данных с клавивтуры
        // Вызов метода calc и вывод в консоль возвращаемого результата
        try {
            System.out.println(calc(input));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String calc(String input) throws Exception {
        // Разбиение ввода в массив строк, разделяя элементы по знаку " "
        String[] splitIn = input.split(" ");
        // Проверка массива на нужное колличество элементов
        if (splitIn.length < 3){
            throw new Exception("Строка не является математической операцией");
        }
        if (splitIn.length > 3){
            throw new Exception("Формат математической операции не удовлетворяет заданию - два операнда и один оператор (+, -, /, *)");
        }
        // Получение операндов через вызов метода stringsToNumbers (отправляем строку -> получаем число)
        int num1 = stringsToNumbers(splitIn[0]);
        int num2 = stringsToNumbers(splitIn[2]);
        // Отработка исключения если из двух операндов только один определился как римское чило
        if (romanNumsCount == 1){
            throw  new Exception("Введены числа разных систем счисления");
        }
        // Получение оператора
        String operator = splitIn[1];
        // Собственно калькулирование :)
        int result = switch (operator) {
            case "+" -> num1 + num2;
            case "-" -> num1 - num2;
            case "*" -> num1 * num2;
            case "/" -> num1 / num2;
            default -> throw new Exception("Введен некорректный оператор");
        };
        // Отработка исключения если оба операнда римские, а результат математической операции меньше единицы
        if (romanNumsCount == 2 && result < 1){
            throw new Exception("В римской системе результат не должен быть меньше единицы");
        }
        // Возвращение римского аналога арабского числа из результата калькулирования через индекс елемента в массиве,
        // в случае если оба операнда были римские, в качестве результата
        if (romanNumsCount == 2){
            String[] romanOut = {" ",
                    "I", "II", "III", "IV", "V", "VI", "VII", "VIII", "IX", "X",
                    "XI", "XII", "XIII", "XIV", "XV", "XVI", "XVII", "XVIII", "XIX", "XX",
                    "XXI", "XXII", "XXIII", "XXIV", "XXV", "XXVI", "XXVII", "XXVIII", "XXIX", "XXX",
                    "XXXI", "XXXII", "XXXIII", "XXXIV", "XXXV", "XXXVI", "XXXVII", "XXXVIII", "XXXIX", "XL",
                    "XLI", "XLII", "XLIII", "XLIV", "XLV", "XLVI", "XLVII", "XLVIII", "XLIX", "L",
                    "LI", "LII", "LIII", "LIV", "LV", "LVI", "LVII", "LVIII", "LIX", "LX",
                    "LXI", "LXII", "LXIII", "LXIV", "LXV", "LXVI", "LXVII", "LXVIII", "LXIX", "LXX",
                    "LXXI", "LXXII", "LXXIII", "LXXIV", "LXXV", "LXXVI", "LXXVII", "LXXVIII", "LXXIX", "LXXX",
                    "LXXXI", "LXXXII", "LXXXIII", "LXXXIV", "LXXXV", "LXXXVI", "LXXXVII", "LXXXVIII", "LXXXIX", "XC",
                    "XCI", "XCII", "XCIII", "XCIV", "XCV", "XCVI", "XCVII", "XCVIII", "XCIX", "C"};
            return romanOut[result];
        }
        // Возвращение результата строкой, если операнды были арабские
        else {
            return String.valueOf(result);
        }
    }

    // Метод перевода опереандов из строк в числа
    static int stringsToNumbers(String operand) throws Exception {
        int num;
        try { // Выполняется если в строке арабское число
            num = Integer.parseInt(operand);
        } catch (NumberFormatException e){
            try { // Выполняется если в строке что-то отличное от арабского числа
                // Забирает из перечисления RomanIn арабский аналог римского числа через индекс елемента
                RomanIn romanIn = RomanIn.valueOf(operand);
                num = romanIn.ordinal() + 1;
                // Добовляет единицу к счётчику если операнд содержал римское число из представленных в RomanIn
                romanNumsCount++;
            } catch (Exception ex) { // Отработка исключения если введёный операнд не имеет соответствия в RomanIn
                throw new Exception("Можно использовать римские числа только от I до X");
            }
        }
        // Отработка исключения если введёное арабское число не входит в разрешённый диапазон
        if (num < 1 || num > 10){
            throw new Exception("Можно использовать арабские числа только от 1 до 10");
        }
        // Возвращение введённого строкой с клавиатуры операнда числом для последующих математических операций
        return num;
    }
}