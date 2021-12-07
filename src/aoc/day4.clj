(ns aoc.day4
  (:require [aoc.core :as aoc]))

(defn ->board
  ^{:pre [(= 25 (count nums))]}
  [nums]
  {:numbers         nums
   :type            ::board
   :already-checked #{}})

(defn load-board [data]
  (let [[nums-drawn & boards] (filter (complement empty?) data)
        nums-drawn            (read-string (str "[" nums-drawn "]"))
        boards                (read-string (str "[" (clojure.string/join " " boards) "]"))
        boards                (partition-all 25 boards)]
    [nums-drawn (map ->board boards)]))
