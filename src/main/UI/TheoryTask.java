public class TheoryTask {
    private String question;
    private String correctAnswer;
    private String[] keywords;
    private String explanation;


    public TheoryTask(String question, String correctAnswer, String[] keywords, String explanation) {
        this.correctAnswer = correctAnswer;
        this.keywords = keywords;
        this.question = question;
        this.explanation = explanation;
    }

    public String getQuestion() {return  question; }
    public String getCorrectAnswer() {return correctAnswer; }
    public String[] getKeywords() {return keywords; }
    public String getExplanation() {return  explanation; }
}
