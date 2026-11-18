public class PracticeTask {
    private String description; // Текст задачи
    private String correctCode; // Правильный код (например, "double x = 15.0 / 24;")
    private String hint; // Подсказка или объяснение решения

    public PracticeTask(String description, String correctCode, String hint) {
        this.description = description;
        this.correctCode = correctCode;
        this.hint = hint;
    }

    public String getDescription() { return description; }
    public String getCorrectCode() { return correctCode; }
    public String getHint() { return hint; }
}
