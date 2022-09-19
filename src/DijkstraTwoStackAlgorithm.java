
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.Stack;

/* - - - - - - - - - - - - - -  HOW DOES DIJKSTRA'S TWO STACK ALGORITHM WORK - - - - - - - - - - - - - - - -
take an arithmetic expression, with PROPERLY BALANCED parentheses
examples:
    ((2 + 5) + (4 + 3))
    ( ( 14 + 150 ) / 2 )
    (((3*6)/2)*(5*2))
Read the expression character by character, if the character is a number, push it onto the value stack and if an operator,
push it onto the operator stack. Ignore left parentheses and when right parentheses are encountered:
- pop an operator and two values (from their respective stacks)
- perform an operation with those values (e.g. 4 + 3)
- push the result onto the value stack
- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
*/

/* - - - - - - - - - - - - - - - - - - - - HOW DOES THIS PROGRAM WORK - - - - - - - - - - - - - - - - - - - - - -
There are no restrictions for entering UNBALANCED parentheses or wrong characters (characters that aren't numbers, round brackets or operations + - * /)
or incorrect expressions (example dividing with 0). Follow the console during the application run to see what is happening with the characters and the stacks.
Have fun!
*/

public class DijkstraTwoStackAlgorithm {

    class ArrayStack<Item> {

        private Item[] stack;
        private int length;

        public boolean isEmpty() {
            return length == 0;
        }

        public int size() {
            return length;
        }

        public void push(Item item) {
            stack[length++] = item;
        }

        public Item pop() {
            if (isEmpty()) throw new NoSuchElementException("No top element meaning the stack is empty.");
            Item item = stack[length - 1];
            stack[length - 1] = null;
            length--;
            return item;
        }
    }

    public static int calculate(String expression){
        Stack<String> operators = new Stack<String>();
        Stack<Integer> values = new Stack<Integer>();

        String[] array = new String[expression.length()];
        for(int i = 0; i < expression.length(); i++){
            array[i] = String.valueOf(expression.charAt(i));
        }

        int i = 0;
        int currentNum1, currentNum2, temporary, result;

        wait(1000);

        while(i < expression.length()){

            if(array[i].equals(" ")){
                i++;
                continue;
            }else if(array[i].equals("(")){
                System.out.println("Ignoring '(' character");
                i++;
                wait(1000);
                continue;
            }

            if(array[i].equals(")")){
                System.out.println("-- Values Stack --");
                wait(1000);
                for(int j = values.size() - 1; j >= 0; j--) {
                    System.out.println(values.get(j));
                    wait(1000);
                }
                wait(2000);

                System.out.println("-- Operators Stack --");
                wait(1000);
                for(int j = operators.size() - 1; j >= 0; j--){
                    System.out.println(operators.get(j));
                    wait(1000);
                }
                wait(2000);

                currentNum2 = values.get(values.size() - 1);
                values.pop();
                currentNum1 = values.get(values.size() - 1);
                values.pop();


                if(operators.get(operators.size() - 1).equals("+")) {
                    temporary = currentNum1 + currentNum2;
                    values.push(temporary);
                    operators.pop();
                    System.out.println("Removing number " + currentNum2 + " and " + currentNum1 + " from values stack.");
                    wait(1000);
                    System.out.println("Removing operator '+' from operators stack");
                    wait(1000);
                    System.out.println("Adding number "+ temporary + " to values stack ("+ currentNum1 +"+"+ currentNum2 +"="+ temporary +")");
                    wait(2000);
                } else if(operators.get(operators.size() - 1).equals("-")) {
                    temporary = currentNum1 - currentNum2;
                    values.push(temporary);
                    operators.pop();
                    System.out.println("Removing number " + currentNum2 + " and " + currentNum1 + " from values stack.");
                    wait(1000);
                    System.out.println("Removing operator '-' from operators stack");
                    wait(1000);
                    System.out.println("Adding number "+ temporary + " to values stack ("+ currentNum1 +"-"+ currentNum2 +"="+ temporary +")");
                    wait(2000);
                } else if(operators.get(operators.size() - 1).equals("*")) {
                    temporary = currentNum1 * currentNum2;
                    values.push(temporary);
                    operators.pop();
                    System.out.println("Removing number " + currentNum2 + " and " + currentNum1 + " from values stack.");
                    wait(1000);
                    System.out.println("Removing operator '*' from operators stack");
                    wait(1000);
                    System.out.println("Adding number "+ temporary + " to values stack ("+ currentNum1 +"*"+ currentNum2 +"="+ temporary +")");
                    wait(2000);
                } else if(operators.get(operators.size() - 1).equals("/")) {
                    temporary = currentNum1 / currentNum2;
                    values.push(temporary);
                    operators.pop();
                    System.out.println("Removing number " + currentNum2 + " and " + currentNum1 + " from values stack.");
                    wait(1000);
                    System.out.println("Removing operator '/' from operators stack");
                    wait(1000);
                    System.out.println("Adding number "+ temporary + " to values stack ("+ currentNum1 +"/"+ currentNum2 +"="+ temporary +")");
                    wait(2000);
                }
                i++;
                System.out.println("-- -- -- -- -- --");
                System.out.println("Expression: " + expression);
                wait(1000);
                continue;
            }

            try{
                result = Integer.parseInt(array[i]);
                values.push(result);
                while(true){
                    try{
                        Integer.parseInt(array[i+1]);
                    } catch(NumberFormatException e){
                        break;
                    }
                    result = (result * 10) + Integer.parseInt(array[i+1]);
                    i++;
                }
                values.pop();
                values.push(result);
                System.out.println("Adding "+ result + " to values stack");
                wait(2000);
            } catch(NumberFormatException e){
                operators.push(array[i]);
                System.out.println("Adding "+ array[i] + " to operators stack");
                wait(2000);
            }
            i++;
        }

        int sum = 0;
        int j = 0;
        while(j < values.size()){
            sum += values.get(j);
            j++;
        }

        return sum;
    }

    public static int userInput(){
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter your expression: ");
        String input = scanner.nextLine();
        return calculate(input);
    }

    public static void wait(int ms){
        try {
            Thread.sleep(ms);
        } catch (InterruptedException e) {
            System.out.println(e);
        }
    }


    public static void main(String[] args) {
        System.out.println("The result is: " + userInput());
    }
}