(ns aoc.day9
  (:require [aoc.core :as aoc]
            clojure.set))

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
     :risk      (when low-point (inc self))
     :x-y       [x y]}))

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

(defn low-points [hmap]
  (filter :low-point hmap))


(defn to-the-right
  [[x y]]
  [(inc x) y])

(defn to-the-left
  [[x y]]
  [(dec x) y])

(defn to-the-top
  [[x y]]
  [x (dec y)])

(defn to-the-bottom
  [[x y]]
  [x (inc y)])

(defn at
  [hmap [x y]]
  (get-in hmap [y x]))

(defn no-basin-value?
  [value]
  (or (= 9 value)
      (nil? value)))

(defn basin
  [hmap result-set point & {:keys [coming-from] :or {coming-from nil}}]
  (let [[x y]           point
        value           (at hmap point)
        already-visited (get result-set point nil)]
    (if (or (no-basin-value? value)
            already-visited)
      result-set
      (let [right      (to-the-right [x y])
            left       (to-the-left [x y])
            top        (to-the-top [x y])
            bottom     (to-the-bottom [x y])
            follow-dir #(and (not= nil (at hmap %1))
                             (not= coming-from %2))]
        (clojure.set/union

         (when (follow-dir right :right)
           (basin hmap (conj result-set point) right :coming-from :left))
         (when (follow-dir left :left)
          (basin hmap (conj result-set point) left :coming-from :right))
         (when (follow-dir top :top)
           (basin hmap (conj result-set point) top :coming-from :bottom))
         (when (follow-dir bottom :bottom)
           (basin hmap (conj result-set point) bottom :coming-from :top)))))))
