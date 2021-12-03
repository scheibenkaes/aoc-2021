(ns aoc.day3
  (:require [aoc.core :as aoc]))

(defn occurences
  [line]
  (reduce (fn [acc c] (update-in acc [c] inc)) {\0 0 \1 0} line))

(defn calc-rate
  [rate line]
  (->> line
       occurences
       (sort-by val rate)
       ffirst))

(def epsilon (partial calc-rate <))

(def gamma (partial calc-rate >=))

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

;; pt 2

(defn oxygen
  ([lines] (oxygen lines 0))
  ([lines bit-pos]
   (let [fst (take-bit-at-pos lines bit-pos)
         sig (gamma fst)
         w1  (filter #(= (get % bit-pos) sig) lines)]
     (if (= (count w1) 1)
       (Integer/parseInt (first w1) 2)
       (recur w1 (inc bit-pos))))))

(defn co2
  ([lines] (co2 lines 0))
  ([lines bit-pos]
   (let [fst (take-bit-at-pos lines bit-pos)
         sig (epsilon fst)
         w1  (filter #(= (get % bit-pos) sig) lines)]
     (if (= (count w1) 1)
       (Integer/parseInt (first w1) 2)
       (recur w1 (inc bit-pos))))))

(defn solve
  [nums]
  (let [bits    (to-nth-bits nums)
        eps     (-> (map epsilon bits)
                    (char-seq->int))
        gm      (-> (map gamma bits)
                    (char-seq->int))
        oxygen* (oxygen nums)
        co2*    (co2 nums)]
    {:epsilon      eps
     :gamma        gm
     :bits         bits
     :mult         (* eps gm)
     :life-support (* co2* oxygen*)}))

(defn run-on-test-data!
  []
  (let [lines (aoc/load-input 3)]
    (solve lines)))
