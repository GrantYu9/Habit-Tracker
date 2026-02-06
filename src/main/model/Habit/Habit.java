package model.Habit;

public class Habit {
    private String unit;
    private int goal;
    private int amount;
    private Progress progress;
    private enum Progress {
        UNDERDONE,
        DONE,
        OVERLOADED
    }

    // REQUIRES: unit must contain at least one alphabetical character
    // goal > 0
    // amount > 0
    // EFFECTS: instantiates an instance of Habit with
    // this.unit as unit
    // this.goal as goal
    // this.amount as amount
    // this.progress as UNDERDONE
    public void habit(String unit, int goal, int amount) {
        this.unit = unit;
        this.goal = goal;
        this.amount = amount;
        this.progress = Progress.UNDERDONE;
    }

    // saturation percentage !!!
    // delete !!!

    // MODIFIES: this
    // EFFECTS: increments this.amount by 1
    // if this.amount = this.goal, set progress = Progress.DONE
    // if this.amount > this.goal, set progress = Progress.OVERLOADED
    public void incrementHabit() {
        // !!!
    }

    // REQUIRES: unit must contain at least one alphabetical character
    // MODIFIES: this
    // EFFECTS: updates this.unit to unit
    public void setUnit(String unit) {
        this.unit = unit;
    }

    // REQUIRES: goal > 0
    // MODIFIES: this
    // EFFECTS: updates this.goal to goal
    public void setGoal(int goal) {
        this.goal = goal;
    }

    // REQUIRES: amount > 0
    // MODIFIES: this
    // EFFECTS: updates this.amount to goal
    public void setAmount(int amount) {
        this.amount = amount;
    }

    public void setProgress(Progress progress) {
        this.progress = progress;
    }

    public String getUnit() {
        return this.unit;
    }

    public int getGoal() {
        return this.goal;
    }

    public int getAmount() {
        return this.amount;
    }

    public Progress getProgress() {
        return this.progress;
    }
}
