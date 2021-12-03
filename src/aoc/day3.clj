(ns aoc.day3
  (:require [aoc.core :as aoc]))

(defn calc-rate
  [rate line]
  (->> line
       (reduce (fn [acc c] (update-in acc [c] inc)) {\0 0 \1 0})
       (sort-by val rate)
       ffirst))

(def epsilon (partial calc-rate <))

(def gamma (partial calc-rate >))

(defn take-bit-at-pos
  [lines pos]
  (as-> lines s
    (map #(get % pos) s)
    (clojure.string/join s)))

(defn to-nth-bits
  [lines]
  (for [i (range (-> lines first count))]
    (take-bit-at-pos lines i)))

(defn char-seq->int
  [cs]
  (as-> cs cs
    (clojure.string/join cs)
    (Integer/parseInt cs 2)))

(defn solve
  [nums]
  (let [bits (to-nth-bits nums)
        eps  (-> (map epsilon bits)
                 (char-seq->int))
        gm   (-> (map gamma bits)
                 (char-seq->int))]
    {:epsilon eps
     :gamma   gm
     :bits    bits
     :mult    (* eps gm)}))

(defn run-on-test-data!
  []
  (let [lines (aoc/load-input 3)]
    (solve lines)))
