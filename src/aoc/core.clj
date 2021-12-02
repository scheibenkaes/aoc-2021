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


(defn load-input-as-instructions
  [day]
  (->> day
       load-input
       (mapv (fn [line]
               (let [[dir amount] (clojure.string/split line #" ")]
                [(keyword dir) (Integer/parseInt amount)])))))
