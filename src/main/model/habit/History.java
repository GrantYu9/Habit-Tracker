package model.habit;

import java.util.ArrayList;
import java.util.List;

/*
A record of past habits, frozen in time
May only contain habits of the same title
 */
public class History {
    List<Habit> pastDays;

    // EFFECTS: Initializes pastDays such that it is empty
    public History() {
        pastDays = new ArrayList<>();
    }

    public List<Habit> getPastDays() {
        return pastDays;
    }
}
