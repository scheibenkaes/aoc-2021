(ns aoc.core
  (:require [clojure.java.io :as io]))

(defn load-input
  "Load input for a given day."
  [day]
  (-> (str "input" day ".txt")
      (io/resource)
      (io/reader)
      line-seq))

(defn load-input-with
  "Load input for a given day and apply a line-transformer to each line."
  [day line-transformer]
  (->> day
       load-input
       (map line-transformer)))

(defn load-input-as-numbers
  "Interpret input as numbers."
  [day]
  (load-input-with day #(Integer/parseInt %)))


(defn load-input-as-instructions
  [day]
  (load-input-with day (fn [line]
                         (let [[dir amount] (clojure.string/split line #" ")]
                           [(keyword dir) (Integer/parseInt amount)]))))

(def inc* (fnil inc 0))

(defn ->int
  ""
  [s]
  (Integer/parseInt s))
