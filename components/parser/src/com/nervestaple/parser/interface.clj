(ns com.nervestaple.parser.interface
  (:require [com.nervestaple.parser.core :as core]))

(defn ->sql [text]
  (core/->sql text))

(defn ->str [sql]
  (core/->str sql))

(defn and [sql expression]
  (core/append-and sql expression))
