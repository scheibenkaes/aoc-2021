(ns aoc.core
  (:require [clojure.java.io :as io]))

(defn load-input
  "Load input for a given day."
  [day]
  (-> (str "input" day ".txt")
      (io/resource)
      (io/reader)
      line-seq))

(defn load-input-as-numbers
  "Interpret input as numbers."
  [day]
  (->> day
       load-input
       (map #(Integer/parseInt %))))
