(ns aoc.day1
  (:require [aoc.core :as aoc]))

(defn solve
  [scans]
  (reduce (fn [acc n]
            (if (empty? acc)
              [[n nil]]
              (let [[n-prev _] (last acc)]
                (if (> n-prev n)
                  (conj acc [n :decreased])
                  (conj acc [n :increased]))))) [] scans))

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
