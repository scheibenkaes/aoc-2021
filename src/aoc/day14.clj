(ns aoc.day14
  (:require [aoc.core :as aoc]))


(defn str->insertion
  ""
  [s]
  (let [[[p1 p2] [ins]] (clojure.string/split s #" -> ")]
    {:p1 p1 :p2 p2 :ins ins}))

(defn insert
  ""
  [{:keys [p1 p2 ins]}]
  (vector p1 ins p2))

(defn trans-seq
  [insertions]
  (fn
    ([] (println "t0") [])
    ([x] (println "t1" x) (vec x))
    ([sequence [p1 p2 :as pair]]
     (println "t2" sequence pair)
     (let [i-fn (get insertions pair)]
       (conj (vec sequence) (insert i-fn))))))

(defn read-input
  [lines]
  (let [[template & insertions] (filter (complement empty?) lines)
        template                (seq template)
        insertions              (map str->insertion insertions)
        insertions              (into {} (map (fn [i]
                                                [[(:p1 i) (:p2 i)] i]) insertions))]
    [template insertions]))

(defn create-insertions
  [template funs]
  (let [pairs   (partition 2 (interleave template (rest template)))
        pair-fn (fn [p] (get funs p))
        res     (map pair-fn pairs)
        res     (map :ins res)]
    res))

(defn apply-insertions
  [template insertions]
  (concat (interleave template insertions) [(last template)]))


(defn step
  ([template insertions]
   (apply-insertions template insertions))
  ([template insertions times]
   ))
