# DIT3-1-MarcVeslino-Act05

1. How did you implement CRUD using SQLite?

- I implemented CRUD operations using a `DatabaseHelper` class that extends `SQLiteOpenHelper`. I also made sure to open and close the database properly to avoid memory leaks.

2. What challenges did you face in maintaining data persistence?

- Some challenges I faced include managing database version changes, handling and closing Cursors properly, converting data types (like timestamps), and making sure data actually saved after closing the app. I also realized database operations should ideally run in the background for better performance.

3. How could you improve performance or UI design in future versions?

- To improve performance, I could use coroutines for database operations, add indexing, and use ViewModel with LiveData for reactive updates. For UI improvements, I plan to add search and sorting features, note categories, dark mode, and swipe-to-delete. I could also add extra features like note sharing, reminders, and cloud sync.
