# My Habit Tracker

## Abstract

### What Will It Do?

#### In Paragraph Form

This habit tracker will be able to support a large amount of habits, each with a vast amount of customization such as descriptions, tags, the choice to make it a building or breaking habit, a variety of units such as steps, mL, or hours, and colours to distinguish between habits. Users will be able to view habits individually, as a selection, or all of them at once, both as normal habits and in heatmaps. Normally, habits will look like a bar, where users can interact with them such as setting the habit, incrementing them, decrementing them, restting them, so on and so forth. The user may also view habist as a heatmap, with varying amounts of past time, such as the past month, past week, or yesterday; all view models will include the present day. This will provide an opportunity for the user to freely choose how they with to build or break habits, without concerns on matters that should be trivial, such as, limited habits, unsupported niches of habits, or inability to view progress.

#### In List Form

- Can track a large amount of habits. The upper limit will probably be 100 to prevent performance issues.
- Tags and folders will be provided to organise habits.
- A search function will allow one to quickly search for a habit.
- One can choose to build a habit or break a habit.
- Will support a variety of ways to view time and habits, such as past month, past week, and yesterday, all including today. There will be calendars for each habit and a centralized tab to view the calendars of certain habits.
- Multiple units will be supported. For example, number of steps  to take in a day, mL of water to drink, number of hours studying, number of sweets avoided, so on and so forth.
- Calendars will support a heatmap, where a day will initially be white or grey, and the saturation of a colour will increase in increments up until a goal, where maximum saturation will be achieved. How much the saturation will increase at each step will be determined by the amount as the goal the person sets. This will work the same way if someone is trying to break a habit; a day will be grey, and increase in saturation the more someone tries to break a habit. This is to ensure uniformity between all of a person's habits, so they can associate higher saturation with progression, without having to think about if they are building or breaking a habit.
- Lots of colours will be supported to help visually differentiate habits. Or, a person may choose to set all their habits to the same colour, which will also be a uniquely supported feature, to avoid manually having to assign colours when the user has no interest in different colours.
- Habit overloading will be supported, where if a person goees beyond their goal, a number with a unit will be displayed by how much they went over. This is to motivate those who choose to go beyond their expectations. 

### Who Will Use It?
Anyone who wants to build or break a habit can use it, so the possible demographics will be vast. I expect older teens and beyond, especially those in academia, those working to advance their career, or those who wish to personally develop themselves, will particularly find interest in this application.

### Why Did I Pick This Idea?
I picked this idea because all the habit trackers I have tried thus far have not live up to my standards. Most supported a maximum of 3 habits before one had to pay premium, had clunky mechanics with working with the habits, such as advancing them and trying to reset them, and did not support habit overloading, where one could go beyond their goals. The closest one that lived up my standards was Habitica, but I ended up focusing more on the aspect that it was a game than the purpose behind it, which was to build habits. Thus, I decided to make my own habit tracker, which would be more sterile, in this sense that it would be a plain habit tracker, but it would have lots of features so anyone can fully focus on trying to build habits, as opposed to wasting time setting up an environment.

## User Stories

### Phase 0
- Adding X to Y: As a user, I want to be able to place certain habits in certain habit pages to assist in organizing my habits.
- List all Xs in Y: As a user, I want a default habit page that has all my habits in one place.
- As a user, I want to be able to make a variety of habits, with different incrementing and decrementing behaviour, differing amounts of steps, goals, starting amounts, units, and with unique cycling times.
- As a user, I want to not only be able to meet my habits, but be rewarded for going above and beyond.

### Phase 2
- As a user, I want the choice to save my application to file.
- As a user, I want the choice to load my application from file and update my habits as needed.

### Phase 3
- Display all Xs in Y: as a user, I want to be able to display all habits in a habit page.
- Related action 1: as a user, I want a button to be able to make a habit.
- Related action 2: as a user, I want a button to be able to make a page.
- Load and save: as a user, I want to be able to load and save the state of the application.
- Visual component: as a user, I want to be congratulated for completing a habit with a "good job" image.

### Instructions for End User

#### Viewing All Xs in Y
Click the "Show all habits" button on the bottom left. If you have no habits, it will be blank.

#### Related Action 1: Adding a Habit
To add a habit, click on the "Make habit" button on the left. There are some requirements on the fields:
- All entries must have something in them.
- Goal, starting amount, and step amount: must all be integers.
- Cycle time hour: must be a nonnegative integer below 24.
- Cycle time minute: must be a nonnegative integer below 60.

#### Related Action 2: Adding a Page
To add a page, click on the "Make page" button on the left. It must have something in the title field. It does not require a habit. Habits must be given by their title with exact casing. Separate habits with a comma and a space. E.g. "<habit_1>, <habit_2>, etc."

#### Visual Component
Make a habit, view all habits, click "Progress" until you are done the habit. An image of a thumbs up emoji should pop up.

#### Save & Load
Click the save and load buttons on the top left, respectively.

### Phase 4

#### Task 2 - Sample of Events
Thu Mar 26 23:50:10 PDT 2026
Habit of title "test" added.
Thu Mar 26 23:50:10 PDT 2026
Habit of title "another" added.
Thu Mar 26 23:50:10 PDT 2026
Habit of title "hex" added.
Thu Mar 26 23:50:10 PDT 2026
Habit of title "binary" added.
Thu Mar 26 23:50:10 PDT 2026
Habit of title "INeedMore" added.
Thu Mar 26 23:50:10 PDT 2026
Habit of title "moreeeee" added.
Thu Mar 26 23:50:10 PDT 2026
Habit of title "pushTheLimit" added.
Thu Mar 26 23:50:10 PDT 2026
Habit of title "epsilon" added.
Thu Mar 26 23:50:10 PDT 2026
Habit of title "idk" added.
Thu Mar 26 23:50:10 PDT 2026
Habit of title "whenscroll" added.
Thu Mar 26 23:50:10 PDT 2026
Habit of title "pleaseScroll" added.
Thu Mar 26 23:50:10 PDT 2026
Habit of title "ILoveOpenSets" added.
Thu Mar 26 23:50:10 PDT 2026
Page of title "hmmm" added.

#### Task 3 - Reflection
A general theme would be adding more abstract classes interface to implement off of to reduce duplicated code. For example, making a general "DataManager" interface for all data managers to implement off of, and I can simply override the read and write methods in the interface and still use my private methods in the three of my persistence classes. Another one would be making general PagesPage and HabitsPage interfaces, so that AllGenericPagesPage and AllTagPagesPage could implement the PagesPage interface, and AllHabitsPage, FavouritesPage, HomePage, TagPage, and Page, which would be renamed to GenericPage, could all implement the HabitsPage interface. This would solve a frequent problem I had when testing and building user interfaces, where I had to duplicate a lot of code and handle each case separately, which took up a lot of extra resources and increased the number of opportunities to make mistakes.

Another thing I should have done was make a getter method in HabitGUI to get the Habit it represents, instead of passing it to Bar and Heatmap, as their main responsibility is to render, and the backend information should reside in HabitGUI.

