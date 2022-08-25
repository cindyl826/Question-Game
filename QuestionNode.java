// The QuestionNode class is used to store information for 
// the questions/answers in the guessing game

public class QuestionNode {
    public String text;
    public QuestionNode yes;
    public QuestionNode no;
    
    /*
     * Constructs an answer node that would be a leaf node
     * @param text - the given text that would be passed in
     */
    public QuestionNode(String text) {
        this(text, null, null);
    }
    
    /*
     * Constructs a question node that has left and right subtree
     * @param text - the given text that would be passed in
     * @param yes - the left subtree
     * @param no - the right subtree
     */
    public QuestionNode(String text, QuestionNode yes, QuestionNode no) {
        this.text = text;
        this.yes = yes;
        this.no = no;
    }
}
