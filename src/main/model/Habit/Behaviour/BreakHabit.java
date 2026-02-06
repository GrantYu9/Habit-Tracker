package model.Habit.Behaviour;

/*
!!!
 */
public class BreakHabit extends Habit{
    /*
    private String unit; // Type of units. E.g. mL or steps
    private int goal; // A goal amount. E.g. 500 mL or 10,000 steps
    private int amount; // Current amount. E.g. 100 mL or 1,000 steps
    private int progressPercentage; // Amount of progress made towards a goal
    private ViewMode viewMode; // !!!
    private enum ViewMode {
        BAR,
        HEATMAP
    } // !!!
     */
    private ProgressType progressType;
    private enum ProgressType {
        UNDERDONE,
        DONE
    }
   
    /*
    !!!
     */
    public void breakHabit() {
        // !!!
    }

    /*
    public abstract void calculateProgressPercentage(int amount, int goal);
    public abstract void addToHistory(int progressPercentage);

    public abstract void setGoal(int goal);
    public abstract void setAmount(int amount);
     */

    // !!!
}
