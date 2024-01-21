# SQLish

While re-writing another complicated SQL query into yet another wacky query builder tool (it may have been [PyPika][pypika] or maybe it was [Honey SQL][honeysql] or [jOOQ][jooq]) I started to wonder to myself...

> Why do I need to re-write my perfectly good query? Am I really doing it just for my tool? And, now that I think about it, who is the tool in this scenario?

Yes, perhaps it is me. I can't blame anyone else for this process where I write a query and then re-write it again, painstakingly testing as I go to make sure it works the same as my SQL query. Wouldn't it be nice if I could write the query once in SQL and then be done with it?

That's the goal of this project: you write regular SQL and SQLish will parse it and figure out how it works. Later on, when you need to, you can add more clauses to make it do what you need it to do. We promise, we won't make you port your SQL to another kind-of-like-SQL data structure! Well, maybe just a little bit.

## A Simple Example to Get You Thinking

For instance, say you have a query that returns all of the science fiction books in your library. You write that query in SQL and then you feed it to SQLish where it does all of the hard work of parsing it out into it's own data structure.

```clojure
(def scifi-query (sql/->sql "select * from books where category = 'scifi'"))
```

Your query has now been parsed into it's various components such that the tool can manipulate it as you might need. Which is good because now you have some code where you want just the books that end with "Phlebas". It's not for us to ask why, simply to provide the how: add more SQL!

```clojure
(defn scifi-title-like [text]
  (sql/and scifi-query (format "title like '%s'" text)))
```

Look, the tool added another "AND" clause to your query. It's nice to have the tool make your work easier for a change, isn't it? 😉 And, Of course, SQLish can turn it back into a query you can provide to your JDBC driver or whatever.
 
```clojure
> (sql/->str (scifi-title-like "%Phlebas")
"SELECT * FROM books WHERE category = 'scifi' AND title LIKE '%Phlebas'"
```

This is just and example, in real life I'm sure the query you'd write would be much more complicated, full of windowing functions and functions and so on. But you get the idea.

## How Does it Work?

The heavy lifting is done by the [JSQLParser][jsqlparser] library; it parses SQL queries into its own data structure and provides methods for manipulating and transforming that data structure. This library is mostly a wrapper around that library, with functions that make it easier to manage SQL code in your Clojure application.


## Hacking on this Project

This project is written in [Clojure][clojure], the project itself is managed with the Polylith tool. More information on Polylith can be found here:

- The [high-level documentation][poly-intro]
- The [poly tool documentation][poly-tool]

----
[pypika]: https://pypika.readthedocs.io/en/latest/
[honeysql]: https://github.com/seancorfield/honeysql
[jooq]: https://www.jooq.org/
[clojure]: https://clojure.org
[poly-intro]: https://polylith.gitbook.io/polylith
[poly-tool]: https://cljdoc.org/d/polylith/clj-poly/CURRENT
[jsqlparser]: https://github.com/JSQLParser/JSqlParser
