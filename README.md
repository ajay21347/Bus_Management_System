\# Bus Management System (Java)



A Java-based application to find bus routes using stop names, display all possible paths,

highlight the optimal route, and show bus \& driver details using a Swing GUI.



\## Key Features

\- Search routes using stop names (no IDs)

\- Auto-suggest stops while typing (TST)

\- Displays all possible routes

\- Routes sorted by travel time

\- Shortest route highlighted

\- Time shown in HH : MM format

\- Bus \& driver information

\- Route filters (All / Fastest / Least Stops)

\- Export results to CSV



\## Technologies Used

\- Java (JDK 17 / 21)

\- Java Swing

\- Data Structures: Graph, DFS, Ternary Search Tree

\- File-based data (GTFS-style)

\- Git \& GitHub



\## Project Structure

src/

&nbsp;├── main/

&nbsp;├── ui/

&nbsp;├── service/

&nbsp;├── model/

&nbsp;├── algorithm/

&nbsp;├── util/

&nbsp;└── data/



\## How to Run



javac -d out -sourcepath src src/main/App.java

java -cp out main.App



\## Algorithms Used

\- DFS to enumerate all possible routes

\- Graph traversal using transfer times

\- Ternary Search Tree for auto-suggest



Ajay Bhandari

Java | DSA 



