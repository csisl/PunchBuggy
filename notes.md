# Notes

Things I've learned from working on this project.

### Files to include on github

You should include gradle files (build.gradle, settings.gradle)
on github so users who clone the project can build it.

Without settings.gradle, android studio will not recognize the *Module*,
which in this case is Punch Buggy. You also need to have the build.gradle
file to tell android studio the build configurations.

Once the files were added and cloned, going to `File > Sync Project with Gradle Files`
made the build work.

## Adapters and ListViews

MutableList -> ArrayAdapter -> ListView

To display a dynamic list you can use an `ArrayAdapter`. I did this when
I decided to display all of the users in the game. Each `Game` object has
a `MutableList` of `Players`.

```kotlin
private var players: MutableList<Player> = mutableListOf()
```

Every time a new player is made inside of the `UserManagment` activity,
they should be added to this ListView.

An ArrayAdapter takes in 3 arguments:
    1. the current class
    2. the layout for hte list
    3. the list of values to display

Once you have an ArrayAdapter and ListView, you can set the ListView's
adapter to the ArrayAdapter that was created.

```kotlin
private lateinit var playerListView: ListView
private lateinit var playerViewAdapter: ArrayAdapter<String>
...
// inside of onCreate:
playerListView = findViewById(R.id.playerListView)
playerViewAdapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, runningGame.getPlayerNames())
playerListView.adapter = playerViewAdapter
```

## Passing objects with Gson