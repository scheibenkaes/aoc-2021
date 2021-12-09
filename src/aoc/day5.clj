(ns aoc.day5
  (:require [aoc.core :as aoc]))

(defn ->path
  [[x1 y1 :as p1] [x2 y2 :as p2]]
  (let [orientation (cond
                      (= x1 x2) :vertical
                      (= y1 y2) :horizontal
                      :default  :diagonal)
        x-fn        (cond (> x2 x1) inc
                          (< x2 x1) dec
                          :else     identity)
        y-fn        (cond (> y2 y1) inc
                          (< y2 y1) dec
                          :else     identity)
        itr         (fn [[x y :as p]]
                      [(x-fn x) (y-fn y)])]
    {:path (-> (take-while #(not= % p2) (iterate itr p1))
               (conj p2)
               set)
     :orientation orientation}))

(defn ->line
  [line]
  (let [parts   (clojure.string/split line #"->" 2)
        p-fn    (fn [p] (read-string (str "[" p "]")))
        [v1 v2] (map p-fn parts)
        path    (->path v1 v2)]
    {:start v1
     :end   v2
     :path  path}))

(defn path [line]
  (get-in line [:path :path]))

(defn filter-diagonals [lines]
  (filter (fn [{path :path}] (not= :diagonal (:orientation path))) lines))

(defn ->line-map
  [lines]
  (let [paths         (map path lines)
        heat-map      (reduce (fn [acc path*]
                                (merge-with + acc (zipmap path* (repeat 1)))) {} paths)]
    heat-map))

(defn weigh
  [heat-map]
  (->> heat-map
       (filter (fn [[k v]] (>= v 2)))
       count))
