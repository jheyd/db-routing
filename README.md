# db-routing
## Task
Find a route from Oldenburg to Karlsruhe using the DB API.
## Solution
I sadly was not able to provide a complete solution in this short timespan. There is also quite some room for performance improvements, like running API queries asynchronously and caching their results.

I started by writing exploratory characterization tests for the JSON API and developing my own API wrapper (understanding the Jackson JSON parsing which I hadn't really worked with before was an additional challenge here). 

The main function currently does not find any results which is most likely due to there being no matches in the first 20 arrivals requested from the API. The getArrivalSchedule method would have to be improved so that it finds all arrivals in a given timespan, which would include querying the API several times and eliminating duplicates. This would've taken some more time that I sadly don't have.

The TravelSearch.findRoute method can currently find connections with max 1 train change (plus the limitation stated above). It could probably be extended to find connections with up to 3 train changes by searching recursively with a breadth-first search in the departures of the first-level departures and doing the same for the arrivals of the first-level arrivals. This would involve a lot of API queries and would take quite some time but should still work for 2 or 3 train changes. Since the number of queries increases by a high factor with each level of recursion it would very likely be infeasible for 4 or more train changes.