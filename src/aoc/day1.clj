(ns aoc.day1
  (:require [aoc.core :as aoc]))

(defn solve
  ""
  [scans]
  (reduce (fn [acc n]
          (if (empty? acc)
            [[n nil]]
            (let [[n-prev _] (last acc)]
              (if (> n-prev n)
                (conj acc [n :decreased])
                (conj acc [n :increased]))))) [] scans))
