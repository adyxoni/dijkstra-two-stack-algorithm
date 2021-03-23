
import java.util.NoSuchElementException;
import java.util.Stack;

/* - - - - - - - - - - - - - -  HOW DOES DIJKSTRA'S TWO STACK ALGORITHM WORK - - - - - - - - - - - - - - - -
take an arithmetic expression, with PROPERLY BALANCED parentheses
example: ((2 + 5) + (4 + 3))
Read the expression character by character, if the character is a number, push it onto the value stack and if an operator,
push it onto the operator stack. Ignore left parentheses and when right parentheses are encountered:
- pop an operator and two values (from their respective stacks)
- perform an operation with those values (e.g. 4 + 3)
- push the result onto the value stack
- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
*/

/* - - - - - - - - - - - - - - - - - - - - HOW DOES THIS PROGRAM WORK - - - - - - - - - - - - - - - - - - - - - -
I haven't added exceptions or restricted input for entering UNBALANCED parentheses or incorrect expressions (yet)
- example dividing with 0 and similar or putting ( 2+5 - (1-5) ). You have to use correct expressions for it to work.
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
        Stack<Integer> numbers = new Stack<Integer>();

        String[] array = new String[expression.length()];
        for(int i = 0; i < expression.length(); i++){
            array[i] = String.valueOf(expression.charAt(i));
        }

        int i = 0;
        int currentNum1, currentNum2;
        String test;

        while(i < expression.length()){
            if(array[i].equals(" ") || array[i].equals("(")){
                i++;
                continue;
            }

            if(array[i].equals(")")){
                currentNum2 = numbers.get(numbers.size() - 1);
                numbers.pop();
                currentNum1 = numbers.get(numbers.size() - 1);
                numbers.pop();

                if(operators.get(operators.size() - 1).equals("+")) {
                    numbers.push(currentNum1 + currentNum2);
                    operators.pop();
                } else if(operators.get(operators.size() - 1).equals("-")) {
                    numbers.push(currentNum1 - currentNum2);
                    operators.pop();
                } else if(operators.get(operators.size() - 1).equals("*")) {
                    numbers.push(currentNum1 * currentNum2);
                    operators.pop();
                } else if(operators.get(operators.size() - 1).equals("/")) {
                    numbers.push(currentNum1 / currentNum2);
                    operators.pop();
                }
                i++;
                continue;
            }

            try{
                numbers.push(Integer.parseInt(array[i]));
            } catch(NumberFormatException e){
                operators.push(array[i]);
            }
            i++;
        }

        int sum = 0;
        int j = 0;
        while(j < numbers.size()){
            sum += numbers.get(j);
            j++;
        }

        return sum;
    }

    public static void main(String[] args) {
        System.out.println(calculate("((7-6)*(2+1))+(9/3)")); //result: 6
        System.out.println(calculate("( ( 5 + ( 3 * 8 ) ) - ( 2 * 7 ) )")); //result: 15
        System.out.println(calculate("( ( ( 5 + ( 3 * 8 ) ) - ( 2 * 7 ) ) - 5 )")); //result: 10

    }
}
