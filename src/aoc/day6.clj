(ns aoc.day6
  (:require [aoc.core :as aoc]))

(defn ->fish
  ([] (->fish 8))
  ([timer]
   {:timer timer}))

(defn spawn
  []
  [(->fish 6) (->fish)])

(defn day
  [{timer :timer :as fish}]
  (let [timer* (dec timer)]
    (if (neg? timer*) (spawn)
        (assoc fish :timer timer*))))

(defn days
  [fishes num-days]
  (let [fishes* (->> fishes
                     (map day)
                     flatten)
        num-days* (dec num-days)]
    (if (zero? num-days*)
      fishes*
      (recur fishes* num-days*))))
