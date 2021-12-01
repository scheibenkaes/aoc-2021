(ns aoc.day1
  (:require [aoc.core :as aoc]))

(defn solve
  [scans]
  (reduce (fn [acc n]
            (if (empty? acc)
              [[n nil]]
              (let [[n-prev _] (last acc)
                    change     (cond
                                 (> n-prev n) :decreased
                                 (< n-prev n) :increased
                                 (= n-prev n) :no-change)]
                (conj acc [n change])))) [] scans))

(defn intermediates-to-result
  [intermediates]
  (->> intermediates
       (map second)
       (filter #(= :increased %))
       (count)))

(defn run-on-test-file!
  []
  (->> 1
       aoc/load-input-as-numbers
       solve
       intermediates-to-result))

;; ----------------- pt 2

(defn pt2
  [scans]
  (let [t1 scans
        t2 (drop 1 t1)
        t3 (drop 1 t2)]
  (->> (partition 3 (interleave t1 t2 t3))
       (map #(apply + %))
       (solve))))

(defn run-pt2!
  []
  (->> 1
       aoc/load-input-as-numbers
       pt2
       intermediates-to-result))
