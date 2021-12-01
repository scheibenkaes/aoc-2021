(ns aoc.day1
  (:require [aoc.core :as aoc]))

(def test-input [199
                 200
                 208
                 210
                 200
                 207
                 240
                 269
                 260
                 263])

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
