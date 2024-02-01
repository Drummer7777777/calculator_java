import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Main {
    public static Map<String, Integer> dictionary = new HashMap<String, Integer>();
    public static String[] numbers_string;
    public static void main(String[] args) throws Exception {
        dictionary.put("I", 1);
        dictionary.put("V", 5);
        dictionary.put("X", 10);
        dictionary.put("L", 50);
        dictionary.put("C", 100);
        Integer value = dictionary.get("L");
        Scanner input_text = new Scanner(System.in);
        String input = input_text.nextLine().trim();
        calc(input);
    }
    public static String calc(String input) throws Exception {
        char[] array_input = input.toCharArray();
        boolean roman = false;
        boolean arabic = false;
        String number_1_string = "";
        String number_2_string = "";
        String operation = "";
        //разбор ввода посимвольно
        for (char c : array_input) {
            if (dictionary.containsKey(Character.toString(c))) {
                roman = true;
                if (operation == "") {
                    number_1_string += dictionary.get(Character.toString(c));
                }
                else {
                    number_2_string += dictionary.get(Character.toString(c));
                }
            }
            if (Character.isDigit(c)) {
                arabic = true;
                if (operation == "") {
                    number_1_string += Character.toString(c);
                }
                else {
                    number_2_string += Character.toString(c);
                }
            }
            if (c == '.' || c == ','){
                throw new Exception("throws Exception //т.к. Калькулятор умеет работать только с целыми числами.");
            }
            if (roman && arabic) {
                throw new Exception("throws Exception //т.к. используются одновременно разные системы счисления");
            }
            if (!Character.isDigit(c) && !dictionary.containsKey(Character.toString(c))){
                if (operation=="" && (c == '+' || c == '-' || c == '*' || c == '/')){
                    operation = Character.toString(c);
                }
                else{
                    throw new Exception("throws Exception //т.к. формат математической операции не удовлетворяет " +
                            "заданию - два операнда и один оператор (+, -, /, *)");
                }
            }
        }
        if (number_2_string == ""){
            throw new Exception("throws Exception //т.к. строка не является математической операцией");
        }
        if (roman == true){
            number_1_string = parse_roman(number_1_string);
            number_2_string = parse_roman(number_2_string);
        }
        int answer = 0;
        if ((Integer.valueOf(number_1_string)>0 && Integer.valueOf(number_1_string)<=10) && (Integer.valueOf(number_2_string)>0 && Integer.valueOf(number_2_string)<=10)) {
            if (operation.equals("+")) {
                answer = Integer.valueOf(number_1_string) + Integer.valueOf(number_2_string);
            }
            if (operation.equals("-")) {
                answer = Integer.valueOf(number_1_string) - Integer.valueOf(number_2_string);
            }
            if (operation.equals("*")) {
                answer = Integer.valueOf(number_1_string) * Integer.valueOf(number_2_string);
            }
            if (operation.equals("/")) {
                answer = Integer.valueOf(number_1_string) / Integer.valueOf(number_2_string);
            }
            if (roman == true){
                System.out.println(parse_in_roman(answer));
            }
            else{
                System.out.println(answer);
            }
        }
        else {
            throw new Exception("throws Exception //т.к. Калькулятор должен принимать на вход числа от 1 до 10 включительно, не более.");
        }
        if (answer < 1){
            throw new Exception("throws Exception //т.к. Результатом работы калькулятора с римскими числами могут быть только положительные числа.");
        }
        return null;
    }

    //перевод арабских в римские
    public static String parse_in_roman(int number) {
        int ratio = 0;
        int[] roman_dig = new int[] {100,50,10,5,1};
        String[] roman_str = new String[] {"C","L","X","V","I"};
        String answer_roman = "";
        for (int i = 0; i < roman_dig.length; i++) {
            int value = roman_dig[i];
            ratio = number / value;
            if (ratio > 3){
                if (answer_roman.contains(String.valueOf(roman_str[i-1].charAt(0)))){
                    answer_roman=answer_roman.replace(String.valueOf(roman_str[i-1].charAt(0)),(String.valueOf(roman_str[i].charAt(0))+String.valueOf(roman_str[i-2].charAt(0))));
                }
                else {
                    answer_roman += String.valueOf(roman_str[i - 1].charAt(0));
                }
                number -= value * ratio;
                continue;
            }
            if (ratio > 0){
                answer_roman += String.valueOf(roman_str[i].charAt(0)).repeat(ratio);
                number -= value * ratio;
            }
        }
        return answer_roman;
    }

    //перевод римских в арабские
    public static String parse_roman(String number){
        int number_int = 0;
        int count_zero = 0;
        int last_c = 0;
        int int_c;
        char[] charArray = number.toCharArray();
        for (int i = charArray.length-1; i >= 0;  i--) {
            char c = charArray[i];
            int_c = Integer.parseInt(Character.toString(c));
            if (int_c == 0){
                count_zero += 1;
                continue;
            }
            if (count_zero > 0){
                int_c *= 10;
                count_zero = 0;
            }
            if (last_c == 0){
                number_int+=int_c;
                last_c = int_c;
            }
            else {
                if (last_c <= int_c) {
                    number_int += int_c;
                    last_c = int_c;
                }
                if (last_c > int_c) {
                    number_int -= int_c;
                }
            }
        }
        return String.valueOf(number_int);
    }
}
