(ns aoc.day9
  (:require [aoc.core :as aoc]))

(defn load-from-lines
  ""
  [lines]
  (->> lines
       (filter (complement empty?))
       (mapv (fn [line] (mapv #(Integer/parseInt (str %)) line)))))

(defn load-from-string [s]
  (->> s
       (clojure.string/split-lines)
       load-from-lines))

(defn low-point-at [hmap [x y]]
  (let [self          (get-in hmap [y x])
        top-n         (get-in hmap [(dec y) x])
        bottom-n      (get-in hmap [(inc y) x])
        left-n        (get-in hmap [y (dec x)])
        right-n       (get-in hmap [y (inc x)])
        neighbor-vals (filter (complement nil?) (set [top-n bottom-n left-n right-n]))
        low-point     (apply < self (sort neighbor-vals))]
    {:self      self
     :top-n     top-n
     :bottom-n  bottom-n
     :left-n    left-n
     :right-n   right-n
     :low-point low-point
     :risk      (when low-point (inc self))}))

(defn calc-low-points
  ""
  [hmap]
  (for [y (range (count hmap))
        x (range (count (first hmap)))]
    (low-point-at hmap [x y])))

(defn risk-level
  [hmap]
  (->> hmap
       (filter :risk)
       (map :risk)
       (apply +)))
