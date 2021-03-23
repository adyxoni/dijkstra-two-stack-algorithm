
import java.util.NoSuchElementException;
import java.util.Stack;

public class DijkstraTwoStackAlgorithm {

    class ResizingArrayStack<Item> { //copied from hw1

        private Item[] stack;
        private int length;

        public ResizingArrayStack() {
            stack = (Item[]) new Object[8];
            length = 0;
        }

        public boolean isEmpty() {
            return length == 0;
        }

        public int size() {
            return length;
        }

        private void resize(int capacity) {
            assert capacity >= length;

            Item[] copy = (Item[]) new Object[capacity];
            for (int i = 0; i < length; i++) {
                copy[i] = stack[i];
            }
            stack = copy;
        }

        public void push(Item item) {
            if (length == stack.length) resize(2 * stack.length);
            stack[length++] = item;
        }

        public Item pop() {
            if (isEmpty()) throw new NoSuchElementException("No top element meaning the stack is empty.");
            Item item = stack[length - 1];
            stack[length - 1] = null;
            length--;
            if (length > 0 && length == stack.length / 4) resize(stack.length / 2);
            return item;
        }
    }//hw1

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
    }
}