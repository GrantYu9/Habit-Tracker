package ui.viewingperspectives;

// !!!
public class Heatmap {
    private int timeDuration; // !!!
    private TimeDurationType timeDurationType; // !!!
    public enum TimeDurationType {
        DAY,
        WEEK,
        MONTH,
        YEAR
    } // !!!

    // !!!
    public void setTimeUnitCount(int timeDuration) {
        this.timeDuration = timeDuration;
    }

    public void setTimeDuration(TimeDurationType timeDurationType) {
        this.timeDurationType = timeDurationType;
    }
}
