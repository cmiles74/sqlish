(ns com.nervestaple.parser.core
  (:import (net.sf.jsqlparser.parser CCJSqlParserUtil)
           (net.sf.jsqlparser.util.deparser StatementDeParser)
           (net.sf.jsqlparser.expression.operators.conditional AndExpression)))

(defn ->sql [text]
  (CCJSqlParserUtil/parse text))

(defn ->str [sql]
  (let [builder (StringBuilder.)
        deparser (StatementDeParser. builder)]
    (.visit deparser sql)
    (str builder)))

(defn append-and [sql expression]
  (let [select (.getSelectBody sql)
        where (.getWhere select)
        cond-exp (CCJSqlParserUtil/parseCondExpression expression)]
    (.setWhere sql (AndExpression. where cond-exp))
    sql))
