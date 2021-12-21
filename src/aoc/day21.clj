(ns aoc.day21
  (:require [aoc.core :as aoc]))

(defn load-starting-pos
  [[p1 p2]]
  (letfn [(read-pos [l]
            (-> (re-find #"Player [0-9]{1} starting position: (.*)" l)
                second
                aoc/->int))]
    [(read-pos p1) (read-pos p2)]))
