// Cindy Lin
// CSE143
// TA:Jeremy Lipschutz
// Take-Home Assessment #7
//
// This QuestionGame class represents a guessing game of 20 questions, 
// it would generate yes/no questions for the player to answer, as well
// as storing the questions and answers users inputted, which would 
// be outputted to a file, where the users can also choose to interact with in later rounds.
import java.util.*;
import java.io.*;

public class QuestionsGame {
    private QuestionNode overallRoot;
    private Scanner console;

    // Constructs a new QuestionGame
    public QuestionsGame() {
        overallRoot = new QuestionNode("computer");
        console = new Scanner(System.in);
    }

    /*
     * This method would store the current question or answer to a file,
     * which can be used later in the game for another round
     * @param output - the PrintStream instance used to output the 
                       question/answer into a file          
     */
    public void write(PrintStream output) {
        write(output, overallRoot);
    }

    /* 
     * This helper method that would determine if the current
     * input is a question or answer then store it into a file
     * accordingly based on the standard format
     * @param output - the PrintStream instance used to output
                       the question/answer into a file
     * @param root - the current root(input) that to be determined
                     to stored as question or answer in the file
     */
    private void write(PrintStream output, QuestionNode root) {
        if (root.yes != null || root.no != null) {
            output.println("Q:");
            output.println(root.text);
            write(output, root.yes);
            write(output, root.no);
        } else {
            output.println("A:");
            output.println(root.text);
        }
    }

    /*
     * Read in the input based on the given file
     * to replace the current questions/answers
     * with new questions/answers based on the type of current input
     * @param input - the Scanner instance used to read in a file
     */
    public void read(Scanner input) {
        overallRoot = readHelper(input);
    }

    /*
     * This helper method would be replacing the 
     * the current tree with a new tree based
     * on the given input
     * @param input - the Scanner instance used to read in the file
     */
    private QuestionNode readHelper(Scanner input) {
        String qOrA = input.nextLine();
        String data = input.nextLine();
        if (qOrA.equals("Q:")) {
            return new QuestionNode(data, readHelper(input), readHelper(input));
        } else {
            return new QuestionNode(data);
        }
    }

    /* 
     * Asking users questions until the answer was guessed or if it is 
     * the wrong answer, users would be asked to input a question and an answer
     */
    public void askQuestions() {
        overallRoot = askQuestions(overallRoot);
    }

    /*
     * This helper method would generate many questions based on current tree for the users
     * to answer throughout the game.If the answer was guessed wrong
     * users would input a question and an answer to be replaced to the current tree
     * The game would end once the leaf node(answer) is reached
     * @param currNode - the current node in the tree
     * @return - the current node that got regenerated or created
     */
    private QuestionNode askQuestions(QuestionNode currNode) {
        if (currNode.yes != null || currNode.no != null) { 
             if (yesTo(currNode.text)) {
                currNode.yes = askQuestions(currNode.yes);
            } else {
                currNode.no = askQuestions(currNode.no);
            }          
        } else {
           currNode = modifyTree(currNode);
        }
        return currNode;
    }

    /*
     * This helper method would determine if the current node reached is the answer
     * if not, the user would be asked to input a question and an answer to replace it
     * @param currNode - the current node in the tree
     * @return - the newly create node based on users answer
     */
    private QuestionNode modifyTree(QuestionNode currNode) {
        if (yesTo("Would your object happen to be " + currNode.text + "? ")) {
            System.out.println("Great, I got it right!");
        } else {
            System.out.print("What is the name of your object? ");
            String answer = console.nextLine();
            QuestionNode node = new QuestionNode(answer);
            System.out.println("Please give me a yes/no question that");
            System.out.println("distinguishes between your object");
            System.out.print("and mine--> ");
            String question = console.nextLine();
            if (yesTo("And what is the answer for your object? ")) {
                currNode = new QuestionNode(question, node, currNode);
            } else {
                currNode = new QuestionNode(question, currNode, node);
            }
        } 
        return currNode;
    }

    // Do not modify this method in any way
    // post: asks the user a question, forcing an answer of "y" or "n";
    //       returns true if the answer was yes, returns false otherwise
    public boolean yesTo(String prompt) {
        System.out.print(prompt + " (y/n)? ");
        String response = console.nextLine().trim().toLowerCase();
        while (!response.equals("y") && !response.equals("n")) {
            System.out.println("Please answer y or n.");
            System.out.print(prompt + " (y/n)? ");
            response = console.nextLine().trim().toLowerCase();
        }
        return response.equals("y");
    }
}
