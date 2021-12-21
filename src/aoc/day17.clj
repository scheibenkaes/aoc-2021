(ns aoc.day17
  (:require [aoc.core :as aoc]))

(defn load-target-area
  ""
  [s]
  (let [area    (clojure.string/replace-first s #"target area: " "")
        [_ x y] (re-find #"x=(.*), y=(.*)" area)
        f       (fn [s]
                  (as-> s s
                    (clojure.string/split s #"\.\.")
                    (mapv #(Integer/parseInt %) s)))]
    [(f x) (f y)]))

(defn ->probe
  [[x-vel y-vel]]
  {:position [0 0]
   :x-vel    x-vel
   :y-vel    y-vel})

(defn in-target-area?
  [[[x-min x-max] [y-min y-max]] [x y]]
  (and (<= x-min x x-max)
       (<= y-min y y-max)))

(defn overshot?
  [[[_ x-max] [y-min _]] [x y]]
  (or (> x x-max)
      (< y y-min)))

(defn trajectory
  [{:keys [position x-vel y-vel] :as probe}]
  (let [[x y]  position
        x*     (+ x x-vel)
        y*     (+ y y-vel)
        x-vel* (cond
                 (zero? x-vel) 0
                 (pos? x-vel)  (dec x-vel)
                 :else         (inc x-vel))
        y-vel* (dec y-vel)]
    (assoc probe
           :position [x* y*]
           :x-vel x-vel*
           :y-vel y-vel*)))

(defn on-track-hof
  [area]
  (fn [probe]
    (not (overshot? area (:position probe)))))

(defn calc-path
  [target-area probe]
  (let [on-track (on-track-hof target-area)]
    (take-while on-track (iterate trajectory probe))))
