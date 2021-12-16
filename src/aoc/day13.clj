(ns aoc.day13
  (:require [aoc.core :as aoc]
            clojure.string))
(defn load
  [lines]
  (letfn [(->coors [s]
            (as-> s s
              (clojure.string/split s #"," 2)
              (mapv #(Integer/parseInt %) s)))
          (->fold [s]
            (let [[_ dir count] (re-find #"(\w{1})=(\d+)" s)]
              {:axis (keyword dir) :at (Integer/parseInt count)}))]
    (let [coord-lines (filter #(clojure.string/includes? % ",") lines)
          folds       (filter #(clojure.string/includes? % "fold along") lines)]
      {:coords (set (map ->coors coord-lines))
       :folds  (map ->fold folds)})))


(defn fold-y
  [coords fold]
  (let [coords-folding   (filter #(> (second %) (:at fold)) coords)
        coords-remaining (clojure.set/difference coords (set coords-folding))
        projected        (map (fn [[x y]]
                                [x (Math/abs (- y (* 2 (:at fold))))]) coords-folding)
        folded           (concat coords-remaining projected)]
    (set folded)))


(defn fold-x
  [coords fold]
  (let [coords           (filter #(not= (:at fold) (first %)) coords)
        coords-folding   (filter #(> (first %) (:at fold)) coords)
        coords-remaining (clojure.set/difference (set coords) (set coords-folding))
        projected        (map (fn [[x y]]
                                [(Math/abs (- x (* 2 (:at fold)))) y]) coords-folding)
        folded           (concat coords-remaining projected)]
    (set folded)))

(defmulti fold (fn [coords fold] (:axis fold)))

(defmethod fold :x [coords fold]
  (fold-x coords fold))

(defmethod fold :y [coords fold]
  (fold-y coords fold))
